package com.liangzhicheng.modules.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 四级菜单VO类
 * @author liangzhicheng
 * @since 2021-08-06
 */
@Data
@ApiModel(value="SysMenuTwoVO")
public class SysMenuFourVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单id")
    private String id;
    @ApiModelProperty("菜单级别：1一级菜单，2二级菜单，3三级菜单，4四级菜单")
    private String level;
    @ApiModelProperty("父级id")
    private String parentId;
    @ApiModelProperty("菜单名称")
    private String name;
    @ApiModelProperty("图标")
    private String icon;
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
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @ApiModelProperty("权限id")
    private String permId;
    @ApiModelProperty("表达式")
    private String expression;

}
