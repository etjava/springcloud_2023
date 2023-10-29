package com.et;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.et.mapper")
@EnableDiscoveryClient // 注册到nacos注册中心
public class SeckillUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillUserApplication.class, args);
    }

}
