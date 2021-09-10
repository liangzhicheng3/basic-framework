package com.liangzhicheng.modules.controller.server;

import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.basic.ResponseResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.config.mvc.interceptor.annotation.LoginValidate;
import com.liangzhicheng.modules.entity.dto.SysRoleDTO;
import com.liangzhicheng.modules.entity.vo.SysRoleDescVO;
import com.liangzhicheng.modules.entity.vo.SysRoleVO;
import com.liangzhicheng.modules.service.ISysRoleService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description 【服务端】角色相关控制器
 * @author liangzhicheng
 * @since 2021-07-03
 */
@Api(value="Server-RoleServerController", tags={"【服务端】角色相关控制器"})
@RestController
@RequestMapping("/server/roleServerController")
public class RoleServerController extends BaseController {

    @Resource
    private ISysRoleService roleService;

    @ApiOperation(value = "角色管理")
    @PostMapping(value = "/listRole")
    @ApiOperationSupport(ignoreParameters = {"roleDTO.id", "roleDTO.name",
            "roleDTO.description", "roleDTO.menuIds", "roleDTO.permIds"})
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功",
            response = SysRoleVO.class)})
//    @LoginValidate
    public ResponseResult listRole(@RequestBody SysRoleDTO roleDTO){
        return buildSuccessInfo(roleService.listRole(roleDTO));
    }

    @ApiOperation(value = "获取角色")
    @PostMapping(value = "/getRole")
    @ApiOperationSupport(ignoreParameters = {"roleDTO.keyword", "roleDTO.dateStart",
            "roleDTO.dateEnd", "roleDTO.name", "roleDTO.description", "roleDTO.menuIds",
            "roleDTO.permIds", "roleDTO.page", "roleDTO.pageSize"})
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功",
            response = SysRoleDescVO.class)})
    @LoginValidate
    public ResponseResult getRole(@RequestBody SysRoleDTO roleDTO){
        return buildSuccessInfo(roleService.getRole(roleDTO));
    }

    @ApiOperation(value = "保存角色")
    @PostMapping(value = "/saveRole")
    @ApiOperationSupport(ignoreParameters = {"roleDTO.keyword",
            "roleDTO.dateStart", "roleDTO.dateEnd",
            "roleDTO.page", "roleDTO.pageSize"})
    @LoginValidate
    public ResponseResult saveRole(@RequestBody SysRoleDTO roleDTO){
        roleService.saveRole(roleDTO);
        return buildSuccessInfo(null);
    }

    @ApiOperation(value = "删除角色")
    @PostMapping(value = "/deleteRole")
    @ApiOperationSupport(ignoreParameters = {"roleDTO.keyword",
            "roleDTO.dateStart", "roleDTO.dateEnd", "roleDTO.name",
            "roleDTO.description", "roleDTO.menuIds",
            "roleDTO.permIds", "roleDTO.page", "roleDTO.pageSize"})
    @LoginValidate
    public ResponseResult deleteRole(@RequestBody SysRoleDTO roleDTO){
        roleService.deleteRole(roleDTO);
        return buildSuccessInfo(null);
    }

}
