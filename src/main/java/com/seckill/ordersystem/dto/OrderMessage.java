package com.seckill.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderMessage {
    private Long activityId;
    private Long userId;
    private Integer quantity;
    private String orderNo;

    public OrderMessage() {}    // Default constructor for JsonDeserializer
}
