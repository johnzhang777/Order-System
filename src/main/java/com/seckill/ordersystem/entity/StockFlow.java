package com.seckill.ordersystem.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockFlow {
    private Long id;
    private Long activityId;
    private Long userId;
    private String orderNo; // Reference to the order if applicable
    private String flowType; // 'RESERVE', 'CONFIRM', 'RELEASE', 'CANCLE'
    private Integer quantity;
    private Integer preStock; // Stock before the flow
    private Integer postStock; // Stock after the flow
    private LocalDateTime createTime;
}
