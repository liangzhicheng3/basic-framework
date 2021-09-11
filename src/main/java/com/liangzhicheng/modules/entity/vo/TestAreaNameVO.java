package com.liangzhicheng.modules.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 地区名称信息表
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-03-05
 */
@Data
@ApiModel(value="TestAreaNameVO")
public class TestAreaNameVO {

    @ApiModelProperty("地区名称")
    private String areaName;
    @ApiModelProperty("地区编码")
    private String areaCode;


    @ApiModelProperty("地区id")
    private String areaId;
    @ApiModelProperty("地区层级")
    private Integer areaLevel;

}
