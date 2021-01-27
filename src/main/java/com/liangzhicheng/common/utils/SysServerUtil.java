package com.liangzhicheng.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.enums.UserTokenServerEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * @description Web工具类
 * @author liangzhicheng
 * @since 2020-08-14
 */
public class SysServerUtil {

    /**
     * @description 用户登录处理
     * @param accountId
     * @param token
     * @return boolean
     */
    public static boolean loginDispose(String accountId, String token){
        /*if(SysToolUtil.isBlank(userId) || request == null){
            return false;
        }
        String sessionUserId = (String) request.getSession().getAttribute(Constants.LOGIN_USER_ID);
        if(SysToolUtil.isBlank(sessionUserId) || (!sessionUserId.equals(userId))){
            return false;
        }*/

//        if(SysToolUtil.isBlank(accountId, token)){
//            return false;
//        }
//        String user = SysCacheUtil.getByKey(UserTokenServerEnum.LOGIN_TOKEN_SERVER.join(token));
//        if(SysToolUtil.isBlank(user)){
//            return false;
//        }
//        SysUserEntity sysUser = JSON.parseObject(user, SysUserEntity.class);
//        if(sysUser == null && accountId.equals(sysUser.getAccountId())){
//            return false;
//        }
        return true;
    }

    /**
     * @description 判断用户是否登录
     * @param accountId
     * @param token
     * @return boolean
     */
    public static boolean isLogin(String accountId, String token){
        return !loginDispose(accountId, token);
    }

}
