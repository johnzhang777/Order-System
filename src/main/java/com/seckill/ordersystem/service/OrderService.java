package com.seckill.ordersystem.service;

public interface OrderService {
    void createOrder(Long userId, Long activityId, Integer quantity);
}
