package com.et.service;

import com.et.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service("rabbitmqService")
public class RabbitMQServiceImpl implements RabbitMQService{

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private CachingConnectionFactory factory;

    @Bean(name = "limitContainerFactory")
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(){
        SimpleRabbitListenerContainerFactory fac = new SimpleRabbitListenerContainerFactory();
        fac.setConnectionFactory(factory);
        fac.setPrefetchCount(3);// 每次在队列中获取三条消息
        return fac;
    }


    @Override
    public void receiveMessage(String queueName) {
        String msg = (String) amqpTemplate.receiveAndConvert(RabbitMQConfig.DIRECTQUEUE);
        System.out.println("接收到的消息："+msg);
    }

    @Override
    //@RabbitListener(queues = {"directQueue"})
    public void receiveMessage2(String message) {
        System.out.println("消费者1 接收到的消息："+message);
    }

   // @RabbitListener(queues = {"directQueue"})
    @Override
    public void receiveMessage3(String message) {
        System.out.println("消费者2 接收到的消息："+message);
    }

    @Override
    @RabbitListener(queues = {"fanoutQueue1"})
    public void receiveFanoutMessage1(String message) {
        System.out.println("订阅者1接收到的消息："+message);
    }

    @Override
    @RabbitListener(queues = {"fanoutQueue2"})
    public void receiveFanoutMessage2(String message) {
        System.out.println("订阅者2接收到的消息："+message);
    }

    @Override
    @RabbitListener(queues = {"routtingQueue1"})
    public void receiveRoutingMessage1(String message) {
        System.out.println("路由模式1 接收到的消息："+message);
    }

    @Override
    @RabbitListener(queues = {"routtingQueue2"})
    public void receiveRoutingMessage2(String message) {
        System.out.println("路由模式2 接收到的消息："+message);
    }

    @Override
    @RabbitListener(queues = {"topicQueue1"})
    public void receiveTopicMessage(String message) {
        System.out.println("topic模式接收到的消息："+message);
    }

    @Override
    @RabbitListener(queues = {"topicQueue2"})
    public void receiveTopicMessage2(String message) {
        System.out.println("topic模式接收到的消息："+message);
    }

    /**
     *
     * @param message 消息主题
     * @param channel 管道 用来手动确认接收消息
     * @param deliveryTag 每条消息的唯一标识
     */
    @Override
    //@RabbitListener(queues = {"directQueue"})
    public void receiveAckMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        System.out.println("接收到的消息："+message);
        try {
            System.out.println("模拟处理业务逻辑");
//            System.out.println(1/0);
            if(deliveryTag==5){// 每五条消息批量处理一次签收
                // 手动通知已经接收到了消息  true表示是否批量处理
                channel.basicAck(deliveryTag,true);
            }
        } catch (IOException e) {
            try {
                // 消息拒签
                /**
                 * long deliveryTag  消息的唯一标识
                 * boolean multiple 是否批量处理 false 不批量处理
                 * boolean requeue 消息是否回到队列中
                 */
                // 将处理失败的消息放回到队列中
                //channel.basicNack(deliveryTag,false,true);
                // 丢掉当前消息
                //channel.basicNack(deliveryTag,false,false);
                // 与basicNack类似 都是用来拒签消息的
                channel.basicReject(deliveryTag,false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    @Override
    @RabbitListener(queues = {"directQueue"},concurrency = "5-10",containerFactory = "limitContainerFactory")
    public void receiveAckMessage2(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        System.out.println("接收到的消息："+message);
        try {
            System.out.println("模拟处理业务逻辑");
            System.out.println("DELIVERY_TAG: "+deliveryTag);
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            try {
                // 消息拒签
                channel.basicReject(deliveryTag,false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    @RabbitListener(queues = {"delayedQueue"})
    public void reveiveDelayedMessage(String message) {
        System.out.println("接收到的延迟消息："+message+" 接收时间："+new Date().toString());
    }
}
