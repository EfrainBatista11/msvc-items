package com.computerey.springcloud.msvc.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.computerey.springcloud.msvc.items.models.Product;

@FeignClient(name = "msvc-products")
public interface ProductFeignClient {
    
    @GetMapping("/api/products")
    List<Product> findAll();

    @GetMapping("/api/products/{id}")
    Product details(@PathVariable Long id);
}
