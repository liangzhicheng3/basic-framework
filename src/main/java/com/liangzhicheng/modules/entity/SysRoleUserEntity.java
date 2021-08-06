package com.liangzhicheng.modules.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色用户表
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role_user")
public class SysRoleUserEntity extends Model<SysRoleUserEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色用户id(主键)
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
     * 账号id
     */
    private String accountId;

    /**
     * 账号名称
     */
    private String accountName;

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
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public SysRoleUserEntity() {
        super();
    }

    public SysRoleUserEntity(String id, String roleId, String roleName, String accountId, String accountName) {
        this.id = id;
        this.roleId = roleId;
        this.roleName = roleName;
        this.accountId = accountId;
        this.accountName = accountName;
    }

}
