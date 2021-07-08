package com.liangzhicheng.modules.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description 【服务端】登录相关数据传输对象
 * @author liangzhicheng
 * @since 2021-01-27
 */
@Data
public class TestLoginServerDTO {

    @ApiModelProperty(value = "账号id", required = true)
    private String accountId;

    @ApiModelProperty(value = "账号", required = true)
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

}
