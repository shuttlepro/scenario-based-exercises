package com.shuttle.sceneexer.anticorruptionlayer.modern;

import com.shuttle.sceneexer.anticorruptionlayer.acl.AntiCorruptionLayer;
import com.shuttle.sceneexer.anticorruptionlayer.exception.ShopException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * @author: Shuttle
 * @description: ModernShop
 */

@RequiredArgsConstructor
public class ModernShop {

    private final ModernStore store;
    private final AntiCorruptionLayer antiCorruptionLayer;

    public void placeOrder(ModernOrder order) {
        String id = order.getId();
        // check if the order is already present in the legacy system
        Optional<ModernOrder> orderInObsoleteSystem = antiCorruptionLayer.findOrderInLegacySystem(id);

        if (orderInObsoleteSystem.isPresent()) {
            ModernOrder legacyOrder = orderInObsoleteSystem.get();
            if (!order.equals(legacyOrder)) {
                throw ShopException.throwDuplicateOrderException(legacyOrder.toString(), order.toString());
            }
        } else {
            store.put(id, order);
        }
    }

    /**
     * Finds the order in the modern system.
     */
    public Optional<ModernOrder> findOrder(String orderId) {
        return store.get(orderId);
    }

}
