package com.liangzhicheng.modules.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzhicheng.common.page.PageResult;
import com.liangzhicheng.modules.entity.TestPersonEntity;
import com.liangzhicheng.modules.entity.dto.TestPersonDTO;
import com.liangzhicheng.modules.entity.vo.TestPersonVO;

public interface ITestPersonService extends IService<TestPersonEntity> {

    Long getCountUserNo();

    String getRankUserNo();

    PageResult page2(TestPersonDTO personDto);

    IPage<TestPersonVO> page3(TestPersonDTO personDto);

}
