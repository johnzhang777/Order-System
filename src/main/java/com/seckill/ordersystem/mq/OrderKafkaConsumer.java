package com.seckill.ordersystem.mq;

import com.seckill.ordersystem.dto.OrderCreateRequestDTO;
import com.seckill.ordersystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderKafkaConsumer {
    private final OrderService orderService;
    private final StringRedisTemplate stringRedisTemplate;

    @KafkaListener(topics = "order-topic", groupId = "order-group")
    public void consumeOrder(OrderCreateRequestDTO message) {
        log.info("Received order message: {}", message);
        try {
            orderService.createOrder(message.getUserId(), message.getActivityId(), message.getQuantity(), message.getOrderNo());
            stringRedisTemplate.opsForValue().set("seckill:order_status:" + message.getOrderNo(), "SUCCESS", Duration.ofMinutes(5));
        } catch (Exception e) {
            stringRedisTemplate.opsForValue().set("seckill:order_status:" + message.getOrderNo(), "FAILED", Duration.ofMinutes(5));
        }
    }
}
