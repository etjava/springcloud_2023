package com.et.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.et.entity.Order;
import com.et.entity.User;
import com.et.mapper.OrderMapper;
import com.et.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Override
    public Order findById(String id) {
        return orderMapper.findById(id);
    }

    @Override
    public List<Order> finOrderWithUserIdAndGoodsId(Map<String, Object> map) {
        return orderMapper.finOrderWithUserIdAndGoodsId(map);
    }
}
