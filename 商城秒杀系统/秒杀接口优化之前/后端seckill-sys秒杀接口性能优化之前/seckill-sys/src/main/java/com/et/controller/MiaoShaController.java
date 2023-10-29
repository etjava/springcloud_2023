package com.et.controller;

import com.alibaba.druid.util.StringUtils;
import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.entity.Order;
import com.et.entity.User;
import com.et.service.MiaoShaGoodsService;
import com.et.service.MiaoShaService;
import com.et.service.OrderService;
import com.et.util.R;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 秒杀操作Controller
 */
@RestController
@RequestMapping("/miaoSha")
public class MiaoShaController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;

    @Autowired
    private MiaoShaService miaoShaService;

    @Autowired
    private OrderService orderService;

    /**
     * 执行秒杀
     * @param request 通过request获取header中的token
     * @param goodsId 商品ID
     * @return
     */
    @RequestMapping("/exec")
    public R exec(HttpServletRequest request,Integer goodsId,String verifyCode){
        // 判断验证码
        if(StringUtils.isEmpty(verifyCode)){
            return R.error("验证码不能为空");
        }
        //1. 根据token获取用户信息
        String token = request.getHeader("token");
        User user = (User) redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX,token);
        System.out.println("token="+token+", "+user);
        MiaoShaGoods goods = miaoShaGoodsService.findById(goodsId);
        // 校验验证码的合法性
        Object rnd = redisUtil.get(RedisConstant.REDIS_VERIFYCODE_PREFIX,user.getId()+","+goods.getId());
        if(rnd==null){
            return R.error("验证码过期");
        }else if(!verifyCode.equals(String.valueOf(rnd))){
            return R.error("验证码错误");
        }
        //2. 判断库存是否充足
        if(goods.getStock()<=0){
            // 没有库存了
            return R.error("库存不足");
        }
        //3. 判断用户是否重复秒杀
        Map<String,Object> map2 = new HashMap<>();
        map2.put("userId",user.getId());
        map2.put("goodsId",goods.getId());
        List<Order> orders = orderService.finOrderWithUserIdAndGoodsId(map2);
        if(orders.size()!=0){
            return R.error("已购买过此商品");
        }
        //4. 减库存 落单 必须在同一事务中
        String orderId = miaoShaService.exec(user, goods);
        if(orderId!=null){
            Map<String,Object> map = new HashMap<>();
            map.put("orderId",orderId);
            return R.ok(map);
        }else{
            return R.error("下单失败，稍后再试");
        }
    }
}
