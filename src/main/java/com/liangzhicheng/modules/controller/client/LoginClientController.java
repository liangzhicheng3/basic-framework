package com.liangzhicheng.modules.controller.client;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.basic.WebResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.utils.SysCacheUtil;
import com.liangzhicheng.common.utils.SysSmsUtil;
import com.liangzhicheng.common.utils.SysTokenUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.config.mvc.interceptor.annotation.LoginClientValidate;
import io.swagger.annotations.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 登录相关控制器-客户端
 * @author liangzhicheng
 * @since 2020-08-06
 */
@Api(value="Client-LoginClientController", description="登录相关控制器-客户端")
@RestController
@RequestMapping("/client/loginClientController")
public class LoginClientController extends BaseController implements Constants {

    @ApiOperation(value = "小程序授权登录，不需要token")
    @RequestMapping(value = "/loginCode", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult loginCode(@ApiParam(name = "param", value = "需要传的参数说明", required = true) @RequestBody @Valid String param,
                               BindingResult bindingResult
            /*@ApiParam(value = "授权登录的code") @RequestParam(required = true) String code,
            @ApiParam(value = "用户信息密文") @RequestParam(required = true) String encryptedData,
            @ApiParam(value = "解密算法初始向量") @RequestParam(required = true) String iv*/){
        SysToolUtil.info("--- loginCode come start ///", LoginClientController.class);
        JSONObject json = JSON.parseObject(param);
        String code = json.getString("code");
        String encryptedData = json.getString("encryptedData");
        String iv = json.getString("iv");
        Map<String, Object> miniMap = new HashMap<String, Object>();
        miniMap.put("appid", WECHAT_MINI_APP_ID);
        miniMap.put("secret", WECHAT_MINI_APP_SECRET);
        miniMap.put("js_code", code);
        miniMap.put("grant_type", WECHAT_MINI_GRANT_TYPE);
        json = JSON.parseObject(SysToolUtil.post(WECHAT_MINI_URL, miniMap));
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

        //生成JSON Web Token
        Date expireTime = SysToolUtil.dateAdd(new Date(), 1);
        String token = SysTokenUtil.createTokenMINI("6688", expireTime);
        SysTokenUtil.updateTokenMINI("6688", token);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("user", userInfo);
        resultMap.put("token", token);
        return buildSuccessInfo(resultMap);
    }

    @ApiOperation(value = "获取短信验证码，不需要token")
    @RequestMapping(value = "/sendSMS", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult sendSMS(@ApiParam(name = "param", value = "手机号码", required = true) @RequestBody @Valid String param,
                             BindingResult bindingResult){
        JSONObject json = JSON.parseObject(param);
        String phone = json.getString("phone");
        if(SysToolUtil.isBlank(phone)){
            return buildFailedInfo(ApiConstant.PARAM_IS_NULL);
        }
        if(!SysToolUtil.isPhone(phone)){
            return buildFailedInfo(ApiConstant.PARAM_PHONE_ERROR);
        }
        String vcode = SysToolUtil.random().substring(2);
        try{
            SysSmsUtil.sendSMS(SMS_ACCOUNT_SID, SMS_AUTH_TOKEN, SMS_APP_ID, SMS_TEMPLATE_ID, SMS_URL, phone, vcode);
        }catch(Exception e){
            e.printStackTrace();
            return buildFailedInfo(ApiConstant.BASE_FAIL_CODE, "发送失败！");
        }
        SysCacheUtil.set(phone, vcode);
        //测试展示到缓存Map
        SysCacheUtil.hset("SMS_TEST_MAP", phone, vcode);
        SysCacheUtil.expire("SMS_TEST_MAP", 5*60);
        return buildSuccessInfo("发送成功！");
    }

    @ApiOperation(value = "APP手机号码登录，不需要token")
    @RequestMapping(value = "/loginPhone", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult loginPhone(@ApiParam(name = "param", value = "手机号码,短信验证码", required = true) @RequestBody @Valid String param,
                                BindingResult bindingResult){
        JSONObject json = JSON.parseObject(param);
        String phone = json.getString("phone");
        String vcode = json.getString("vcode");
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
        //生成JSON Web Token
        Date expireTime = SysToolUtil.dateAdd(new Date(), 1);
        String token = SysTokenUtil.createTokenAPP("6688", expireTime);
        SysTokenUtil.updateTokenAPP("6688", token);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("user", "user");
        resultMap.put("token", token);
        return buildSuccessInfo(resultMap);
    }

    @ApiOperation(value = "APP微信登录")
    @RequestMapping(value = "/loginWeChat", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult loginWeChat(@ApiParam(name = "param", value = "openId", required = true) @RequestBody @Valid String param,
                                 BindingResult bindingResult){
        JSONObject json = JSON.parseObject(param);
        String openId = json.getString("openId");
        //根据openId判断用户是否存在，存在直接返回用户信息，不存在新增用户信息
        Date expireTime = SysToolUtil.dateAdd(new Date(), 1);
        String token = SysTokenUtil.createTokenAPP("6688", expireTime);
        SysTokenUtil.updateTokenAPP("6688", token);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("user", "user");
        resultMap.put("token", token);
        return buildSuccessInfo(resultMap);
    }

    @LoginClientValidate
    @ApiOperation(value = "APP退出登录")
    @RequestMapping(value = "/logOutAPP", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult logOutAPP(@ApiParam(name = "param", value = "用户id", required = true) @RequestBody @Valid String param,
                               BindingResult bindingResult){
        JSONObject json = JSON.parseObject(param);
        String userId = json.getString("userId");
        //判断用户是否存在
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

