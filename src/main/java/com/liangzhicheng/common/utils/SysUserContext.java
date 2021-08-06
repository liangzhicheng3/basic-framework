package com.liangzhicheng.common.utils;

import com.liangzhicheng.modules.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;

/**
 * @description 用户上下文相关工具类
 * @author liangzhicheng
 * @since 2021-07-08
 */
public class SysUserContext {

    /**
     * @description 从请求头中获取当前登录用户
     * @return SysUserEntity
     */
    public static SysUserEntity getCurrentUser(){
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

}
