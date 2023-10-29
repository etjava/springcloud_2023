package com.et;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.et.mapper")
@EnableDiscoveryClient // 发现服务
public class SeckillGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillGoodsApplication.class, args);
    }

}
