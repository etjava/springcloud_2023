package com.et.controller;

import com.et.entity.Order;
import com.et.service.OrderService;
import com.et.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 根据id查询秒杀商品详情
     * @param id
     * @return
     */
    @RequestMapping("/detail")
    public R detail(String id){
        Order order = orderService.findById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("data",order);
        return R.ok(map);
    }
}

