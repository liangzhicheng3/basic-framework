package com.liangzhicheng.modules.service;

import com.liangzhicheng.modules.entity.SysRolePermEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色权限信息表 服务类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
public interface ISysRolePermService extends IService<SysRolePermEntity> {

    /**
     * @description 根据key，value获取角色权限列表
     * @param key
     * @param value
     * @return List<SysRolePermEntity>
     */
    List<SysRolePermEntity> list(String key, String value);

    /**
     * @description 权限Map
     * @return Map<String, Object>
     */
    Map<String, Object> mapRolePerm();

}
