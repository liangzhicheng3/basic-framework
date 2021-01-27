package com.liangzhicheng.common.utils;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description StringRedis缓存工具类
 * @author liangzhicheng
 * @since 2021-01-27
 */
@Component
public class SysStringRedisUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @description key操作，设置key的存活时间
     * @param key
     * @param second
     * @return boolean
     */
    public boolean expire(String key, long second,TimeUnit unit){
        return stringRedisTemplate.expire(key, second, unit);
    }

    /**
     * @description 获取Map
     * @param mapName
     * @return Map
     */
    public Map<Object, Object> entries(String mapName) {
        HashOperations<String, Object, Object> ops = stringRedisTemplate.opsForHash();
        return ops.entries(mapName);
    }

    /**
     * @description 写操作，永久存活
     * @param key
     * @param value json格式字符串
     */
    public void set(String key, String value) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key, value);
    }

    /**
     * @description 写操作，可设置存活秒数
     * @param key
     * @param value
     */
    public void set(String key, String value, long count, TimeUnit timeUnit) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key, value, count, timeUnit);
    }

    /**
     * @description 写操作，向名称为key的set中添加元素member
     * @param key
     * @param value
     */
    public void sadd(String key, String value) {
        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        ops.add(key, value);
    }

    /**
     * @description 删除操作，删除名称为key的set中的元素member
     * @param key
     * @param value
     */
    public void srem(String key, String value){
        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        ops.remove(key, value);
    }

    /**
     * @description 读操作，返回名称为key的set的所有元素
     * @param key
     * @return Set
     */
    public Set<String> smembers(String key){
        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        return ops.members(key);
    }

    /**
     * @description 读操作，返回名称为key的set的指定数量随机元素
     * @param key
     * @return Set
     */
    public List<String> srandmember(String key, long count ){
        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        return ops.randomMembers(key, count);
    }

    /**
     * @description 删除操作，随机弹出名称为key的set集合指定数量的元素
     * @param key
     * @return Set
     */
    public List<String> spop(String key, long count ){
        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        return ops.pop(key, count);
    }

    /**
     * @description 在集合中随机获取count个不同的元素
     * @param key
     * @return Set
     */
    public Set<String> distinctRandomMembers(String key, long count ){
        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        return ops.distinctRandomMembers(key, count);
    }

    /**
     * @description 获取集合长度
     * @param key
     * @return Long
     */
    public Long size(String key){
        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        return ops.size(key);
    }

    /**
     * @description 复制集合A
     * @param key
     * @return Set<String>
     */
    public Set<String> intersect(String key){
        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        return ops.intersect(key, "");
    }

    /**
     * @description 将集合A和集合B合并后的结果存放到集合C中
     * @param key
     * @param otherKey
     * @param destKey
     * @return Long
     */
    public Long unionAndStore(String key, String otherKey, String destKey){
        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        return ops.unionAndStore(key, otherKey, destKey);
    }

    /**
     * @description 将集合A复制到集合C中
     * @param key
     * @param destKey
     * @return Long
     */
    public Long copySet(String key, String destKey){
        SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
        return ops.unionAndStore(key, "", destKey);
    }

    /**
     * @description 读操作
     * @param key
     * @return String
     */
    public String get(String key) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        return ops.get(key);
    }

    /**
     * @description 删除操作
     * @param key
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * @description 判断key是否存在
     * @param key
     * @return boolean
     */
    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * @description hash写操作，向名称为key的hash中添加元素field
     * @param key
     * @param field
     * @param value
     */
    public void hset(String key, String field, String value) {
        HashOperations<String, String, String> ops = stringRedisTemplate.opsForHash();
        ops.put(key, field, value);
    }

    /**
     * @description hash读操作，返回名称为key的hash中field对应的value
     * @param key
     * @param field
     * @return String
     */
    public String hget(String key, String field) {
        HashOperations<String, String, String> ops = stringRedisTemplate.opsForHash();
        return ops.get(key, field);
    }

    /**
     * @description 判断hash的某个map是否存在某个key
     * @param mapName
     * @param key
     * @return boolean
     */
    public boolean hHasKey(String mapName, String key) {
        HashOperations<String, String, String> ops = stringRedisTemplate.opsForHash();
        return ops.hasKey(mapName, key);
    }

    /**
     * @description hash删除操作，删除名称为key的hash中键为field的域
     * @param key
     * @param field
     */
    public void hdel(String key, String field) {
        HashOperations<String, String, String> ops = stringRedisTemplate.opsForHash();
        ops.delete(key, field);
    }

}
