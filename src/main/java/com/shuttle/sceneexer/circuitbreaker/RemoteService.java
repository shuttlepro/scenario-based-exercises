package com.shuttle.sceneexer.circuitbreaker;

/**
 * @author: Shuttle
 * @description: 远程服务接口
 */
public interface RemoteService {

    /**
     * 调用远程服务
     *
     * @return 服务响应
     * @throws RemoteServiceException 远程服务调用异常
     */
    String call() throws RemoteServiceException;
}
