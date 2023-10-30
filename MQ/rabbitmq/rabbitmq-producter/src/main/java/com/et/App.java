package com.et;

import com.et.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(App.class, args);
        RabbitMQService rabbitMQService = applicationContext.getBean("rabbitmqService",RabbitMQService.class);
//        for (int i = 0; i < 10; i++) {
//            //rabbitMQService.send("hello rabbitmq "+i);
//            //rabbitMQService.fanoutSend("群发消息："+i);
//        }
        // 路由模式发送消息
//        rabbitMQService.routingSend("error","发送error级别的消息 ");
//        rabbitMQService.routingSend("info","发送info级别的消息 ");
//        rabbitMQService.routingSend("warning","发送warning级别的消息 ");

        // topic 模式
        //rabbitMQService.topicSend("quick.orange.rabbit","飞快的橘色的小兔子");
        //rabbitMQService.topicSend("lazy.blue.rabbit","行动缓慢的蓝色小兔子");

        // 带回调确认的消息模式
//        for (int i = 0; i < 100; i++) {
//            rabbitMQService.send("hello rabbitmq "+i);
//        }

        // TTL message
//        for (int i = 0; i < 100; i++) {
//            rabbitMQService.sendTTLMessage("带有过期时间的消息");
//        }

        // 发送延迟消息
        rabbitMQService.sendDelayedMessage("测试延迟消息 10s",10000);
        rabbitMQService.sendDelayedMessage("测试延迟消息 20s",20000);

    }
}
