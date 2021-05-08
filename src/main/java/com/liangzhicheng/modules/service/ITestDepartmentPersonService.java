package com.liangzhicheng.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzhicheng.modules.entity.TestDepartmentPersonEntity;

/**
 * <p>
 * 测试部门人员 服务类
 * </p>
 *
 * @author liangzhicheng
 * @since 2020-08-04
 */
public interface ITestDepartmentPersonService extends IService<TestDepartmentPersonEntity> {

    /**
     * @description 测试在线人数
     * @param personId
     */
    void testOnlinePerson(String personId);

}
