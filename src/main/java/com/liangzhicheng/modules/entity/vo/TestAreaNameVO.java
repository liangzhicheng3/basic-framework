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

    @ApiModelProperty("语言")
    private String lang;

    @ApiModelProperty("地区名称")
    private String areaName;

    @ApiModelProperty("地区编码")
    private String areaCode;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("是否编辑：0否，1是")
    private String isEdit;

    @ApiModelProperty("备注")
    private String remarks;

}
