package com.et.service;

public interface RabbitMQService {

    /**
     * 发送消息
     * @param message
     */
    void send(String message);
    // 发布订阅模式 发送消息
    void fanoutSend(String message);


    /**
     * routting模式 发送消息
     * @param message 消息内容
     * @param routingKey  消息路由模式  有 error  info  warning
     */
    void routingSend(String routingKey,String message);

    // topic模式
    // routingKey 模糊匹配的就是它
    void topicSend(String routingKey,String message);


    // 测试带有过期时间的消息
    void sendTTLMessage(String message);
    // 发送延迟消息 delayedTime延迟时间 单位毫秒
    void sendDelayedMessage(String message,Integer delayedTime);
}
