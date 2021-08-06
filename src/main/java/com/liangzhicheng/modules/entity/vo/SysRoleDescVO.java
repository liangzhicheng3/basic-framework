package com.liangzhicheng.modules.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description 角色详情VO类
 * @author liangzhicheng
 * @since 2021-08-06
 */
@Data
@ApiModel(value="SysRoleDescVO")
public class SysRoleDescVO {

    @ApiModelProperty("角色id")
    private String id;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("职务描述")
    private String description;
    @ApiModelProperty("菜单id列表")
    private List<String> menuIds;
    @ApiModelProperty("权限id列表")
    private List<String> permIds;

}
