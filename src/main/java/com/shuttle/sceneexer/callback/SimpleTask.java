package com.shuttle.sceneexer.callback;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Shuttle
 * @description: Task 类的一种简单实现
 */
@Slf4j
public final class SimpleTask extends Task {

    @Override
    public void execute() {
        log.info("SimpleTask execute...");
    }
}
