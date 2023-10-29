package com.et.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * SpringCloud相关配置
 * @author Administrator
 *
 */
@Configuration
public class SpringCloudConfig {
    /**
     * 调用服务模版
     * @return
     */
    @Bean
    @LoadBalanced// 该注解3.1.3后的版本 默认采用的是RoundRobinRule，轮询策略
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }



}
