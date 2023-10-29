package com.et.controller;

import com.et.entity.Order;
import com.et.service.OrderService;
import com.et.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * 查询秒杀结果 - 远程接口调用(openfeign)
     * @param userId
     * @param goodsId
     * @return
     */
    @RequestMapping("/getMiaoShaResult")
    public String getMiaoShaResult(@RequestParam("userId") Integer userId, @RequestParam("goodsId") Integer goodsId){
        System.out.println("获取秒杀结果2："+userId+" ,"+goodsId);
        return orderService.getMiaoShaResult(userId,goodsId);
    }
}

