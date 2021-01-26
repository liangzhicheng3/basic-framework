package com.liangzhicheng.common.utils;

import com.liangzhicheng.common.constant.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @description AES加密、解密算法工具类
 * @author liangzhicheng
 * @since 2020-08-14
 */
public class SysAESUtil {

    /**
     * @description AES加密
     * @param content
     * @return String
     */
    public static String aesEncrypt(String content) {
        try {
            return aesEncrypt(content, Constants.AES_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @description AES解密
     * @param encrypt
     * @return String
     */
    public static String aesDecrypt(String encrypt) {
        try {
            return aesDecrypt(encrypt, Constants.AES_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @description 从AES签名中获取参数
     * @param key
     * @param sign
     * @return String
     */
    public static String getParam(String key, String sign){
        if(SysToolUtil.isNotBlank(key, sign)) {
            try {
                Map<String,String> map = signToMap(sign);
                return map.get(key);
            } catch (Exception e) {
                SysToolUtil.error(e.getMessage(), SysAESUtil.class);
                return null;
            }
        }
        return null;
    }

    /**
     * @description 将AES签名后的字符串转成map
     * @param sign
     * @return Map
     */
    public static Map<String, String> signToMap(String sign) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            String string = aesDecrypt(sign, Constants.AES_KEY);
            String[] str = string.split("&");
            for (int i = 0; i < str.length; i++) {
                String key = str[i].substring(0, str[i].indexOf("="));
                String value = str[i].substring(str[i].indexOf("=") + 1);
                map.put(key, value);
            }
            return map;
        }catch(Exception e) {
            SysToolUtil.error(e.getMessage(), SysAESUtil.class);
            return null;
        }
    }

    /**
     * @description AES加密为base64Code
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base64Code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * @description 将base64Code AES解密
     * @param encryptStr 待解密base64Code
     * @param decryptKey 解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    /**
     * @description base64Encode加密
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes){
        return Base64.encodeBase64String(bytes);
    }

    /**
     * @description base64Decode解密
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception{
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }

    /**
     * @description AES加密
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(Constants.AES_ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }

    /**
     * @description AES解密
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey 解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(Constants.AES_ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    /*public static void main(String[] args) {
        String sign = aesEncrypt("orderId=1122&userId=123");
        System.out.println(sign);
    }*/

}
