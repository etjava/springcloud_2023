package com.et.controller;

import com.et.entity.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {


    @GetMapping("/{id}")
    public Product detail(@PathVariable("id") Integer id,String info2){
        System.out.println("info2 = "+info2);// 这个info2是过滤器传递过来的
        return new Product(id,"xx商品"+id,2,100.00);
    }
}
