package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.utils.SysSnowFlakeUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.entity.SysMenuEntity;
import com.liangzhicheng.modules.entity.SysPermEntity;
import com.liangzhicheng.modules.entity.SysPermMenuEntity;
import com.liangzhicheng.modules.dao.ISysPermMenuDao;
import com.liangzhicheng.modules.service.ISysPermMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 权限菜单信息表 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-08-06
 */
@Service
public class SysPermMenuServiceImpl extends ServiceImpl<ISysPermMenuDao, SysPermMenuEntity> implements ISysPermMenuService {

    /**
     * @description 根据key，value获取权限菜单
     * @param key
     * @param value
     * @return SysPermMenuEntity
     */
    @Override
    public SysPermMenuEntity getOne(String key, String value) {
        return baseMapper.selectOne(new QueryWrapper<SysPermMenuEntity>()
                .eq(key, value).eq(Constants.DEL_FLAG, Constants.ZERO));
    }

    /**
     * @description 新增权限菜单
     * @param perm
     * @param menu
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertPermMenu(SysPermEntity perm, SysMenuEntity menu) {
        if(SysToolUtil.isNotNull(perm) && SysToolUtil.isNotNull(menu)){
            SysPermMenuEntity permMenu = new SysPermMenuEntity(SysSnowFlakeUtil.get().nextId() + "",
                    perm.getId(), perm.getName(), menu.getId(), menu.getName());
            baseMapper.insert(permMenu);
        }
    }

}
