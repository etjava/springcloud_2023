package com.et.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQ配置
 */
@Configuration
public class RabbitMQConfig {

    // 交换机名称
    public static String DIRECTEXCHANGENAME="directExchange";
    // 队列名称
    public static String DIRECTQUEUE="directQueue";
    // 路由key
    public static String DIRECTROUTINGKEY="directRoutingKey";
    // 广播类型的交换机
    public static String FANOUTEXCHANGE="fanoutExchange";
    public static String FANOUTQUEUE1="fanoutQueue1";
    public static String FANOUTQUEUE2="fanoutQueue2";

    // 路由模式
    public static String ROUTTINGEXCHANGE="routtingtExchange";
    public static String ROUTTINGQUEUE1="routtingQueue1";
    public static String ROUTTINGQUEUE2="routtingQueue2";

    // 带过期时间的消息
    public static String TTL_DIRECTEXCHANGE="ttlDirectExchange";
    public static String TTL_DIRECTQUEUE="ttlDirectQueue";
    public static String TTL_ROUTINGKEY="ttlRoutingKey";

    // 主题模式
    public static String TOPICEXCHANGE="topicExchange";
    public static String TOPICQUEUE1="topicQueue1";
    public static String TOPICQUEUE2="topicQueue2";

    // 死信队列
    public static String DLXEXCHANGE="dlxExchange";
    public static String DLXROUTINGKEY="dlxRoutingKey";
    public static String DLXQUEUE="dlxQueue";


    // 延迟队列
    public static String DELAYEDEXCHANGE="delayedExchange";
    public static String DELAYEDQUEUE="delayedQueue";
    public static String DELAYEDROUTINGKEY="delayedRoutingKey";

    // 创建direct交换机
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(DIRECTEXCHANGENAME);
    }



    /**
     * 创建direct队列
     * @return
     */
    @Bean
    public Queue directQueue(){
        return new Queue(DIRECTQUEUE);
    }

    /*
    队列和交换机的绑定规则
     */
    @Bean
    public Binding directBinding(){
        // 从队列绑定到交换机
        return BindingBuilder.bind(directQueue()).to(directExchange())
                .with(DIRECTROUTINGKEY);// 路由规则
    }

    /////////////////////[发布订阅模式]/////////////////////////////////////

    // 创建Fanout交换机 - 广播模式
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUTEXCHANGE);
    }

    // 创建两个队列分别绑定到fanout类型的交换机上
    @Bean
    public Queue fanouttQueue1(){
        return new Queue(FANOUTQUEUE1);
    }
    @Bean
    public Queue fanouttQueue2(){
        return new Queue(FANOUTQUEUE2);
    }


    @Bean
    public Binding fanoutBinding1(){
        // 从队列绑定到交换机
        return BindingBuilder.bind(fanouttQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2(){
        // 从队列绑定到交换机
        return BindingBuilder.bind(fanouttQueue2()).to(fanoutExchange());
    }

    /////////////////////[路由模式]/////////////////////////////////////
    // 创建交换机 - routting模式也是使用direct的交换机
    @Bean
    public DirectExchange routingExchange(){
        return new DirectExchange(ROUTTINGEXCHANGE);
    }

    // 创建两个队列

    @Bean
    public Queue routtingQueue1(){
        return new Queue(ROUTTINGQUEUE1);
    }
    @Bean
    public Queue routtingQueue2(){
        return new Queue(ROUTTINGQUEUE2);
    }
    // 将队列绑定到交换机 - 有一种模式就需要创建一个绑定方法
    @Bean
    public Binding routtingBinding1(){
        // 从队列绑定到交换机
        return BindingBuilder.bind(routtingQueue1()).to(routingExchange()).with("error");
    }

    @Bean
    public Binding routtingBinding2(){
        // 从队列绑定到交换机
        return BindingBuilder.bind(routtingQueue2()).to(routingExchange()).with("info");
    }

    @Bean
    public Binding routtingBinding3(){
        // 从队列绑定到交换机
        return BindingBuilder.bind(routtingQueue2()).to(routingExchange()).with("error");
    }

    @Bean
    public Binding routtingBinding4(){
        // 从队列绑定到交换机
        return BindingBuilder.bind(routtingQueue2()).to(routingExchange()).with("warning");
    }

    ///////////////////////[TOPIC 主题模式(模糊匹配)]///////////////////////////////////////
    // 定义主题模式的交换机
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPICEXCHANGE);
    }
    // 定义两个topic模式队列
    @Bean
    public Queue topicQueue1(){
        return new Queue(TOPICQUEUE1);
    }
    @Bean
    public Queue topicQueue2(){
        return new Queue(TOPICQUEUE2);
    }
    // 队列绑定到交换机
    @Bean
    public Binding topicBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("*.orange.*");
    }
    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("*.*.rabbit");
    }
    @Bean
    public Binding topicBinding3(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("lazy.#");
    }

    ///////////////////////[测试消息的过期时间]///////////////////////////////////////
    // 定义交换机 - 过期时间
    @Bean
    public DirectExchange ttlDirectExchange(){
        return new DirectExchange(TTL_DIRECTEXCHANGE);
    }
    // 定义队列 - 带过期时间
    @Bean
    public Queue ttlQueue(){
        // 创建队列时指定死信队列
        Map<String,Object> map = new HashMap<>();
//        map.put("x-message-ttl",5000);// 过期时间 单位毫秒
        map.put("x-max-length",50);// 消息最大条数 超出的消息会被放到死信队列
        map.put("x-dead-letter-exchange",DLXEXCHANGE);// 死信交换机
        map.put("x-dead-letter-routing-key",DLXROUTINGKEY);// 死信路由key
        /*
        String name, 队列名称
        boolean durable, 是否持久化
        boolean exclusive, 是否独占
        boolean autoDelete, 是否自动删除
	    @Nullable Map<String, Object> arguments 其他选项 可设置队列的过期时间等
         */
        return new Queue(TTL_DIRECTQUEUE,true,false,false,map);
    }

    // 队列绑定到交换机
    @Bean
    public Binding ttlBinding(){
        return BindingBuilder.bind(ttlQueue()).to(ttlDirectExchange()).with(TTL_ROUTINGKEY);
    }

    /////////////////[死信队列]///////////////////////////////////
    // 创建死信交换机
    @Bean
    public DirectExchange dlxExchange(){
        return new DirectExchange(DLXEXCHANGE);
    }
    // 创建死信队列
    @Bean
    public Queue dlxQueue(){
        return new Queue(DLXQUEUE);
    }
    // 绑定到交换机
    @Bean
    public Binding dlxBinding(){
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(DLXROUTINGKEY);
    }

    ///////////////[延迟队列]//////////////////////////////////////
    // 创建交换机 使用自定义交换机
    @Bean
    public CustomExchange delayedCustomExchange(){
        Map<String,Object> map = new HashMap<>();
        map.put("x-delayed-type","direct");// 指定交换机类型
        /*
        String name, 交换机名称
        String type, 交换机类型
        boolean durable, 是否持久化
        boolean autoDelete 是否自动删除
         */
        return new CustomExchange(DELAYEDEXCHANGE,"x-delayed-message",true,false,map);
    }

    // 创建队列
    @Bean
    public Queue delayedQueue(){
        return new Queue(DELAYEDQUEUE);
    }
    // 绑定到交换机
    @Bean
    public Binding delayedBinding(){
        return BindingBuilder.bind(delayedQueue()).to(delayedCustomExchange()).with(DELAYEDROUTINGKEY).noargs();
    }
}
