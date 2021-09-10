package com.liangzhicheng.modules.controller.server;

import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.basic.ResponseResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.config.mvc.interceptor.annotation.LoginValidate;
import com.liangzhicheng.modules.entity.dto.SysUserDTO;
import com.liangzhicheng.modules.entity.vo.SysPersonInfoVO;
import com.liangzhicheng.modules.entity.vo.SysUserDescVO;
import com.liangzhicheng.modules.entity.vo.SysUserVO;
import com.liangzhicheng.modules.service.ISysUserService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description 【服务端】账号相关控制器
 * @author liangzhicheng
 * @since 2021-08-06
 */
@Api(value="Server-AccountServerController", tags={"【服务端】账号相关控制器"})
@RestController
@RequestMapping("/server/accountServerController")
public class AccountServerController extends BaseController {

    @Resource
    private ISysUserService sysUserService;

    /**
     * @description 更新当前登录用户头像
     * @param userDTO
     * @return ResponseResult
     */
    @ApiOperation(value = "更新头像")
    @PostMapping(value = "/updateAvatar")
    @ApiOperationSupport(ignoreParameters = {"userDTO.companyId", "userDTO.deptId",
            "userDTO.roleIds", "userDTO.keyword", "userDTO.id", "userDTO.accountName",
            "userDTO.truename", "userDTO.password", "userDTO.isAdmin", "userDTO.loginStatus",
            "userDTO.newPassword", "userDTO.page", "userDTO.pageSize"})
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功",
            response = SysPersonInfoVO.class)})
    @LoginValidate
    public ResponseResult updateAvatar(@RequestBody SysUserDTO userDTO, HttpServletRequest request){
        return buildSuccessInfo(sysUserService.updateAvatar(userDTO, request));
    }

    /**
     * @description 更新当前登录用户密码
     * @param userDTO
     * @return ResponseResult
     */
    @ApiOperation(value = "更新密码")
    @PostMapping(value = "/updatePassword")
    @ApiOperationSupport(ignoreParameters = {"userDTO.companyId", "userDTO.deptId",
            "userDTO.roleIds", "userDTO.keyword", "userDTO.id",
            "userDTO.accountName", "userDTO.truename", "userDTO.avatar",
            "userDTO.isAdmin", "userDTO.loginStatus",
            "userDTO.page", "userDTO.pageSize"})
    @LoginValidate
    public ResponseResult updatePassword(@RequestBody SysUserDTO userDTO, HttpServletRequest request){
        sysUserService.updatePassword(userDTO, request);
        return buildSuccessInfo(null);
    }

    @ApiOperation(value = "账号管理")
    @PostMapping(value = "/listAccount")
    @ApiOperationSupport(ignoreParameters = {"userDTO.id",
            "userDTO.accountName", "userDTO.truename", "userDTO.password",
            "userDTO.avatar", "userDTO.isAdmin", "userDTO.newPassword"})
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功",
            response = SysUserVO.class)})
    @LoginValidate
    public ResponseResult listAccount(@RequestBody SysUserDTO userDTO){
        return buildSuccessInfo(sysUserService.listAccount(userDTO));
    }

    @ApiOperation(value = "获取账号")
    @PostMapping(value = "/getAccount")
    @ApiOperationSupport(ignoreParameters = {"userDTO.companyId", "userDTO.deptId",
            "userDTO.roleIds", "userDTO.keyword", "userDTO.accountName", "userDTO.truename",
            "userDTO.password", "userDTO.avatar", "userDTO.isAdmin", "userDTO.loginStatus",
            "userDTO.newPassword", "userDTO.page", "userDTO.pageSize"})
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功",
            response = SysUserDescVO.class)})
    @LoginValidate
    public ResponseResult getAccount(@RequestBody SysUserDTO userDTO){
        return buildSuccessInfo(sysUserService.getAccount(userDTO));
    }

    @ApiOperation(value = "保存账号")
    @PostMapping(value = "/saveAccount")
    @ApiOperationSupport(ignoreParameters = {"userDTO.keyword",
            "userDTO.password", "userDTO.avatar", "userDTO.isAdmin",
            "userDTO.newPassword", "userDTO.page", "userDTO.pageSize"})
    @LoginValidate
    public ResponseResult saveAccount(@RequestBody SysUserDTO userDTO){
        sysUserService.saveAccount(userDTO);
        return buildSuccessInfo(null);
    }

    @ApiOperation(value = "重置密码")
    @PostMapping(value = "/resetPassword")
    @ApiOperationSupport(ignoreParameters = {"userDTO.companyId", "userDTO.deptId",
            "userDTO.roleIds", "userDTO.keyword", "userDTO.accountName",
            "userDTO.truename", "userDTO.password", "userDTO.avatar",
            "userDTO.isAdmin", "userDTO.loginStatus", "userDTO.newPassword",
            "userDTO.page", "userDTO.pageSize"})
    @LoginValidate
    public ResponseResult resetPassword(@RequestBody SysUserDTO userDTO){
        sysUserService.resetPassword(userDTO);
        return buildSuccessInfo(null);
    }

    @ApiOperation(value = "删除账号")
    @PostMapping(value = "/deleteAccount")
    @ApiOperationSupport(ignoreParameters = {"userDTO.companyId", "userDTO.deptId",
            "userDTO.roleIds", "userDTO.keyword", "userDTO.accountName",
            "userDTO.truename", "userDTO.password", "userDTO.avatar",
            "userDTO.isAdmin", "userDTO.loginStatus", "userDTO.newPassword",
            "userDTO.page", "userDTO.pageSize"})
    @LoginValidate
    public ResponseResult deleteAccount(@RequestBody SysUserDTO userDTO){
        sysUserService.deleteAccount(userDTO);
        return buildSuccessInfo(null);
    }

}
