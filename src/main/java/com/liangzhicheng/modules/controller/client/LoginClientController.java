package com.liangzhicheng.modules.controller.client;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.basic.WebResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.utils.*;
import com.liangzhicheng.config.mvc.interceptor.annotation.LoginValidate;
import com.liangzhicheng.modules.entity.TestAreaNameEntity;
import com.liangzhicheng.modules.entity.dto.TestLoginClientDto;
import com.liangzhicheng.modules.entity.dto.TestLoginWeChatDto;
import com.liangzhicheng.modules.service.ITestAreaNameService;
import io.swagger.annotations.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.*;

/**
 * @description 登录相关控制器-客户端
 * @author liangzhicheng
 * @since 2020-08-06
 */
@Api(value="Client-LoginClientController", tags={"【客户端】登录相关控制器"})
@RestController
@RequestMapping("/client/loginClientController")
public class LoginClientController extends BaseController {

    @Resource
    private ITestAreaNameService areaNameService;

    @ApiOperation(value = "获取短信验证码，不需要token")
    @PostMapping(value = "/sendSMS")
    @ApiOperationSupport(ignoreParameters = {"dto.userId", "dto.email", "dto.vcode"})
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult sendSMS(@RequestBody TestLoginClientDto dto){
        String phone = dto.getPhone();
        if(SysToolUtil.isBlank(phone)){
            return buildFailedInfo(ApiConstant.PARAM_IS_NULL);
        }
        if(!SysToolUtil.isPhone(phone)){
            return buildFailedInfo(ApiConstant.PARAM_PHONE_ERROR);
        }
        String vcode = SysToolUtil.random();
        SysSmsUtil.sendSMS(Constants.SMS_ACCOUNT_SID, Constants.SMS_AUTH_TOKEN, Constants.SMS_APP_ID, Constants.SMS_TEMPLATE_ID, Constants.SMS_URL, phone, vcode);
        SysCacheUtil.set(phone, vcode);
        //测试展示到缓存Map
        SysCacheUtil.hset("SMS_TEST_MAP", phone, vcode);
        SysCacheUtil.expire("SMS_TEST_MAP", 5*60);
        return buildSuccessInfo("发送成功！");
    }

    @ApiOperation(value = "获取邮箱验证码，不需要token")
    @PostMapping(value = "/sendEmailCode")
    @ApiOperationSupport(ignoreParameters = {"dto.userId", "dto.phone", "dto.vcode"})
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult sendEmailCode(@RequestBody TestLoginClientDto dto) {
        String email = dto.getEmail();
        if(SysToolUtil.isBlank(email)){
            //throw new TransactionException(ApiConstant.PARAM_ERROR);
            return buildFailedInfo(ApiConstant.PARAM_ERROR);
        }
        if(!SysToolUtil.isEmail(email)){
            //throw new TransactionException(ApiConstant.EMAIL_FORMAT_ERROR);
            return buildFailedInfo(ApiConstant.PARAM_EMAIL_ERROR);
        }
        String vcode = SysToolUtil.random();
        SysEmailCodeUtil.sendEmailCode(email, vcode);
        SysCacheUtil.set(email, vcode, 5*60);
        return buildSuccessInfo(null);
    }

    @ApiOperation(value = "APP手机号码登录，不需要token")
    @PostMapping(value = "/loginPhone")
    @ApiOperationSupport(ignoreParameters = {"dto.userId", "dto.email"})
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult loginPhone(@RequestBody TestLoginClientDto dto){
        String phone = dto.getPhone();
        String vcode = dto.getVcode();
        if(SysToolUtil.isBlank(phone, vcode)){
            return buildFailedInfo(ApiConstant.PARAM_IS_NULL);
        }
        if(!SysToolUtil.isPhone(phone)){
            return buildFailedInfo(ApiConstant.PARAM_PHONE_ERROR);
        }
        String existVcode = (String) SysCacheUtil.get(phone);
        if(SysToolUtil.isBlank(existVcode) || !existVcode.equals(vcode)){
            return buildFailedInfo(ApiConstant.PARAM_VCODE_ERROR);
        }
        //同一台设备登录推送下线通知 (deviceToken:'设备号 必传',appType:'IOS或ANDROID 必传')
        //XingePush.updateTokenByLogout(user.getUserId(), deviceToken, appType);
        //生成JSON Web Token
        Date expireTime = SysToolUtil.dateAdd(new Date(), 1);
        String token = SysTokenUtil.createTokenAPP("6688", expireTime);
        SysTokenUtil.updateTokenAPP("6688", token);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("user", "user");
        resultMap.put("token", token);
        return buildSuccessInfo(resultMap);
    }

    @ApiOperation(value = "小程序授权登录，不需要token")
    @PostMapping(value = "/loginMINI")
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult loginMINI(@Validated @RequestBody TestLoginWeChatDto dto){
        SysToolUtil.info("--- loginCode come start ///", LoginClientController.class);
        String code = dto.getCode();
        String encryptedData = dto.getEncryptedData();
        String iv = dto.getIv();
        if(SysToolUtil.isBlank(code, encryptedData, iv)){
            return buildFailedInfo(ApiConstant.PARAM_IS_NULL);
        }
        Map<String, Object> miniMap = new HashMap<String, Object>();
        miniMap.put("appid", Constants.WECHAT_MINI_APP_ID);
        miniMap.put("secret", Constants.WECHAT_MINI_APP_SECRET);
        miniMap.put("js_code", code);
        miniMap.put("grant_type", Constants.WECHAT_MINI_GRANT_TYPE);
        JSONObject json = JSON.parseObject(SysToolUtil.sendPost(Constants.WECHAT_MINI_URL, miniMap));
        String sessionKey = json.getString("session_key");
        JSONObject userInfo = this.getUserInfo(sessionKey, encryptedData, iv);
        String avatar = userInfo.getString("avatarUrl");
        String nickName = userInfo.getString("nickName");
        String gender = userInfo.getString("gender");
        String country = userInfo.getString("country");
        String province = userInfo.getString("province");
        String city = userInfo.getString("city");
        /**
         * 保存用户信息 save(user);
         * 1.根据微信授权后返回用户信息获取openId
         * 2.根据openId查询用户信息记录是否存在，不存在则新增
         */
        String openId = json.getString("openid");

        //地区处理
        TestAreaNameEntity entity = new TestAreaNameEntity();
        entity.setCountry(country);
        entity.setProvince(province);
        entity.setCity(city);
        List<Map<String, Object>> resultList = areaNameService.getAreaInfo(entity);
        String areaName = "";
        String areaCode = "";
        if (resultList != null && resultList.size() > 0) {
            areaName = (String) resultList.get(0).get("areaName");
            areaCode = ((String) resultList.get(0).get("areaCode")).substring(5);
//            user.setCountryName(areaName);
//            user.setCountryId(areaCode);
            if (resultList.size() > 1) {
                areaName = (String) resultList.get(1).get("areaName");
                areaCode = ((String) resultList.get(1).get("areaCode")).substring(5);
//                user.setProvinceName(areaName);
//                user.setProvinceId(areaCode);
            }
            if (resultList.size() > 2) {
                areaName = (String) resultList.get(2).get("areaName");
                areaCode = ((String) resultList.get(2).get("areaCode")).substring(5);
//                user.setCityName(areaName);
//                user.setCityId(areaCode);
            }
        }

        //生成JSON Web Token
        Date expireTime = SysToolUtil.dateAdd(new Date(), 1);
        String token = SysTokenUtil.createTokenMINI("6688", expireTime);
        SysTokenUtil.updateTokenMINI("6688", token);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("user", userInfo);
        resultMap.put("token", token);
        return buildSuccessInfo(resultMap);
    }

    @ApiOperation(value = "APP微信登录")
    @PostMapping(value = "/loginAPP")
    @ApiOperationSupport(ignoreParameters = {"dto.encryptedData", "dto.iv"})
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult loginAPP(@Validated @RequestBody TestLoginWeChatDto dto){
        String code = dto.getCode();
        /**
         * 1.根据code获取用户信息
         * 2.根据openId判断用户是否存在，存在直接返回用户信息，不存在新增用户信息
         */
        String result = SysWeChatUtil.getUserInfoByCode(code);
        JSONObject userInfo = JSON.parseObject(result);

        Date expireTime = SysToolUtil.dateAdd(new Date(), 1);
        String token = SysTokenUtil.createTokenAPP("6688", expireTime);
        SysTokenUtil.updateTokenAPP("6688", token);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("user", "user");
        resultMap.put("token", token);
        return buildSuccessInfo(resultMap);
    }

    @LoginValidate
    @ApiOperation(value = "APP退出登录")
    @PostMapping(value = "/logOutAPP")
    @ApiOperationSupport(ignoreParameters = {"dto.phone", "dto.email", "dto.vcode"})
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult logOutAPP(@RequestBody TestLoginClientDto dto){
        String userId = dto.getUserId();
        //判断用户是否存在
        // TODO user
        //清除缓存中账号及设备登录类型
        //XingePush.clearTokenByLogout(userId);
        //XingePush.clearTypeByLogout(userId);
        SysTokenUtil.clearTokenAPP(userId);
        return buildSuccessInfo("退出成功！");
    }

    /**
     * @description 根据会话密钥、加密数据获取用户信息
     * @param sessionKey
     * @param encryptedData
     * @param iv
     * @return JSONObject
     */
    private JSONObject getUserInfo(String sessionKey, String encryptedData, String iv) {
        //加密秘钥
        byte[] keyB = Base64.decode(sessionKey);
        //被加密的数据
        byte[] dataB = Base64.decode(encryptedData);
        //加密算法初始向量
        byte[] ivB = Base64.decode(iv);
        try {
            int base = 16;//密钥不足16位，补足
            if(keyB.length % base != 0){
                int groups = keyB.length / base + (keyB.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyB, 0, temp, 0, keyB.length);
                keyB = temp;
            }
            //初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyB, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivB));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataB);
            if(resultByte != null && resultByte.length > 0){
                String result = new String(resultByte, "UTF-8");
                return JSON.parseObject(result);
            }
        } catch (NoSuchAlgorithmException e) {
            SysToolUtil.info("--- NoSuchAlgorithmException : " + e.getMessage(), LoginClientController.class);
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            SysToolUtil.info("--- NoSuchPaddingException : " + e.getMessage(), LoginClientController.class);
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            SysToolUtil.info("--- InvalidParameterSpecException : " + e.getMessage(), LoginClientController.class);
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            SysToolUtil.info("--- IllegalBlockSizeException : " + e.getMessage(), LoginClientController.class);
            e.printStackTrace();
        } catch (BadPaddingException e) {
            SysToolUtil.info("--- BadPaddingException : " + e.getMessage(), LoginClientController.class);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            SysToolUtil.info("--- UnsupportedEncodingException : " + e.getMessage(), LoginClientController.class);
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            SysToolUtil.info("--- InvalidKeyException : " + e.getMessage(), LoginClientController.class);
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            SysToolUtil.info("--- InvalidAlgorithmParameterException : " + e.getMessage(), LoginClientController.class);
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            SysToolUtil.info("--- NoSuchProviderException : " + e.getMessage(), LoginClientController.class);
            e.printStackTrace();
        }
        return null;
    }

}

