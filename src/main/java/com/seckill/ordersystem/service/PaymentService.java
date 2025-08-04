package com.seckill.ordersystem.service;

import com.seckill.ordersystem.dto.PaymentRequestDTO;

public interface PaymentService {
    void pay(PaymentRequestDTO request);
}
