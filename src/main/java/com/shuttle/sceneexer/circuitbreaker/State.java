package com.shuttle.sceneexer.circuitbreaker;

/**
 * @author: Shuttle
 * @description: 熔断器状态枚举
 */
public enum State {
    CLOSED,
    OPEN,
    HALF_OPEN
}