package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.modules.entity.SysRoleUserEntity;
import com.liangzhicheng.modules.dao.ISysRoleUserDao;
import com.liangzhicheng.modules.service.ISysRoleUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色用户表 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
@Service
public class SysRoleUserServiceImpl extends ServiceImpl<ISysRoleUserDao, SysRoleUserEntity> implements ISysRoleUserService {

    /**
     * @description 根据key，value获取角色用户列表
     * @param key
     * @param value
     * @return List<SysRoleUserEntity>
     */
    @Override
    public List<SysRoleUserEntity> list(String key, String value) {
        return baseMapper.selectList(new QueryWrapper<SysRoleUserEntity>()
                .eq(key, value).eq(Constants.DEL_FLAG, Constants.ZERO));
    }

}
