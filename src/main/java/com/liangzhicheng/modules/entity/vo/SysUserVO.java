package com.liangzhicheng.modules.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

/**
 * <p>
 * 账号信息表
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
@Data
@ApiModel(value="SysUserVO")
public class SysUserVO {

    @ApiModelProperty("账号id")
    private String id;
    @ApiModelProperty("部门id")
    private String deptId;
    @ApiModelProperty("部门名称")
    private String deptName;
    @ApiModelProperty("账号名称")
    private String accountName;
    @ApiModelProperty("真实姓名")
    private String truename;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("超级管理员：0否，1是")
    private String isAdmin;
    @ApiModelProperty("登录状态：0冻结，1正常")
    private String loginStatus;


    @ApiModelProperty("角色名称")
    private String roleName;

}
