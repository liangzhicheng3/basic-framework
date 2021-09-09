package com.liangzhicheng.modules.entity.dto;

import com.liangzhicheng.modules.entity.dto.basic.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TestPersonDTO extends BaseDTO {

    @ApiModelProperty(value = "关键字(昵称，手机号)")
    private String keyword;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "创建时间")
    List<String> createDate;

}
