package com.et.controller;

import com.et.entity.Order;
import com.et.entity.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/{id}")
    public Order detail(@PathVariable("id") Integer id){
        return new Order(id,"20221228xxx",9999.99,"用户信息"+id,222);
    }
}
