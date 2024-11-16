package com.shuttle.sceneexer.circuitbreaker;

/**
 * @author: Shuttle
 * @description: 远程服务调用异常类
 */
public class RemoteServiceException extends Exception {

    public RemoteServiceException(String message) {
        super(message);
    }
}
