package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.liangzhicheng.common.page.PageResult;
import com.liangzhicheng.common.utils.SysBeanUtil;
import com.liangzhicheng.common.utils.SysQueryUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.dao.ITestPersonDao;
import com.liangzhicheng.modules.entity.TestPersonEntity;
import com.liangzhicheng.modules.entity.dto.TestPersonDTO;
import com.liangzhicheng.modules.entity.query.TestPersonQueryCondition;
import com.liangzhicheng.modules.entity.query.TestPersonQueryEntity;
import com.liangzhicheng.modules.entity.vo.TestPersonVO;
import com.liangzhicheng.modules.service.ITestPersonService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class TestPersonServiceImpl extends BaseServiceImpl<ITestPersonDao, TestPersonEntity> implements ITestPersonService {

    @Override
    public Long getCountUserNo() {
        return baseMapper.getCountUserNo();
    }

    @Override
    public String getRankUserNo() {
        return baseMapper.getRankUserNo();
    }

    @Override
    public PageResult page2(TestPersonDTO personDto) {
        TestPersonQueryEntity personQuery = new TestPersonQueryEntity(personDto);
        Long count = baseMapper.getPersonCount(personQuery);
        if(count.intValue() < 1){
            return new PageResult(personQuery.getPage(), personQuery.getPageSize(), Collections.emptyList(), count.intValue());
        }
        return new PageResult<>(personQuery.getPage(), personQuery.getPageSize(), baseMapper.getPersonList(personQuery), count.intValue());
    }

    @Override
    public IPage<TestPersonVO> page3(TestPersonDTO personDto) {
        String keyword = personDto.getKeyword();
        return baseMapper.page3(new Page<TestPersonEntity>().setCurrent(personDto.getPage()).setSize(personDto.getPageSize()), keyword);
    }

    @Override
    public Map<String, Object> testListPerson(TestPersonDTO personDTO, Pageable pageable) {
        TestPersonQueryCondition personQuery = new TestPersonQueryCondition(personDTO);
        //自定义排序
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC,"gender"));
        orders.add(new Sort.Order(Sort.Direction.DESC,"work_time"));
        this.getPage(this.customizeSort(personQuery, orders), personQuery.getPage(), personQuery.getPageSize());
        //只根据创建时间排序
//        this.getPage(pageable, personQuery.getPage(), personQuery.getPageSize());
        List<TestPersonEntity> personList = baseMapper.selectList(
                SysQueryUtil.getQueryWrapper(TestPersonEntity.class, personQuery));
        PageInfo<TestPersonEntity> page = new PageInfo<>();
        List records = Lists.newArrayList();
        if(SysToolUtil.listSizeGT(personList)){
            page = new PageInfo<>(personList);
            records = SysBeanUtil.copyList(page.getList(), TestPersonVO.class);
        }
        return this.pageResult(records, page);
    }

}
