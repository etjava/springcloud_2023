package com.et.rabbitmq;

import com.et.config.MQConfig;
import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.entity.MiaoShaMessage;
import com.et.entity.Order;
import com.et.entity.User;
import com.et.service.MiaoShaGoodsService;
import com.et.service.MiaoShaService;
import com.et.service.OrderService;
import com.et.util.BeanUtil;
import com.et.util.RedisUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息接收者
 */
@Service
public class MQReceiver {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;

    @Autowired
    private MiaoShaService miaoShaService;

    @Autowired
    private OrderService orderService;

    // 监听队列消息 只要存在就会自动获取
    @RabbitListener(queues = {MQConfig.MIAOSHA_QUEUE})
    public void receive(String message){
        // 将消息转为对象
        MiaoShaMessage miaoShaMessage = BeanUtil.stringToBean(message, MiaoShaMessage.class);
        User user = miaoShaMessage.getUser();
        Integer goodsId = miaoShaMessage.getMiaoShaGoodsId();
        //2. 判断库存是否充足
        MiaoShaGoods goods = miaoShaGoodsService.findById(goodsId);
        if(goods.getStock()<=0){
            // 没有库存了
            System.out.println("库存不足");
            // 设置redis中的秒杀商品 库存不足 默认false表示没有秒杀完成
            redisUtil.set(RedisConstant.REDIS_GOODS_MIAOSHA_OVER_PREFIX,goodsId+"",true);
            return;
        }
        //3. 判断用户是否重复秒杀
        Map<String,Object> map2 = new HashMap<>();
        map2.put("userId",user.getId());
        map2.put("goodsId",goods.getId());
        List<Order> orders = orderService.finOrderWithUserIdAndGoodsId(map2);
        if(orders.size()!=0){
            System.out.println("已购买过此商品");
            return;
        }
        //4. 减库存 落单 必须在同一事务中
        String orderId = miaoShaService.exec(user, goods);
    }
}
