package com.liangzhicheng.modules.controller.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.basic.WebResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.config.mvc.interceptor.annotation.LoginServerValidate;
import io.swagger.annotations.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @description 登录相关控制器-服务端
 * @author liangzhicheng
 * @since 2020-08-06
 */
@Api(value="Server-LoginServerController", tags={"【服务端】登录相关控制器"})
@RestController
@RequestMapping("/server/loginServerController")
public class LoginServerController extends BaseController {

    @ApiOperation(value = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult register(@ApiParam(name = "param", value = "需要传的参数说明", required = true) @RequestBody @Valid String param,
                              BindingResult bindingResult){
        JSONObject json = JSON.parseObject(param);
        String username = json.getString("username");
        String password = json.getString("password");
        String address = json.getString("address");
        String vcode = json.getString("vcode");
        if(SysToolUtil.isBlank(username, password, address, vcode)){
            return buildFailedInfo(ApiConstant.PARAM_IS_NULL);
        }
        //判断短信验证码是否存在，是否与传过来的vcode相等
        //判断username是否存在
        //保存用户信息
        return buildSuccessInfo(null);
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult login(@ApiParam(name = "param", value = "需要传的参数说明", required = true) @RequestBody @Valid String param,
                           BindingResult bindingResult, HttpServletRequest request){
        JSONObject json = JSON.parseObject(param);
        String account = json.getString("account");
        String password = json.getString("password");
        if(SysToolUtil.isBlank(account, password)){
            return buildFailedInfo(ApiConstant.PARAM_IS_NULL);
        }
        //判断账号是否存在，不存在抛出不存在信息
        //账号存在，获取账号密码与当前传过来的密码进行判断
        request.getSession().setAttribute(Constants.LOGIN_USER_ID, "userId");
        return buildSuccessInfo("登录成功！");
    }

    @LoginServerValidate
    @ApiOperation(value = "重置密码")
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult resetPassword(@ApiParam(name = "param", value = "需要传的参数说明", required = true) @RequestBody @Valid String param,
                                   BindingResult bindingResult){
        JSONObject json = JSON.parseObject(param);
        String userId = json.getString("userId");
        String oldPassword = json.getString("oldPassword");
        String newPassword = json.getString("newPassword");
        if(SysToolUtil.isBlank(userId, oldPassword, newPassword)){
            return buildFailedInfo(ApiConstant.PARAM_IS_NULL);
        }
        if(!oldPassword.equals(/*user.getPassword()*/"")){
            return buildFailedInfo(ApiConstant.PASSWORD_ERROR);
        }
        //重置密码保存
        return buildSuccessInfo("设置成功！");
    }

    @LoginServerValidate
    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult logOut(@ApiParam(name = "param", value = "需要传的参数说明", required = true) @RequestBody @Valid String param,
                            BindingResult bindingResult, HttpServletRequest request){
        JSONObject json = JSON.parseObject(param);
        String userId = json.getString("userId");
        if(SysToolUtil.isBlank(userId)){
            return buildFailedInfo(ApiConstant.PARAM_IS_NULL);
        }
        request.getSession().removeAttribute("userId");
        return buildSuccessInfo("退出成功！");
    }

}

