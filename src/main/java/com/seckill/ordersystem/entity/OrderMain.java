package com.seckill.ordersystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderMain {
    private Long id;
    private String orderNo;
    private Long userId;
    private Long activityId;
    private BigDecimal totalPrice;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime payTime;
}
