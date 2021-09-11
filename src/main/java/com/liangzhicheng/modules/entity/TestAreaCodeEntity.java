package com.liangzhicheng.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 地区编码信息表
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("test_area_code")
public class TestAreaCodeEntity extends Model<TestAreaCodeEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 地区id
     */
    private String areaId;

    /**
     * 地区编码
     */
    private String areaCode;

    /**
     * 地区层级
     */
    private Integer areaLevel;

    @Override
    public Serializable pkVal() {
        return null;
    }

}
