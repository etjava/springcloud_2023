# RabbitMQ

## 概述

```
RabbitMQ是是西安了高级消息队列协议（AMQP）的开源消息代理软件(也叫做面向消息的中间件)
官网：https://www.rabbitmq.com/
RabbitMQ的MQ是Message Queuing 消息队列
RabbitMQ是用Erlang语言编写的 而集群和故障转移是构建在开放电信平台上的 所有主要的变成语言均与代理接口通讯的客户端库

历史
RabbitMQ是由RabbitMQ Technologies Ltd开发并且提供商业支持的。该公司在2010年4月被SpringSource（VMWare的一个部门）收购。在2013年5月被并入Pivotal。其实VMWare，Pivotal和EMC本质上是一家的。不同的是VMWare是独立上市子公司，而Pivotal是整合了EMC的某些资源，现在并没有上市。

基础概念
RabbitMQ是一套开源（MPL）的消息队列服务软件，是由LShift提供的一个Advanced Message Queuing Protocol(AMQP)的开源实现，由以高性能，健壮及可伸缩性出名的Erlang编写
RabbitMQ支持下列操作系统
linux
windowsNT 到10
windows server 2003到2016
macOS
Solaris
FreeBSD
...

RabbitMQ支持下列变成语言
Java
python
PHP
C#
JavaScript
Go
...

```

使用场景 参考 https://www.cnblogs.com/haixiang/p/10199754.html



各个消息队列比对

https://blog.csdn.net/belvine/article/details/80842240



## 安装

### docker方式安装

```
在官网直接复制docker安装指令 然后到docker服务器执行安装即可
https://www.rabbitmq.com/download.html

原始的
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management

改造后的 需要添加上默认的登录用户名和密码 否则无法登录
docker run -it --rm --name rabbitmq -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management


5672端口的TCP监听的 也就是应用程序连接时需要使用的
15672 是web端粒界面端口
```

![image-20231001214752449](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310012148211.png)

### RabbitMQ修改登录密码

```
1 进入到rabbitmq容器
docker exec -it rabbitmq容器ID /bin/bash
2 查看当前用户列表
rabbitmqctl list_users
3 修改密码
rabbitmqctl change_password 用户名 '新密码'
例如 rabbitmqctl  change_password  admin  'admin'
```





## RabbitMQ运作原理

![img](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310042155113.jpeg)



```
1、Server：Broker，接受client连接，实现AMQP实体服务　　
2、Connection：应用程序和Broker的网络连接　　
3、Channel：网络信道，读写都是在Channel中进行（NIO的概念），包括对MQ进行的一些操作（例如clear queue等）都是在Channel中进行，客户端可建立多个Channel，每个Channel代表一个会话任务　　
4、Message：由properties（有消息优先级、延迟等特性）和Body（消息内容）组成　　
5、Virtual host：用于消息隔离（类似Redis 16个db这种概念），最上层的消息路由，一个包含若干Exchange和Queue，同一个里面Exchange和Queue的名称不能存在相同的。　　
6、Exchange：Routing and Filter　　
7、Binding：把Exchange和Queue进行Binding　　
8、Routing key：路由规则　　
9、Queue：物理上存储消息
```

## RabbitMQ快速入门

### 基本环境

采用父子项目

#### 新建父项目

![image-20231004220217752](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310042202879.png)

![image-20231004221438274](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310042214658.png)

#### 父项目添加依赖

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.et</groupId>
    <artifactId>rabbitmq</artifactId>
    <packaging>pom</packaging>
    <version>0.0.1-SNAPSHOT</version>
    <name>rabbitmq</name>
    <description>rabbitmq</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.2.RELEASE</version>
    </parent>
    <modules>
        <module>rabbitmq-producter</module>
        <module>rabbitmq-consumer</module>
        <module>rabbitmq-common</module>
    </modules>
    <properties>
        <project.build.sourceEncdoing>UTF-8</project.build.sourceEncdoing>
        <maven.compile.source>1.8</maven.compile.source>
        <maven.compile.target>1.8</maven.compile.target>
        <java.version>1.8</java.version>
        <springboot.version>2.3.2.RELEASE</springboot.version>
    </properties>


   <dependencyManagement>
       <dependencies>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-dependencies</artifactId>
               <version>${springboot.version}</version>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
               <version>${springboot.version}</version>
           </dependency>
       </dependencies>
   </dependencyManagement>

</project>

```

#### common模块添加依赖

```xml
 <dependencies>
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter</artifactId>
     </dependency>
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-amqp</artifactId>
     </dependency>
</dependencies>
```

#### 配置rabbitmq

```java
package com.et.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 */
@Configuration
public class RabbitMQConfig {

    // 交换机名称
    private static String DIRECTNAME="directExchange";
    // 队列名称
    private static String DIRECTQUEUE="directQueue";
    // 路由key
    private static String DIRECTROUTINGKEY="directRoutingKey";

    // 创建direct交换机
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(DIRECTNAME);
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
}

```



### 生产者实现

#### 引入common模块

```xml
<dependency>
    <groupId>com.et</groupId>
    <artifactId>rabbitmq-common</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

#### 添加启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class,args);
    }
}
```

#### 配置run deshboard

```
deshboard 可以方便的快速启动多个服务
配置方式 快捷键 alt+8 然后点击+号选择springboot
```

![image-20231004224832536](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310042248637.png)

#### 配置springboot整合rabbitmq

```xml
server:
  port: 8081

spring:
  rabbitmq:
    host: 192.168.199.106
    port: 5672
    username: admin
    password: admin
    virtual-host: / #需要连接的交换机
```

#### 发送消息到队列

##### 发送消息service接口

```java
package com.et.service;

public interface RabbitMQService {

    /**
     * 发送消息
     * @param message
     */
    void send(String message);
}

```

#### 发送消息Service实现类

```java
package com.et.service;

import com.et.config.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rabbitmqService")
public class RabbitMQServiceImpl implements RabbitMQService{

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void send(String message) {
        amqpTemplate.convertAndSend(RabbitMQConfig.DIRECTEXCHANGENAME,RabbitMQConfig.DIRECTROUTINGKEY,message);
    }
}

```

#### 测试发送消息

直接在启动类中测试

```java
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(App.class, args);
        RabbitMQService rabbitMQService = applicationContext.getBean("rabbitmqService",RabbitMQService.class);
        rabbitMQService.send("hello rabbitmq");
    }
}
```

![image-20231004232227711](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310042322669.png)



```
Queue:
durable 是否持久化，true叫花鸡或队列会存储到本地文件数据库，当mq重启时依然存在，false 断电或重启就没有了 默认true
autoDelete 是否自动删除 true当没有消费者时自动删除队列中的消息，默认false
exclusive 是否独占 true 只能有一个消费者监听这个队列，默认false
```

![image-20231005000138073](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310050001164.png)

### 消费者实现

#### 引入common模块

```xml
<dependency>
    <groupId>com.et</groupId>
    <artifactId>rabbitmq-common</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

#### 添加启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class,args);
    }
}

```

#### 配置springboot整合rabbitmq

```xml
server:
  port: 8082

spring:
  rabbitmq:
    host: 192.168.199.106
    port: 5672
    username: admin
    password: admin
    virtual-host: / #需要连接的交换机
```

#### 接收消息

##### 普通方式接收

该方式只能每次调用时才会接收消息

###### service接口

```java
package com.et.service;

public interface RabbitMQService {

    /**
     * 接收消息
     * @param queueName  队列名称
     */
    void receiveMessage(String queueName);
}

```

###### service接口实现类

```java
package com.et.service;

import com.et.config.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rabbitmqService")
public class RabbitMQServiceImpl implements RabbitMQService{

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void send(String message) {
        amqpTemplate.convertAndSend(RabbitMQConfig.DIRECTEXCHANGENAME,RabbitMQConfig.DIRECTROUTINGKEY,message);
    }
}

```

###### 启动类中测试接收消息

```java
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
        RabbitMQService rabbitmqService = ac.getBean("rabbitmqService", RabbitMQService.class);
        rabbitmqService.receiveMessage(RabbitMQConfig.DIRECTQUEUE);
    }
}

```

![image-20231004234630652](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310042346641.png)

##### 监听方式接收消息

该方式会自动监听一个或多个队列 只要有消息就会自动接收

###### service接口添加方法

```java
 /**
     * 监听方式接收消息
     * @param queueName
     */
void receiveMessage2(String queueName);
```

###### service接口实现类

```java
@Override
@RabbitListener(queues = {"directQueue"})
public void receiveMessage2(String message) {
    System.out.println("接收到的消息："+message);
}
```

###### 测试接收消息

启动类中不需要单独获取service然后在调用里面的方法了 启动时就会自动监听

```java
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

```

![image-20231004235333525](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310042353482.png)

## RabbitMQ生产方式

### helloworld

![Producer -> Queue -> Consuming: send and receive messages from a named queue.](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310050002872.png)

```
一个生产者 一个消费者
```

### work queues

工作模式

![Producer -> Queue -> Consuming: Work Queue used to distribute time-consuming tasks among multiple workers.](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310050004932.png)

```
一个生产者 多个消费者
工作模式是一个或多个消费者共同消费一个队列中的消息
队列中的每一个消息只可能被其中一个消费者消费
```

#### 模拟多个消费者消费消息

##### 生产者

发送多条消息到队列中

启动类中添加循环发送即可

```java
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
        for (int i = 0; i < 10; i++) {
            rabbitMQService.send("hello rabbitmq "+i);
        }
    }
}

```

##### 消费者

service接口

```java
// 测试多个消费者消费消息
void receiveMessage3(String message);
```

service接口实现类

```java
@RabbitListener(queues = {"directQueue"})
@Override
public void receiveMessage3(String message) {
    System.out.println("消费者2 接收到的消息："+message);
}
```

##### 测试启动

```java
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

```

![image-20231005001907901](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310050019457.png)



![image-20231005001453825](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310050014029.png)

### Publish/Subscribe

发布订阅模式

![Producer -> Queue -> Consuming: deliver a message to multiple consumers. This pattern is known as publish/subscribe](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310050021709.png)

```
发布订阅模式
生产者生产的消息 所有订阅过的消费者都能够接收到此消息
交换机(Exchange)采用Fanout类型 即广播方式
Fanout类型的交换机会把消息发送到所有绑定到该交换机的队列
适用场景   消息推送，右键群发等
```

#### 案例

##### common模块中rabbitmq配置

添加发布订阅模式的相关配置

```java
package com.et.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}

```

##### 生产者生产消息

service接口

```java
void fanoutSend(String message);
```

service接口实现类

```java
@Override
public void fanoutSend(String message) {
    // 没有路由key 给个空值即可 不能使用两个参数的 因为两个参数的 会把第一个当作路由key
    amqpTemplate.convertAndSend(RabbitMQConfig.FANOUTEXCHANGE,"",message);
}
```

测试类

发送消息到队列中

```java
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
        for (int i = 0; i < 10; i++) {
            //rabbitMQService.send("hello rabbitmq "+i);
            rabbitMQService.fanoutSend("群发消息："+i);
        }
    }
}

```

![image-20231005010759884](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310050108468.png)

##### 消费者订阅消息

service接口

```java
  // 订阅者1接收消息
void receiveFanoutMessage1(String message);

// 订阅者2接收消息
void receiveFanoutMessage2(String message);
```

service实现类

```java
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
```

测试

```java
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

```

![image-20231005010922935](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310050109073.png)

小结

```
发布订阅模式 
1. 使用Fanout类型的交换机
2. 所有的消息队列都绑定到该交换机上
3. 发送消息时 是通过交换机去找当前交换机上绑定的所有队列
4. 消息消息时 监听只要是绑定在该交换机上的队列都会收到一份相同的消息并进行消费
```

### Routing

![Producer -> Queue -> Consuming: subscribe to a subset of the messages only.](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310050113176.png)

```
路由模式
路由模式与发布订阅模式类似，只是在订阅模式的基础上添加了消息的类型，订阅模式是分发到所有绑定到该交换机上的队列，而路由模式只分发到绑定在交换机上指定路由键的队列上

```

#### 案例

```
1. rabbitmq配置中添加routting方式的配置
2. 生产者中
```

#### common模块中配置rabbitmq的routting模式

RabbitMQConfig

```java
package com.et.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}

```

#### 生产者

生产者发送消息到队列中

service接口

```java
/**
     * routting模式 发送消息
     * @param message 消息内容
     * @param routingKey  消息路由模式  有 error  info  warning
     */
void routingSendError(String routingKey,String message);
```

service接口实现类

```java
@Override
public void routingSendError(String routingKey,String message) {
    amqpTemplate.convertAndSend(RabbitMQConfig.ROUTTINGEXCHANGE,routingKey,message);
}
```

测试

```java
public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(App.class, args);
        RabbitMQService rabbitMQService = applicationContext.getBean("rabbitmqService",RabbitMQService.class);
        for (int i = 0; i < 10; i++) {
            //rabbitMQService.send("hello rabbitmq "+i);
            //rabbitMQService.fanoutSend("群发消息："+i);
            // 路由模式发送消息
            rabbitMQService.routingSendError("error","发送error级别的消息 "+i);
            rabbitMQService.routingSendError("info","发送info级别的消息 "+i);
            rabbitMQService.routingSendError("warning","发送warning级别的消息 "+i);
        }
    }
```

![image-20231005092040833](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310050920140.png)

![image-20231005100244973](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051002662.png)

消费者

接收消息

service接口

```java
public void receiveRoutingMessage2(String message);
```

service接口实现类

```java
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
```

测试

```java
 public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(ConsumerApp.class,args);
//        RabbitMQService rabbitmqService = ac.getBean("rabbitmqService", RabbitMQService.class);
//        rabbitmqService.receiveMessage(RabbitMQConfig.DIRECTQUEUE);
    }
```



![image-20231005100431422](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051004467.png)

调整生产者发送不同级别消息然后在测试

![image-20231005100727713](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051007796.png)

![image-20231005100631227](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051006302.png)

### Topic

![Producer -> Queue -> Consuming: receiving messages based on a pattern (topics).](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051057140.png)



```
主题模式
主题模式与路由模式类似
如果把路由模式理解为精确匹配 那么主题模式则是模糊匹配
主题模式的交换机是topic类型，通过通配符方式进行匹配队列
	* 表示匹配一个单词  * (star) can substitute for exactly one word.
	# 表示匹配零个或多个单词 * (star) can substitute for exactly one word.
```

#### common模块中rabbitmq配置

配置topic模式

```java
package com.et.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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


    // 主题模式
    public static String TOPICEXCHANGE="topicExchange";
    public static String TOPICQUEUE1="topicQueue1";
    public static String TOPICQUEUE2="topicQueue2";


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
}

```

#### 生产者

生产者发送消息时 采用的是模糊匹配

service接口

```java
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

}

```

service接口实现类

```java
package com.et.service;

import com.et.config.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rabbitmqService")
public class RabbitMQServiceImpl implements RabbitMQService{

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void send(String message) {
        amqpTemplate.convertAndSend(RabbitMQConfig.DIRECTEXCHANGENAME,RabbitMQConfig.DIRECTROUTINGKEY,message);
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
}

```

测试

```java
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
        for (int i = 0; i < 10; i++) {
            //rabbitMQService.send("hello rabbitmq "+i);
            //rabbitMQService.fanoutSend("群发消息："+i);
        }
        // 路由模式发送消息
//        rabbitMQService.routingSend("error","发送error级别的消息 ");
//        rabbitMQService.routingSend("info","发送info级别的消息 ");
//        rabbitMQService.routingSend("warning","发送warning级别的消息 ");

        // topic 模式
        rabbitMQService.topicSend("quick.orange.rabbit","飞快的橘色的小兔子");
		// rabbitMQService.topicSend("lazy.blue.rabbit","行动缓慢的蓝色小兔子");
    }
}

```

![image-20231005111906931](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051119830.png)

#### 消费者

service接口

```java
// topic模式
void receiveTopicMessage(String message);
void receiveTopicMessage2(String message);
```

service接口实现类

```java
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
```

![image-20231005112305679](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051123705.png)



### RPC

![Producer -> Queue -> Consuming: RPC (Remote Procedure Call), the request/reply pattern.](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051140132.png)

```
RPC = Remote Procedure Call 即 远程调用模式
简单理解 两段程序不带一个内存空间中，无法直接通过方法名进行调用，就需要通过网络通讯的方式进行调用
对于Rabbit MQ本身就是用于消息通信的  简单的rabbitmq是生产者发送消息 经由交换器到达消息队列
消费者不需要知道生产者 ，消费者只需要订阅队列 - 消费队列中的消息即可
而对于RPC 希望消费者消费消息后 返回一个结果 ，该结果经由网络 在返回给生产者
缺陷：
	不考虑Rabbit MQ针对RPC的特有设计，最简单的设计是 生产者和消费者共同约定消费队列和恢复队列，同时生产者每次发送消息时指定一个唯一ID 生产者将消息和唯一ID发送给消费队列，消费者栋消息队列中获取消息 处理后 将结果和生产者发送过来的唯一ID一并发送给回复队列，升段这从回复队列获取消息和ID，判断ID是否匹配，匹配则表示此消息为回复消息
	以上实现的RPC存在问题：
		生产者和消费者需要约定回复队列，就需要生产者和消费者相互知道，这无法实现解耦
		解决方案：生产者在发送消息时也将回复队列名称消息一起发送给队列
```

### 消息投递的可靠性

```
在消息发送出去后 会经过两个阶段 1 到交换机，2 交换机到队列
在这两个阶段中都有可能出现故障导致消息发送失败
解决这种问题有两种方案
1 带确认的消息模式
2 回退模式
```

#### Publisher Confirms

```
带确认的消息模式
要使用该模式 必须要配置如下内容
1. publisher-confirm-type: correlated
2. 在service接口实现类上实现RabbitTemplate.ConfirmCallback接口 并实现confirm方法
	 	@Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            if(ack){
                System.out.println("交换机 消息发送成功"+cause);
            }else{
                System.out.println("交换机 消息发送失败"+cause);
                // 做消息补发措施
            }
        }
  
3. 给所有的交换机上设置发送成功与否都需要自动回调
	@Autowired
    private RabbitTemplate rabbitTemplate;
    // 初始化后执行
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
    }
4. 测试发送
```

##### 消息生产者

生产者的配置文件

```yaml
server:
  port: 8081
spring:
  rabbitmq:
    host: 192.168.199.106
    port: 5672
    username: admin
    password: admin
    virtual-host: /
    publisher-confirm-type: correlated #开启消息回调
```



service实现类

```java
package com.et.service;

import com.et.config.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service("rabbitmqService")
public class RabbitMQServiceImpl implements RabbitMQService, RabbitTemplate.ConfirmCallback {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    // 初始化后执行
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
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
}

```

发送测试消息

```java
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
        for (int i = 0; i < 10; i++) {
            //rabbitMQService.send("hello rabbitmq "+i);
            //rabbitMQService.fanoutSend("群发消息："+i);
        }
        // 路由模式发送消息
//        rabbitMQService.routingSend("error","发送error级别的消息 ");
//        rabbitMQService.routingSend("info","发送info级别的消息 ");
//        rabbitMQService.routingSend("warning","发送warning级别的消息 ");

        // topic 模式
        //rabbitMQService.topicSend("quick.orange.rabbit","飞快的橘色的小兔子");
        //rabbitMQService.topicSend("lazy.blue.rabbit","行动缓慢的蓝色小兔子");

        // 带回调确认的消息模式
        rabbitMQService.send("hello rabbitmq ");
    }
}

```



![image-20231005120506206](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051205221.png)

![image-20231005120735338](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051207363.png)

##### 消费者

消费者端正常接收消息即可

service接口

```java
package com.et.service;

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
}

```

service接口实现类

```java
package com.et.service;

import com.et.config.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rabbitmqService")
public class RabbitMQServiceImpl implements RabbitMQService{

    @Autowired
    private AmqpTemplate amqpTemplate;


    @Override
    public void receiveMessage(String queueName) {
        String msg = (String) amqpTemplate.receiveAndConvert(RabbitMQConfig.DIRECTQUEUE);
        System.out.println("接收到的消息："+msg);
    }

    @Override
    @RabbitListener(queues = {"directQueue"})
    public void receiveMessage2(String message) {
        System.out.println("消费者1 接收到的消息："+message);
    }

    @RabbitListener(queues = {"directQueue"})
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
}

```



![image-20231005121044500](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051210606.png)



#### Publisher Return

```
消息回退模式
当消息发送失败时 进行回退
1. 要使用此模式 也必须在消息发送端（生产者端）进行配置
publisher-returns: true # 开启回退模式
2. 消息发送接口实现类必须实现RabbitTemplate.ReturnCallback接口
returnedMessage()
3. 测试
	为了测试回退 模拟一个找不到路由key的消息
```

service接口实现类

```java
package com.et.service;

import com.et.config.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

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
        // 模拟消息发送失败回退 这里设置一个不存在的路由key - no routingkey
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECTEXCHANGENAME,
                "no routingkey",message,new CorrelationData("1234567"));

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

```

测试

```java
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
        for (int i = 0; i < 10; i++) {
            //rabbitMQService.send("hello rabbitmq "+i);
            //rabbitMQService.fanoutSend("群发消息："+i);
        }
        // 路由模式发送消息
//        rabbitMQService.routingSend("error","发送error级别的消息 ");
//        rabbitMQService.routingSend("info","发送info级别的消息 ");
//        rabbitMQService.routingSend("warning","发送warning级别的消息 ");

        // topic 模式
        //rabbitMQService.topicSend("quick.orange.rabbit","飞快的橘色的小兔子");
        //rabbitMQService.topicSend("lazy.blue.rabbit","行动缓慢的蓝色小兔子");

        // 带回调确认的消息模式
        rabbitMQService.send("hello rabbitmq2 ");
    }
}

```

![image-20231005122502523](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051225754.png)

### ACK消费确认机制

```
消费端在接收到消息后 手动的进行确认消息
ack = acknowledge （承认，大写，告知已收到，确认）在rabbitmq中指代的是消费者收到消息后确认的一种行为，关注点在于消费者能否实际接收到MQ发送的消息
提供了三种确认方式
	自动确认
		ancknowledge="none" 当消费者接收到消息的时候 就会自动给到RabbitMQ一个回执，告诉MQ已经收到消息了，不在乎消费者接收到消息之后业务处理的成功与否
		acknowledge="manual" 当消费者接收到消息后 不会立即告诉RabbitMQ已经收到消息了 而是等待业务处理成功后 通过调用代码的方式 手动向MQ发送确认消息已收到，当业务处理失败时 就可以做一些重试机制，甚至可以让MQ重新向消费者发送消息都可以
		acknowledge="auto" 该方式是通过抛出异常的类型来做相应处理 如 重发消息，确认等
		这种方式相对复杂
当消息一旦被消费者接收到 会立刻自动向MQ确认接收 并将相应的message从RabbitMQ消息缓存中移除，但在实际业务处理中 会出现消息收到了 但业务处理出现异常的情况，在自动确认的模式下 该条业务处理失败的message就等同于被丢弃了，如果设置了手动确认 则需要在业务处理完成之后 手动调用channel.basickAck() 手动签收，如果业务处理失败，则手动调用 channel.basicNack()方法聚首 并让MQ重新发送该消息

如果不做任何关于acknowledge的配置 默认就是自动确认签收

```

#### 消费者

##### 配置开启ACK机制

```yaml
server:
  port: 8082

spring:
  rabbitmq:
    host: 192.168.199.106
    port: 5672
    username: admin
    password: admin
    virtual-host: / #
    listener:
      simple:
        acknowledge: manual # 开启消息确认为manual模式（手动向MQ发送确认消息已收到）
```

##### 接收消息接口实现类

```java
 /**
     *
     * @param message 消息主题
     * @param channel 管道 用来手动确认接收消息
     * @param deliveryTag 每条消息的唯一标识
     */
@Override
@RabbitListener(queues = {"directQueue"})
public void receiveAckMessage(String message, Channel channel,@Header(AmqpHeaders.DELIVERY_TAG)  long deliveryTag) {
    System.out.println("接收到的消息："+message);
    try {
        System.out.println("模拟处理业务逻辑");
        // 手动通知已经接收到了消息  true表示是否批量处理
        channel.basicAck(deliveryTag,true);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
```

测试

测试时需要备注掉其余监听directQueue队列的方法 否则会被其他方法给消费掉

```java
 public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(ConsumerApp.class,args);
//        RabbitMQService rabbitmqService = ac.getBean("rabbitmqService", RabbitMQService.class);
//        rabbitmqService.receiveMessage(RabbitMQConfig.DIRECTQUEUE);
    }
```

![image-20231005163751604](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051637853.png)

模拟发生异常时 消息拒签

```java
/**
     *
     * @param message 消息主题
     * @param channel 管道 用来手动确认接收消息
     * @param deliveryTag 每条消息的唯一标识
     */
    @Override
    @RabbitListener(queues = {"directQueue"})
    public void receiveAckMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        System.out.println("接收到的消息："+message);
        try {
            System.out.println("模拟处理业务逻辑");
            System.out.println(1/0);
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
                // channel.basicNack(deliveryTag,false,false);
                // 与basicNack类似 都是用来拒签消息的
                channel.basicReject(deliveryTag,false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
```

### 消费者端并发和限流

#### 并发

```
有时候 我们需要加快消息处理的速度，这时候可以通过提高消息处理程序的并发量来提高消息的处理能力
在rabbitListener中配置concurency="min-max"即可
	min 表示最小并发  max表示最大并发数
	例如concurency="5-10"
```

消费者端接收消息接口实现类

```java
 @Override
    @RabbitListener(queues = {"directQueue"},concurrency = "5-10")
    public void receiveAckMessage2(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        System.out.println("接收到的消息："+message);
        try {
            System.out.println("模拟处理业务逻辑");
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
```

生产者发生消息进行测试

```java
Application启动类中发生消息
for (int i = 0; i < 100; i++) {
    rabbitMQService.send("hello rabbitmq "+i);
}
```

![image-20231005171347638](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051713027.png)



#### 限流

```
有些场景 消费者处理并发太大的时候会影响消息处理的性能，例如 同时需要操作数据库或ws等三方接口等
此时需要配合限流使用

@Autowired
private CachingConnectionFactory factory;

 @Bean(name = "limitContainerFactory")
 public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(){
 SimpleRabbitListenerContainerFactory fac = new SimpleRabbitListenerContainerFactory();
 fac.setConnectionFactory(factory);
 fac.setPrefetchCount(3);// 每次在队列中获取三条消息
 return fac;
 }
 
 然后在rabbitListener中配置containerFactory = "limitContainerFactory"
 例如
 @RabbitListener(queues = {"directQueue"},containerFactory = "limitContainerFactory")
```

消费者端接收消息实现类

```java
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
}

```



![image-20231005172537809](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051725929.png)

### TTL

```
TTL Time To Live 过期时间
主要有两种方式
指定一条消息的过期时间
给队列设置消息的过期时间 队列中的所有消息都会有同样的过期时间

应用场景
用户购买某个商品 在一定时间内未付款，则订单自动删除

说明
如果同时指定了单条消息的TTL和队列的TTL 优先级为最小的那一个 即 单挑的TTL

注意：
TTL的延时队列存在一个问题 就是同一个队列里的消息延时时间最好一致，比如队列里的延时时间都是1h 千祈唔要将队列里的消息延时时间随意设置 这样的花 先入队的消息如果延时时间过长会堵着后入队的消息，导致后面的消息即便到期也无法变成死信转发出去
例如 ABC三条消息的延时时间分别是3h 2h 1h 结果到了1h后C不会死 到了2h后B也不回死 到了3h后A死了 BC也跟着死了


```

#### 单条消息案例

带有过期时间的单条消息

##### common模块中重新配置一个队列

1 创建交换机

2 创建队列

3 将交换机绑定到队列

```java
package com.et.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        return new Queue(TTL_DIRECTQUEUE);
    }

    // 队列绑定到交换机
    @Bean
    public Binding ttlBinding(){
        return BindingBuilder.bind(ttlQueue()).to(ttlDirectExchange()).with(TTL_ROUTINGKEY);
    }
}

```

##### 生产者

service接口发生带有过期时间的消息

```java
// 测试带有过期时间的消息
void sendTTLMessage(String message);
```

service接口实现类 在发生时需要指定过期时间

```java
 @Override
public void sendTTLMessage(String message) {
    MessageProperties prop = new MessageProperties();
    prop.setExpiration("10000"); // 设置过期时间 单位毫秒
    Message msg = new Message(message.getBytes(StandardCharsets.UTF_8),prop);
    rabbitTemplate.send(RabbitMQConfig.TTL_DIRECTEXCHANGE,RabbitMQConfig.TTL_ROUTINGKEY,msg);
}
```

测试

发送消息到队列后 等待一会 消息会自动销毁

```java
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
        // TTL message
        rabbitMQService.sendTTLMessage("带有过期时间的消息");
    }
}

```

![image-20231005184455930](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051844231.png)



#### 给整个队列设置过期时间

```
队列参数：
name 队列的名称
actualName 队列的真实名称 默认为name参数 如果为空 则根据规则生成一个
durable 是否持久化
excluslve 是否独占
arguments 队列的其他属性参数 有如下可选项
	x-message-ttl 消息的过期时间 单位毫秒
	x-explres 队列的过期时间 队列在多少时间违背访问将被删除 单位毫秒
	x-max-length 队列最大长度 超过最大值则将从队列头部开始删除消息
	x-max-length-bytes 队列消息内容占用最大空间，受限于内存大小，超出该阙值则从队列头部开始删除
	x-overflow 设置队列溢出行为，这决定了当到达队列最大长度时 消息会发生什么，有效值是drop-head,rehect-publish或reject-publish-dlx 仲裁队列类型仅支持drop-head
	x-dead-letter-exchange 死信交换器 过期或被删除的消息可指定发送到该交换器
	x-dead-letter-routing-key 死信消息路由器 在消息发送到死信交换机时会调用该路由键
	x-single-active-consumer 表示队列是否是第一活动消费者 true时注册的消费组内只有一个消费者消费消息
		其他被忽略，false时消息循环分发给所有消费者 默认false
	x-max-priority 队列要支持的最大优先级 未设置 队列不支持消息的优先级
	x-ququq-mode 队列设置为延迟模式
	x-ququ-master-locator 集群模式下设置镜像队列的主节点信息
```

##### common模块中 调整

调整发送消息时给队列设置过期时间

```java
 @Bean
public Queue ttlQueue(){
    Map<String,Object> map = new HashMap<>();
    map.put("x-message-ttl",30000);
    /*
        String name, 队列名称
        boolean durable, 是否持久化
        boolean exclusive, 是否独占
        boolean autoDelete, 是否自动删除
	    @Nullable Map<String, Object> arguments 其他选项 可设置队列的过期时间等
         */
    return new Queue(TTL_DIRECTQUEUE,true,false,false,map);
}
```

生产者测试

测试时使用单条消息的测试案例即可 可以改成多条消息(使用循环发送即可)

![image-20231005193827587](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051938688.png)

![image-20231005193837914](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310051938180.png)

### 死信队列

![image-20231005205053978](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310052050832.png)

```
死信队列英文缩写DLX   Dead Letter Exchange(死信交换机) 当消息称为dead message时 可以被重新发送到另一个交换机 这个交换机就是DLX

队列中消息可能会变成死信消息 金额当一下几个事件任意一个发生时 消息将被重新发送到一个交换机
1 消息被消费者使用 basic.reject或basic.nck方法且requeue参数设置为false的方式进行消息确认
2 消息的有效期过期
3 消息队列超过其长度限制而被丢弃
注意 队列的有效期并不会导致其中的消息过期

x-dead-letter-exchange 指定死信交换机
x-dead-letter-routing-key 指定死信路由key
```

#### 案例1 过期的消息

超过有效期的消息会进入到死信队列

##### common模块中配置rabbitmq

添加死信队列 用于当消息过期或被消费等时 自动进入到死信队列

使用时 在普通队列中添加队列的过期时间 同时指定队列中消息的去向(例如被消费了 或超过有效期 去到死信队列中)

```java
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

    // 带过期时间的消息
    public static String TTL_DIRECTEXCHANGE="ttlDirectExchange";
    public static String TTL_DIRECTQUEUE="ttlDirectQueue";
    public static String TTL_ROUTINGKEY="ttlRoutingKey";

    // 死信队列
    public static String DLXEXCHANGE="dlxExchange";
    public static String DLXROUTINGKEY="dlxRoutingKey";
    public static String DLXQUEUE="dlxQueue";

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
        map.put("x-message-ttl",5000);// 过期时间 单位毫秒
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
}

```

##### 生产者

service接口实现类

```java
 @Override
public void sendTTLMessage(String message) {       rabbitTemplate.convertAndSend(RabbitMQConfig.TTL_DIRECTEXCHANGE,RabbitMQConfig.TTL_ROUTINGKEY,message);
}
```

测试

```java
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
        

        // TTL message
        for (int i = 0; i < 100; i++) {
            rabbitMQService.sendTTLMessage("带有过期时间的消息");
        }
    }
}

```

![image-20231005200223618](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310052002760.png)

![image-20231005200207766](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310052002917.png)

#### 案例2 消息长度

当消息长度超出范围 放到死信队列中

##### common模块中调整rabbitmq配置

```java
@Bean
public Queue ttlQueue(){// 因为我们发送消息 使用的这个队列 因此在该队列中进行调整
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
```

测试

```java
for (int i = 0; i < 100; i++) {
    rabbitMQService.sendTTLMessage("带有过期时间的消息");
}
```

![image-20231005211934748](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310052119755.png)

### 延迟队列

```
延迟队列，首先它是一种队列，队列意味着内部的元素是有序的，元素出队和入队是有方向性的 元素从一端进入 从另一端取出，其次延迟队列最紧要嘅特性在于它的延时属性上，跟普通队列不一样的是 普通队列中的元素总是等待早点被取出处理，而延时队列中的元素则是希望在指定的时间得到取出和处理 所以延时队列中的元素都是带有时间属性的 
简单黎讲 延迟队列就系用来存放需要在指定嘅时间被处理的元素的队列
延迟队列的使用场景
订单在一定时间内未支付 则自动取消 等

延迟队列的实现方式
1 TTL+死信队列
生产者生产一条延时消息 根据需要演示的时间 利用不同的routingkey将消息路由到不同的队列中，每个队列都设置了不同的TTL属性，并绑定在同一个死信交换机中，消息过期后 根据routingkey的不同 又会被路由到不同的死信队列中，消费者只需要监听对应死信队列进行处理即可

2. 利用RabbitMQ插件实现
安装一个插件即可
https://www.rabbitmq.com/community-plugins.html
下载rabbitmq_delayed_message_exchange插件 然后解压缩到RabbitMQ的插件目录
接下来进入RabbitMQ安装目录下的bin目录中执行下边命令让插件生效 最后重启rabbitMQ即可
执行命令
	rabbit-plugins enable rabbitmq_delayed_message_exchange


```

#### 添加插件

```
1 目录挂载
我们使用的是docker方式 要将插件添加到容器中 需要进行目录挂载
docker run -di --rm --name rabbitmq -v /home/docker/rabbitmq/:/root/data/ -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management

2 添加插件到/home/docker/rabbitmq目录下
docker exec -it 2c81df27b36a /bin/bash
3 进入到容器 复制插件到 /plugins目录下
cp rabbitmq_delayed_message_exchange-3.12.0.ez /plugins
4 执行安装插件
rabbitmq-plugins enable rabbitmq_delayed_message_exchange // 注意 插件不能带后缀和版本号
5 重启rabbitmq
docker restart 容器ID
```

下载插件

![image-20231005223647227](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310052236208.png)

![image-20231005223849805](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310052238227.png)

![image-20231005224359923](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310052244157.png)

![image-20231005224504093](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310052245161.png)

#### common模块中新增延迟队列的配置

1 创建交换机 使用自定义交换机

2 创建队列

3 绑定队列到交换机

```java
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

    

    // 延迟队列
    public static String DELAYEDEXCHANGE="delayedExchange";
    public static String DELAYEDQUEUE="delayedQueue";
    public static String DELAYEDROUTINGKEY="delayedRoutingKey";

    

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

```

#### 生产者

##### service接口和实现类 

```java
// 发送延迟消息 delayedTime延迟时间 单位毫秒
void sendDelayedMessage(String message,Integer delayedTime);


@Override
public void sendDelayedMessage(String message, Integer delayedTime) {
    rabbitTemplate.convertAndSend(RabbitMQConfig.DELAYEDEXCHANGE,RabbitMQConfig.DELAYEDROUTINGKEY,
                                  message,t->{// 借助Message设置延迟时间
                                      t.getMessageProperties().setDelay(delayedTime);
                                      return t;
                                  });
}
```

##### 测试

```java
// 发送延迟消息
rabbitMQService.sendDelayedMessage("测试延迟消息 10s",10000);
rabbitMQService.sendDelayedMessage("测试延迟消息 20s",20000);
```

![image-20231005232516838](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310052325447.png)

#### 消费者

service接口和实现类

```java
// 接收延迟消息
void reveiveDelayedMessage(String message);


@Override
@RabbitListener(queues = {"delayedQueue"})
public void reveiveDelayedMessage(String message) {
    System.out.println("接收到的延迟消息："+message+" 接收时间："+new Date().toString());
}
```

测试

测试前 需要将消费端的手动确认消息改为自动确认消息

```
server:
  port: 8082

spring:
  rabbitmq:
    host: 192.168.199.106
    port: 5672
    username: admin
    password: admin
    virtual-host: / #
    listener:
      simple:
        acknowledge: auto #manual # 开启消息确认为manual模式（手动向MQ发送确认消息已收到）
```

![image-20231005232825231](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310052328379.png)

```
public static void main(String[] args) {
	SpringApplication.run(ConsumerApp.class,args);
}
```

![image-20231005232729307](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310052327668.png)

## RabbitMQ集群

参考

https://blog.csdn.net/fqydhk/article/details/80430503
