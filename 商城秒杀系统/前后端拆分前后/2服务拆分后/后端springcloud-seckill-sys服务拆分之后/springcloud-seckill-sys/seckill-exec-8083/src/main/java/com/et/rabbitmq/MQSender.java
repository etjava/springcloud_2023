package com.et.rabbitmq;

import com.et.config.MQConfig;
import com.et.entity.MiaoShaMessage;
import com.et.util.BeanUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发送消息到队列中
 */
@Service
public class MQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMiaoShaMsg(MiaoShaMessage miaoShaMessage){
        String msg = BeanUtil.beanToString(miaoShaMessage);
        System.out.println("发送的消息 "+msg);
        // 发送消息到队列中  交换机使用默认的
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE,msg);
    }
}
