package com.shuttle.sceneexer.circuitbreaker;

/**
 * @author: Shuttle
 * @description: 熔断器接口
 */
public interface CircuitBreaker {

    /**
     * 记录成功地调用，还原失败次数
     */
    void recordSuccess();

    /**
     * 记录失败地调用，并缓存失败结果
     *
     * @param response 失败响应
     */
    void recordFailure(String response);

    /**
     * 获取当前熔断器的状态
     *
     * @return 熔断器的状态
     */
    String getState();

    /**
     * 设置熔断器的状态
     *
     * @param state 需要设置的状态
     */
    void setState(State state);

    /**
     * 尝试获取远程服务响应
     *
     * @return 远程服务响应
     * @throws RemoteServiceException 远程服务调用异常
     */
    String attemptRequest() throws RemoteServiceException;
}
