package com.liangzhicheng.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzhicheng.modules.entity.TestPersonEntity;

public interface ITestPersonService extends IService<TestPersonEntity> {

    Long getCountUserNo();

    String getRankUserNo();

}
