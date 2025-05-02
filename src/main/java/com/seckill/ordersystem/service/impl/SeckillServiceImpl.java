package com.seckill.ordersystem.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.seckill.ordersystem.dto.OrderMessage;
import com.seckill.ordersystem.mq.OrderKafkaProducer;
import com.seckill.ordersystem.service.SeckillService;
import com.seckill.ordersystem.util.LuaScriptUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;

@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private OrderKafkaProducer orderKafkaProducer;

    @Override
    public String seckill(Long activityId, Long userId, Integer quantity) {

        String redisKey = "seckill:stock:" + activityId;
        DefaultRedisScript<Long> script = LuaScriptUtil.stockDeductScript();
        Long result = stringRedisTemplate.execute(script, Collections.singletonList(redisKey), "1");

        if (result == null || result != 1L) {
            return "库存不足，已售罄";
        }

        String orderNo = IdWorker.getIdStr();
        stringRedisTemplate.opsForValue().set("seckill:order_status:" + orderNo, "PENDING", Duration.ofMinutes(5));
        OrderMessage message = new OrderMessage(activityId, userId, quantity, orderNo);
        orderKafkaProducer.sendOrderMessage(message);

        return "排队中，订单编号：" + orderNo;
    }
}
