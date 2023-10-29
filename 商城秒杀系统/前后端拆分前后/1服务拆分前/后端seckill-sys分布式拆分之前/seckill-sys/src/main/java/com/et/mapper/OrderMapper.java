package com.et.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.et.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    Order findById(String id);

    /**
     * 生成订单
     * @param order
     * @return
     */
    Integer add(Order order);

    /**
     * 根据用户ID和秒杀的商品ID查询订单
     * @param map
     * @return
     */
    List<Order> finOrderWithUserIdAndGoodsId(Map<String,Object> map);

}
