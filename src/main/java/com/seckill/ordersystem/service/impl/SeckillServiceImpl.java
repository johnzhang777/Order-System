package com.seckill.ordersystem.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.seckill.ordersystem.common.CommonResult;
import com.seckill.ordersystem.common.SeckillErrorCode;
import com.seckill.ordersystem.dto.OrderCreateRequestDTO;
import com.seckill.ordersystem.dto.OrderResponseDTO;
import com.seckill.ordersystem.entity.SeckillActivity;
import com.seckill.ordersystem.mapper.SeckillActivityMapper;
import com.seckill.ordersystem.mq.OrderKafkaProducer;
import com.seckill.ordersystem.service.SeckillService;
import com.seckill.ordersystem.util.LuaScriptUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;

@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private OrderKafkaProducer orderKafkaProducer;

    @Resource
    private SeckillActivityMapper seckillActivityMapper;

    @Override
    public CommonResult<OrderResponseDTO> seckill(Long activityId, Long userId, Integer quantity) {

        String redisKey = "seckill:stock:" + activityId;
        DefaultRedisScript<Long> script = LuaScriptUtil.stockDeductScript();
        Long result = stringRedisTemplate.execute(script, Collections.singletonList(redisKey), "1");

        if (result == null || result != 1L) {
            return CommonResult.fail(SeckillErrorCode.STOCK_NOT_ENOUGH);
        }

        String orderNo = IdWorker.getIdStr();
        stringRedisTemplate.opsForValue().set("seckill:order_status:" + orderNo, "PENDING", Duration.ofMinutes(5));
        OrderCreateRequestDTO message = new OrderCreateRequestDTO(activityId, userId, quantity, orderNo);
        orderKafkaProducer.sendOrderMessage(message);

        SeckillActivity seckillActivity = seckillActivityMapper.selectById(activityId);
        if (seckillActivity == null) {
            return CommonResult.fail(SeckillErrorCode.ACTIVITY_NOT_FOUND);
        }
        BigDecimal totalPrice = seckillActivity.getSeckillPrice().multiply(BigDecimal.valueOf(quantity));
        OrderResponseDTO dto = new OrderResponseDTO(orderNo, userId, activityId, quantity, totalPrice);
        return CommonResult.success(dto);
    }
}
