package com.seckill.ordersystem.controller;

import com.seckill.ordersystem.service.SeckillService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seckill")
public class SeckillController {
    @Resource
    private SeckillService seckiillService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @PostMapping("/{activityId}")
    public String seckill(@PathVariable Long activityId, @RequestParam Long userId, @RequestParam Integer quantity) {
        return seckiillService.seckill(activityId, userId, quantity);
    }

    @GetMapping("/init/{activityId}")
    public String init(@PathVariable Long activityId) {
        stringRedisTemplate.opsForValue().set("seckill:stock:" + activityId, "10");
        return "库存初始化完成";
    }

    @GetMapping("/order/status/{orderNo}")
    public String getOrderStatus(@PathVariable String orderNo) {
        String status = stringRedisTemplate.opsForValue().get("seckill:order_status:" + orderNo);
        return status != null ? status : "NOT_FOUND";
    }

}
