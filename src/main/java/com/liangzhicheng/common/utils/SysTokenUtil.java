package com.liangzhicheng.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.liangzhicheng.common.constant.Constants;

import java.util.Date;

/**
 * @description JSON Web Token工具类
 * @author liangzhicheng
 * @since 2020-08-10
 */
public class SysTokenUtil {

    /**
     * @description 生成用户JSON Web Token(MINI)
     * @param userId
     * @param expireTime
     * @return String
     */
    public static String createTokenMINI(String userId, Date expireTime){
        return createToken(userId + Constants.USER_ID_SUFFIX_MINI, expireTime);
    }

    /**
     * @description 生成用户JSON Web Token(APP)
     * @param userId
     * @param expireTime
     * @return String
     */
    public static String createTokenAPP(String userId, Date expireTime){
        return createToken(userId + Constants.USER_ID_SUFFIX_APP, expireTime);
    }

    /**
     * @description 生成用户JSON Web Token(WEB)
     * @param userId
     * @param expireTime
     * @return String
     */
    public static String createTokenWEB(String userId, Date expireTime){
        return createToken(userId + Constants.USER_ID_SUFFIX_WEB, expireTime);
    }

    /**
     * @description 更新用户Token(MINI)
     * @param userId
     * @param token
     */
    public static void updateTokenMINI(String userId, String token){
        updateToken(userId + Constants.USER_ID_SUFFIX_MINI, token);
    }

    /**
     * @description 更新用户Token(APP)
     * @param userId
     * @param token
     */
    public static void updateTokenAPP(String userId, String token){
        updateToken(userId + Constants.USER_ID_SUFFIX_APP, token);
    }

    /**
     * @description 更新用户Token(WEB)
     * @param userId
     * @param token
     */
    public static void updateTokenWEB(String userId, String token){
        updateToken(userId + Constants.USER_ID_SUFFIX_WEB, token);
    }

    /**
     * @description 清除用户缓存Token(MINI)
     * @param userId
     * @return String
     */
    public static String clearTokenMINI(String userId){
        return clearToken(userId + Constants.USER_ID_SUFFIX_MINI);
    }

    /**
     * @description 清除用户缓存Token(APP)
     * @param userId
     * @return String
     */
    public static String clearTokenAPP(String userId){
        return clearToken(userId + Constants.USER_ID_SUFFIX_APP);
    }

    /**
     * @description 清除用户缓存Token(WEB)
     * @param userId
     * @return String
     */
    public static String clearTokenWEB(String userId){
        return clearToken(userId + Constants.USER_ID_SUFFIX_WEB);
    }

    /**
     * @description 生成JSON Web Token
     * @param userId
     * @param expireTime
     * @return String
     */
    private static String createToken(String userId, Date expireTime){
        try{
            Algorithm algorithm = Algorithm.HMAC256(Constants.JWT_SECRET);
            return JWT.create().withExpiresAt(expireTime).withIssuer(userId).sign(algorithm);
        }catch(JWTCreationException e){
            SysToolUtil.info("--- JWTCreationException : " + e.getMessage(), SysTokenUtil.class);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description 更新用户Token
     * @param userId
     * @param token
     */
    private static void updateToken(String userId, String token){
        clearToken(userId);
        SysCacheUtil.hset(Constants.TOKEN_KEY_MAP, userId, token);
    }

    /**
     * @description 清除用户缓存Token
     * @param userId
     * @return String
     */
    private static String clearToken(String userId){
        String oldKey = (String) SysCacheUtil.hget(Constants.TOKEN_KEY_MAP, userId);
        SysCacheUtil.hdel(Constants.TOKEN_KEY_MAP, userId);
        return oldKey;
    }

    /**
     * @description 判断用户是否登录(MINI)
     * @param userId
     * @param token
     * @return boolean
     */
    public static boolean isLoginMINI(String userId, String token){
        return isLogin(userId + Constants.USER_ID_SUFFIX_MINI, token);
    }

    /**
     * @description 判断用户是否登录(APP)
     * @param userId
     * @param token
     * @return boolean
     */
    public static boolean isLoginAPP(String userId, String token){
        return isLogin(userId + Constants.USER_ID_SUFFIX_APP, token);
    }

    /**
     * @description 判断用户是否登录(WEB)
     * @param userId
     * @param token
     * @return boolean
     */
    public static boolean isLoginWEB(String userId, String token){
        return isLogin(userId + Constants.USER_ID_SUFFIX_WEB, token);
    }

    /**
     * @description 判断用户是否登录
     * @param userId
     * @param token
     * @return boolean
     */
    public static boolean isLogin(String userId, String token){
        if(SysToolUtil.isNotBlank(userId)) {
            if(SysToolUtil.isNotBlank(token)) {
                String existValue = (String) SysCacheUtil.hget(Constants.TOKEN_KEY_MAP, userId);
                if(SysToolUtil.isNotBlank(existValue) && existValue.equals(token)) {
                    boolean isLogin = verifyToken(userId, token);
                    if(!isLogin){
                        SysToolUtil.info("--- isLogin userId:"+userId+",token:"+token+",existValue:"+existValue+",verifyToken:"+isLogin, SysTokenUtil.class);
                        SysCacheUtil.hdel(Constants.TOKEN_KEY_MAP, userId);
                    }
                    return isLogin;
                }
            }
        }
        return false;
    }

    /**
     * @description 判断用户是否登录
     * @param userId
     * @param token
     * @return boolean
     */
    public static boolean isNotLogin(String userId, String token){
        return !isLogin(userId, token);
    }

    /**
     * @description 校验Toekn是否有效
     * @param userId
     * @param token
     * @return boolean
     */
    public static boolean verifyToken(String userId, String token){
        boolean active = true;
        try {
            Algorithm algorithm = Algorithm.HMAC256(Constants.JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(userId).build();
            verifier.verify(token);
        } catch (TokenExpiredException exception){
            //System.out.println("--- token 过期");
            active = false;
        } catch (JWTDecodeException exception){
            //System.out.println("--- token 无效");
            active = false;
        } catch (JWTVerificationException exception){
            //System.out.println("--- token 错误");
            active = false;
        }
        return active;
    }

}
