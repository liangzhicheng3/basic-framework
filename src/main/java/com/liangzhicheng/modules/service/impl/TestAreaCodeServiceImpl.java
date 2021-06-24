package com.liangzhicheng.modules.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.liangzhicheng.common.page.PageResult;
import com.liangzhicheng.common.utils.SysBeanUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.dao.ITestAreaCodeDao;
import com.liangzhicheng.modules.entity.TestAreaCodeEntity;
import com.liangzhicheng.modules.entity.dto.TestAreaDto;
import com.liangzhicheng.modules.entity.query.TestAreaQueryEntity;
import com.liangzhicheng.modules.entity.vo.TestAreaCodeVO;
import com.liangzhicheng.modules.service.ITestAreaCodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
