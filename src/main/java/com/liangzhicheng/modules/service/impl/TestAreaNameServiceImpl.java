package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangzhicheng.modules.dao.ITestAreaNameDao;
import com.liangzhicheng.modules.entity.TestAreaNameEntity;
import com.liangzhicheng.modules.service.ITestAreaNameService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地区名称信息表 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2020-10-10
 */
@Service
public class TestAreaNameServiceImpl extends ServiceImpl<ITestAreaNameDao, TestAreaNameEntity> implements ITestAreaNameService {

    /**
     * @description 查询地区信息
     * @param areaName
     * @return List<Map<String, Object>>
     */
    @Override
    public List<Map<String, Object>> getAreaInfo(TestAreaNameEntity areaName) {
        return baseMapper.getAreaInfo(areaName);
    }

}
