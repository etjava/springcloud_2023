package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 能够让注册中心发现
public class NacosStockApplication2 {

    public static void main(String[] args) {
        SpringApplication.run(NacosStockApplication2.class,args);
    }
}
