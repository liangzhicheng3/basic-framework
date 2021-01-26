package com.liangzhicheng.common.push.xinge;

import com.google.common.collect.Lists;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.utils.SysCacheUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.tencent.xinge.XingeApp;
import com.tencent.xinge.bean.*;
import com.tencent.xinge.push.app.PushAppRequest;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author liangzhicheng
 * @description Xinge推送通知
 * @since 2021-01-26
 */
public class XingePush {

    /**
     * @description 推送活动通知
     * @param content
     * @param userId
     */
    public static void pushByActivity(String userId, String title, String content, String appType) {
        String token = (String) SysCacheUtil.hget(Constants.XINGE_PUSH_TOKEN_KEY_MAP, userId);
        if(SysToolUtil.isNotBlank(token)) {
            pushAccount(token, title, content, appType);
        }
    }

    /**
     * @description 单账号推送通知
     * @param account
     * @param title
     * @param content
     * @param appType
     */
    public static void pushAccount(String account, String title, String content, String appType) {
        XingeApp xingeApp = config(appType);
        PushAppRequest pushAppRequest = new PushAppRequest();
        pushAppRequest.setAudience_type(AudienceType.account);
        pushAppRequest.setMessage_type(MessageType.notify);
        pushAppRequest.setAccount_push_type(1);
        Message message = new Message();
        //苹果、安卓参数处理
        disposeCommonParam(appType, pushAppRequest, message);
        message.setTitle(title);
        message.setContent(content);
        pushAppRequest.setMessage(message);
        ArrayList<String> accountList = Lists.newArrayList();
        accountList.add(account);
        pushAppRequest.setAccount_list(accountList);
        JSONObject json = xingeApp.pushApp(pushAppRequest);
        SysToolUtil.info("--- Xinge Push Account Result : " + json.toString());
    }

    /**
     * @description 全部推送通知
     * @param title
     * @param content
     * @param appType
     */
    public static void pushAll(String title, String content, String appType) {
        XingeApp xingeApp = config(appType);
        PushAppRequest pushAppRequest = new PushAppRequest();
        pushAppRequest.setAudience_type(AudienceType.all);
        pushAppRequest.setMessage_type(MessageType.notify);
        Message message = new Message();
        //苹果、安卓参数处理
        disposeCommonParam(appType, pushAppRequest, message);
        message.setTitle(title);
        message.setContent(content);
        pushAppRequest.setMessage(message);
        JSONObject json = xingeApp.pushApp(pushAppRequest);
        SysToolUtil.info("--- Xinge Push All Result : " + json.toString());
    }

    /**
     * @description 与TPNS后台交互接口，由XingeApp.Builder进行构建
     * @param appType
     * @return XingeApp
     */
    private static XingeApp config(String appType){
        String accessId = "";
        String secretKey = "";
        if("IOS".equals(appType)){
            accessId = Constants.ACCESS_ID_IOS;
            secretKey = Constants.SECRET_KEY_IOS;
        }else{
            accessId = Constants.ACCESS_ID_ANDROID;
            secretKey = Constants.SECRET_KEY_ANDROID;
        }
        XingeApp xingeApp = new XingeApp.Builder()
                .appId(accessId) //推送目标accessID（可在【产品管理】页面获取）
                .secretKey(secretKey)
                .build();
        return xingeApp;
    }

    /**
     * @description 苹果、安卓参数处理
     * @param appType
     * @param pushAppRequest
     * @param message
     */
    private static void disposeCommonParam(String appType, PushAppRequest pushAppRequest, Message message){
        MessageIOS messageIOS = null;
        MessageAndroid messageAndroid = null;
        if("IOS".equals(appType)){
            pushAppRequest.setPlatform(Platform.ios);
            pushAppRequest.setEnvironment(Environment.dev);
            messageIOS = new MessageIOS();
            message.setIos(messageIOS);
        }else{
            pushAppRequest.setPlatform(Platform.android);
            messageAndroid = new MessageAndroid();
            message.setAndroid(messageAndroid);
        }
    }

    /**
     * @description 更新用户token
     * @param userId
     * @param token
     * @param appType
     */
    public static void updateTokenByLogout(String userId, String token, String appType){
        String oldToken = clearTokenByLogout(userId);
        String oldType = clearTypeByLogout(userId);
        SysCacheUtil.hset(Constants.XINGE_PUSH_TOKEN_KEY_MAP, userId, token);
        SysCacheUtil.hset(Constants.XINGE_PUSH_TYPE_KEY_MAP, userId, appType);
        //判断旧token不等于新token，推送下线消息
        if(SysToolUtil.isNotBlank(oldToken) && !oldToken.equals(token)){
            pushAccount(oldToken, "下线通知", "您已在其他设备登录", oldType);
        }
    }

    /**
     * @description 清除用户token
     * @param userId
     * @return String
     */
    public static String clearTokenByLogout(String userId){
        String oldToken = (String) SysCacheUtil.hget(Constants.XINGE_PUSH_TOKEN_KEY_MAP, userId);
        SysCacheUtil.hdel(Constants.XINGE_PUSH_TOKEN_KEY_MAP, userId);
        return oldToken;
    }

    /**
     * @description 清除用户设备
     * @param userId
     * @return String
     */
    public static String clearTypeByLogout(String userId){
        String oldType = (String) SysCacheUtil.hget(Constants.XINGE_PUSH_TYPE_KEY_MAP, userId);
        SysToolUtil.info("------ oldType : " + oldType);
        SysCacheUtil.hdel(Constants.XINGE_PUSH_TYPE_KEY_MAP, userId);
        return oldType;
    }

}
