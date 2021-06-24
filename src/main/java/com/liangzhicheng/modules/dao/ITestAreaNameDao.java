package com.liangzhicheng.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liangzhicheng.modules.entity.TestAreaNameEntity;
import com.liangzhicheng.modules.entity.query.TestAreaQueryEntity;

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
     * @description 获取地区总记录
     * @param areaQuery
     * @return long
     */
    Long getCount(TestAreaQueryEntity areaQuery);

    /**
     * @description 获取地区列表
     * @param areaQuery
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> listArea(TestAreaQueryEntity areaQuery);

    /**
     * @description 查询地区信息
     * @param areaName
     * @return List<Map<String, Object>>
     */
    List<Map<String,Object>> getAreaInfo(TestAreaNameEntity areaName);

}
