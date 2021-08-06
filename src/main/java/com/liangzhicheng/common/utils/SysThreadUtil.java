package com.liangzhicheng.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
 * @author liangzhicheng
 * @since 2021-08-06
 */
public class SysThreadUtil {

    /**
     * 构造器
     */
    private SysThreadUtil(){}

    /**
     * 创建一个可缓存线程池
     */
    private static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    /**
     * @description 获取缓存线程池
     * @return
     */
    public static ExecutorService getCachedThreadPool(){
        return cachedThreadPool;
    }

    /**
     * @description 开启线程刷新系统权限菜单列表
     */
    public static void threadListPermMenu(){
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                SysCacheUtil.refreshListPermMenu();
                SysToolUtil.info("------ 刷新 sys listPermMenu success ------");
            }
        });
    }

    /**
     * @description 开启线程刷新系统权限信息
     */
    public static void threadRolePerm(){
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                SysCacheUtil.refreshRolePerm();
                SysToolUtil.info("------ 刷新 sys rolePerm success ------");
            }
        });
    }

}
