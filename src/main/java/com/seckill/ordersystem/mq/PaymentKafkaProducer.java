package com.seckill.ordersystem.mq;

import com.seckill.ordersystem.dto.PaymentRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Component
@Slf4j
public class PaymentKafkaProducer {

    private static final String TOPIC = "payment-topic";

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentMessage(PaymentRequestDTO request) {
        kafkaTemplate.send(TOPIC, request);
        log.info("发送支付消息: {}", request);
    }
}
