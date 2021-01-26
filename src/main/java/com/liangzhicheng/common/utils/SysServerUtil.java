package com.liangzhicheng.common.utils;

import com.liangzhicheng.common.constant.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * @description Web工具类
 * @author liangzhicheng
 * @since 2020-08-14
 */
public class SysServerUtil {

    /**
     * @description 用户登录处理
     * @param userId
     * @param request
     * @return boolean
     */
    public static boolean loginDispose(String userId, HttpServletRequest request){
        if(SysToolUtil.isBlank(userId) || request == null){
            return false;
        }
        String sessionUserId = (String) request.getSession().getAttribute(Constants.LOGIN_USER_ID);
        if(SysToolUtil.isBlank(sessionUserId) || (!sessionUserId.equals(userId))){
            return false;
        }
        return true;
    }

    /**
     * @description 判断用户是否登录
     * @param userId
     * @param request
     * @return boolean
     */
    public static boolean isLogin(String userId, HttpServletRequest request){
        return !loginDispose(userId, request);
    }

}
