package com.shuttle.sceneexer.anticorruptionlayer.legacy;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: Shuttle
 * @description: LegacyOrder
 */

@Data
@AllArgsConstructor
public class LegacyOrder {

    /**
     * 订单 ID
     */
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 商品名
     */
    private String productName;

    /**
     * 数量
     */
    private int quantity;

    /**
     * 价格
     */
    private BigDecimal price;

}
