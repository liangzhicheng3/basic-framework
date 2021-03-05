package com.liangzhicheng.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzhicheng.modules.entity.TestAreaNameEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地区名称信息表 服务类
 * </p>
 *
 * @author liangzhicheng
 * @since 2020-10-10
 */
public interface ITestAreaNameService extends IService<TestAreaNameEntity> {

    /**
     * @description 查询地区信息
     * @param areaName
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getAreaInfo(TestAreaNameEntity areaName);

}
