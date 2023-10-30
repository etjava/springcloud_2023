package com.et.service;

import com.et.config.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Service("rabbitmqService")
public class RabbitMQServiceImpl implements RabbitMQService, RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    // 初始化后执行
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this); // 设置回调确认
        rabbitTemplate.setReturnCallback(this);// 设置回退
    }

    @Override
    public void send(String message) {
        //amqpTemplate.convertAndSend(RabbitMQConfig.DIRECTEXCHANGENAME,RabbitMQConfig.DIRECTROUTINGKEY,message);
        // 测试带有回调的发送消息
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECTEXCHANGENAME,
                RabbitMQConfig.DIRECTROUTINGKEY,message,new CorrelationData("1234567"));

    }

    @Override
    public void fanoutSend(String message) {
        // 没有路由key 给个空值即可 不能使用两个参数的 因为两个参数的 会把第一个当作路由key
        amqpTemplate.convertAndSend(RabbitMQConfig.FANOUTEXCHANGE,"",message);
    }

    @Override
    public void routingSend(String routingKey,String message) {
        amqpTemplate.convertAndSend(RabbitMQConfig.ROUTTINGEXCHANGE,routingKey,message);
    }

    @Override
    public void topicSend(String routingKey,String message) {
        amqpTemplate.convertAndSend(RabbitMQConfig.TOPICEXCHANGE,routingKey,message);
    }

    @Override
    public void sendTTLMessage(String message) {
//        MessageProperties prop = new MessageProperties();
//        prop.setExpiration("10000"); // 设置过期时间 单位毫秒
//        Message msg = new Message(message.getBytes(StandardCharsets.UTF_8),prop);
//        rabbitTemplate.send(RabbitMQConfig.TTL_DIRECTEXCHANGE,RabbitMQConfig.TTL_ROUTINGKEY,msg);
        rabbitTemplate.convertAndSend(RabbitMQConfig.TTL_DIRECTEXCHANGE,RabbitMQConfig.TTL_ROUTINGKEY,message);
    }

    @Override
    public void sendDelayedMessage(String message, Integer delayedTime) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DELAYEDEXCHANGE,RabbitMQConfig.DELAYEDROUTINGKEY,
                message,t->{// 借助Message设置延迟时间
                    t.getMessageProperties().setDelay(delayedTime);
                    return t;
                });
    }

    /**
     *
     * @param correlationData correlation data for the callback. 消息的唯一标识
     * @param ack true for ack, false for nack 交换机是否成功收到消息
     * @param cause An optional cause, for nack, when available, otherwise null. 失败的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("消息回调方法执行了 发送的内容："+correlationData);
        if(ack){
            System.out.println("交换机 消息发送成功"+cause);
        }else{
            System.out.println("交换机 消息发送失败"+cause);
            // 做消息补发措施
        }
    }

    /**
     *
     * @param message the returned message.  消息主题
     * @param replyCode the reply code. 返回的Code
     * @param replyText the reply text. 返回的信息
     * @param exchange the exchange. 交换机
     * @param routingKey the routing key. 路由key
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("消息主题："+new String(message.getBody()));
        System.out.println("返回的code："+replyCode);
        System.out.println("返回的Text："+replyText);
        System.out.println("返回的exchange："+exchange);
        System.out.println("返回的routingKey："+routingKey);

    }
}
