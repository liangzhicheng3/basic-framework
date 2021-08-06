package com.liangzhicheng.modules.service;

import com.liangzhicheng.modules.entity.SysPermEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzhicheng.modules.entity.dto.SysMenuDTO;

/**
 * <p>
 * 权限信息表 服务类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
public interface ISysPermService extends IService<SysPermEntity> {

    /**
     * @description 根据菜单id获取权限标识
     * @param menuId
     * @return String
     */
    SysPermEntity getPerm(String menuId);

    /**
     * @description 新增权限
     * @param menuDTO
     * @return SysPermEntity
     */
    SysPermEntity insertPerm(SysMenuDTO menuDTO);

}
