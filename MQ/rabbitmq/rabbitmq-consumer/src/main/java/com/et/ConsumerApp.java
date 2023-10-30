package com.et;

import com.et.config.RabbitMQConfig;
import com.et.service.RabbitMQService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ConsumerApp {

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(ConsumerApp.class,args);
//        RabbitMQService rabbitmqService = ac.getBean("rabbitmqService", RabbitMQService.class);
//        rabbitmqService.receiveMessage(RabbitMQConfig.DIRECTQUEUE);
    }
}
