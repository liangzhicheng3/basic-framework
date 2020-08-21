package com.liangzhicheng.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liangzhicheng.modules.entity.TestPersonEntity;

public interface ITestPersonDao extends BaseMapper<TestPersonEntity> {

    Long getCountUserNo();

    String getRankUserNo();

}
