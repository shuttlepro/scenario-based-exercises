package com.shuttle.sceneexer.circuitbreaker;

/**
 * @author: Shuttle
 * @description: 远程服务的一种实现，模拟快速响应的场景
 */
public class QuickRemoteService implements RemoteService {

    /**
     * 快速服务调用
     *
     * @return 服务响应
     * @throws RemoteServiceException 远程调用异常
     */
    @Override
    public String call() throws RemoteServiceException {
        return "Quick Service is working";
    }
}
