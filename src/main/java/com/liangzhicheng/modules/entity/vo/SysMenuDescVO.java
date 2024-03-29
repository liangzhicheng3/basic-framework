package com.liangzhicheng.modules.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description 菜单详情VO类
 * @author liangzhicheng
 * @since 2021-08-06
 */
@Data
@ApiModel(value="SysMenuDescVO")
public class SysMenuDescVO {

    @ApiModelProperty("菜单id")
    private String id;
    @ApiModelProperty("菜单级别：1一级菜单，2二级菜单，3三级菜单，4四级菜单")
    private String level;
    @ApiModelProperty("父级id")
    private String parentId;
    @ApiModelProperty("菜单名称")
    private String name;
    @ApiModelProperty("父组件")
    private String component;
    @ApiModelProperty("路由路径")
    private String routerPath;
    @ApiModelProperty("路由名称")
    private String routerName;
    @ApiModelProperty("重定向")
    private String redirect;
    @ApiModelProperty("是否隐藏：0显示，1隐藏")
    private String isHide;
    @ApiModelProperty("排序")
    private Integer rank;
    @ApiModelProperty("创建时间")
    private String createDate;
    @ApiModelProperty("权限id")
    private String permId;
    @ApiModelProperty("权限名称")
    private String permName;
    @ApiModelProperty("表达式")
    private String expression;

}
