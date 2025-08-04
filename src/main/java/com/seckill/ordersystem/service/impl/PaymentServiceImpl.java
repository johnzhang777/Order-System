package com.seckill.ordersystem.service.impl;

import com.seckill.ordersystem.dto.PaymentRequestDTO;
import com.seckill.ordersystem.entity.OrderMain;
import com.seckill.ordersystem.entity.SeckillActivity;
import com.seckill.ordersystem.entity.StockFlow;
import com.seckill.ordersystem.mapper.OrderMainMapper;
import com.seckill.ordersystem.mapper.SeckillActivityMapper;
import com.seckill.ordersystem.mapper.StockFlowMapper;
import com.seckill.ordersystem.service.PaymentService;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
@Slf4j
@Data
//@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final OrderMainMapper orderMainMapper;
    private final SeckillActivityMapper seckillActivityMapper;
    private final StockFlowMapper stockFlowMapper;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void pay(PaymentRequestDTO request) {
        String orderNo = request.getOrderNo();
        Long userId = request.getUserId();
        OrderMain order = orderMainMapper.selectByOrderNo(orderNo);
        if (order == null || !order.getUserId().equals(userId)) {
            log.warn("订单不存在或用户不匹配，orderNo={}", orderNo);
            return;
        }

        if (!"0".equals(order.getStatus().toString())) {
            log.info("订单已处理，orderNo={}, status={}", orderNo, order.getStatus());
            return;
        }

        // 模拟支付结果（1成功、3失败、2超时）
        int status = new Random().nextInt(3) + 1;

        if (status == 1) {
            boolean orderUpdated = orderMainMapper.updateStatusByOrderNo(orderNo, 0, 1, order.getVersion()) == 1;
            if (!orderUpdated) {
                log.warn("订单状态更新失败，可能并发冲突");
                return;
            }

            SeckillActivity activity = seckillActivityMapper.selectById(order.getActivityId());
            boolean stockUpdated = seckillActivityMapper.updateStockWithVersion(
                    activity.getId(),
                    order.getQuantity(),
                    activity.getVersion()
            ) == 1;

            if (!stockUpdated) {
                // 回滚订单状态
                orderMainMapper.updateStatusByOrderNo(orderNo, 1, 0, order.getVersion() + 1);
                log.warn("库存扣减失败，回滚订单状态成功，orderNo={}", orderNo);
                return;
            }

            StockFlow flow = new StockFlow();
            flow.setActivityId(order.getActivityId());
            flow.setUserId(userId);
            flow.setOrderNo(orderNo);
            flow.setFlowType("PAYMENT");
            flow.setQuantity(order.getQuantity());
            stockFlowMapper.insert(flow);

            stringRedisTemplate.opsForValue().set("seckill:order_status:" + orderNo, "SUCCESS", Duration.ofMinutes(5));
            log.info("支付成功，orderNo={}", orderNo);
        } else if (status == 2) {
            orderMainMapper.updateStatusByOrderNo(orderNo, 0, 2, order.getVersion());
            stringRedisTemplate.opsForValue().set("seckill:order_status:" + orderNo, "TIMEOUT", Duration.ofMinutes(5));
            log.info("支付超时，orderNo={}", orderNo);
        } else {
            orderMainMapper.updateStatusByOrderNo(orderNo, 0, 3, order.getVersion());
            stringRedisTemplate.opsForValue().set("seckill:order_status:" + orderNo, "FAILED", Duration.ofMinutes(5));
            log.info("支付失败，orderNo={}", orderNo);
        }
    }
}


