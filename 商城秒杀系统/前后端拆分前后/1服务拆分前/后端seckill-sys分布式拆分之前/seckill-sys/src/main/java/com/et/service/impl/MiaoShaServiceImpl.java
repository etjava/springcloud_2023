package com.et.service.impl;

import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.entity.Order;
import com.et.entity.User;
import com.et.mapper.MiaoShaGoodsMapper;
import com.et.mapper.OrderMapper;
import com.et.service.MiaoShaService;
import com.et.util.DateUtil;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MiaoShaServiceImpl implements MiaoShaService {

    @Autowired
    private MiaoShaGoodsMapper miaoShaGoodsMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisUtil redisUtil;

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
