package com.seckill.ordersystem.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(CommonErrorCode.SUCCESS.getCode(), CommonErrorCode.SUCCESS.getMessage(), data);
    }

    public static <T> CommonResult<T> fail(ApiCode code) {
        return new CommonResult<>(code.getCode(), code.getMessage(), null);
    }

    public static <T> CommonResult<T> fail(ApiCode code, T data) {
        return new CommonResult<>(code.getCode(), code.getMessage(), data);
    }
}
