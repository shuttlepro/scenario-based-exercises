package com.shuttle.sceneexer.circuitbreaker;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Shuttle
 * @description: 熔断器测试示例
 */
@Slf4j
public class CircuitBreakerApp {
    public static void main(String[] args) {
        long serverStartTime = System.nanoTime();

        // 创建两个远程服务，分别有 5s 的延迟和 0s 的延迟
        RemoteService delayedService = new DelayedRemoteService(serverStartTime, 5);
        CircuitBreaker delayedServiceCircuitBreaker = new DefaultCircuitBreaker(
                delayedService, 3000, 2, 2000 * 1000 * 1000);

        RemoteService quickService = new QuickRemoteService();
        CircuitBreaker quickServiceCircuitBreaker = new DefaultCircuitBreaker(
                quickService, 3000, 2, 2000 * 1000 * 1000);

        // 创建监控服务
        MonitoringService monitoringService = new MonitoringService(delayedServiceCircuitBreaker, quickServiceCircuitBreaker);

        // 模拟本地资源请求，success
        log.info(monitoringService.localResourceResponse());

        // 模拟延迟服务失败两次，服务被熔断
        log.info(monitoringService.delayedServiceResponse());
        log.info(monitoringService.delayedServiceResponse());

        // 此时状态为 OPEN
        log.info(delayedServiceCircuitBreaker.getState());

        // 模拟快速服务请求，状态为 CLOSED
        log.info(monitoringService.quickServiceResponse());
        log.info(quickServiceCircuitBreaker.getState());

        try {
            log.info("Waiting for delayed service to become responsive");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error("An error occurred: ", e);
        }

        // 在延时 5s 后尝试获取状态，此时为 HALF_OPEN
        log.info(delayedServiceCircuitBreaker.getState());
        // 再次尝试调用延时远程服务，success
        log.info(monitoringService.delayedServiceResponse());
        // 此时状态为 CLOSED
        log.info(delayedServiceCircuitBreaker.getState());
    }
}
