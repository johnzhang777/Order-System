package com.seckill.ordersystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seckill.ordersystem.entity.Product;
import com.seckill.ordersystem.mapper.ProductMapper;
import com.seckill.ordersystem.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
}
