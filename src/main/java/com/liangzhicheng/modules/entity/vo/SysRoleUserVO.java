package com.liangzhicheng.modules.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

/**
 * <p>
 * 角色用户表
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
@Data
@ApiModel(value="SysRoleUserVO")
public class SysRoleUserVO {

    @ApiModelProperty("角色用户id")
    private String id;
    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("账号id")
    private String accountId;
    @ApiModelProperty("账号名称")
    private String accountName;

}
