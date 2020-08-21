package com.liangzhicheng.common.utils;

import net.sf.json.JSONObject;

/**
 * @description 云之讯短信获取工具类
 * @author liangzhicheng
 * @since 2020-08-13
 */
public class SysSmsUtil {

    public static String sendSMS(String accountSid, String authToken, String appId, String templateId, String url, String phone, String ... vcodes) {
        String content = "";
        if(vcodes != null && vcodes.length > 0){
            for(String str : vcodes){
                content += (str + ",");
            }
            content = content.substring(0,content.length()-1);
        }
        JSONObject json = new JSONObject();
        json.element("sid", accountSid);
        json.element("token", authToken);
        json.element("appid", appId);
        json.element("templateid", templateId);
        json.element("mobile", phone);
        json.element("param", content);
        String result = SysToolUtil.post(url, json.toString());
        SysToolUtil.info("--- sendSms 调用成功 : \nphone : " + phone + ", content : " + content + " \nresult : " + result, SysSmsUtil.class);
        return result;
    }

}
