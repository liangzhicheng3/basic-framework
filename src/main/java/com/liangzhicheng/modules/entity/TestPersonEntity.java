package com.liangzhicheng.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("test_person")
public class TestPersonEntity extends Model<TestPersonEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别：0女 1男
     */
    private Boolean gender;

    /**
     * 睡觉时间
     */
    private Date sleepTime;

    /**
     * 上班时间
     */
    private Date workTime;

    /**
     * 出生时间
     */
    private Date birthday;

    /**
     * 个人简介
     */
    private String intro;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    /**
     * 用户编号
     */
    private String userNo;

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
