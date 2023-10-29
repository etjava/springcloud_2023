package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderApplication_1003 {
 
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication_1003.class, args);
    }
}