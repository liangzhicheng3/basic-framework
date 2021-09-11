package com.liangzhicheng.modules.entity.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

/**
 * <p>
 * 测试部门人员
 * </p>
 *
 * @author liangzhicheng
 * @since 2020-08-04
 */
@Data
@ApiModel(value="TestDepartmentPersonVO")
public class TestDepartmentPersonVO {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("部门id")
    private Long deptId;
    @ApiModelProperty("人员id")
    private Long personId;
    @ApiModelProperty("创建时间")
    private Date createDate;
    @ApiModelProperty("更新时间")
    private Date updateDate;

}
