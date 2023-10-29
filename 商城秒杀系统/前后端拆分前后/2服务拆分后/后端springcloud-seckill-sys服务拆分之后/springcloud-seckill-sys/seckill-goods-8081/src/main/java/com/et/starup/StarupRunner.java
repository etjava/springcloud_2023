package com.et.starup;

import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.service.MiaoShaGoodsService;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 项目启动时去加载秒杀商品信息及秒杀是否完成的状态到redis中
 */
    @Component("starupRunner")
public class StarupRunner implements CommandLineRunner {

    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public void run(String... args) throws Exception {
        List<MiaoShaGoods> goodsList = miaoShaGoodsService.listAll();
        System.out.println("启动时加载秒杀商品库存信息");
        for (MiaoShaGoods goods : goodsList) {
            System.out.println("[ "+goods.getId()+","+goods.getStock()+" ]");
            // 库存放到redis中
            // set2 方法是使用redis的序列化 因此我们需要使用redis的减库存操作
            redisUtil.set2(RedisConstant.REDIS_STOCK_PREFIX,goods.getId()+"",goods.getStock()+"");
            // 将秒杀状态存放到redis中 默认没有秒杀完
            redisUtil.set(RedisConstant.REDIS_GOODS_MIAOSHA_OVER_PREFIX,goods.getId()+"",false);
        }
    }
}
