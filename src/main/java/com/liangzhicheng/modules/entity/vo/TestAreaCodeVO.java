package com.liangzhicheng.modules.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 地区编码信息表
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-03-05
 */
@Data
@ApiModel(value="TestAreaCodeVO")
public class TestAreaCodeVO {

    @ApiModelProperty("地区id")
    private String areaId;

    @ApiModelProperty("地区编码")
    private String areaCode;

    @ApiModelProperty("地区层级")
    private Integer areaLevel;

}
