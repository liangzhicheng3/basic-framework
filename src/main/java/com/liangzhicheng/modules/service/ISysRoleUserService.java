package com.liangzhicheng.modules.service;

import com.liangzhicheng.modules.entity.SysRoleUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色用户表 服务类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
public interface ISysRoleUserService extends IService<SysRoleUserEntity> {

    /**
     * @description 获取用户角色信息
     * @param accountId
     * @return SysRoleUserEntity
     */
    SysRoleUserEntity getOne(String accountId);

}
