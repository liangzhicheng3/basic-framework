package com.liangzhicheng.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzhicheng.common.page.PageResult;
import com.liangzhicheng.modules.entity.TestPersonEntity;
import com.liangzhicheng.modules.entity.dto.TestPersonDto;

public interface ITestPersonService extends IService<TestPersonEntity> {

    Long getCountUserNo();

    String getRankUserNo();

    PageResult page2(TestPersonDto personDto);

}
