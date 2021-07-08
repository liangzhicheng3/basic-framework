package com.liangzhicheng.modules.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色权限信息表
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
@Data
@ApiModel(value="SysRolePermVO")
public class SysRolePermVO {

    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty("权限id")
    private String permId;
    @ApiModelProperty("权限名称")
    private String permName;
    @ApiModelProperty("表达式")
    private String expression;

}
