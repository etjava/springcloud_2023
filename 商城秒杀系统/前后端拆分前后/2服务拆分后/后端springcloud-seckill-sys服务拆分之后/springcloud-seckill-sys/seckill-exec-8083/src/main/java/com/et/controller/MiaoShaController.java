package com.et.controller;

import com.alibaba.druid.util.StringUtils;
import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.entity.MiaoShaMessage;
import com.et.entity.User;
import com.et.feign.OrderFeignService;
import com.et.rabbitmq.MQSender;
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
    private OrderFeignService orderFeignService;

    @Autowired
    private MQSender mqSender;

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

        // 验证库存 - 取redis中的数据
        boolean isOver = (boolean)redisUtil.get(RedisConstant.REDIS_GOODS_MIAOSHA_OVER_PREFIX,goodsId+"");
        if(isOver){
            return R.ok("秒杀结束");
        }
        // redis中减库存操作 -1操作
        long stock = redisUtil.decr(RedisConstant.REDIS_STOCK_PREFIX,goodsId+"",1);
        if(stock<0){
            return R.error("秒杀失败，商品库存不足 谢谢惠顾");
        }
        // redis中当库存-1操作后 库存不足时 将秒杀是否完成状态设置为true 表示已经秒杀完成了
        // 在MQReceive中判断库存时进行设置


        // 封装商品和用户信息发送到队列中
        MiaoShaMessage msg = new MiaoShaMessage(user,goodsId);
        mqSender.sendMiaoShaMsg(msg);
        return R.ok("排队抢购中");

        /*

         */

        // 下边的操作迁移至消息接收者中进行处理
        /*//2. 判断库存是否充足
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
        }*/
    }

    /**
     * 秒杀结果查询
     * @param request
     * @param goodsId
     * @return
     *      大于0 秒杀成功 返回订单ID，
     *      0 秒杀还没结束 还在排队中
     *      -1 秒杀失败
     *
     */
    @RequestMapping("/result")
    public R result(HttpServletRequest request,Integer goodsId){
        //1. 根据token获取用户信息
        String token = request.getHeader("token");
        User user = (User) redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX,token);
        System.out.println("token="+token+", "+user);
//        String result = miaoShaService.getMiaoShaResult(user.getId(),goodsId);
        // 远程调用 - 调用Order模块中的方法
        String result = orderFeignService.getMiaoShaResult(user.getId(),goodsId);
        Map<String,Object> map = new HashMap<>();
        map.put("result",result);
        return R.ok(map);
    }
}
