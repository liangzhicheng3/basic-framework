package com.liangzhicheng.modules.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description 地区相关数据传输对象
 * @author liangzhicheng
 * @since 2021-03-05
 */
@Data
public class TestAreaDto extends BaseDto {

    /**
     * areaLevel为0时可以为空
     */
    @ApiModelProperty(value = "地区id")
    private String areaId;

    @ApiModelProperty(value = "地区层级", required = true)
    private String areaLevel;

}
