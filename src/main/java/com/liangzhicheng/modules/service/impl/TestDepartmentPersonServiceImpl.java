package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangzhicheng.modules.dao.ITestDepartmentPersonDao;
import com.liangzhicheng.modules.entity.TestDepartmentPersonEntity;
import com.liangzhicheng.modules.service.ITestDepartmentPersonService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试部门人员 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2020-08-04
 */
@Service
public class TestDepartmentPersonServiceImpl extends ServiceImpl<ITestDepartmentPersonDao, TestDepartmentPersonEntity> implements ITestDepartmentPersonService {

}
