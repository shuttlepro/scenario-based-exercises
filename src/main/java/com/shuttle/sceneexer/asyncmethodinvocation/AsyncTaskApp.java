/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.shuttle.sceneexer.asyncmethodinvocation;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author: Shuttle
 * @description: AsyncTaskApp
 */

@Slf4j
public class AsyncTaskApp {

    private static final String ROCKET_LAUNCH_LOG_PATTERN = "Space rocket <%s> launched successfully";

    public static void main(String[] args) throws Exception {
        ThreadAsyncExecutor executor = new ThreadAsyncExecutor();

        AsyncResult<Integer> asyncResult1 = executor.startProcess(lazyval(10, 500));
        AsyncResult<String> asyncResult2 = executor.startProcess(lazyval("test", 300));
        AsyncResult<Long> asyncResult3 = executor.startProcess(lazyval(50L, 700));
        AsyncResult<Integer> asyncResult4 = executor.startProcess(lazyval(20, 400),
                callback("Deploying lunar rover"));
        AsyncResult<String> asyncResult5 =
                executor.startProcess(lazyval("callback", 600), callback("Deploying lunar rover"));

        Thread.sleep(350);
        log("Mission command is sipping coffee");

        Integer result1 = executor.endProcess(asyncResult1);
        String result2 = executor.endProcess(asyncResult2);
        Long result3 = executor.endProcess(asyncResult3);
        asyncResult4.await();
        asyncResult5.await();

        log(String.format(ROCKET_LAUNCH_LOG_PATTERN, result1));
        log(String.format(ROCKET_LAUNCH_LOG_PATTERN, result2));
        log(String.format(ROCKET_LAUNCH_LOG_PATTERN, result3));
    }

    private static <T> Callable<T> lazyval(T value, long delayMillis) {
        return () -> {
            Thread.sleep(delayMillis);
            log(String.format(ROCKET_LAUNCH_LOG_PATTERN, value));
            return value;
        };
    }

    private static <T> AsyncCallback<T> callback(String name) {
        return new AsyncCallback<>() {
            @Override
            public void onComplete(T value) {
                log(name + " <" + value + ">");
            }

            @Override
            public void onError(Exception ex) {
                log(name + " failed: " + ex.getMessage());
            }
        };
    }

    private static void log(String msg) {
        log.info(msg);
    }
}
