package com.liangzhicheng.modules.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description 【客户端】登录相关数据传输对象
 * @author liangzhicheng
 * @since 2021-01-26
 */
@Data
public class TestLoginClientDTO {

    @ApiModelProperty(value = "用户id", required = true)
    private String userId;

    @ApiModelProperty(value = "手机号码", required = true)
    private String phone;

    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @ApiModelProperty(value = "短信验证码", required = true)
    private String vcode;

}
