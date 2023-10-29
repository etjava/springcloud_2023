package com.et.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 远程调用客户端
 */
// seckill-order 为被调用的服务的名称
@FeignClient("seckill-order")
public interface OrderFeignService {
    /**
     * 查询秒杀结果
     * @param userId
     * @param goodsId
     * @return
     */
    // 请求路径 调用的哪个模块 就写具体模块的请求地址
    @RequestMapping("/order/getMiaoShaResult")
    public String getMiaoShaResult(@RequestParam("userId") Integer userId, @RequestParam("goodsId") Integer goodsId);
}
