package com.liangzhicheng.modules.service.impl;

import com.liangzhicheng.common.utils.SysSnowFlakeUtil;
import com.liangzhicheng.common.utils.SysThreadUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.entity.SysPermEntity;
import com.liangzhicheng.modules.dao.ISysPermDao;
import com.liangzhicheng.modules.entity.SysPermMenuEntity;
import com.liangzhicheng.modules.entity.dto.SysMenuDTO;
import com.liangzhicheng.modules.service.ISysPermMenuService;
import com.liangzhicheng.modules.service.ISysPermService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 权限信息表 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
@Service
public class SysPermServiceImpl extends ServiceImpl<ISysPermDao, SysPermEntity> implements ISysPermService {

    @Resource
    private ISysPermMenuService permMenuService;

    /**
     * @description 根据菜单id获取权限标识
     * @param menuId
     * @return String
     */
    @Override
    public SysPermEntity getPerm(String menuId) {
        if(SysToolUtil.isNotBlank(menuId)){
            SysPermMenuEntity permMenu = permMenuService.getOne("menu_id", menuId);
            if(SysToolUtil.isNotNull(permMenu)){
                return baseMapper.selectById(permMenu.getPermId());
            }
        }
        return null;
    }

    /**
     * @description 新增权限
     * @param menuDTO
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SysPermEntity insertPerm(SysMenuDTO menuDTO) {
        String permName = menuDTO.getPermName();
        String expression = menuDTO.getExpression();
        SysPermEntity perm = null;
        if(SysToolUtil.isNotBlank(permName, expression)){
            perm = new SysPermEntity();
            perm.setId(SysSnowFlakeUtil.get().nextId() + "");
            perm.setName(permName);
            perm.setExpression(expression);
            baseMapper.insert(perm);
            //开启线程刷新缓存中权限
            SysThreadUtil.threadRolePerm();
        }
        return perm;
    }

}
