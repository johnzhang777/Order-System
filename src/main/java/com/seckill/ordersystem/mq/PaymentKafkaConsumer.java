package com.seckill.ordersystem.mq;

import com.seckill.ordersystem.dto.PaymentRequestDTO;
import com.seckill.ordersystem.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Slf4j
@Component
public class PaymentKafkaConsumer {

    private static final String TOPIC = "payment-topic";

    @Resource
    private PaymentService paymentService;

    @KafkaListener(topics = TOPIC, groupId = "payment-group")
    public void consumePayment(PaymentRequestDTO dto) {
        try {
            paymentService.pay(dto);
        } catch (Exception e) {
            log.error("处理支付失败: {}", dto, e);
        }
    }
}

