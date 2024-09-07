package com.shuttle.sceneexer.asyncmethodinvocation;

import java.util.concurrent.ExecutionException;

/**
 * @author: Shuttle
 * @description: AsyncResult
 */
public interface AsyncResult<T> {

    /**
     * 判断异步任务是否完成
     *
     * @return 异步任务是否完成
     */
    boolean isCompleted();

    /**
     * 异步任务完成后返回值
     *
     * @return 异步任务返回值
     * @throws ExecutionException 执行异常
     */
    T getValue() throws ExecutionException;

    /**
     * 阻塞当前线程直到异步任务执行结束
     *
     * @throws InterruptedException 线程中断异常
     */
    void await() throws InterruptedException;

}
