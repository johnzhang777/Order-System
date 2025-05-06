package com.seckill.ordersystem.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeckillErrorCode implements ApiCode {
    STOCK_NOT_ENOUGH(1001, "Stock not enough"),
    ACTIVITY_NOT_FOUND(1002, "Activity not found"),
    REPEAT_PURCHASE(1003, "Repeat purchase"),
    INVALID_QUANTITY(1004, "Invalid quantity"),;

    private final int code;
    private final String message;
}
