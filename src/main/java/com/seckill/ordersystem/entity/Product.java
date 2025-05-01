package com.seckill.ordersystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {
    private Long id;
    private String productName;
    private String description;
    private BigDecimal basePrice;
    private String mainImage;
    private Integer categoryId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
