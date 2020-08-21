package com.liangzhicheng.modules.controller;

import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.basic.WebResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.utils.SysAESUtil;
import com.liangzhicheng.common.utils.SysCacheUtil;
import com.liangzhicheng.common.utils.SysTokenUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.config.mvc.interceptor.annotation.LoginClientValidate;
import com.liangzhicheng.config.mvc.interceptor.annotation.LoginServerValidate;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(value="Api-TestController", description="测试接口（仅供后台调试使用）")
@RestController
@RequestMapping("/api/testApiController")
public class TestApiController extends BaseController implements Constants {

    @ApiOperation(value = "AES加密、解密测试")
    @RequestMapping(value = "/testAES", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult testAES(@ApiParam(name = "sign", value = "把下面所有参数AES签名", required = true) @RequestParam String sign,
                             @ApiParam(name = "userId", value = "用户id") @RequestParam(required = false) String userId,
                             @ApiParam(name = "orderId", value = "订单id") @RequestParam(required = false) String orderId
                             /*BindingResult bindingResult*/){
        userId = SysAESUtil.getParam("userId", sign);
        orderId = SysAESUtil.getParam("orderId", sign);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("orderId", orderId);
        //Map<String, String> map = SysAESUtil.signToMap(sign);
        return buildSuccessInfo(map);
    }

    @ApiOperation(value = "生成token-MINI")
    @RequestMapping(value = "/testCreateTokenMINI", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult testCreateTokenMINI(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId){
        Date expireTime = SysToolUtil.dateAdd(new Date(), 1);
        String token = SysTokenUtil.createTokenMINI(userId, expireTime);
        SysTokenUtil.updateTokenMINI(userId, token);
        return buildSuccessInfo(token);
    }

    @ApiOperation(value = "生成token-APP")
    @RequestMapping(value = "/testCreateTokenAPP", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult testCreateTokenAPP(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId){
        Date expireTime = SysToolUtil.dateAdd(new Date(), 1);
        String token = SysTokenUtil.createTokenMINI(userId, expireTime);
        SysTokenUtil.updateTokenMINI(userId, token);
        return buildSuccessInfo(token);
    }

    @ApiOperation(value = "校验token")
    @RequestMapping(value = "/testVerifyToken", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult testVerifyToken(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
                                     @ApiParam(name = "token", value = "登录密钥校验") @RequestParam(required = true) String token){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("MINI", SysTokenUtil.isLoginMINI(userId, token));
        map.put("APP", SysTokenUtil.isLoginAPP(userId, token));
        return buildSuccessInfo(map);
    }

    @LoginClientValidate
    @ApiOperation(value = "校验是否登录-客户端")
    @RequestMapping(value = "/testIsLoginClient", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult testIsLoginClient(@ApiParam(name = "tokenMINI", value = "登录密钥校验-小程序") @RequestParam(required = false) String tokenMINI,
                                       @ApiParam(name = "tokenAPP", value = "登录密钥校验-APP") @RequestParam(required = false) String tokenAPP,
                                       @ApiParam(name = "userId", value = "用户id") @RequestParam(required = false) String userId){
        return buildSuccessInfo(null);
    }

    @ApiOperation(value = "登录-服务端")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = Boolean.class)})
    public WebResult login(@ApiParam(name = "userId", value = "用户id") @RequestParam(required = false) String userId,
                           HttpServletRequest request){
        request.getSession().setAttribute(LOGIN_USER_ID, userId);
        return buildSuccessInfo("登录成功！");
    }

    @LoginServerValidate
    @ApiOperation(value = "校验是否登录-服务端")
    @RequestMapping(value = "/testIsLoginServer", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult testIsLoginServer(@ApiParam(name = "userId", value = "用户id") @RequestParam(required = false) String userId){
        return buildSuccessInfo(null);
    }

    @ApiOperation(value = "手机验证码测试")
    @RequestMapping(value = "/testSMS", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult testSMS(){
        return buildSuccessInfo(SysCacheUtil.entries("SMS_TEST_MAP"));
    }

    @ApiOperation(value = "获取初始化数据")
    @RequestMapping(value = "/testInitData", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult testInitData(){
        String phone = (String) SysCacheUtil.get(INIT_PHONE_SERVICE);
        String cooperation = (String) SysCacheUtil.get(INIT_COOPERATION_AISLE);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phone", phone);
        map.put("cooperation", cooperation);
        return buildSuccessInfo(map);
    }

}
