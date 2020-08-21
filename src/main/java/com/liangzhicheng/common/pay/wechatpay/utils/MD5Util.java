package com.liangzhicheng.common.pay.wechatpay.utils;

import java.security.MessageDigest;

/**
 * @description MD5工具类
 * @author liangzhicheng
 * @since 2020-08-12
 */
public class MD5Util {

    /**
     * @description MD5加密String字符串返回小写，如果upper是true，返回大写
     * @param str
     * @param upper
     * @return String
     */
    public static String encryptString(String str, boolean upper){
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] md5 = digest.digest(str.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            String part = null;
            for (int i = 0; i < md5.length; i++) {
                part = Integer.toHexString(md5[i] & 0xFF);
                if (part.length() == 1) {
                    part = "0" + part;
                }
                sb.append(part);
            }
            String result = sb.toString().toLowerCase();
            if(upper == true){
                result = result.toUpperCase();
            }
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
	
}
