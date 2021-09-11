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
 * 权限菜单信息表
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_perm_menu")
public class SysPermMenuEntity extends Model<SysPermMenuEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限菜单id(主键)
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 权限id
     */
    private String permId;

    /**
     * 权限名称
     */
    private String permName;

    /**
     * 菜单id
     */
    private String menuId;

    /**
     * 菜单名称
     */
    private String menuName;

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

    public SysPermMenuEntity() {
        super();
    }

    public SysPermMenuEntity(String id, String permId, String permName, String menuId, String menuName) {
        this.id = id;
        this.permId = permId;
        this.permName = permName;
        this.menuId = menuId;
        this.menuName = menuName;
    }

}
