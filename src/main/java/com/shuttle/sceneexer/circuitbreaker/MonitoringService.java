package com.shuttle.sceneexer.circuitbreaker;

import lombok.RequiredArgsConstructor;

/**
 * @author: Shuttle
 * @description: 远程服务监视器
 */
@RequiredArgsConstructor
public class MonitoringService {

    private final CircuitBreaker delayedService;
    private final CircuitBreaker quickService;

    /**
     * 模拟本地资源调用
     *
     * @return 本地资源的响应
     */
    public String localResourceResponse() {
        return "Local Service is working";
    }

    /**
     * 监视器充当远程服务调用的代理，模拟调用延时服务
     *
     * @return 延时服务的响应
     */
    public String delayedServiceResponse() {
        try {
            return this.delayedService.attemptRequest();
        } catch (RemoteServiceException e) {
            return e.getMessage();
        }
    }

    /**
     * 监视器充当远程服务调用的代理，模拟调用快速服务
     *
     * @return 快速服务的响应
     */
    public String quickServiceResponse() {
        try {
            return this.quickService.attemptRequest();
        } catch (RemoteServiceException e) {
            return e.getMessage();
        }
    }
}