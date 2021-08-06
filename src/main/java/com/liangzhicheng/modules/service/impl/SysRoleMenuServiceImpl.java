package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.modules.entity.SysRoleMenuEntity;
import com.liangzhicheng.modules.dao.ISysRoleMenuDao;
import com.liangzhicheng.modules.service.ISysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色菜单信息表 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-08-06
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<ISysRoleMenuDao, SysRoleMenuEntity> implements ISysRoleMenuService {

    /**
     * @description 根据key，value获取角色菜单列表
     * @param key
     * @param value
     * @return List<SysRoleMenuEntity>
     */
    @Override
    public List<SysRoleMenuEntity> list(String key, String value) {
        return baseMapper.selectList(new QueryWrapper<SysRoleMenuEntity>()
                .eq(key, value).eq(Constants.DEL_FLAG, Constants.ZERO));
    }

}
