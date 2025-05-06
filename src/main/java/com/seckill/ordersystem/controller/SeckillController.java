package com.seckill.ordersystem.controller;

import com.seckill.ordersystem.common.CommonResult;
import com.seckill.ordersystem.dto.OrderResponseDTO;
import com.seckill.ordersystem.service.SeckillService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import static com.seckill.ordersystem.common.SeckillErrorCode.ACTIVITY_NOT_FOUND;

@RestController
@RequestMapping("/api/seckill")
public class SeckillController {
    @Resource
    private SeckillService seckiillService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @PostMapping("/{activityId}")
    public CommonResult<OrderResponseDTO> seckill(@PathVariable Long activityId, @RequestParam Long userId, @RequestParam Integer quantity) {
        return seckiillService.seckill(activityId, userId, quantity);
    }

    @GetMapping("/init_stock")
    public CommonResult<String> init(@RequestParam Long activityId, @RequestParam Integer stock) {
        stringRedisTemplate.opsForValue().set("seckill:stock:" + activityId, stock.toString());
        return CommonResult.success("Activity " + activityId + ", stock initialized as " + stock);
    }

    @GetMapping("/order/status")
    public CommonResult<String> getOrderStatus(@RequestParam String orderNo) {
        String status = stringRedisTemplate.opsForValue().get("seckill:order_status:" + orderNo);
        if (status == null) {
            return CommonResult.fail(ACTIVITY_NOT_FOUND);
        }
        else {
            return CommonResult.success(status);
        }
    }
}
