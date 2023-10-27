# SpringCloud-Nacos-GateWay

## 概述

```
本demo是通过SpringCloud+Nacos+GateWay 实现简单的微服务系统架构
技术：
	SpringCloud 
	Nacos
	GateWay
```

## Nacos注册中心

启动采用本地方式测试

![image-20231024113119669](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310241131725.png)



## 项目搭建

采用父子项目模式进行搭建

### 父项目搭建



#### 添加依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <modules>
        <module>order1</module>
    </modules>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.et</groupId>
    <artifactId>NacosGatewayDemo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>NacosGatewayDemo</name>
    <packaging>pom</packaging>
    <description>NacosGatewayDemo</description>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <spring-cloud.version>Hoxton.SR9</spring-cloud.version>
        <springboot.version>2.3.2.RELEASE</springboot.version>
        <springcloudalibaba.version>2.2.6.RELEASE</springcloudalibaba.version>
        <httpclient.version>4.5</httpclient.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <!--                <version>${spring-cloud.version}</version>-->
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>${springboot.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>${springcloudalibaba.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>

        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>${httpclient.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
            <version>2.2.6.RELEASE</version>
        </dependency>
    </dependencies>

</project>

```

### 创建库存模块

![image-20231027091127046](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270911236.png)

#### 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>2.3.2.RELEASE</version>
    </dependency>

    <!--配置中心-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        <version>2.2.6.RELEASE</version>
    </dependency>

    <!--服务注册与发现-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        <version>2.2.6.RELEASE</version>
    </dependency>
</dependencies>
```

#### 添加配置文件

```yaml
server:
  port: 8082
  servlet:
    context-path: /
# 服务注册
spring:
  application:
    name: stock-server
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        # 自定义服务发布到Nacos后的集群名称
        namespace: e5c78c07-74ef-45ed-8389-b1ac2d1cd8c0

```

#### 创建启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 能够让注册中心发现
public class StockApp {

    public static void main(String[] args) {
        SpringApplication.run(StockApp.class,args);
    }
}

```

#### 创建业务Controller

```java
package com.et.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    @RequestMapping("/info")
    public String test(String info){
        return "库存模块接收到的消息 "+info;
    }
}

```

#### 启动库存模块 验证是否注册到注册中心

![image-20231027091720060](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270917151.png)



单独调用测试

![image-20231027091903826](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270919082.png)

### 创建商品模块

![image-20231027090115347](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270901145.png)

#### 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>2.3.2.RELEASE</version>
    </dependency>

    <!--配置中心-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        <version>2.2.6.RELEASE</version>
    </dependency>

    <!--服务注册与发现-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        <version>2.2.6.RELEASE</version>
    </dependency>
</dependencies>
```



#### 添加配置

```yaml
server:
  port: 8081
  servlet:
    context-path: /
# 服务注册
spring:
  application:
    name: order-server
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        namespace: e5c78c07-74ef-45ed-8389-b1ac2d1cd8c0 # 指定服务存放的命名空间 默认为public命名空间
```

#### 创建启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.Ordered;

@SpringBootApplication
@EnableDiscoveryClient // 能够让注册中心发现
@EnableFeignClients // 开启Feign客户端
public class OrderApp {

    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class,args);
    }
}

```

#### 创建调用配置类

```
StockFeignService
```

```java
package com.et.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// stock-server 是服务名称
@FeignClient("stock-server")
public interface StockFeignService {

    // 库存服务中的方法
    @RequestMapping("/stock/info")
    public String test(@RequestParam("info") String info);
}

```

#### 创建Controller

```java
package com.et.controller;

import com.et.feign.StockFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private StockFeignService stockFeignService;

    @RequestMapping("/test")
    public String test(){
        return stockFeignService.test("HELLO");
    }
}

```

#### 启动测试

![image-20231027092620101](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270926378.png)

单独调用测试

这里的单独调用指的是通过order调用stock模块

![image-20231027092919242](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270929663.png)

## 配置GateWay

### 创建GateWay模块

![image-20231027094010657](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270940773.png)

### 添加依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>NacosGatewayDemo</artifactId>
        <groupId>com.et</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>gateway1</artifactId>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>

    <dependencies>
       <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>

        <!--配置中心-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
            <version>2.2.6.RELEASE</version>
        </dependency>

        <!--服务注册与发现-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <version>2.2.6.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
            <version>2.2.6.RELEASE</version>
        </dependency>
    </dependencies>

</project>
```

### 添加配置文件

```yml
server:
  port: 80
  servlet:
    context-path: /
# 服务注册
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes:
        - id: suibianxie
          uri: http://localhost:8081
          predicates:
            - Path=/order/**
      filter:
        secure-headers:
          disable: x-frame-options,strict-transport-security
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        namespace: e5c78c07-74ef-45ed-8389-b1ac2d1cd8c0
```

### 创建启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient // 能够让注册中心发现
@EnableFeignClients // 开启Feign客户端
public class GateWayApp {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApp.class,args);
    }
}

```

### 跨域处理

如果出现跨域问题 使用下边方式处理

```
package com.et;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;
 
@Configuration
public class CorConfig {
    //处理跨域
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
```

### 启动测试

查看Nacos

![image-20231027113558737](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310271136097.png)

访问测试

![image-20231027113618597](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310271136793.png)



## gateway只拉取不注册

```yaml
server:
  port: 80
  servlet:
    context-path: /
# 服务注册
spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true #开启微服务自动定位功能
      routes:
        - id: suibianxie
          uri: http://localhost:8081
          predicates:
            - Path=/order/**
      filter:
        secure-headers:
          disable: x-frame-options,strict-transport-security
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        #namespace: e5c78c07-74ef-45ed-8389-b1ac2d1cd8c0
        register-enabled: false #只拉取不注册
```









