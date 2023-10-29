package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class StudentConsumerApplication_80 {
 
    public static void main(String[] args) {
        SpringApplication.run(StudentConsumerApplication_80.class,args);
    }
}