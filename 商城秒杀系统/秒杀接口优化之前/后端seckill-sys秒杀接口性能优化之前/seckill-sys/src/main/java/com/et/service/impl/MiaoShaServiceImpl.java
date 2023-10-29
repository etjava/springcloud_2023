package com.et.service.impl;

import com.et.entity.MiaoShaGoods;
import com.et.entity.Order;
import com.et.entity.User;
import com.et.mapper.MiaoShaGoodsMapper;
import com.et.mapper.OrderMapper;
import com.et.service.MiaoShaService;
import com.et.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoShaServiceImpl implements MiaoShaService {

    @Autowired
    private MiaoShaGoodsMapper miaoShaGoodsMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 执行秒杀
     * @param user
     * @param miaoShaGoods
     * @return
     */
    @Override
    @Transactional
    public String exec(User user, MiaoShaGoods miaoShaGoods) {
        // 减库存
        Integer affectedRows = miaoShaGoodsMapper.reduceStock(miaoShaGoods.getId());
        if(affectedRows==0){
            return null;
        }
        // 生成订单
        Order order = new Order();
        order.setId(DateUtil.getCurrentDateStr());// 订单号
        order.setMiaoShaGoods(miaoShaGoods);
        order.setUser(user);
        order.setPayDate(null);// 还没有支付
        order.setCountNum(1);// 每次秒杀只能秒杀一件商品
        order.setStatus(0);// 0表示生成订单
        order.setTotalPrice(miaoShaGoods.getPrice());
        Integer res = orderMapper.add(order);
        if(res==0){
            return null;
        }
        return order.getId();
    }
}
