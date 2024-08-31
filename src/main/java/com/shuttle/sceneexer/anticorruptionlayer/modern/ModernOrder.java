package com.shuttle.sceneexer.anticorruptionlayer.modern;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author: Shuttle
 * @description: ModernOrder
 */

@Data
@AllArgsConstructor
public class ModernOrder {

    /**
     * 订单 ID
     */
    private String id;

    /**
     * 用户
     */
    private Customer customer;

    /**
     * 条目详情
     */
    private List<ItemDetail> itemDetails;

    /**
     * 附加信息
     */
    private String extra;

}
