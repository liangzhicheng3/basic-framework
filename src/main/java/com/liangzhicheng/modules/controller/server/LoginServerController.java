package com.liangzhicheng.modules.controller.server;

import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.basic.WebResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.config.mvc.interceptor.annotation.LoginValidate;
import com.liangzhicheng.modules.entity.dto.SysUserDTO;
import com.liangzhicheng.modules.entity.dto.TestLoginServerDTO;
import com.liangzhicheng.modules.entity.vo.SysUserLoginVO;
import com.liangzhicheng.modules.service.ISysUserService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description 登录相关控制器-服务端
 * @author liangzhicheng
 * @since 2020-08-06
 */
@Api(value="Server-LoginServerController", tags={"【服务端】登录相关控制器"})
@RestController
@RequestMapping("/server/loginServerController")
public class LoginServerController extends BaseController {

    @Resource
    private ISysUserService sysUserService;

    @ApiOperation(value = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperationSupport(ignoreParameters = {"dto.accountId", "dto.newPassword"})
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult register(@RequestBody TestLoginServerDTO dto){
        String username = dto.getUsername();
        String password = dto.getPassword();
        if(SysToolUtil.isBlank(username, password)){
            return buildFailedInfo(ApiConstant.PARAM_IS_NULL);
        }
        //判断短信验证码是否存在，是否与传过来的vcode相等
        //判断username是否存在
        //保存用户信息
        return buildSuccessInfo(null);
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperationSupport(ignoreParameters = {"userDTO.page", "userDTO.pageSize"})
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功",
            response = SysUserLoginVO.class)})
    public WebResult login(@RequestBody SysUserDTO userDTO,
                           HttpServletRequest request){
        return buildSuccessInfo(sysUserService.login(userDTO, request));
    }

    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
    @LoginValidate
    public WebResult logOut(HttpServletRequest request){
        sysUserService.logOut(request);
        return buildSuccessInfo(null);
    }

}

