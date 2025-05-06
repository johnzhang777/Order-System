package com.seckill.ordersystem.controller;

import com.seckill.ordersystem.entity.Product;
import com.seckill.ordersystem.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @PostMapping("/test-insert")
    @Transactional
    public String insertTestProduct() {
        Product product = new Product();
        product.setProductName("测试商品");
        product.setDescription("这是一个测试商品");
        product.setBasePrice(new BigDecimal("199.99"));
        product.setMainImage("http://example.com/image.jpg");
        product.setCategoryId(1);
        product.setStatus(1);
        boolean saved = productService.save(product);
        System.out.println("Product saved: " + saved);
        productService.getById(product.getId());
        return saved ? "插入成功" : "插入失败";
    }

    @GetMapping("/test-select")
    public Product selectTestProduct() {
        // Assuming you want to fetch the first product for testing
        return productService.getById(1L); // Replace with actual ID or logic to fetch a product
    }
}
