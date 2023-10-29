package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker// 开启hystrix
public class ProviderHystrixApplication_1006 {

    public static void main(String[] args) {
        SpringApplication.run(ProviderHystrixApplication_1006.class,args);
    }
}
