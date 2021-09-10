package com.liangzhicheng.modules.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liangzhicheng.common.page.PageResult;
import com.liangzhicheng.modules.entity.TestPersonEntity;
import com.liangzhicheng.modules.entity.dto.TestPersonDTO;
import com.liangzhicheng.modules.entity.vo.TestPersonVO;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ITestPersonService extends IBaseService<TestPersonEntity> {

    Long getCountUserNo();

    String getRankUserNo();

    PageResult page2(TestPersonDTO personDto);

    IPage<TestPersonVO> page3(TestPersonDTO personDto);

    Map<String, Object> testListPerson(TestPersonDTO personDTO, Pageable pageable);

}
