package com.shuttle.sceneexer.anticorruptionlayer;

import com.shuttle.sceneexer.anticorruptionlayer.acl.AntiCorruptionLayer;
import com.shuttle.sceneexer.anticorruptionlayer.legacy.LegacyOrder;
import com.shuttle.sceneexer.anticorruptionlayer.legacy.LegacyShop;
import com.shuttle.sceneexer.anticorruptionlayer.legacy.LegacyStore;
import com.shuttle.sceneexer.anticorruptionlayer.modern.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: Shuttle
 * @description: ShopApp
 */
@Slf4j
public class ShopApp {
    public static void main(String[] args) {
        LegacyShop legacyShop = new LegacyShop(new LegacyStore());
        legacyShop.placeOrder(new LegacyOrder("L1001", "John", "Pencil", 2, new BigDecimal("1.99")));
        log.info("LegacyShop placeOrder:{}", legacyShop.findOrder("L1001"));

        legacyShop.placeOrder(new LegacyOrder("L1002", "Mary", "Notebook", 5, new BigDecimal("7.50")));
        log.info("LegacyShop placeOrder:{}", legacyShop.findOrder("L1002"));
        log.info("LegacyShop findOrder:{}", legacyShop.findOrder("L1003"));

        log.info("-----Convert to ModernShop-----");

        AntiCorruptionLayer antiCorruptionLayer = new AntiCorruptionLayer(legacyShop);
        log.info("AntiCorruptionLayer get order:{} from legacy shop", antiCorruptionLayer.findOrderInLegacySystem("L1001"));

        ModernShop modernShop = new ModernShop(new ModernStore(), antiCorruptionLayer);
        modernShop.placeOrder(
                new ModernOrder(
                        "M1001",
                        new Customer("Alex"),
                        List.of(new ItemDetail("Computer", 1, new BigDecimal("2099.99")),
                                new ItemDetail("Mouse", 1, new BigDecimal("50.99"))),
                        "Good Luck"
                ));
        log.info("ModernShop placeOrder:{}", modernShop.findOrder("M1001"));
        log.info("ModernShop findOrder:{}", modernShop.findOrder("M1002"));

        // 新旧系统产生了一个重复的订单号，customer 不一样，抛出异常
        modernShop.placeOrder(
                new ModernOrder(
                        "L1002",
                        new Customer("Alex"),
                        List.of(new ItemDetail("Notebook", 5, new BigDecimal("7.50"))),
                        null
                )
        );
    }
}
