package com.seckill.ordersystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seckill.ordersystem.entity.OrderMain;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OrderMainMapper extends BaseMapper<OrderMain> {
    // Define any custom query methods here if needed
    // For example:
    // List<OrderMain> findByUserId(Long userId);
    // OrderMain findByOrderNo(String orderNo);
    // 在 OrderMainMapper 接口中定义

    @Select("SELECT * FROM order_main WHERE order_no = #{orderNo}")
    OrderMain selectByOrderNo(@Param("orderNo") String orderNo);

    @Update("""
        UPDATE order_main 
        SET status = #{newStatus}, version = version + 1 
        WHERE order_no = #{orderNo} AND status = #{oldStatus} AND version = #{version}
    """)
    int updateStatusByOrderNo(@Param("orderNo") String orderNo,
                              @Param("oldStatus") Integer oldStatus,
                              @Param("newStatus") Integer newStatus,
                              @Param("version") Long version);
}
