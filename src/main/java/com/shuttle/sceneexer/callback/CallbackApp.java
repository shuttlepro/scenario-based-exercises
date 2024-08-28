package com.shuttle.sceneexer.callback;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Shuttle
 * @description: 回调应用
 */
@Slf4j
public class CallbackApp {

    private CallbackApp() {
    }

    public static void main(String[] args) {
        Task simpleTask = new SimpleTask();
        simpleTask.executeWith(() -> log.info("Callback function doing now..."));
    }
}
