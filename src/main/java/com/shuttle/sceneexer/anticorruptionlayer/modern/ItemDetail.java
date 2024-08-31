package com.shuttle.sceneexer.anticorruptionlayer.modern;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: Shuttle
 * @description: ItemDetail
 */

@Data
@AllArgsConstructor
public class ItemDetail {

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
