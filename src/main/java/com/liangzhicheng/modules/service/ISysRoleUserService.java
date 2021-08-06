package com.liangzhicheng.modules.service;

import com.liangzhicheng.modules.entity.SysRoleUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
     * @description 根据key，value获取角色用户列表
     * @param key
     * @param value
     * @return List<SysRoleUserEntity>
     */
    List<SysRoleUserEntity> list(String key, String value);

}
