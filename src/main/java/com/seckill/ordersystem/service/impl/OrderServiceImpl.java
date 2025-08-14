package com.seckill.ordersystem.service.impl;

import com.seckill.ordersystem.common.CommonResult;
import com.seckill.ordersystem.common.SeckillErrorCode;
import com.seckill.ordersystem.entity.OrderMain;
import com.seckill.ordersystem.entity.SeckillActivity;
import com.seckill.ordersystem.entity.StockFlow;
import com.seckill.ordersystem.mapper.OrderMainMapper;
import com.seckill.ordersystem.mapper.ProductMapper;
import com.seckill.ordersystem.mapper.SeckillActivityMapper;
import com.seckill.ordersystem.mapper.StockFlowMapper;
import com.seckill.ordersystem.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Data
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMainMapper orderMainMapper;
    private final StockFlowMapper stockFlowMapper;
    private final SeckillActivityMapper seckillActivityMapper;
    private final ProductMapper productMapper;

    @Override
    public void createOrder(Long userId, Long activityId, Integer quantity, String orderNo) {
        SeckillActivity seckillActivity = seckillActivityMapper.selectById(activityId);

        if (seckillActivity == null) {
            CommonResult.fail(SeckillErrorCode.ACTIVITY_NOT_FOUND);
            throw new RuntimeException("Activity not found：" + activityId);
        }

        // 1. 创建订单
        OrderMain order = new OrderMain();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setActivityId(activityId);
        order.setQuantity(quantity);
        order.setTotalPrice(seckillActivity.getSeckillPrice().multiply(BigDecimal.valueOf(quantity)));
        order.setStatus(0); // waiting for paying
        orderMainMapper.insert(order);

        // 2. 创建库存流水记录（类型为 RESERVE）
        StockFlow flow = new StockFlow();
        flow.setActivityId(activityId);
        flow.setUserId(userId);
        flow.setOrderNo(orderNo);
        flow.setFlowType("RESERVE");
        flow.setQuantity(quantity);
        stockFlowMapper.insert(flow);

        log.info("订单创建成功，orderNo={}", orderNo);
    }
}
