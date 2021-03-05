package com.liangzhicheng.modules.entity.vo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("TestPersonVO")
public class TestPersonVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("性别：0女 1男")
    private Boolean gender;

    @ApiModelProperty("睡觉时间")
    private LocalTime sleepTime;

    @ApiModelProperty("上班时间")
    private LocalDateTime workTime;

    @ApiModelProperty("出生时间")
    private LocalDate birthday;

    @ApiModelProperty("个人简介")
    private String intro;

}
