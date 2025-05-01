package com.seckill.ordersystem.entity;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SeckillActivity {
    private Long id;
    private Long productId;
    private BigDecimal seckillPrice;
    private Integer stock;
    private Integer initialStock;
    private Integer limitPerUser;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer status;

    @Version
    private Long version;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
