package com.seckill.ordersystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seckill.ordersystem.entity.SeckillActivity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface SeckillActivityMapper extends BaseMapper<SeckillActivity> {
    // Define any custom query methods here if needed
    // For example:
    // List<SeckillActivity> findActiveActivities();
    // SeckillActivity findByProductId(Long productId);

    @Update("""
        UPDATE seckill_activity 
        SET stock = stock - #{quantity}, version = version + 1 
        WHERE id = #{id} AND version = #{version} AND stock >= #{quantity}
    """)
    int updateStockWithVersion(@Param("id") Long id,
                               @Param("quantity") Integer quantity,
                               @Param("version") Long version);
}
