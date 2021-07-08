package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangzhicheng.modules.dao.ITestAreaCodeDao;
import com.liangzhicheng.modules.entity.TestAreaCodeEntity;
import com.liangzhicheng.modules.service.ITestAreaCodeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地区编码信息表 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-03-05
 */
@Service
public class TestAreaCodeServiceImpl extends ServiceImpl<ITestAreaCodeDao, TestAreaCodeEntity> implements ITestAreaCodeService {

}
