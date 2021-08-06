package com.liangzhicheng.modules.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

/**
 * <p>
 * 角色菜单信息表
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-08-06
 */
@Data
@ApiModel(value="SysRoleMenuVO")
public class SysRoleMenuVO {

    @ApiModelProperty("角色菜单id(主键)")
    private String id;
    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("菜单id")
    private String menuId;
    @ApiModelProperty("菜单名称")
    private String menuName;

}
