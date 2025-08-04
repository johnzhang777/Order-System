package com.seckill.ordersystem.controller;

import com.seckill.ordersystem.common.CommonResult;
import com.seckill.ordersystem.dto.PaymentRequestDTO;
import com.seckill.ordersystem.dto.PaymentResponseDTO;
import com.seckill.ordersystem.mq.PaymentKafkaProducer;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Resource
    private PaymentKafkaProducer paymentKafkaProducer;

    @PostMapping("/pay")
    public CommonResult<PaymentResponseDTO> pay(@RequestBody PaymentRequestDTO dto) {
        paymentKafkaProducer.sendPaymentMessage(dto);
        // 立即返回 pending，实际处理由消费者异步处理
        return CommonResult.success(new PaymentResponseDTO(dto.getOrderNo(), dto.getUserId(), 0));
    }
}
