package com.et.controller;

import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.service.MiaoShaGoodsService;
import com.et.util.R;
import com.et.util.RedisUtil;
import io.netty.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/miaoShaGoods")
public class MiaoShaGoodsController {

    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 查询所有秒杀商品
     * @return
     */
    @RequestMapping("/findAll")
    public R findAll(){
        List<MiaoShaGoods> miaoShaGoodsList=null;
        Object o=redisUtil.get(RedisConstant.REDIS_MIAOSHA_GOODS);
        if(o==null){
            System.out.println("从数据库里面查询");
            miaoShaGoodsList = miaoShaGoodsService.listAll();
            redisUtil.set(RedisConstant.REDIS_MIAOSHA_GOODS,miaoShaGoodsList,RedisConstant.REDIS_MIAOSHA_GOODS_EXPIRE);
        }else{
            System.out.println("从redis中取值");
            miaoShaGoodsList= (List<MiaoShaGoods>) o;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("data",miaoShaGoodsList);
        return R.ok(map);
    }

    @RequestMapping("/findById")
    public R findById(Integer id){
       MiaoShaGoods miaoShaGoods = miaoShaGoodsService.findById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("data",miaoShaGoods);
        return R.ok(map);
    }
}
