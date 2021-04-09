package com.liangzhicheng.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liangzhicheng.modules.entity.TestPersonEntity;
import com.liangzhicheng.modules.entity.dto.TestPersonDto;
import com.liangzhicheng.modules.entity.query.TestPersonQueryEntity;
import com.liangzhicheng.modules.entity.vo.TestPersonVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ITestPersonDao extends BaseMapper<TestPersonEntity> {

    Long getCountUserNo();

    String getRankUserNo();

    Long getPersonCount(TestPersonQueryEntity personQuery);

    List<TestPersonVO> getPersonList(TestPersonQueryEntity personQuery);

    IPage<TestPersonVO> page3(@Param("page") Page<TestPersonEntity> page, @Param("keyword") String keyword);

}
