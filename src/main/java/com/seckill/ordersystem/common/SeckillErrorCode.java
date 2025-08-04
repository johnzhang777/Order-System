package com.seckill.ordersystem.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeckillErrorCode implements ApiCode {
    STOCK_NOT_ENOUGH(1001, "Stock not enough"),
    ACTIVITY_NOT_FOUND(1002, "Activity not found"),
    REPEAT_PURCHASE(1003, "Repeat purchase"),
    INVALID_QUANTITY(1004, "Invalid quantity"),
    ORDER_NOT_FOUND(1005, "订单不存在"),
    ORDER_STATUS_INVALID(1006, "订单状态不合法"),
    ACTIVITY_CREATE_FAILED(1007, "秒杀活动创建失败");

    private final int code;
    private final String message;
}
