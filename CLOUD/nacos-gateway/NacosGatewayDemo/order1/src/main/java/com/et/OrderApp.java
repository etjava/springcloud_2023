package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.Ordered;

@SpringBootApplication
@EnableDiscoveryClient // 能够让注册中心发现
@EnableFeignClients // 开启Feign客户端
public class OrderApp {

    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class,args);
    }
}
