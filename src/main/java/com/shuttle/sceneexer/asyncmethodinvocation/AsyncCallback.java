package com.shuttle.sceneexer.asyncmethodinvocation;

/**
 * @author: Shuttle
 * @description: AsyncCallback
 */
public interface AsyncCallback<T> {

    /**
     * 当异步任务执行完成后需要调用的方法
     *
     * @param value 异步任务执行结果
     */
    void onComplete(T value);

    /**
     * 当异步任务执行失败后需要调用的方法
     *
     * @param e 异步任务执行过程中抛出的异常信息
     */
    void onError(Exception e);

}
