package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangzhicheng.modules.dao.ITestDepartmentDao;
import com.liangzhicheng.modules.dao.ITestDepartmentPersonDao;
import com.liangzhicheng.modules.entity.TestDepartmentEntity;
import com.liangzhicheng.modules.service.ITestDepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 测试部门 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2020-08-04
 */
@Service
public class TestDepartmentServiceImpl extends ServiceImpl<ITestDepartmentDao, TestDepartmentEntity> implements ITestDepartmentService {

    @Resource
    private ITestDepartmentPersonDao departmentPersonDao;

    @Override
    public Page<Map<String, Object>> getDepartmentPersonList(int page, int pageSize, int deptId) {
        Page<Map<String, Object>> resultList = departmentPersonDao.getDepartmentPersonList(new Page<Map<String, Object>>(page, pageSize), deptId);
        return resultList;
    }
}
