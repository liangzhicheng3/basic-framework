package com.liangzhicheng.common.utils;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description Redis缓存工具类
 * @author liangzhicheng
 * @since 2020-08-10
 */
@Component
public class SysRedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @description key操作，设置某个key的存活时间
     * @param key
     * @param second
     * @return boolean
     */
    public boolean expire(String key, int second){
        return redisTemplate.expire(key, second, TimeUnit.SECONDS);
    }

    /**
     * @description 获取Map
     * @param mapName
     * @return Map
     */
    public Map<String, Object> entries(String mapName) {
        HashOperations<String, String, Object> ops = redisTemplate.opsForHash();
        return ops.entries(mapName);
    }

    /**
     * @description 写操作，永久存活
     * @param key
     * @param obj
     */
    public void set(String key, Object obj) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(key, obj);
    }

    /**
     * @description 写操作，可设置存活秒数
     * @param key
     * @param obj
     * @param second
     */
    public void set(String key, Object obj, int second) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(key, obj, second, TimeUnit.SECONDS);
    }

    /**
     * @description 写操作，向名称为key的set中添加元素member
     * @param key
     * @param obj
     */
    public void sadd(String key, Object obj) {
        SetOperations<String, Object> ops = redisTemplate.opsForSet();
        ops.add(key, obj);
    }

    /**
     * @description 删除操作，删除名称为key的set中的元素member
     * @param key
     * @param obj
     */
    public void srem(String key, Object obj){
        SetOperations<String, Object> ops = redisTemplate.opsForSet();
        ops.remove(key, obj);
    }

    /**
     * @description 读操作，返回名称为key的set的所有元素
     * @param key
     * @return Set
     */
    public Set<Object> smembers(String key){
        SetOperations<String, Object> ops = redisTemplate.opsForSet();
        return ops.members(key);
    }

    /**
     * @description 读操作
     * @param key
     * @return Object
     */
    public Object get(String key) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    /**
     * @description 删除操作
     * @param key
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * @description 判断key是否存在
     * @param key
     * @return boolean
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @description hash写操作，向名称为key的hash中添加元素field
     * @param key
     * @param field
     * @param obj
     */
    public void hset(String key, String field, Object obj) {
        HashOperations<String, String, Object> ops = redisTemplate.opsForHash();
        ops.put(key, field, obj);
    }

    /**
     * @description hash读操作，返回名称为key的hash中field对应的value
     * @param key
     * @param field
     * @return Object
     */
    public Object hget(String key, String field) {
        HashOperations<String, String, Object> ops = redisTemplate.opsForHash();
        return ops.get(key, field);
    }

    /**
     * @description 判断hash的某个map是否存在某个key
     * @param mapName
     * @param key
     * @return boolean
     */
    public boolean hHasKey(String mapName, String key) {
        HashOperations<String, String, Object> ops = redisTemplate.opsForHash();
        return ops.hasKey(mapName, key);
    }

    /**
     * @description hash删除操作，删除名称为key的hash中键为field的域
     * @param key
     * @param field
     */
    public void hdel(String key, String field) {
        HashOperations<String, String, Object> ops = redisTemplate.opsForHash();
        ops.delete(key, field);
    }

}
