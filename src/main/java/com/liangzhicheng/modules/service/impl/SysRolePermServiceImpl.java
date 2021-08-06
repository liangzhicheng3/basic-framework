package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.entity.*;
import com.liangzhicheng.modules.dao.ISysRolePermDao;
import com.liangzhicheng.modules.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 角色权限信息表 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
@Service
public class SysRolePermServiceImpl extends ServiceImpl<ISysRolePermDao, SysRolePermEntity> implements ISysRolePermService {

    @Resource
    private ISysUserService userService;
    @Resource
    private ISysRoleService roleService;
    @Resource
    private ISysPermService permService;
    @Resource
    private ISysRoleUserService roleUserService;
    @Resource
    private ISysRolePermService rolePermService;

    /**
     * @description 根据key，value获取角色权限列表
     * @param key
     * @param value
     * @return List<SysRolePermEntity>
     */
    @Override
    public List<SysRolePermEntity> list(String key, String value) {
        return baseMapper.selectList(new QueryWrapper<SysRolePermEntity>()
                .eq(key, value).eq(Constants.DEL_FLAG, Constants.ZERO));
    }

    /**
     * @description 权限Map
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> mapRolePerm() {
        Map<String, Object> resultMap = Maps.newHashMap();
        Map<String, Object> roleMap = Maps.newHashMap();
        Map<String, Object> permMap = Maps.newHashMap();
        List<SysUserEntity> userList = userService.list(null, null);
        if(SysToolUtil.listSizeGT(userList)){
            List<SysRoleEntity> roleList = Lists.newArrayList();
            List<SysPermEntity> permList = Lists.newArrayList();
            List<SysRoleUserEntity> roleUserList = Lists.newArrayList();
            List<SysRolePermEntity> rolePermList = Lists.newArrayList();
            for(SysUserEntity user : userList){
                Set<String> roles = Sets.newHashSet();
                Set<String> perms = Sets.newHashSet();
                if(Constants.ONE.equals(user.getIsAdmin())){
                    roleList = roleService.list(new QueryWrapper<SysRoleEntity>()
                            .eq(Constants.DEL_FLAG, Constants.ZERO));
                    if(SysToolUtil.listSizeGT(roleList)){
                        for(SysRoleEntity role : roleList){
                            roles.add(role.getName());
                        }
                    }
                    permList = permService.list(new QueryWrapper<SysPermEntity>()
                            .eq(Constants.DEL_FLAG, Constants.ZERO));
                    if(SysToolUtil.listSizeGT(permList)){
                        for(SysPermEntity perm : permList){
                            perms.add(perm.getExpression());
                        }
                    }
                }else{
                    roleUserList = roleUserService.list("account_id", user.getId());
                    if(SysToolUtil.listSizeGT(roleUserList)){
                        for(SysRoleUserEntity roleUser : roleUserList){
                            roles.add(roleUser.getRoleName());
                            rolePermList = rolePermService.list("role_id", roleUser.getRoleId());
                            if(SysToolUtil.listSizeGT(rolePermList)){
                                for(SysRolePermEntity rolePerm : rolePermList){
                                    perms.add(rolePerm.getExpression());
                                }
                            }
                        }
                    }
                }
                roleMap.put(user.getId(), roles);
                permMap.put(user.getId(), perms);
            }
        }
        resultMap.put("roleMap", roleMap);
        resultMap.put("permMap", permMap);
        return resultMap;
    }

}
