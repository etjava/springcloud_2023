package com.et.config;

import com.et.filter.MyGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration// 测试限流 先注释调全局过滤器 使用配置文件方式
public class GatewayFilterConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder build){
        return build.routes().route(r->r.path("/product/**")
                .uri("http://localhost:8080/")
                .filter(new MyGatewayFilter())
                .id("myGatewayFilter")
        ).build();
    }
}
