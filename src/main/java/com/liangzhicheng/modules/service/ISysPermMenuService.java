package com.liangzhicheng.modules.service;

import com.liangzhicheng.modules.entity.SysMenuEntity;
import com.liangzhicheng.modules.entity.SysPermEntity;
import com.liangzhicheng.modules.entity.SysPermMenuEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 权限菜单信息表 服务类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-08-06
 */
public interface ISysPermMenuService extends IService<SysPermMenuEntity> {

    /**
     * @description 根据key，value获取权限菜单
     * @param key
     * @param value
     * @return SysPermMenuEntity
     */
    SysPermMenuEntity getOne(String key, String value);

    /**
     * @description 新增权限菜单
     * @param perm
     * @param menu
     */
    void insertPermMenu(SysPermEntity perm, SysMenuEntity menu);

}
