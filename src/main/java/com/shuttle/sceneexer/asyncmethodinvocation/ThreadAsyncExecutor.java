package com.shuttle.sceneexer.asyncmethodinvocation;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author: Shuttle
 * @description: ThreadAsyncExecutor
 */
public class ThreadAsyncExecutor implements AsyncExecutor {

    @Override
    public <T> AsyncResult<T> startProcess(Callable<T> task) {
        return startProcess(task, null);
    }

    @Override
    public <T> AsyncResult<T> startProcess(Callable<T> task, AsyncCallback<T> callback) {
        CompletableResult<T> result = new CompletableResult<>(callback);
        IOIntensiveThreadPool.getThreadPool().execute(() -> {
            try {
                result.setValue(task.call());
            } catch (Exception ex) {
                result.setException(ex);
            }
        });
        return result;
    }

    @Override
    public <T> T endProcess(AsyncResult<T> asyncResult) throws ExecutionException, InterruptedException {
        if (!asyncResult.isCompleted()) {
            asyncResult.await();
        }
        return asyncResult.getValue();
    }

    /**
     * 异步结果的简单实现
     */
    private static class CompletableResult<T> implements AsyncResult<T> {

        /**
         * 任务运行中
         */
        static final int RUNNING = 1;
        /**
         * 任务执行失败
         */
        static final int FAILED = 2;
        /**
         * 任务完成
         */
        static final int COMPLETED = 3;

        /**
         * 对象锁
         */
        final Object lock;
        /**
         * 回调函数
         */
        final AsyncCallback<T> callback;

        /**
         * 当前任务状态，需要加 volatile 保证变量可见性
         */
        volatile int state = RUNNING;
        /**
         * 任务结果
         */
        T value;
        /**
         * 执行任务中捕获的异常
         */
        Exception exception;

        CompletableResult(AsyncCallback<T> callback) {
            this.lock = new Object();
            this.callback = callback;
        }

        boolean hasCallback() {
            return callback != null;
        }

        void setValue(T value) {
            this.value = value;
            this.state = COMPLETED;
            if (hasCallback()) {
                callback.onComplete(value);
            }
            synchronized (lock) {
                lock.notifyAll();
            }
        }

        void setException(Exception exception) {
            this.exception = exception;
            this.state = FAILED;
            if (hasCallback()) {
                callback.onError(exception);
            }
            synchronized (lock) {
                lock.notifyAll();
            }
        }

        @Override
        public boolean isCompleted() {
            return state > RUNNING;
        }

        @Override
        public T getValue() throws ExecutionException {
            if (state == COMPLETED) {
                return value;
            } else if (state == FAILED) {
                throw new ExecutionException(exception);
            } else {
                throw new IllegalStateException("Execution not completed yet");
            }
        }

        @Override
        public void await() throws InterruptedException {
            synchronized (lock) {
                while (!isCompleted()) {
                    lock.wait();
                }
            }
        }
    }
}
