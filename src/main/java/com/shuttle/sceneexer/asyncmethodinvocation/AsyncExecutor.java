package com.shuttle.sceneexer.asyncmethodinvocation;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author: Shuttle
 * @description: AsyncExecutor
 */
public interface AsyncExecutor {

    /**
     * 开始执行异步任务
     *
     * @param task 异步执行的任务
     * @return 异步任务执行的结果
     */
    <T> AsyncResult<T> startProcess(Callable<T> task);

    /**
     * 开始执行异步任务，并在任务结束后执行回调
     *
     * @param task     异步执行的任务
     * @param callback 异步任务执行完成后的回调
     * @return 异步任务执行的结果
     */
    <T> AsyncResult<T> startProcess(Callable<T> task, AsyncCallback<T> callback);

    /**
     * 阻塞当前线程，直到异步任务执行完成。
     *
     * @param asyncResult 异步任务执行的结果对象
     * @return 异步任务执行的结果值
     * @throws ExecutionException   执行异常
     * @throws InterruptedException 线程中断异常
     */
    <T> T endProcess(AsyncResult<T> asyncResult) throws ExecutionException, InterruptedException;

}
