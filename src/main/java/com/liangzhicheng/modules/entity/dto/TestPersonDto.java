package com.liangzhicheng.modules.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TestPersonDto extends BaseDto {

    @ApiModelProperty(value = "关键字(昵称，手机号)")
    private String keyword;

    @ApiModelProperty(value = "点赞数")
    private String praiseNum;

    @ApiModelProperty(value = "开始时间")
    private String dateStart;

    @ApiModelProperty(value = "结束时间")
    private String dateEnd;

}
