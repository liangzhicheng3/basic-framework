package com.liangzhicheng.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 地区名称信息表
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("test_area_name")
public class TestAreaNameEntity extends Model<TestAreaNameEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 语言
     */
    private String lang;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 地区编码
     */
    private String areaCode;

    /**
     * 类型
     */
    private String type;

    /**
     * 是否编辑：0否，1是
     */
    private String isEdit;

    /**
     * 备注
     */
    private String remarks;

    @Override
    public Serializable pkVal() {
        return null;
    }


    //查询条件
    private String country;
    private String province;
    private String city;

}
