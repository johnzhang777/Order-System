package com.seckill.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderResponseDTO {
    private String orderNo;
    private Long userId;
    private Long activityId;
    private Integer quantity;
    private BigDecimal totalPrice;

    public OrderResponseDTO() {}    // Default constructor for JsonDeserializer
}
