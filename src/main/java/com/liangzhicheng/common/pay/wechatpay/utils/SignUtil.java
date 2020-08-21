package com.liangzhicheng.common.pay.wechatpay.utils;

import com.liangzhicheng.common.constant.Constants;

import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

/**
 * @description 签名工具类
 * @author liangzhicheng
 * @since 2020-08-12
 */
public class SignUtil implements Constants {

	/**
	 * @description 小程序支付签名
	 * @param map
	 * @param secret
	 * @return String
	 * @throws Exception
	 */
	public static String signMini(SortedMap<String, Object> map, String secret) throws Exception {
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, Object>> mapEntrySet = map.entrySet();
		for(Entry<String, Object> entry : mapEntrySet){
			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());
			if(value != null && !value.equals("") && !key.equals("sign") && !key.equals("key")){
				sb.append(key + "=" + value + "&");
			}
		}
		sb.append("key=" + secret);
		return MD5Util.encryptString(sb.toString(), false);
	}

	/**
	 * @description APP支付签名
	 * @param map
	 * @param secret
	 * @return String
	 * @throws Exception
	 */
	public static String signApp(SortedMap<String,Object> map, String secret) throws Exception {
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, Object>> mapEntrySet = map.entrySet();
		for(Entry<String, Object> entry : mapEntrySet){
			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());
			if(value != null && !value.equals("") && !key.equals("sign") && !key.equals("key")){
				sb.append(key + "=" + value + "&");
			}
		}
		sb.append("key=" + secret);
		return MD5Util.encryptString(sb.toString(), false);
	}

	/**
	 * @description 公众号支付签名
	 * @param map
	 * @param secret
	 * @return String
	 * @throws Exception
	 */
	public static String signPub(SortedMap<String, Object> map, String secret) throws Exception {
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, Object>> mapEntrySet = map.entrySet();
		for(Entry<String, Object> entry : mapEntrySet){
			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());
			if(value != null && !value.equals("") && !key.equals("sign") && !key.equals("key")){
				sb.append(key + "=" + value + "&");
			}
		}
		sb.append("key=" + secret/*WECHAT_PUB_SECRET*/);
		return MD5Util.encryptString(sb.toString(), false);
	}

	/**
	 * @description 扫码支付签名
	 * @param map
	 * @param secret
	 * @return String
	 * @throws Exception
	 */
	public static String signScan(SortedMap<String, Object> map, String secret) throws Exception {
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, Object>> mapEntrySet = map.entrySet();
		for(Entry<String, Object> entry : mapEntrySet){
			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());
			if(value != null && !value.equals("") && !key.equals("sign") && !key.equals("key")){
				sb.append(key + "=" + value + "&");
			}
		}
		sb.append("key=" + secret/*WECHAT_SCAN_SECRET*/);
		return MD5Util.encryptString(sb.toString(), false);
	}

}
