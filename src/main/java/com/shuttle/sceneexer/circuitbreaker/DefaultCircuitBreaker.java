package com.shuttle.sceneexer.circuitbreaker;

/**
 * @author: Shuttle
 * @description: 默认的熔断器实现
 */
public class DefaultCircuitBreaker implements CircuitBreaker {

    /**
     * 服务调用超时时间
     */
    private final long timeout;
    /**
     * 熔断器重试时间间隔
     */
    private final long retryTimePeriod;
    /**
     * 被调用的远程服务
     */
    private final RemoteService service;
    /**
     * 上次调用失败的时间
     */
    long lastFailureTime;
    /**
     * 上次调用失败的响应
     */
    private String lastFailureResponse;
    /**
     * 当前调用失败的次数
     */
    int failureCount;
    /**
     * 熔断器熔断阈值
     */
    private final int failureThreshold;
    /**
     * 当前熔断器的状态
     */
    private State state;
    /**
     * 未来一段时间不会发生失败调用的时间间隔
     */
    private final long futureTime = 1_000_000_000_000L;

    /**
     * 默认熔断器的构造函数
     *
     * @param serviceToCall    将要被调用的远程服务
     * @param timeout          服务调用超时时间
     * @param failureThreshold 熔断器熔断阈值
     * @param retryTimePeriod  熔断器重试时间间隔
     */
    DefaultCircuitBreaker(RemoteService serviceToCall, long timeout, int failureThreshold, long retryTimePeriod) {
        this.service = serviceToCall;
        this.state = State.CLOSED;
        this.failureThreshold = failureThreshold;
        this.timeout = timeout;
        this.retryTimePeriod = retryTimePeriod;
        this.lastFailureTime = System.nanoTime() + futureTime;
        this.failureCount = 0;
    }

    /**
     * 记录成功调用，并重置熔断器的失败状态
     */
    @Override
    public void recordSuccess() {
        this.failureCount = 0;
        this.lastFailureTime = System.nanoTime() + futureTime;
        this.state = State.CLOSED;
    }

    /**
     * 记录失败调用，并处理熔断器状态
     *
     * @param response 失败响应
     */
    @Override
    public void recordFailure(String response) {
        failureCount = failureCount + 1;
        this.lastFailureTime = System.nanoTime();
        // 缓存熔断器打开时的最后响应
        this.lastFailureResponse = response;
    }

    /**
     * 评估当前熔断器的状态
     */
    protected void evaluateState() {
        // 失败调用次数大于等于熔断器熔断阈值
        if (failureCount >= failureThreshold) {
            // 检查是否已经过了重试时间间隔
            if ((System.nanoTime() - lastFailureTime) > retryTimePeriod) {
                // 将熔断器状态设为 HALT_OPEN，允许进行一次正常的服务调用
                state = State.HALF_OPEN;
            } else {
                // 熔断器处于 OPEN 状态
                state = State.OPEN;
            }
        } else {
            // 服务正常，熔断器状态为 CLOSED
            state = State.CLOSED;
        }
    }

    /**
     * 获取当前熔断器的状态
     *
     * @return 熔断器的状态
     */
    @Override
    public String getState() {
        evaluateState();
        return state.name();
    }

    /**
     * 设置熔断器状态
     *
     * @param state 需要设置的状态
     */
    @Override
    public void setState(State state) {
        this.state = state;
        switch (state) {
            case OPEN -> {
                this.failureCount = failureThreshold;
                this.lastFailureTime = System.nanoTime();
            }
            case HALF_OPEN -> {
                this.failureCount = failureThreshold;
                this.lastFailureTime = System.nanoTime() - retryTimePeriod;
            }
            default -> this.failureCount = 0;
        }
    }

    /**
     * 尝试调用远程服务，如果服务调用失败，则返回缓存的失败响应
     *
     * @return 调用结果
     * @throws RemoteServiceException 远程调用异常
     */
    @Override
    public String attemptRequest() throws RemoteServiceException {
        evaluateState();
        if (state == State.OPEN) {
            return this.lastFailureResponse;
        } else {
            try {
                String response = service.call();
                recordSuccess();
                return response;
            } catch (RemoteServiceException ex) {
                recordFailure(ex.getMessage());
                throw ex;
            }
        }
    }
}
