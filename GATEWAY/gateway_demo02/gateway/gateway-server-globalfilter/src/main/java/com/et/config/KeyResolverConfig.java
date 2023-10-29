package com.et.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class KeyResolverConfig {

    /*
    通过URL限流
    注意 这里的方法名要与配置文件中key-resolver指定的一致
     */
    @Bean
    public KeyResolver pathKeyResolver(){
        /*return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                // 根据URL进行限流
                return Mono.just(exchange.getRequest().getURI().getPath());
            }
        };*/

        // e = exchange
//        return e -> Mono.just(e.getRequest().getURI().getPath());// 根据URL限流

        //return e -> Mono.just(e.getRequest().getQueryParams().getFirst("token"));// 根据参数限流
        // IP限流
        return e -> Mono.just(e.getRequest().getRemoteAddress().getHostName());
    }
}
