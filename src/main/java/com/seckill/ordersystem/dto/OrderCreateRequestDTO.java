package com.seckill.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCreateRequestDTO {
    private Long activityId;
    private Long userId;
    private Integer quantity;
    private String orderNo;

    public OrderCreateRequestDTO() {}    // Default constructor for JsonDeserializer
}
