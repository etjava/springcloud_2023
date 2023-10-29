package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient // 能够让注册中心发现
@EnableFeignClients // 开启Feign客户端
public class GateWayApp {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApp.class,args);
    }
}
