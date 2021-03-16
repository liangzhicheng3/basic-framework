package com.liangzhicheng.modules.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description 【微信】登录相关数据传输对象
 * @author liangzhicheng
 * @since 2021-01-26
 */
@Data
public class TestLoginWeChatDto {

    @ApiModelProperty(value = "微信code", required = true)
    @NotBlank(message = "code字段不能为空")
    private String code;

    @ApiModelProperty(value = "用户信息密文", required = true)
    @NotBlank(message = "encryptedData字段不能为空")
    private String encryptedData;

    @ApiModelProperty(value = "解密算法初始向量", required = true)
    @NotBlank(message = "iv字段不能为空")
    private String iv;

}
