package com.liangzhicheng.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liangzhicheng.modules.entity.TestAreaCodeEntity;
import com.liangzhicheng.modules.entity.query.TestAreaQueryEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地区编码信息表 Mapper 接口
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-03-05
 */
public interface ITestAreaCodeDao extends BaseMapper<TestAreaCodeEntity> {

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

}
