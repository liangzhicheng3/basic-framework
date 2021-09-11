package com.liangzhicheng.modules.entity.dto;

import com.liangzhicheng.modules.entity.dto.basic.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description 部门相关数据传输对象
 * @author liangzhicheng
 * @since 2021-08-06
 */
@Data
public class SysDeptDTO extends BaseDTO {

    /**
     * 公用参数
     */

    /**
     * 查询参数
     */
    @ApiModelProperty(value = "部门名称")
    private String keyword;
    @ApiModelProperty(value = "创建时间")
    private String createDate;

    /**
     * 保存参数
     */
    @ApiModelProperty("部门id")
    private String id;
    @ApiModelProperty("部门名称")
    private String name;
    @ApiModelProperty("部门描述")
    private String description;

}
