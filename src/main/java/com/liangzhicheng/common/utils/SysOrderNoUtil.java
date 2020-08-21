package com.liangzhicheng.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description 订单号生成工具类
 * @author liangzhicheng
 * @since 2020-07-30
 */
public class SysOrderNoUtil {

    //使用单例模式，不允许直接创建实例
    private SysOrderNoUtil(){}
    //创建一个空实例对象，类需要用的时候才赋值
    private static SysOrderNoUtil instance = null;
    //单例模式->懒汉模式
    public static synchronized SysOrderNoUtil getInstance(){
        if (instance == null){
            instance = new SysOrderNoUtil();
        }
        return instance;
    }

    //定义锁对象
    private static final ReentrantLock lock = new ReentrantLock();
    //格式化时间字符串
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    //记录上一次时间，用来判断是否需要递增全局数
    private static String now = null;
    //全局自增数
    private static int count = 1;

    //调用的方法
    public static String generateOrderNo(){
        String orderNo = null;
        String dateStr = getCurrentDateStr();
        lock.lock(); //加锁
        if(dateStr.equals(now)){ //判断是时间是否相同
            orderNo = orderNoDispose(orderNo);
        }else{
            now = getCurrentDateStr();
            orderNo = orderNoDispose(orderNo);
        }
        return orderNo;
    }

    //订单号处理
    private static String orderNoDispose(String orderNo){
        try {
            if(count >= 10000){
                count = 1;
            }
            if(count < 10){
                orderNo = "YC" + getCurrentDateStr() + "000" + count + SysToolUtil.random();
            }else if(count < 100){
                orderNo = "YC" + getCurrentDateStr() + "00" + count + SysToolUtil.random();
            }else if(count < 1000){
                orderNo = "YC" + getCurrentDateStr() + "0" + count + SysToolUtil.random();
            }else{
                orderNo = "YC" + getCurrentDateStr() + count + SysToolUtil.random();
            }
            count++;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
        return orderNo;
    }

    //获取当前时间年月日时分秒毫秒字符串
    private static String getCurrentDateStr(){
        return sdf.format(new Date());
    }

}
