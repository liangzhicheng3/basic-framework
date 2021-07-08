package com.liangzhicheng.modules.entity.dto;

import com.liangzhicheng.modules.entity.dto.basic.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description 账号相关数据传输对象
 * @author liangzhicheng
 * @since 2021-07-08
 */
@Data
public class SysUserDTO extends BaseDTO {

    @ApiModelProperty("账号名称")
    private String accountName;
    @ApiModelProperty("密码")
    private String password;

}
