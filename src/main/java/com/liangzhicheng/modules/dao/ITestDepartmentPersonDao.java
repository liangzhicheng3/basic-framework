package com.liangzhicheng.modules.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liangzhicheng.modules.entity.TestDepartmentPersonEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 测试部门人员 Mapper 接口
 * </p>
 *
 * @author liangzhicheng
 * @since 2020-08-04
 */
public interface ITestDepartmentPersonDao extends BaseMapper<TestDepartmentPersonEntity> {

    Page<Map<String, Object>> getDepartmentPersonList(Page<Map<String, Object>> page, @Param("deptId") int deptId);

}
