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
 * 角色菜单信息表
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role_menu")
public class SysRoleMenuEntity extends Model<SysRoleMenuEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色菜单id(主键)
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

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

    public SysRoleMenuEntity() {
        super();
    }

    public SysRoleMenuEntity(String id, String roleId, String roleName, String menuId, String menuName) {
        this.id = id;
        this.roleId = roleId;
        this.roleName = roleName;
        this.menuId = menuId;
        this.menuName = menuName;
    }

}
