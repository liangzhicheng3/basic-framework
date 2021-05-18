package com.liangzhicheng.common.utils;


import com.alibaba.fastjson.JSONObject;

/**
 * @description 云之讯短信获取工具类
 * @author liangzhicheng
 * @since 2020-08-13
 */
public class SysSmsUtil {

    public static void sendSMS(String accountSid, String authToken, String appId, String templateId, String url, String phone, String ... vcodes) {
        String content = "";
        if(vcodes != null && vcodes.length > 0){
            for(String str : vcodes){
                content += (str + ",");
            }
            content = content.substring(0,content.length() - 1);
        }
        JSONObject json = new JSONObject();
        json.put("sid", accountSid);
        json.put("token", authToken);
        json.put("appid", appId);
        json.put("templateid", templateId);
        json.put("mobile", phone);
        json.put("param", content);
        json.put("uid", null);
        try{
            String result = SysToolUtil.sendPost(url, json.toJSONString(), null);
            SysToolUtil.info("--- sendSms 调用成功 : \nphone : " + phone + ", content : " + content + " \nresult : " + result, SysSmsUtil.class);
        }catch(Exception e){
            SysToolUtil.error("--- sendSms 调用失败 : " + e.getMessage(), SysSmsUtil.class);
        }
    }

}
