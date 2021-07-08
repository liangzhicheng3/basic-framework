package com.liangzhicheng.modules.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

/**
 * <p>
 * 角色信息表
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
@Data
@ApiModel(value="SysRoleVO")
public class SysRoleVO {

    @ApiModelProperty("角色id")
    private String id;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("职务描述")
    private String description;
    @ApiModelProperty("部门id")
    private String deptId;
    @ApiModelProperty("部门名称")
    private String deptName;

}
