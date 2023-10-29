package com.et.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MQ配置类
 */
@Configuration
public class MQConfig {

    // 队列名称
    public static final String MIAOSHA_QUEUE="miaosha_queue";

    /**
     * 定义消息队列
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue(MIAOSHA_QUEUE);
    }
}
