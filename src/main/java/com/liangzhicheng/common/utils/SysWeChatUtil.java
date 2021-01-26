package com.liangzhicheng.common.utils;

import com.liangzhicheng.common.constant.Constants;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 微信相关工具类
 * @author liangzhicheng
 * @since 2021-01-26
 */
public class SysWeChatUtil {

    /**
     * @description 根据网页授权code获取用户资料
     * @param code
     * @return String
     */
    public static String getUserInfoByCode(String code){
        String appid = Constants.WECHAT_APP_APP_ID_USER;
        String secrect = Constants.WECHAT_APP_APP_SECRET_USER;
        String authorizationCode = Constants.WECHAT_APP_GRANT_TYPE_USER;
        String url = Constants.WECHAT_APP_URL_USER+"appid="+appid+"&secret="+secrect+"&code="+code+"&grant_type="+authorizationCode;
        String result = SysToolUtil.sendGet(url);
        try{
            JSONObject json = JSONObject.fromObject(result);
            String openId = json.getString("openid");
            String accessToken = json.getString("access_token");
            return getUserInfoByOpenId(openId, accessToken);
        }catch(JSONException e){
            SysToolUtil.info("--- SysWeChatUtil getUserInfoByCode() result : "+result);
            return result;
        }
    }

    /**
     * @description 根据openId，accessToken获取用户信息
     * @param openId
     * @return String
     */
    public static String getUserInfoByOpenId(String openId, String accessToken){
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
        String result = SysToolUtil.sendGet(url);
        return result;
    }

    /**
     * @description 根据code获取用户openId，accessToken
     * @param code
     * @return String
     */
    public static Map<String, Object> getOpenIdByCode(String code){
        String appid = Constants.WECHAT_APP_APP_ID_USER;
        String secrect = Constants.WECHAT_APP_APP_SECRET_USER;
        String authorizationCode = Constants.WECHAT_APP_GRANT_TYPE_USER;
        String url = Constants.WECHAT_APP_URL_USER+"appid="+appid+"&secret="+secrect+"&code="+code+"&grant_type="+authorizationCode;
        String result = SysToolUtil.sendGet(url);
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            JSONObject json = JSONObject.fromObject(result);
            String openId = json.getString("openid");
            String accessToken = json.getString("access_token");
            map.put("openId", openId);
            map.put("accessToken", accessToken);
            return map;
        }catch(JSONException e){
            SysToolUtil.info("--- SysWeChatUtil getOpenIdByCode() result : "+result);
            map.put("result", result);
            return map;
        }
    }

}
