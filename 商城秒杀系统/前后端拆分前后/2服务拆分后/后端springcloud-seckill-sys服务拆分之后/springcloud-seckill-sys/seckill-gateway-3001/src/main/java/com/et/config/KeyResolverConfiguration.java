package com.et.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * 网关配置类 - 限流规则配置类
 */
@Configuration
public class KeyResolverConfiguration {

    @Bean
    public KeyResolver pathKeyResolver(){

        /*return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                return Mono.just(exchange.getRequest().getURI().getPath());
            }
        };*/
        return exchange -> Mono.just(exchange.getRequest().getURI().getPath());
    }
}
