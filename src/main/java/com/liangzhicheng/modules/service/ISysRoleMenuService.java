package com.liangzhicheng.modules.service;

import com.liangzhicheng.modules.entity.SysRoleMenuEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色菜单信息表 服务类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-08-06
 */
public interface ISysRoleMenuService extends IService<SysRoleMenuEntity> {

    /**
     * @description 根据key，value获取角色菜单列表
     * @param key
     * @param value
     * @return List<SysRoleMenuEntity>
     */
    List<SysRoleMenuEntity> list(String key, String value);

}
