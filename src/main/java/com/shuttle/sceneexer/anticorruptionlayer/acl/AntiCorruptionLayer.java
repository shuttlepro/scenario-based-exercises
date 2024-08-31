package com.shuttle.sceneexer.anticorruptionlayer.acl;

import com.shuttle.sceneexer.anticorruptionlayer.legacy.LegacyShop;
import com.shuttle.sceneexer.anticorruptionlayer.modern.Customer;
import com.shuttle.sceneexer.anticorruptionlayer.modern.ItemDetail;
import com.shuttle.sceneexer.anticorruptionlayer.modern.ModernOrder;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * @author: Shuttle
 * @description: AntiCorruptionLayer
 */

@RequiredArgsConstructor
public class AntiCorruptionLayer {

    private final LegacyShop legacyShop;

    public Optional<ModernOrder> findOrderInLegacySystem(String id) {
        return legacyShop.findOrder(id)
                .map(o -> new ModernOrder(
                                o.getId(),
                                new Customer(o.getUserName()),
                                List.of(new ItemDetail(o.getProductName(), o.getQuantity(), o.getPrice())),
                                null
                        )
                );
    }

}
