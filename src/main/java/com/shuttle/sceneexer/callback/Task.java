package com.shuttle.sceneexer.callback;

import java.util.Optional;

/**
 * @author: Shuttle
 * @description: 工作任务抽象类
 */
public abstract class Task {

    /**
     * 执行方法并进行回调
     *
     * @param callback 回调对象
     */
    public final void executeWith(Callback callback) {
        execute();
        // 执行回调
        Optional.ofNullable(callback).ifPresent(Callback::call);
    }

    /**
     * 抽象执行方法，交由子类实现
     */
    public abstract void execute();
}
