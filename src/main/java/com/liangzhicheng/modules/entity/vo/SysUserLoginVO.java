package com.liangzhicheng.modules.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description 账号登录VO类
 * @author liangzhicheng
 * @since 2021-07-08
 */
@Data
@ApiModel(value="SysUserLoginVO")
public class SysUserLoginVO {

    @ApiModelProperty("账号id")
    private String id;
    @ApiModelProperty("账号名称")
    private String accountName;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("超级管理员：0否，1是")
    private String isAdmin;
    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty("权限列表")
    private List<String> permList;
    @ApiModelProperty("登录token")
    private String token;

}
