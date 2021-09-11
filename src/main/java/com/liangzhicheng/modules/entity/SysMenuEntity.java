package com.liangzhicheng.modules.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单信息表
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
public class SysMenuEntity extends Model<SysMenuEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id(主键)
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 菜单级别：1一级菜单，2二级菜单，3三级菜单，4四级菜单
     */
    private String level;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父组件
     */
    private String component;

    /**
     * 路由路径
     */
    private String routerPath;

    /**
     * 路由名称
     */
    private String routerName;

    /**
     * 重定向
     */
    private String redirect;

    /**
     * 是否隐藏：0显示，1隐藏
     */
    private String isHide;

    /**
     * 排序
     */
    private Integer rank;

    /**
     * 删除标记-平台：0否，1是
     */
    private String delFlag;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
