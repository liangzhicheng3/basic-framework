package com.liangzhicheng.modules.entity.dto;

import com.liangzhicheng.modules.entity.dto.basic.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description 角色相关数据传输对象
 * @author liangzhicheng
 * @since 2021-08-06
 */
@Data
public class SysRoleDTO extends BaseDTO {

    /**
     * 查询参数
     */
    @ApiModelProperty(value = "角色id/角色名称")
    private String keyword;
    @ApiModelProperty(value = "创建开始日期")
    private String dateStart;
    @ApiModelProperty(value = "创建结束日期")
    private String dateEnd;

    /**
     * 保存参数
     */
    @ApiModelProperty("角色id(主键)")
    private String id;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("职务描述")
    private String description;
    @ApiModelProperty("菜单id集合")
    private String menuIds;
    @ApiModelProperty("权限id集合")
    private String permIds;

}
