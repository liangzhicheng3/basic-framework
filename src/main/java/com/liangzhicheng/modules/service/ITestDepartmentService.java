package com.liangzhicheng.modules.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzhicheng.modules.entity.TestDepartmentEntity;

import java.util.Map;

/**
 * <p>
 * 测试部门 服务类
 * </p>
 *
 * @author liangzhicheng
 * @since 2020-08-04
 */
public interface ITestDepartmentService extends IService<TestDepartmentEntity> {

    Page<Map<String, Object>> getDepartmentPersonList(int page, int pageSize, int deptId);

}
