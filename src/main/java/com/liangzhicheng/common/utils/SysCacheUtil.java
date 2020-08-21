package com.liangzhicheng.common.utils;

import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.config.context.SpringContextHolder;

import java.util.Map;
import java.util.Set;

/**
 * @description 缓存工具类
 * @author liangzhicheng
 * @since 2020-08-10
 */
public class SysCacheUtil implements Constants {

    private static SysRedisUtil redis = SpringContextHolder.getBean(SysRedisUtil.class);

    /**
     * @description key操作，设置某个key的存活时间
     * @param key
     * @param second
     * @return boolean
     */
    public static boolean expire(String key, int second){
        SysToolUtil.info("--- SysCacheUtil expire with second : " + "key=" + key + ", second=" + second, SysCacheUtil.class);
        return redis.expire(key, second);
    }

    /**
     * @description 获取Map
     * @param mapName
     * @return Map
     */
    public static Map<String, Object> entries(String mapName) {
        SysToolUtil.info("--- SysCacheUtil entries : " + "mapName=" + mapName, SysCacheUtil.class);
        return redis.entries(mapName);
    }

    /**
     * @description 写操作，永久存活
     * @param key
     * @param obj
     */
    public static void set(String key, Object obj) {
        redis.set(key, obj);
        SysToolUtil.info("--- SysCacheUtil set : " + "key=" + key + ", obj=" + obj, SysCacheUtil.class);
    }

    /**
     * @description 写操作，可设置存活秒数
     * @param key
     * @param obj
     * @param second
     */
    public static void set(String key, Object obj, int second) {
        redis.set(key, obj, second);
        SysToolUtil.info("--- SysCacheUtil set with second : " + "key=" + key + ", obj=" + obj + ", second=" + second, SysCacheUtil.class);
    }

    /**
     * @description 写操作，向名称为key的set中添加元素member
     * @param key
     * @param obj
     */
    public static void sadd(String key, Object obj) {
        redis.sadd(key, obj);
    }

    /**
     * @description 删除操作，删除名称为key的set中的元素member
     * @param key
     * @param obj
     */
    public static void srem(String key, Object obj){
        redis.srem(key, obj);
    }

    /**
     * @description 读操作，返回名称为key的set的所有元素
     * @param key
     * @return Set
     */
    public static Set<Object> smembers(String key){
        return redis.smembers(key);
    }

    /**
     * @description 读操作
     * @param key
     * @return Object
     */
    public static Object get(String key) {
        return redis.get(key);
    }

    /**
     * @description 删除操作
     * @param key
     */
    public static void del(String key) {
        redis.del(key);
    }

    /**
     * @description 判断key是否存在
     * @param key
     * @return boolean
     */
    public static boolean hasKey(String key) {
        return redis.hasKey(key);
    }

    /**
     * @description hash写操作，向名称为key的hash中添加元素field
     * @param key
     * @param field
     * @param obj
     */
    public static void hset(String key, String field, Object obj) {
        redis.hset(key, field, obj);
        SysToolUtil.info("--- SysCacheUtil hset : " + "key=" + key + ", field=" + field + ", obj=" + obj, SysCacheUtil.class);
    }

    /**
     * @description hash读操作，返回名称为key的hash中field对应的value
     * @param key
     * @param field
     * @return Object
     */
    public static Object hget(String key, String field) {
        SysToolUtil.info("--- SysCacheUtil hget : " + "key=" + key + ", field=" + field, SysCacheUtil.class);
        return redis.hget(key, field);
    }

    /**
     * @description 判断hash的某个map是否存在某个key
     * @param mapName
     * @param key
     * @return boolean
     */
    public static boolean hHasKey(String mapName, String key) {
        return redis.hHasKey(mapName, key);
    }

    /**
     * @description hash删除操作，删除名称为key的hash中键为field的域
     * @param key
     * @param field
     */
    public static void hdel(String key, String field) {
        redis.hdel(key, field);
        SysToolUtil.info("--- SysCacheUtil hdel : " + "key=" + key + ", field=" + field, SysCacheUtil.class);
    }

    /**
     * @description 初始化
     */
    public static void init() {
        /*Map<String, Object> map = new HashMap<String, Object>();
        map.put(INIT_PHONE_SERVICE, "400-0000-0000");
        map.put(INIT_ONLINE_SERVICE, "");
        map.put(INIT_COOPERATION_AISLE, "13856985541");
        expire(map.toString(), (int) SysToolUtil.dateAdd(new Date(), 1).getTime());*/
        //expire(INIT_PHONE_SERVICE, (int) SysToolUtil.dateAdd(new Date(), 1).getTime());
        set(INIT_PHONE_SERVICE, INIT_PHONE_SERVICE);
        set(INIT_COOPERATION_AISLE, INIT_COOPERATION_AISLE);
    }

}
