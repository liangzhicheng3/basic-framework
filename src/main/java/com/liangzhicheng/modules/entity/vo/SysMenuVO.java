package com.liangzhicheng.modules.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

/**
 * <p>
 * 菜单信息表
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
@Data
@ApiModel(value="SysMenuVO")
public class SysMenuVO {

    @ApiModelProperty("菜单id")
    private String id;
    @ApiModelProperty("菜单级别：1一级菜单，2二级菜单")
    private String level;
    @ApiModelProperty("父级id")
    private String parentId;
    @ApiModelProperty("菜单标题")
    private String title;
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

}
