package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangzhicheng.modules.dao.ITestPersonDao;
import com.liangzhicheng.modules.entity.TestPersonEntity;
import com.liangzhicheng.modules.service.ITestPersonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestPersonServiceImpl extends ServiceImpl<ITestPersonDao, TestPersonEntity> implements ITestPersonService {

    @Resource
    private ITestPersonDao testPersonDao;

    @Override
    public Long getCountUserNo() {
        return testPersonDao.getCountUserNo();
    }

    @Override
    public String getRankUserNo() {
        return testPersonDao.getRankUserNo();
    }

}
