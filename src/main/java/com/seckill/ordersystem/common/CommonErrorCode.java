package com.seckill.ordersystem.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ApiCode {
    SUCCESS(0, "Success"),
    UNKNOWN_ERROR(9999, "System Error"),;

    private final int code;
    private final String message;
}
