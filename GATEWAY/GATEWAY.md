# GateWay

## 简述

### API网关介绍

```
网关的角色作为一个API架构 用来保护、增强和控制对于API服务的访问
API网关是一个处于应用程序或服务之前的系统，用来管理授权，访问控制和限制流量等
这样我们的服务就会被API网关保护起来，对所有的调用者透明，因此隐藏在API网关后面的业务系统就可以专注于创建和管理服务，而不用取处理这些策略性的基础设施
在微服务架构里 服务的力度被进一步细分，各个业务可以被独立的设计，开发，测试，部署及管理，这时各个独立部署单元就可以用不同的开发测试团队进行维护，可以使用不同的编程语言和技术平台进行设计，这就要求必须使用一种语言和平台无关的服务协议作为各个单元间的通讯方式
```

![image-20231018190144179](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310181901832.png)

### API网关的作用

```
请求接入
	作为所有API接口服务请求的接入点
业务聚合
	作为所有后端业务服务的聚合点
中间策略
	实现安全，验证，路由，过滤，流控等策略
统一管理
	对所有API服务和策略进行统一管理
```

![image-20231018191449100](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310181914963.png)

### API网关的开源实现

```
用于实现API网关的技术有很多，大致分为以下几类
1 反向代理 
	Nginx,Haproxy,...
2 网络编程框架
	Netty,Servlet,...
API网关框架
	SpringCloud GateWay,Zuul,Zuul2,...
```

### GateWay介绍

```
官网
https://spring.io/projects/spring-cloud-gateway
文档
https://docs.spring.io/spring-cloud-gateway/docs/2.2.7.RELEASE/reference/html/

SpringCloud GateWay是Spring官方基于SPring5.0，Springboot2.0和Project Reactor等技术开发的网关
SpringCloud GateWay旨在为微服务架构提供一种简单而有效的统一的API路由管理方式
SpringCloud GateWay作为SpringCloud生态系统中的网关，目标是替代Zuul，其不仅提供统一的路由方式并且基于Filter链的方式提供了网关基本的功能 如安全，监控，埋点，限流等
SpringCloud GateWay是用来替代Zuul，是与SpringCloud紧密配合的API网关
SpringCloud GateWay里面明确区分了Router和Filter 并且一个很大的特点是内置了非常多的开箱即用的功能并且都可以通过SpringBoot配置或手动编码链式调用来使用
例如 内置了10种Router 使得我们可以直接配置下就可以随心所欲的根据Header或Path或Query等来做路由
同时还区分了一般的Filter和全局的Filter 内置了20种Filter和9种全局Filter 也都可以直接使用 当然自定义Filter更方便

SpringCloud GateWay特性：
	基于Spring5.0,ProjectReactor和SpringBoot2.0进行构建
	能够匹配任何擦请求
	可以对路由指定Predicates和Filters
	集成了断路器
	集成SpringCloud服务发现
	易于编写的Predicates和Filters
	支持请求跨域
	支持路径重写
```



### GateWay工作原理

```
客户端向SpringCloud GateWay发出请求，如果网关处理程序映射确定请求与路由匹配，则将其发送到网关web处理程序，该处理程序通过特定于请求的过滤器链来运行请求，筛选器由虚线分割的原因是 筛选器可以在发生代理请求之前和之后都运行逻辑，所有前置过滤器逻辑均被执行，然后发送代理请求，发送代理请求后 将运行后置过滤器逻辑

三个概念
	路由	Router
		路由是构建网关的基本模块，它由ID，目标URI等一系列的断言和过滤器组成，如果断言为true则匹配该路由
	断言	Predicates
		参考JDK8的java.util.function.Predicate 开发人员可以匹配HTTP请求中的所有内容 
		例如请求头或请求参数，如果请求与断言相匹配则近些路由
	过滤	Filter
		Spring框架中GateWayFilter的实例，使用过滤器可以将请求被路由前或后对请求近些修改
```

![Spring Cloud Gateway Diagram](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310190029037.png)

## 入门案例1

仅体验gateway的路由功能

### 新建gateway项目

![image-20231021215559728](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310212156852.png)





### 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
</dependencies>
```

### 创建配置文件

```yaml
server:
  port: 81

spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: baidu
          uri: https://www.baidu.com
          predicates: #定义断言规则
            - Path=/ # 访问dog时 跳转到baidu.com
        - id: java
          uri: http://oracle.com
          predicates:
            - Path=/java/** # 访问java时跳转到oracle官网

```

### 创建启动类

```java
package com.et.gateway_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayTestApplication.class, args);
    }

}

```

### 测试

![image-20231021221156648](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310212211815.png)

![image-20231021220549700](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310212205871.png)

## 快速入门2

配合服务模块进行体验路由功能

新建两个项目 对请求进行统一管理

![image-20231021213528800](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310212135062.png)

### 父项目pom

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <modules>
        <module>product-demo</module>
        <module>order-demo</module>
        <module>gateway-server</module>
    </modules>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.et</groupId>
    <artifactId>gateway</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>pom</packaging>
    <name>gateway</name>
    <description>gateway</description>
    <properties>
        <java.version>1.8</java.version>
        <spring.cloud.version>Hoxton.SR8</spring.cloud.version>
        <springboot.version>2.3.2.RELEASE</springboot.version>
        <springcloudalibaba.version>2.2.6.RELEASE</springcloudalibaba.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring.cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${springboot.version}</version>
            </dependency>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${springcloudalibaba.version}</version>
            </dependency>
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

### 新建product模块

#### 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

#### 添加配置文件

```yaml
server:
  port: 8080
  servlet:
    context-path: /

spring:
  application:
    name: product-demo
```

#### 创建实体类

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Integer id;
    private String productName;
    private Integer number;
    private Double price;
}

```

#### 创建controller

```java
package com.et.controller;

import com.et.entity.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {


    @GetMapping("/{id}")
    public Product detail(@PathVariable("id") Integer id){
        return new Product(id,"xx商品"+id,2,100.00);
    }
}

```

#### 启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PeoductApp {
    public static void main(String[] args) {
        SpringApplication.run(PeoductApp.class,args);
    }
}

```

### 新建order模块

#### 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

#### 添加配置文件

```yaml
server:
  port: 8081
  servlet:
    context-path: /

spring:
  application:
    name: order-demo
```

#### 创建实体类

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Integer id;
    private String orderNo;
    private Double totalPrice;
    private String userInfo;
    private Integer productId;
}

```

#### 创建controller

```java
package com.et.controller;

import com.et.entity.Order;
import com.et.entity.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/{id}")
    public Order detail(@PathVariable("id") Integer id){
        return new Order(id,"20221228xxx",9999.99,"用户信息"+id,222);
    }
}

```

#### 启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class OrderApp {

    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class,args);
    }
}

```

### 创建gateway网关模块

#### 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
</dependencies>
```

#### 添加配置文件

```yaml
server:
  port: 80
  servlet:
    context-path: /
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则  可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            - Path=/product/**
        - id: order-service
          uri: http://localhost:8081
          predicates:
            - Path=/order/**
```

#### 启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GateWayApp {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApp.class,args);
    }
}

```

### 测试

```
启动product，order,gateway-server
直接访问product和order
	localhot:8080/product/1
通过网关访问
	localhost/product/1
```

![image-20231021214940250](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310212149362.png)

![image-20231021214954397](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310212149341.png)

## GateWay路由规则介绍

### 路由断言工厂

```
spring cloud gateway 将路由作为spring webflux handlerMapping 基础架构的一部分进行匹配，springcloud gateway包括许多内置的路由断言工厂，所有这些断言都与HTTP请求的不同属性匹配，可以将多个路由断言工厂逻辑使用and语句结合使用
路由断言工厂 RoutPredicateFactory包含的主要实现类如下图所示
```

![image-20231021221933879](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310212219222.png)

#### 日期时间匹配

```
上边入门案例已经体验了Path的路由匹配规则，这里我们在来体验下日期时间的匹配规则
日期时间的匹配规则分为如下三个方式
	匹配指定日期时间之后的请求 After
		请求在定义的时间段之前是不可以访问的
	匹配指定日期时间之前的请求 Before
		请求在定义的时间段之后是不可以访问的
	匹配指定日期时间之间的请求 Between
		请求在超过定义的时间段时是不可以访问的 两个日期之间使用逗号隔开
		
```

##### 匹配指定日期时间之后的请求 After

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            #- Path=/product/** # Path断言
            - After=2023-09-29T06:06:06+08:00[Asia/Shanghai]
```

##### 匹配指定日期时间之前的请求 Before

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            #- Path=/product/** # Path断言
            - Beofre=2023-09-29T06:06:06+08:00[Asia/Shanghai]
```

##### 匹配指定日期时间之间的请求 Between

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            #- Path=/product/** # Path断言
            - Between=2023-09-29T06:06:06+08:00[Asia/Shanghai],2023-10-29T06:06:06+08:00[Asia/Shanghai]
```

#### Cookie匹配

```
Cookie断言工厂采用两个参数进行匹配，一个是token name(自行指定的key)和一个正则表达式
该断言匹配具有给定名称且其值与正则表达式指定的cookie
例如 
	- Cookie=aa,\d+ # 表示带过来的cookie的key必须是aa 其值必须是一个或多个数字（\d+表示一个或多个数字 ）
```

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            #- Path=/product/** # Path断言
            #- Between=2023-09-29T06:06:06+08:00[Asia/Shanghai],2023-10-29T06:06:06+08:00[Asia/Shanghai]
            - Cookie=token,\d+
```

测试时需要使用postman 因为需要设置头信息

![image-20231021230252650](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310212302625.png)

![image-20231021231240383](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310212312371.png)

#### Header路由规则

```
Header路由工厂采用两个参数，消息头name和regexp（正则表达式） 
该断言具有给定名称的头信息匹配，该头信息值与正则表达式匹配则通过路由 否则无法通过路由
```

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            - Header=X-Request-Id, \d+
```

![image-20231023191918754](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310231919921.png)



#### Host路由规则

```
Host路由断言工厂需要一个参数 即 主机名的列表patterns 该模式是带有.分隔符的ant样式的模式
断言与host匹配模式的标头匹配
```

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            - Host=**.abc.org,**.def.io
```

测试时 直接使用localhost是无法匹配到例如abc.org  需要修改本机的hosts文件来进行匹配

C:\Windows\System32\drivers\etc

![image-20231023192428608](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310231924699.png)

![image-20231023192513844](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310231925781.png)

#### Method路由规则

```
需要一个methods参数，该参数指定一个或多个请求方式  然后使用Http的请求方式来匹配
```

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            - Method=GET,POST
```

![image-20231023192811907](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310231928913.png)

#### Path路由规则

```
Path路由断言工厂有两个参数 PathMatcher patterns和一个可选的标志matchOptionalTrailingSeparator
可以通过后台代码获取到动态传入的值
Map<String, String> uriVariables = ServerWebExchangeUtils.getPathPredicateVariables(exchange);
String segment = uriVariables.get("segment");
```

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            - Path=/product/{segment}
```

![image-20231023195812429](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310231958672.png)

#### Query路由规则

```
有两个参数 要求param和可选的regexp（正则表达式）
例如 Query=apple 需要在请求的路径后面加上apple=xxx参数就可以正常访问
```

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            - Query=apple
```

![image-20231023200557714](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310232005820.png)

#### RemoteAddr路由规则

```
RemoteAddr远程地址匹配，需要配置IPv4或IPv6的字符串，如192.168.0.1/16(其中192.168.0.1是ip地址 16是一个子网掩码)
```

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            - RemoteAddr=192.168.147.78/24
```

![image-20231023201212055](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310232012229.png)

#### Weight路由规则

```
Weight权重路由匹配规则
需要两个参数 group和weight(int参数) 权重是按组计算的
通过 Weight指定分组及权重
适用于 灰度发布即 将新功能给到少部分用户进行体验
```

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            - Weight=group1,8
        - id: order-service
          uri: http://localhost:8081
          predicates:
            - Weight=group1,2
```

测试时 使用同一个请求地址 多刷新几次看效果

![image-20231023201952540](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310232019120.png)



## GateWay过滤器

```
gateway提供了20多个内置过滤器，包括头部过滤器，路径过滤器，Hystrix过滤器，变更请求的URL过滤器 还有参数和状态码过滤器等
gateway过滤器分为Pre类型和Post类型两种
	Pre类型的过滤器在请求转发到后端服务之前执行 在Pre类型过滤器中可以做一些鉴权，限流等操作
	Post类型的过滤器在请求执行完成后将结果集返回给客户端之执行

在SpringCloud GateWay中内置了很多Filter ，实现有两种，GateWayFilter和GlobalFilter
	GlobalFilter是全局的过滤器 会应用到所有的路由上
	GateWayFilter只会应用到单个路由或者一个分组的路由上
```

```
过滤器的分类
Pre请求前过滤器
	Header头部过滤器
        AddRequestHeaderGatewayFilterFactory
        RemoveRequestHeaderGatewayFilterFactory
        AddResponseHeaderGatewayFilterFactory
        RemoveResponseHeaderGatewayFilterFactory
        SetRequestHeaderGatewayFilterFactory
        SetResponseHeaderGatewayFilterFactory
        RequestHeaderToRequestUriGatewayFilterFactory
        SecureHeadersGatewayFilterFactory
     Parameter请求参数过滤器
     	AddRequestParameterGatewayFilterFactory
     Path请求路径过滤器
     	PrefixPathGatewayFilterFactory
     	StripPrefixGatewayFilterFactory
     	SetPathGateWayFilterFactory
     	RewritePathGatewayFilterFactory
     Body请求体过滤器
     	ModifyRequestBodyGatewayFilterFactory
     	ModifyResponseBodyGatewayFilterFactory
	
Post请求执行完成后 返回结果集之前的过滤器
	Status返回状态过滤器
		SetStatusGatewayFilterFactory
	Session会话过滤器
		SaveSessionGatewayFilterFactory
	Redirect 重定向过滤器
		RedirectToGateWayFilterFactory
	Retry 重试过滤器
		RetryGatewayFilterFactory
	RateLimiter 限流过滤器
		RequestRateLimiterGatewayFilterFactory
	Hystrix 熔断过滤器
		HystrixGatewayFilterFactory
```

### AddRequestHeaderGatewayFilterFactory

```
AddRequestHeaderGatewayFilterFactory 前置过滤器
是在请求之前执行的过滤
可以给请求添加请求参数
```

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            - Path=/product/** # Path断言
          filters:
            - AddRequestParameter=info2, blue # 前置过滤器 key是info2 值是blue
```

服务端测试接收头信息中添加的内容

```java
@GetMapping("/{id}")
public Product detail(@PathVariable("id") Integer id,String info2){
    System.out.println("info2 = "+info2);// 这个info2是过滤器传递过来的
    return new Product(id,"xx商品"+id,2,100.00);
}
```

测试

![image-20231023210511321](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310232105219.png)

![image-20231023210500447](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310232105450.png)

### RewritePathGatewayFilterFactory

```
RewritePathGatewayFilterFactory
重写请求路径
例如 下列案例 将/api-gateway/product/请求重写成/product/请求 数据前置过滤器
在服务端访问时 会过滤掉/api-gateway/ 这样做的目的是为了保护我们的服务接口不会直接暴漏给调用者
```

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            - Path=/product/** , /api-gateway/** # Path断言
          filters:
            - AddRequestParameter=info2, blue # 前置过滤器 key是info2 值是blue
            - RewritePath=/api-gateway(?<args>/?.*), $\{args}
```

### SetStatusGatewayFilterFactory

```
SetStatusGatewayFilterFactory 后置过滤器
该过滤器 可以改变请求的响应状态

```

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            - Path=/product/** , /api-gateway/** # Path断言
          filters:
            - AddRequestParameter=info2, blue # 前置过滤器 key是info2 值是blue
            - RewritePath=/api-gateway(?<args>/?.*), $\{args}
            - SetStatus=789 # 改变请求的响应状态 - 所有的请求都会改为该响应状态
```

![image-20231023211859110](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310232119112.png)

### AddResponseHeaderGatewayFilterFactory

```
AddResponseHeaderGatewayFilterFactory 是后置过滤器
可以给返回的结果集中添加头信息
```

```yaml
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes: #定义路由规则 可以配置多个
        - id: product-service #路由ID
          uri: http://localhost:8080 #路由地址
          predicates: #定义断言规则
            - Path=/product/** , /api-gateway/** # Path断言
          filters:
            - AddRequestParameter=info2, blue # 前置过滤器 key是info2 值是blue
            - RewritePath=/api-gateway(?<args>/?.*), $\{args}
            - SetStatus=789 # 改变请求的响应状态 - 所有的请求都会改为该响应状态
            - AddResponseHeader=X-Response111-Author, ET #返回的请求状态中添加头信息
```

![image-20231023212254294](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310232122186.png)



### GlobalFilter 全局过滤器

```
全局过滤器不需要在配置文件中进行配置，作用在所有的路由上，我们可以用来来实现很多统一化处理的业务需求，例如 负载均衡，统一过滤，路径转发，监控，日志等
全局过滤器加上网关过滤器组成过滤器链 该过滤器的执行顺序是根据@Order注解指定数字大小 从小到大 数字越小 优先级越高
```

![image-20231023214141184](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310232141049.png)

### 自定义Filter

```
SpringCloud Gateway 提高了过滤器扩展功能，我们可以根据实际的业务需求来子女固定翼GatewayFilter网关过滤器或全局过滤器（GlobalFilter）
```

#### 自定义GatewayFilter

```
实现GatewayFilter Ordered接口以及配置
```

##### 新建一个模块测试自定义网关过滤器

###### 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
</dependencies>

```

###### 创建启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GlobalFilterApp {

    public static void main(String[] args) {
        SpringApplication.run(GlobalFilterApp.class,args);
    }
}

```

###### 创建自定义网关过滤器

需要实现GatewayFilter和Ordered接口

```java
package com.et.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * 自定义网关过滤器
 */
public class MyGatewayFilter implements GatewayFilter, Ordered {

    /**
     * 过滤请求
     * 可以通过exchange交换机实现请求的过滤
     * @param exchange the current server exchange
     * @param chain provides a way to delegate to the next filter
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("=====自定义网关过滤器=====");
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        long date = headers.getDate();
        Date d = new Date(date);
        System.out.println(d);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {// 过滤器排序
        return -1;// 数字越小优先级越高
    }
}

```

###### 创建网关过滤器的配置类

```java
package com.et.config;

import com.et.filter.MyGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayFilterConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder build){
        return build.routes().route(r->r.path("/product/**")
                .uri("http://localhost:8080/")
                .filter(new MyGatewayFilter())
                .id("myGatewayFilter")
        ).build();
    }
}

```

###### 添加配置文件

```yaml
server:
  port: 81
```

###### 启动并测试

![image-20231023224852217](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310232248335.png)



#### 自定义GlobalFilter

全局过滤器实现GlobalFilter和Ordered两个接口即可

需要注意 将自定义的类交给spring管理

```java
package com.et.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器
 */
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("===========全局过滤器==========");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {// 排序 数字越小 优先级越高
        return -5;
    }
}

```

启动并测试

![image-20231023225329157](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310232253100.png)

![image-20231023225543765](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310232255671.png)



## Gateway网关限流

### 令牌桶限流算法

```
令牌桶限流算法
RequestRateLimiter底层实现的是令牌桶算法
令牌桶内存储令牌，令牌桶需要设置令牌容量，也就是系统最大的并发数量
以一定的速率生成令牌(具体根据系统性能设置) 放到令牌桶中 如果桶满了 则丢弃
客户端来一个请求 则先去令牌桶获取令牌 拿到令牌后则处理请求，否则丢弃或者返回失败

令牌桶算法的优点
	通过恒定的速率生成令牌桶 能够让请求处理的更均匀，不会出现短时间大量的请求处理
	比较有好的控制高并发
```

![image-20231023225648365](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310232256939.png)

### 限流实例 - URL限流

在上边的Gateway过滤器上继续新增

#### 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
    <!--添加缓存插件-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <!--lettuce pool 缓存连接池-->
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-pool2</artifactId>
    </dependency>
</dependencies>
```

#### 启动redis缓存插件

docker方式

```
docker run --name redis-docker -p 6379:6379 -v /home/redis/data:/data -v /home/redis/conf/redis.conf:/etc/redis/config/redis.config --restart=always -it redis --appendonly yes


--appendonly yes 表示开启持久化
注意：
	docker中的redis拉取的是没有redis.config配置问价的 需要我们手动创建一个
	在启动容器时做一个目录挂载 然后在redis.conf目录下创建redis.config文件并添加如下内容 然后重启redis
	protected-mode no
	最后重新启动redis即可

```

![image-20231023231047627](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310232310746.png)

![image-20231023231124927](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310232325238.png)

#### 添加配置文件

```yaml
server:
  port: 81
spring:
  redis:
    host: 192.168.199.106
    port: 6379
    password:
    connect-timeout: 10s #连接超时时间
    lettuce: #lettuce缓冲池客户端配置
      pool: #配置连接池
        max-active: 8 #最大连接数 如果为负数表示没有限制 默认8
        max-wait: 200s # 连接池最大阻塞时间 使用负数表示没有显示 默认-1
        max-idle: 8 # 连接池中最大空闲连接数 默认8
        min-idle: 0 # 连接池中最小空闲连接数 默认0
  cloud:
    gateway:
      routes:
        - id: rateLimiter
          uri: http://localhost:8080
          predicates:
            - Path=/product/**
          filters:
            - name: RequestRateLimiter # 限流过滤器
              args:
                redis-rate-limiter.replenishRate: 1 #令牌桶每秒填充速率
                redis-rate-limiter.burstCapacity: 2 # 令牌桶总容量
                redis-rate-limiter.requestedTokens: 1 # 一个请求需要消费的令牌数量
                key-resolver: "#{@pathKeyResolver}" #

```

#### 新建限流配置类

```java
package com.et.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class KeyResolverConfig {

    /*
    通过URL限流
    注意 这里的方法名要与配置文件中key-resolver指定的一致
     */
    @Bean
    public KeyResolver pathKeyResolver(){
        /*return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                // 根据URL进行限流
                return Mono.just(exchange.getRequest().getURI().getPath());
            }
        };*/

        // e = exchange
        return e -> Mono.just(e.getRequest().getURI().getPath());
    }
}

```

#### 测试

测试之前需要先注释调全局的过滤器 否则会走到全局的过滤器配置类中 而不会走配置文件

![image-20231023235508209](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310232355622.png)

![image-20231024000210849](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310240002033.png)

### 限流实例 - 参数限流

只需要改动配置类即可 

```java
package com.et.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class KeyResolverConfig {

    /*
    通过URL限流
    注意 这里的方法名要与配置文件中key-resolver指定的一致
     */
    @Bean
    public KeyResolver pathKeyResolver(){
        

        // e = exchange
        //return e -> Mono.just(e.getRequest().getURI().getPath());// 根据URL限流

        return e -> Mono.just(e.getRequest().getQueryParams().getFirst("token"));// 根据参数限流
    }
}

```

![image-20231024000841300](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310240008401.png)

![image-20231024000936503](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310240009494.png)

### 限流实例 - IP限流

同样只需要修改配置类即可

```java
package com.et.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class KeyResolverConfig {

    /*
    通过URL限流
    注意 这里的方法名要与配置文件中key-resolver指定的一致
     */
    @Bean
    public KeyResolver pathKeyResolver(){
        /*return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                // 根据URL进行限流
                return Mono.just(exchange.getRequest().getURI().getPath());
            }
        };*/

        // e = exchange
//        return e -> Mono.just(e.getRequest().getURI().getPath());// 根据URL限流

        //return e -> Mono.just(e.getRequest().getQueryParams().getFirst("token"));// 根据参数限流
        // IP限流
        return e -> Mono.just(e.getRequest().getRemoteAddress().getHostName());
    }
}

```

![image-20231024001248747](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310240012805.png)