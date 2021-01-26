package com.liangzhicheng.modules.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description 【微信】登录相关数据传输对象
 * @author liangzhicheng
 * @since 2021-01-26
 */
@Data
public class TestLoginWeChatDto {

    @ApiModelProperty(value = "微信code", required = true)
    private String code;

    @ApiModelProperty(value = "用户信息密文", required = true)
    private String encryptedData;

    @ApiModelProperty(value = "解密算法初始向量", required = true)
    private String iv;

}
