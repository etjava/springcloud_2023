package com.et.service;

import com.rabbitmq.client.Channel;

public interface RabbitMQService {

    /**
     * 接收消息
     * @param queueName  队列名称
     */
    void receiveMessage(String queueName);

    /**
     * 监听方式接收消息
     * @param message
     */
    void receiveMessage2(String message);
    // 测试多个消费者消费消息
    void receiveMessage3(String message);


    // 订阅者1接收消息
    void receiveFanoutMessage1(String message);

    // 订阅者2接收消息
    void receiveFanoutMessage2(String message);


    // 路由模式 接收消息
    void receiveRoutingMessage1(String message);
    public void receiveRoutingMessage2(String message);

    // topic模式
    void receiveTopicMessage(String message);
    void receiveTopicMessage2(String message);

    // 测试ack机制

    /**
     *
     * @param message 消息主题
     * @param channel 管道 用来手动确认接收消息
     * @param deliveryTag 每条消息的唯一标识
     */
    void receiveAckMessage(String message, Channel channel,long deliveryTag);

    // 测试配置并发
    void receiveAckMessage2(String message, Channel channel,long deliveryTag);


    // 接收延迟消息
    void reveiveDelayedMessage(String message);
}
