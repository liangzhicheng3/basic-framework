package com.liangzhicheng.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liangzhicheng.modules.entity.TestPersonEntity;
import com.liangzhicheng.modules.entity.query.TestPersonQueryEntity;
import com.liangzhicheng.modules.entity.vo.TestPersonVO;

import java.util.List;

public interface ITestPersonDao extends BaseMapper<TestPersonEntity> {

    Long getCountUserNo();

    String getRankUserNo();

    Long getPersonCount(TestPersonQueryEntity personQuery);

    List<TestPersonVO> getPersonList(TestPersonQueryEntity personQuery);

}
