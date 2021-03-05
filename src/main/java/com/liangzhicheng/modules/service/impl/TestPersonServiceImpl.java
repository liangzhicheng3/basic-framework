package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangzhicheng.common.page.PageResult;
import com.liangzhicheng.modules.dao.ITestPersonDao;
import com.liangzhicheng.modules.entity.TestPersonEntity;
import com.liangzhicheng.modules.entity.dto.TestPersonDto;
import com.liangzhicheng.modules.entity.query.TestPersonQueryEntity;
import com.liangzhicheng.modules.entity.vo.TestPersonVO;
import com.liangzhicheng.modules.service.ITestPersonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

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

    @Override
    public PageResult page2(TestPersonDto personDto) {
        TestPersonQueryEntity personQuery = new TestPersonQueryEntity(personDto);
        Long count = testPersonDao.getPersonCount(personQuery);
        if(count.intValue() < 1){
            return new PageResult(personQuery.getPage(), personQuery.getPageSize(), Collections.emptyList(), count.intValue());
        }
        return new PageResult<>(personQuery.getPage(), personQuery.getPageSize(), testPersonDao.getPersonList(personQuery), count.intValue());
    }

}
