package com.seckill.ordersystem.service;

import com.seckill.ordersystem.common.CommonResult;
import com.seckill.ordersystem.dto.ActivityCreateRequestDTO;
import com.seckill.ordersystem.dto.ActivityCreateResponseDTO;
import com.seckill.ordersystem.dto.OrderResponseDTO;

public interface SeckillService {
    CommonResult<OrderResponseDTO> seckill(Long activityId, Long userId, Integer quantity);
    CommonResult<ActivityCreateResponseDTO> createSeckillActivity(ActivityCreateRequestDTO dto);
}
