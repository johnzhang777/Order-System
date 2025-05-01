package com.seckill.ordersystem.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessControl {
    private Long userId;
    private Long activityId;
    private Integer accessCount;
    private LocalDateTime lastAccessTime;
}
