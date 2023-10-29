package com.et.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * 自定义网关过滤器
 */
public class MyGatewayFilter implements GatewayFilter, Ordered {

    /**
     * 过滤请求
     * 可以通过exchange交换机实现请求的过滤
     * @param exchange the current server exchange
     * @param chain provides a way to delegate to the next filter
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("=====自定义网关过滤器=====");
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        long date = headers.getDate();
        Date d = new Date(date);
        System.out.println(d);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {// 过滤器排序
        return -1;// 数字越小优先级越高
    }
}
