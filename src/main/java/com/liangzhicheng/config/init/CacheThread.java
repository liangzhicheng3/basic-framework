package com.liangzhicheng.config.init;

import com.liangzhicheng.common.utils.SysCacheUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.entity.vo.SysMenuVO;

import java.util.List;
import java.util.Map;

/**
 * @description 缓存初始化线程处理类
 * @author liangzhicheng
 * @since 2021-08-06
 */
public class CacheThread implements Runnable{

    @Override
    public void run() {
        SysToolUtil.info("CacheThread run start ...");
        //系统权限菜单列表初始化处理
        List<SysMenuVO> permMenuVOList = SysCacheUtil.listPermMenu();
        //系统角色权限初始化处理
        Map<String, Object> roleMap = SysCacheUtil.getRoleMap();
        Map<String, Object> permMap = SysCacheUtil.getPermMap();
        if(!SysToolUtil.listSizeGT(permMenuVOList)){
            SysCacheUtil.refreshListPermMenu();
        }
        if(SysToolUtil.isNull(roleMap) || SysToolUtil.isNull(permMap)){
            SysCacheUtil.refreshRolePerm();
        }
        SysToolUtil.info("CacheThread run end ...");
    }

}
