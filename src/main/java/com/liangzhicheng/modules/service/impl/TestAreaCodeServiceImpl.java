package com.liangzhicheng.modules.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangzhicheng.common.page.PageResult;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.dao.ITestAreaCodeDao;
import com.liangzhicheng.modules.entity.TestAreaCodeEntity;
import com.liangzhicheng.modules.entity.dto.TestAreaDto;
import com.liangzhicheng.modules.entity.query.TestAreaQueryEntity;
import com.liangzhicheng.modules.entity.vo.TestAreaCodeVO;
import com.liangzhicheng.modules.service.ITestAreaCodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地区编码信息表 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-03-05
 */
@Service
public class TestAreaCodeServiceImpl extends ServiceImpl<ITestAreaCodeDao, TestAreaCodeEntity> implements ITestAreaCodeService {

    @Resource
    private ITestAreaCodeDao areaCodeDao;

    /**
     * @description 获取地区列表
     * @param areaDto
     * @return PageResult
     */
    @Override
    public PageResult<TestAreaCodeVO> listArea(TestAreaDto areaDto) {
        TestAreaQueryEntity areaQuery = new TestAreaQueryEntity(areaDto);
        Integer page = areaQuery.getPage();
        Integer pageSize = areaQuery.getPageSize();
        Long count = areaCodeDao.getCount(areaQuery);
        if(count.intValue() < 1){
            return new PageResult<>(page, pageSize, Collections.emptyList(), count.intValue());
        }
        List<Map<String, Object>> areaCodeList = areaCodeDao.listArea(areaQuery);
        List<TestAreaCodeVO> areaCodeVOList = new ArrayList<TestAreaCodeVO>();
        if(areaCodeList != null && areaCodeList.size() > 0){
            for(Map<String, Object> area : areaCodeList){
                TestAreaCodeVO areaVO = new TestAreaCodeVO();
                areaVO.setAreaId(String.valueOf(area.get("areaId")));
                areaVO.setAreaCode(String.valueOf(area.get("areaCode")));
                areaVO.setAreaName(String.valueOf(area.get("areaName")));
                areaVO.setAreaLevel(Integer.parseInt(String.valueOf(area.get("areaLevel"))));
                BeanUtils.copyProperties(area, areaVO);
                areaCodeVOList.add(areaVO);
            }
        }
        return new PageResult<>(page, pageSize, areaCodeVOList, count.intValue());
    }

}
