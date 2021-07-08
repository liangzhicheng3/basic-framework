package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.modules.entity.SysRoleUserEntity;
import com.liangzhicheng.modules.dao.ISysRoleUserDao;
import com.liangzhicheng.modules.service.ISysRoleUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
     * @description 获取用户角色信息
     * @param accountId
     * @return SysRoleUserEntity
     */
    @Override
    public SysRoleUserEntity getOne(String accountId) {
        return baseMapper.selectOne(new QueryWrapper<SysRoleUserEntity>()
                .eq("account_id", accountId).eq(Constants.DEL_FLAG, Constants.ZERO));
    }

}
