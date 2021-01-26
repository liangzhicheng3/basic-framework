package com.liangzhicheng.modules.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description 基础参数
 * @author liangzhicheng
 * @since 2021-01-26
 */
@Data
public class BaseParam {

    @ApiModelProperty(value = "页码", required = true)
    private int page;

    @ApiModelProperty(value = "每页数量", required = true)
    private int pageSize;

}
