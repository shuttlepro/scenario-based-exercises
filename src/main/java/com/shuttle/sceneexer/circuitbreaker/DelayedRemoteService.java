package com.shuttle.sceneexer.circuitbreaker;

/**
 * @author: Shuttle
 * @description: 远程服务的一种实现，模拟延迟响应的场景
 */
public class DelayedRemoteService implements RemoteService {

    /**
     * 服务启动时间
     */
    private final long serverStartTime;
    /**
     * 延时时间
     */
    private final int delay;

    /**
     * 延时服务构造器
     *
     * @param serverStartTime 服务启动时间
     * @param delay           延时秒数
     */
    public DelayedRemoteService(long serverStartTime, int delay) {
        this.serverStartTime = serverStartTime;
        this.delay = delay;
    }

    /**
     * 延时服务无参构造器
     */
    public DelayedRemoteService() {
        this.serverStartTime = System.nanoTime();
        this.delay = 20;
    }

    /**
     * 服务调用
     *
     * @return 调用结果
     * @throws RemoteServiceException 远程服务调用异常
     */
    @Override
    public String call() throws RemoteServiceException {
        long currentTime = System.nanoTime();

        // 如果当前时间小于服务启动时间加上延时时间，则抛出异常
        if ((currentTime - serverStartTime) * 1.0 / (1000 * 1000 * 1000) < delay) {
            // 这里也可以等待服务启动后正常返回结果
            throw new RemoteServiceException("Delayed service is down");
        }

        return "Delayed service is working";
    }
}
