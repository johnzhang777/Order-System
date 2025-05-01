package com.seckill.ordersystem.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetail {
    private Long id;
    private String orderNo;
    private Long productId;
    private Integer quantity;
    private BigDecimal singlePrice;
    private String snapshotData;

}
