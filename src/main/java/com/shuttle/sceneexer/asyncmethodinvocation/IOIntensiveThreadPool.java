package com.shuttle.sceneexer.asyncmethodinvocation;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: Shuttle
 * @description: IOIntensiveThreadPool
 */
public class IOIntensiveThreadPool {

    /**
     * CPU 核数
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * IO 线程池最大线程数
     */
    private static final int IO_MAX_THREAD_COUNT = Math.max(2, CPU_COUNT * 2);

    /**
     * 空闲保活时限，单位为秒
     */
    private static final int KEEP_ALIVE_SECONDS = 60;

    /**
     * 有界队列大小
     */
    private static final int QUEUE_SIZE = 256;

    /**
     * 线程池标签
     */
    private static final String THREAD_POOL_TAG = "IO-Intensive";

    /**
     * 懒汉式单例创建线程池
     */
    private static class IOIntensiveThreadPoolLazyHolder {

        private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
                IO_MAX_THREAD_COUNT,
                IO_MAX_THREAD_COUNT,
                KEEP_ALIVE_SECONDS,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(QUEUE_SIZE),
                new CustomThreadFactory(THREAD_POOL_TAG));
    }

    public static ThreadPoolExecutor getThreadPool() {
        return IOIntensiveThreadPoolLazyHolder.EXECUTOR;
    }
}
