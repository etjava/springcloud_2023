package com.et.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// nacos-stock 服务名称
@FeignClient("nacos-stock")
public interface StockFeignService {

    // 库存服务中的方法
    @RequestMapping("/stock/info")
    public String test(@RequestParam("info") String info);
}
