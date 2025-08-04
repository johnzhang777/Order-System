package com.seckill.ordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponseDTO {
    private String orderNo;
    private Long userId;
    private Integer status; // 0-pending, 1-paid, 2-timeout, 3-failed, 4-canceled
}
