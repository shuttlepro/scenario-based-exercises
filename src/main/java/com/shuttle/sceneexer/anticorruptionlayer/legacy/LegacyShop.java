package com.shuttle.sceneexer.anticorruptionlayer.legacy;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * @author: Shuttle
 * @description: LegacyShop
 */
@RequiredArgsConstructor
public class LegacyShop {

    private final LegacyStore store;

    public void placeOrder(LegacyOrder legacyOrder) {
        store.put(legacyOrder.getId(), legacyOrder);
    }

    public Optional<LegacyOrder> findOrder(String orderId) {
        return store.get(orderId);
    }

}
