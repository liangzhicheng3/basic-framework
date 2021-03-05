package com.liangzhicheng.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liangzhicheng.modules.entity.TestAreaNameEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地区名称信息表 Mapper 接口
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-03-05
 */
public interface ITestAreaNameDao extends BaseMapper<TestAreaNameEntity> {

    /**
     * @description 查询地区信息
     * @param areaName
     * @return List<Map<String, Object>>
     */
    List<Map<String,Object>> getAreaInfo(TestAreaNameEntity areaName);

}
