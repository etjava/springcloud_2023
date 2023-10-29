package com.et.service;

import com.et.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Order findById(String id);

    /**
     * 根据用户ID和秒杀的商品ID查询订单
     * @param map
     * @return
     */
    List<Order> finOrderWithUserIdAndGoodsId(Map<String,Object> map);

    /**
     * 获取秒杀结果
     * @param id
     * @param goodsId
     * @return
     */
    String getMiaoShaResult(Integer id, Integer goodsId);
}
