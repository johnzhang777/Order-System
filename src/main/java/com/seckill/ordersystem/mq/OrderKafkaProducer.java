package com.seckill.ordersystem.mq;

import com.seckill.ordersystem.dto.OrderCreateRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderKafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderMessage(OrderCreateRequestDTO message) {
        // ✅ 异步发送 + 回调
        kafkaTemplate.send("order-topic", message)
                .thenAccept(success -> {
                    log.info("消息发送成功: {}", message.getOrderNo());
                })
                .exceptionally(failure -> {
                    log.error("消息发送失败: {}", message.getOrderNo(), failure);
                    // 可写入失败队列，后续重试
                    return null;
                });
    }
}
