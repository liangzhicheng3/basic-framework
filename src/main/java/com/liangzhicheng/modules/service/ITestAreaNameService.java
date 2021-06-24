package com.liangzhicheng.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzhicheng.common.page.PageResult;
import com.liangzhicheng.modules.entity.TestAreaNameEntity;
import com.liangzhicheng.modules.entity.dto.TestAreaDto;

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
     * @description 获取地区列表
     * @param areaDto
     * @return PageResult
     */
    PageResult listArea(TestAreaDto areaDto);

    /**
     * @description 查询地区信息
     * @param areaName
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getAreaInfo(TestAreaNameEntity areaName);

}
