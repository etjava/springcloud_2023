package com.et.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.et.constant.RedisConstant;
import com.et.entity.Order;
import com.et.mapper.OrderMapper;
import com.et.service.OrderService;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public Order findById(String id) {
        return orderMapper.findById(id);
    }

    @Override
    public List<Order> finOrderWithUserIdAndGoodsId(Map<String, Object> map) {
        return orderMapper.finOrderWithUserIdAndGoodsId(map);
    }

    @Override
    public String getMiaoShaResult(Integer userId, Integer goodsId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("goodsId",goodsId);
        List<Order> orderList = orderMapper.finOrderWithUserIdAndGoodsId(map);
        if(orderList.size()!=0){
            return orderList.get(0).getId();
        }else{
            // TODO 查询商品是否秒杀完成 从Redis中判断商品是否秒杀完成
            boolean isOver = (boolean)redisUtil.get(RedisConstant.REDIS_GOODS_MIAOSHA_OVER_PREFIX,goodsId+"");
            if(isOver){
                return "-1";// 没有库存了
            }else{
                return "0";// 排队中
            }
        }
    }
}
