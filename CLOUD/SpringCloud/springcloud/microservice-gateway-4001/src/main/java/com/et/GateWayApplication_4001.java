package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class GateWayApplication_4001 {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication_4001.class,args);
    }
}
