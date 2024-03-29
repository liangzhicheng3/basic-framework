package com.liangzhicheng.modules.controller.server;

import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.basic.ResponseResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.config.mvc.interceptor.annotation.LoginValidate;
import com.liangzhicheng.modules.entity.dto.SysUserDTO;
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

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperationSupport(ignoreParameters = {"userDTO.companyId", "userDTO.deptId",
            "userDTO.roleIds", "userDTO.keyword", "userDTO.id", "userDTO.truename",
            "userDTO.avatar", "userDTO.isAdmin", "userDTO.loginStatus",
            "userDTO.newPassword", "userDTO.page", "userDTO.pageSize"})
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功",
            response = SysUserLoginVO.class)})
    public ResponseResult login(@RequestBody SysUserDTO userDTO,
                                HttpServletRequest request){
        return buildSuccessInfo(sysUserService.login(userDTO, request));
    }

    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
    @LoginValidate
    public ResponseResult logOut(HttpServletRequest request){
        sysUserService.logOut(request);
        return buildSuccessInfo(null);
    }

}

