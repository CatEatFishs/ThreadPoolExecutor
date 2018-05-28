package com.lm.threadpoolexecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by LiMing on 2018/5/10.
 * QQ：1002464056
 * Email: 1002464056@qq.com
 * Version：1.0
 * 自定义线程池
 */

public class ThreadManager {
    public static ThreadPool instance;
    //OkHttp 线程池 核心线程数 5 最大线程数64（OKHttp有两个队列）
    public static int corePoolSize = 5;
    public static int maximumPoolSize = 64;

    // 获取单例的线程池对象
    public static ThreadPool getInstance() {
        if (instance == null) {
            synchronized (ThreadManager.class) {
                if (instance == null) {
                    instance = new ThreadPool(corePoolSize, maximumPoolSize, Integer.MAX_VALUE);
                }
            }
        }
        return instance;
    }

    public static class ThreadPool {
        private ThreadPoolExecutor mExecutor;
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;

        private ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        public void execute(Runnable runnable) {
            if (runnable == null) {
                return;
            }
            if (mExecutor == null) {
                mExecutor = new ThreadPoolExecutor(corePoolSize,// 核心线程数
                        maximumPoolSize, // 最大线程数
                        keepAliveTime, // 闲置线程存活时间
                        TimeUnit.MILLISECONDS,// 时间单位
                        new LinkedBlockingQueue<Runnable>(Integer.MAX_VALUE),// 线程队列
                        Executors.defaultThreadFactory(),// 线程工厂
                        new ThreadPoolExecutor.AbortPolicy() {// 队列已满,而且当前线程数已经超过最大线程数时的异常处理策略
                            @Override
                            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                                super.rejectedExecution(r, e);
                            }
                        }
                );
            }
            mExecutor.execute(runnable);
        }


        // 从线程队列中移除对象
        public void cancel(Runnable runnable) {
            if (mExecutor != null) {
                mExecutor.getQueue().remove(runnable);
            }
        }

    }
}

