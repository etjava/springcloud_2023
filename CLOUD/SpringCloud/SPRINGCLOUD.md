# SpringCloud快速入门

## 简介

```
Spring Cloud是一系列框架的有序集合。它利用Spring Boot的开发便利性巧妙地简化了分布式系统基础设施的开发，如服务发现注册、配置中心、消息总线、负载均衡、断路器、数据监控等，都可以用Spring Boot的开发风格做到一键启动和部署。Spring Cloud并没有重复制造轮子，它只是将目前各家公司开发的比较成熟、经得起实际考验的服务框架组合起来，通过Spring Boot风格进行再封装屏蔽掉了复杂的配置和实现原理，最终给开发者留出了一套简单易懂、易部署和易维护的分布式系统开发工具包。

SpringCloud组成
	Spring Cloud的子项目，大致可分成两类，
		一类是对现有成熟框架"Spring Boot化"的封装和抽象，也是数量最多的项目;
		第二类是开发了一部分分布式系统的基础设施的实现，如Spring Cloud Stream扮演的就是kafka,
        ActiveMQ这样的角色。对于我们想快速实践微服务的开发者来说，第一类子项目就已经足够使用，如:
			Spring Cloud Netflix 是对Netflix开发的一套分布式服务框架的封装，包括服务的发现和注册，
			负载均衡、断路器、REST客户端、请求路由等。
            Spring Cloud Config 将配置信息中央化保存, 配置Spring Cloud Bus
            	可以实现动态修改配置文件
            Spring Cloud Stream 分布式消息队列，是对Kafka, MQ的封装
            Spring Cloud Security 对Spring Security的封装，并能配合Netflix使用
            Spring Cloud Zookeeper 对Zookeeper的封装，使之能配置其它Spring Cloud的子项目使用
            Spring Cloud Eureka 是 Spring Cloud Netflix 微服务套件中的一部分，它基于Netflix 			 Eureka 做了二次封装，主要负责完成微服务架构中的服务治理功能。
SpringCloud前景
	Spring Cloud对于中小型互联网公司来说是一种福音，因为这类公司往往没有实力或者没有足够的资金投入去开发自己的分布式系统基础设施，使用Spring Cloud一站式解决方案能在从容应对业务发展的同时大大减少开发成本。同时，随着近几年微服务架构和Docker容器概念的火爆，也会让Spring Cloud在未来越来越"云"化的软件开发风格中立有一席之地，尤其是在五花八门的分布式解决方案中提供了标准化的、全站式的技术方案，意义可能会堪比当年Servlet规范的诞生，有效推进服务端软件系统技术水平的进步。




SpeingCloud官方主页 https://projects.spring.io/spring-cloud/ 
SpringCloud中文网	https://springcloud.cc/ 
```

## 父项目搭建

```
springcloud会有多个服务共同完成某项功能，因此这里使用父子项目来搭建
```

![image-20231011221725634](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310112218627.png)

### SpringCloud&SpringBoot版本对应

| SpringCloud             | SpringBoot                                              |
| ----------------------- | ------------------------------------------------------- |
| 2022.0.0-M2             | Spring Boot >=3.0.0-M2 and <3.1.0-M1                    |
| 2022.0.0-M1             | Spring Boot >=3.0.0-M1 and <3.0.0-M2                    |
| 2021.0.3                | Spring Boot >=2.6.1 and <3.0.0-M1                       |
| 2021.0.0-RC1            | Spring Boot >=2.6.0-RC1 and <2.6.1                      |
| 2021.0.0-M3             | Spring Boot >=2.6.0-M3 and <2.6.0-RC1                   |
| 2021.0.0-M1             | Spring Boot >=2.6.0-M1 and <2.6.0-M3                    |
| 2020.0.5                | Spring Boot >=2.4.0.M1 and <2.6.0-M1                    |
| Hoxton.SR12             | Spring Boot >=2.2.0.RELEASE and <2.4.0.M1               |
| Hoxton.BUILD-SNAPSHOT   | Spring Boot >=2.2.0.BUILD-SNAPSHOT                      |
| Hoxton.M2               | Spring Boot >=2.2.0.M4 and <=2.2.0.M5                   |
| Greenwich.BUILD-SNAPSHO | Spring Boot >=2.1.9.BUILD-SNAPSHOT and <2.2.0.M4        |
| Greenwich.SR2           | Spring Boot >=2.1.0.RELEASE and <2.1.9.BUILD-SNAPSHOT   |
| Greenwich.M1            | Spring Boot >=2.1.0.M3 and <2.1.0.RELEASE               |
| Finchley.BUILD-SNAPSHOT | Spring Boot >=2.0.999.BUILD-SNAPSHOT and <2.1.0.M3      |
| Finchley.SR4            | Spring Boot >=2.0.3.RELEASE and <2.0.999.BUILD-SNAPSHOT |
| Finchley.RC2            | Spring Boot >=2.0.2.RELEASE and <2.0.3.RELEASE          |
| Finchley.RC1            | Spring Boot >=2.0.1.RELEASE and <2.0.2.RELEASE          |
| Finchley.M9             | Spring Boot >=2.0.0.RELEASE and <=2.0.0.RELEASE         |
| Finchley.M7             | Spring Boot >=2.0.0.RC2 and <=2.0.0.RC2                 |
| Finchley.M6             | Spring Boot >=2.0.0.RC1 and <=2.0.0.RC1                 |
| Finchley.M5             | Spring Boot >=2.0.0.M7 and <=2.0.0.M7                   |
| Finchley.M4             | Spring Boot >=2.0.0.M6 and <=2.0.0.M6                   |
| Finchley.M3             | Spring Boot >=2.0.0.M5 and <=2.0.0.M5                   |
| Finchley.M2             | Spring Boot >=2.0.0.M3 and <2.0.0.M5                    |
| Edgware.SR5             | 1.5.20.RELEASE                                          |
| Edgware.SR5             | 1.5.16.RELEASE                                          |
| Edgware.RELEASE         | 1.5.9.RELEASE                                           |
| Dalston.RC1             | 1.5.2.RELEASE                                           |

### 修改pom文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <modules>
        <module>microservice-common</module>
        <module>microservice-student-provider-1001</module>
    </modules>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.16</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.et</groupId>
    <artifactId>springcloud</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>pom</packaging>
    <name>springcloud</name>
    <description>springcloud</description>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <druid.version>1.2.8</druid.version>
        <mysql.version>8.0.31</mysql.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
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
                <version>2021.0.8</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.7.16</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!-- 连接池 -->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>${druid.version}</version>
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

## 创建公共模块

```
公共模块项目 microservice-common，主要是放一些其他项目公用的东西，比如实体类，工具类等等
```

![image-20231011225215671](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310112252802.png)

### 添加依赖

```xml
 <dependencies>
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-data-jpa</artifactId>
     </dependency>
     <dependency>
         <groupId>com.mysql</groupId>
         <artifactId>mysql-connector-j</artifactId>
     </dependency>
</dependencies>
```

### 创建实体类

```java
package com.et.entity;

import java.io.Serializable;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
 
/**
 * 学生信息实体
 * @author Administrator
 *
 */
@Entity
@Table(name="t_student")
public class Student implements Serializable{// 传输对象必须序列化
 
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
 
    @Id
    @GeneratedValue
    private Integer id; // 编号
     
    @Column(length=50)
    private String name; // 姓名
     
    @Column(length=50)
    private String grade; // 年级
     
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
}
```

## 服务提供者

```
新建一个服务器提供者module子模块，类似前面建的common公共模块，名称是 microservice-student-provider-1001  服务提供者会有多个 这里我们使用端口区分
```

### microservice-student-provider-1001

![image-20231011225533691](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310112255411.png)

#### 添加依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>springcloud</artifactId>
        <groupId>com.et</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservice-student-provider-1001</artifactId>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.et</groupId>
            <artifactId>microservice-common</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>
        <!-- 修改后立即生效，热部署
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
        </dependency>-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>

    </dependencies>

</project>
```

#### 配置文件

application.yml

```yaml
server:
  port: 1001
  context-path: /
 
# 数据源配置
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_springcloud
    username: root
    password: 123456
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  thymeleaf:
    cache: false
```

#### StudentRepository接口

```java
package com.et.repository;

import com.et.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
 

/**
 * 学生Repository接口
 * @author Administrator
 *
 */
public interface StudentRepository extends JpaRepository<Student, Integer>,JpaSpecificationExecutor<Student>{
 
}
```

#### StudentService接口

```java
package com.et.service;

import com.et.entity.Student;

import java.util.List;

/**
 * 学生信息Service接口
 * @author Administrator
 *
 */
public interface StudentService {

    /**
     * 添加或者修改学生信息
     * @param student
     */
    public void save(Student student);

    /**
     * 根据id查找学生信息
     * @param id
     * @return
     */
    public Student findById(Integer id);

    /**
     * 查询学生信息
     * @return
     */
    public List<Student> list();

    /**
     * 根据id删除学生信息
     * @param id
     */
    public void delete(Integer id);


}
```

#### StudentService实现类

```java
package com.et.service.impl;

import java.util.List;
 
import javax.annotation.Resource;
 
import org.springframework.stereotype.Service;
 
import com.et.entity.Student;
import com.et.repository.StudentRepository;
import com.et.service.StudentService;
 
/**
 * 学生信息Service实现类
 * @author Administrator
 *
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService{
 
    @Resource
    private StudentRepository studentRepository;
     
    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student findById(Integer id) {
        return studentRepository.findById(id).get();
    }
 
    @Override
    public List<Student> list() {
        return studentRepository.findAll();
    }
 
    @Override
    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }
 
}
```

#### StudentProviderController

```java
package com.et.controller;

import java.util.List;
 
import javax.annotation.Resource;
 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.et.entity.Student;
import com.et.service.StudentService;
 
/**
 * 服务提供者-学生信息控制器
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/student")
public class StudentProviderController {
 
    @Resource
    private StudentService studentService;
     
    /**
     * 添加或者修改学生信息
     * @param student
     * @return
     */
    @PostMapping(value="/save")
    public boolean save(Student student){
        try{
            studentService.save(student);  
            return true;
        }catch(Exception e){
            return false;
        }
    }
     
    /**
     * 查询学生信息
     * @return
     */
    @GetMapping(value="/list")
    public List<Student> list(){
        return studentService.list();
    }
     
    /**
     * 根据id查询学生信息
     * @return
     */
    @GetMapping(value="/get/{id}")
    public Student get(@PathVariable("id") Integer id){
        return studentService.findById(id);
    }
     
    /**
     * 根据id删除学生信息
     * @return
     */
    @GetMapping(value="/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id){
        try{
            studentService.delete(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
```

#### 启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 
@SpringBootApplication
public class ProviderApplication_1001 {
 
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication_1001.class, args);
    }
}
```

#### 启动测试

```
启动后查看数据库有无t_student表生成，如生成了 则添加些测试数据进去
```

![image-20231011233010977](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310112330266.png)

![image-20231011233027370](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310112330457.png)

访问测试

![image-20231011233058108](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310112331867.png)

## 服务消费者

### microservice-student-consumer-80

#### 创建消费者模块

![image-20231012185940952](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310121859929.png)

#### 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>com.et</groupId>
        <artifactId>microservice-common</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    <!-- 修改后立即生效，热部署 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
    </dependency>
</dependencies>
```

#### application.yml

```yaml
server:
  port: 80
  context-path: /
```

#### SpringCloudConfig配置类

该类为服务提供者需要调用服务的配置类

主要是定义一个bean RestTemplate对象； springcloud消费者，服务提供者之间的交互是http rest方式，比dubbo rpc方式更加灵活方便点

```java
package com.et.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * SpringCloud相关配置
 * @author Administrator
 *
 */
@Configuration
public class SpringCloudConfig {
    /**
     * 调用服务模版
     * @return
     */
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}

```

#### StudentConsumerController

测试调用服务的controller

```java
package com.et.controller;

import java.util.List;

import javax.annotation.Resource;

import com.et.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 *
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/student")
public class StudentConsumerController {

     @Resource
     private RestTemplate restTemplate;

     /**
      * 添加或者修改学生信息
      * @param student
      * @return
      */
     @PostMapping(value="/save")
     private boolean save(Student student){
         return restTemplate.postForObject("http://localhost:1001/student/save", student, Boolean.class);
     }

     /**
     * 查询学生信息
     * @return
     */
    @SuppressWarnings("unchecked")
    @GetMapping(value="/list")
    public List<Student> list(){
        return restTemplate.getForObject("http://localhost:1001/student/list", List.class);
    }

    /**
     * 根据id查询学生信息
     * @return
     */
    @GetMapping(value="/get/{id}")
    public Student get(@PathVariable("id") Integer id){
        return restTemplate.getForObject("http://localhost:1001/student/get/"+id, Student.class);
    }

    /**
     * 根据id删除学生信息
     * @return
     */
    @GetMapping(value="/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id){
        try{
            restTemplate.getForObject("http://localhost:1001/student/delete/"+id, Boolean.class);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
```

#### 启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
// exclude 排除数据源注入，不加的话 会报错
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class StudentConsumerApplication_80 {
 
    public static void main(String[] args) {
        SpringApplication.run(StudentConsumerApplication_80.class,args);
    }
}
```

![image-20231012194612083](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310121946966.png)

#### 测试新增

```
新增时使用postman工具测试
因为浏览器只能模拟get请求
```

![image-20231012195010479](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310121950486.png)

![image-20231012195026419](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310121950425.png)

提示添加成功 但数据为null

这是因为 服务提供者实体接收不到数据，因为是http rest方式交互，这里要求必须加上 @RequestBody

服务端和消费者端都添加上

注意 添加了@RequestBody后 传递的参数格式改为JSON格式

![image-20231012200235911](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122002984.png)

![image-20231012200253938](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122002837.png)

![image-20231012200437354](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122004809.png)

![image-20231012200312567](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122003104.png)

## 服务注册与发现组件Eureka

```
官方地址  https://github.com/Netflix/eureka

```

### 简介

```
Eureka是Netflix开发的服务发现框架，本身是一个基于REST的服务，主要用于定位运行在AWS域中的中间层服务，以达到负载均衡和中间层服务故障转移的目的。SpringCloud将它集成在其子项目spring-cloud-netflix中，以实现SpringCloud的服务发现功能。

Eureka包含两个组件：Eureka Server和Eureka Client。

Eureka Server提供服务注册服务，各个节点启动后，会在Eureka Server中进行注册，这样EurekaServer中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观的看到。

Eureka Client是一个java客户端，用于简化与Eureka Server的交互，客户端同时也就别一个内置的、使用轮询(round-robin)负载算法的负载均衡器。

在应用启动后，将会向Eureka Server发送心跳,默认周期为30秒，如果Eureka Server在多个心跳周期内没有接收到某个节点的心跳，Eureka Server将会从服务注册表中把这个服务节点移除(默认90秒)。

Eureka Server之间通过复制的方式完成数据的同步，Eureka还提供了客户端缓存机制，即使所有的Eureka Server都挂掉，客户端依然可以利用缓存中的信息消费其他服务的API。综上，Eureka通过心跳检查、客户端缓存等机制，确保了系统的高可用性、灵活性和可伸缩性。

类似zookeeper，Eureka也是一个服务注册和发现组件，是SpringCloud的一个优秀子项目
```

![服务注册与发现.png](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122047734.jpeg)

### 搭建Eureka服务注册中心

```
eureka是c/s模式的  server服务端就是服务注册中心，其他的都是client客户端，服务端用来管理所有服务，客户端通过注册中心，来调用具体的服务
```

#### 新建模块microservice-eureka-server-2001

![image-20231012210236789](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122102787.png)

#### 添加依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    <version>2.2.6.RELEASE</version>
</dependency>
```

#### application.yml

```yaml
server:
  port: 2001
  context-path: /

eureka:
  instance:
    hostname: localhost #eureka注册中心实例名称
  client:
    register-with-eureka: false     #false 由于该应用为注册中心，所以设置为false,代表不向注册中心注册自己。
    fetch-registry: false     #false 由于注册中心的职责就是维护服务实例，它并不需要去检索服务，所以也设置为false
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/       #设置与Eureka注册中心交互的地址，查询服务和注册服务用到
```

#### 启动类

需要添加开启Eureka服务注解

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication_2001 {
 
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication_2001.class, args);
    }
}
```

#### 启动并访问2001端口

![image-20231012211651969](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122116002.png)

### 服务提供者注册服务到Eureka

```
服务提供者将服务注册到eureka注册中心
```

#### 修改pom文件

加上eureka客户端依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    <version>2.2.6.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
    <version>3.1.8</version>
</dependency>
<!-- Spring Cloud 新版本默认将 Bootstrap 禁用，需要将 spring-cloud-starter-bootstrap 依赖引入到工程中-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bootstrap</artifactId>
</dependency>
```

#### application.yml

加上eureka注册相关配置

这里的defaultZone要和前面服务注册中心的暴露地址一致

即 http://${eureka.instance.hostname}:${server.port}/eureka/ 

```yaml
eureka:
  instance:
    hostname: localhost  #eureka客户端主机实例名称
    appname: microservice-student  #客户端服务名
    instance-id: microservice-student:1001 #客户端实例名称
    prefer-ip-address: true #显示IP
  client: 
    service-url: 
      defaultZone: http://localhost:2001/eureka   #把服务注册到eureka注册中心
```

#### 启动类

启动类加上开启Eureka客户端的注解

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderApplication_1001 {
 
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication_1001.class, args);
    }
}
```

#### 启动测试

![image-20231012221351111](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122213177.png)

#### 配置显示info信息

```
配置注册中心点击每个服务时 显示对应的info信息
```

##### 服务提供者项目中添加监控依赖

```xml
<!-- actuator监控引入 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

##### 父项目pom.xml里加上构建插件配置

主要是为了再构建的时候扫描子项目配置文件，解析配置用的

```xml
<!-- 构建的时候 解析 src/main/resources 下的配置文件  其实就是application.yml 解析以$开头和结尾的信息 -->
<build>
    <finalName>springcloud</finalName>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <filtering>true</filtering>
        </resource>
    </resources>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-resources-plugin</artifactId>
            <configuration>
                <delimiters>
                    <delimit>$</delimit>
                </delimiters>
            </configuration>
        </plugin>
    </plugins>
</build>
```

##### 服务提供者项目application.yml加上info配置

application.yaml

```yaml
management:
  endpoints:
    web:
      exposure:
        include: info #将info端点暴露给web（SpringBoot2.5.0或更高版本中info端点默认隐藏）
  info:
    env:
      enabled: true #是否在info端点中显示环境信息

# 配置info信息
info:
  app.name: microservice-student-provider-1001
  auther.name: Jerry
  auther.phone: 999
```

##### 测试

启动eureka注册中心 在启动服务提供者

![image-20231012224030616](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122240712.png)

### Eureka高可用集群配置

```
当注册中心扛不住高并发的时候，这时候 要用集群来扛
两个module  microservice-eureka-server-2002  microservice-eureka-server-2003
```

#### 新建2002和2003模块

![image-20231012230459132](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122305351.png)

#### 添加依赖

两个模块分别添加如下依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        <version>2.2.6.RELEASE</version>
    </dependency>
</dependencies>
```

#### 启动类

两个模块分别创建启动类 

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication_2003 {
 
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication_2003.class, args);
    }
}
```

#### 本地域名映射

```
前面单机的时候 eureka注册中心实例名称 是localhost，现在是集群，不能三个实例都是localhost，这里复杂的办法是搞三个虚拟机，麻烦，这里有简单办法，直接配置本机hosts，来实现本机域名映射

找到 C:\Windows\System32\drivers\etc  打开hosts，加配置
127.0.0.1  eureka2001.etjava.com
127.0.0.1  eureka2002.etjava.com
127.0.0.1  eureka2003.etjava.com
```

![image-20231012231113749](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122311054.png)

#### 分别修改三个注册中心的配置文件

application.yml

##### 2001

```yaml
server:
  port: 2001
  context-path: /

eureka:
  instance:
    hostname: eureka2001.etjava.com
  # hostname: localhost #eureka注册中心实例名称 - 单机

  client:
    register-with-eureka: false     #false 由于该应用为注册中心，所以设置为false,代表不向注册中心注册自己。
    fetch-registry: false     #false 由于注册中心的职责就是维护服务实例，它并不需要去检索服务，所以也设置为false
    service-url:
      defaultZone: http://eureka2002.etjava.com:2002/eureka/,http://eureka2003.etjava.com:2003/eureka/ # 集群
      #defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/       #设置与Eureka注册中心交互的地址，查询服务和注册服务用到
```

##### 2002

```yaml
server:
  port: 2002
  context-path: /

eureka:
  instance:
    hostname: eureka2002.etjava.com
  # hostname: localhost #eureka注册中心实例名称 - 单机

  client:
    register-with-eureka: false     #false 由于该应用为注册中心，所以设置为false,代表不向注册中心注册自己。
    fetch-registry: false     #false 由于注册中心的职责就是维护服务实例，它并不需要去检索服务，所以也设置为false
    service-url:
      defaultZone: http://eureka2001.etjava.com:2001/eureka/,http://eureka2003.etjava.com:2003/eureka/ # 集群
      #defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/       #设置与Eureka注册中心交互的地址，查询服务和注册服务用到
```

##### 2003

```yaml
server:
  port: 2003
  context-path: /

eureka:
  instance:
    hostname: eureka2003.etjava.com
  # hostname: localhost #eureka注册中心实例名称 - 单机

  client:
    register-with-eureka: false     #false 由于该应用为注册中心，所以设置为false,代表不向注册中心注册自己。
    fetch-registry: false     #false 由于注册中心的职责就是维护服务实例，它并不需要去检索服务，所以也设置为false
    service-url:
      defaultZone: http://eureka2001.etjava.com:2001/eureka/,http://eureka2002.etjava.com:2002/eureka/ # 集群
      #defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/       #设置与Eureka注册中心交互的地址，查询服务和注册服务用到
```

##### 修改服务提供者配置文件

```
让服务提供者将服务注册到三个注册中心
主要修改eureka.client.service-url.defaultZone
```

application.yml

```yaml
server:
  port: 1001
  context-path: /

# 数据源配置
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_springcloud
    username: root
    password: Karen@1234
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  thymeleaf:
    cache: false

# eureka注册
eureka:
  instance:
    hostname: localhost  #eureka客户端主机实例名称
    appname: microservice-student  #客户端服务名
    instance-id: microservice-student:1001 #客户端实例名称
    prefer-ip-address: true #显示IP
  client:
    service-url:
      defaultZone: http://eureka2001.etjava.com:2001/eureka/,http://eureka2002.etjava.com:2002/eureka/,http://eureka2003.etjava.com:2003/eureka/ # 集群
      #defaultZone: http://localhost:2001/eureka   #把服务注册到eureka注册中心


management:
  endpoints:
    web:
      exposure:
        include: info #将info端点暴露给web（SpringBoot2.5.0或更高版本中info端点默认隐藏）
  info:
    env:
      enabled: true #是否在info端点中显示环境信息

# 配置info信息
info:
  app.name: microservice-student-provider-1001
  auther.name: Jerry
  auther.phone: 999
```

#### 测试

启动三个注册中心，以及服务提供者项目；

![image-20231012232133888](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122321939.png)

![image-20231012232254002](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122322272.png)

![image-20231012232313206](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122323145.png)

![image-20231012232353326](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122323338.png)

### Eureka自我保护机制

```
开发环境中我们经常会遇到这个红色的警告；
当我们长时间为访问服务以及变更服务实例名称的时候，就会出现这个红色警告；
默认情况，如果服务注册中心再一段时间内没有接收到某个微服务实例的心跳，服务注册中心会注销该实例（默认90秒）。
由于正式环境，经常会有网络故障，网络延迟问题发生，服务和注册中心无法正常通信，此时服务是正常的，不应该注销该服务，Eureka这时候，就通过“自我保护模式”来解决问题，当短时间和服务失去通信时，保留服务信息，当恢复网络和通信时候，退出“自我保护模式”；
通过“自我保护模式”，使Eureka集群更加的健壮和稳定；

如要关闭自我保护机制
server端配置
    #关闭自我保护机制，保证不可用服务被及时踢除
    eureka.server.enable-self-preservation=false
    # 清理间隔（单位毫秒，默认是60*1000）
    eureka.server.eviction-interval-timer-in-ms=2000

client端配置
    #Eureka客户端向服务端发送心跳的时间间隔，单位为秒(默认是30秒)
    eureka.instance.lease-renewal-interval-in-seconds=1
    #Eureka服务端在收到最后一次心跳后等待时间上限，单位为秒(默认是90秒)，超时将剔除服务
    eureka.instance.lease-expiration-duration-in-seconds=2
```

![image-20231012232612284](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310122326465.png)

## Ribbon

### 简介

```
Ribbon是用来调用服务时的一个技术，Ribbon也是Netflix发布的负载均衡器，它有助于控制HTTP和TCP的客户端的行为。为Ribbon配置服务提供者地址后，Ribbon就可基于某种负载均衡算法，自动地帮助服务消费者去请求。Ribbon默认为我们提供了很多负载均衡算法，例如轮询、随机等。当然，我们也可为Ribbon实现自定义的负载均衡算法。
在Spring Cloud中，当Ribbon与Eureka配合使用时，Ribbon可自动从Eureka Server获取服务提供者地址列表，并基于负载均衡算法，请求其中一个服务提供者实例。下图为了Ribbon与Eureka配合使用时的架构。
```

![20180919233745.jpg](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310130537545.jpeg)

### 初步使用

```
Ribbon是客户端负载均衡，所以肯定集成再消费端，也就是consumer端
修改microservice-student-consumer-80
引入依赖，pom.xml 加入 ribbon相关依赖
```

### 添加依赖

```xml
<!--Ribbon-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    <version>3.1.7</version>
</dependency>
<!-- netflix-eureka-client里面已经集成了ribbon 不需要单独定义 
加上去会出现jar包冲突 报找不到应用实例错误
<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
            <version>2.2.6.RELEASE</version>
        </dependency>-->

<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-config -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
    <version>3.1.8</version>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bootstrap</artifactId>
    <version>3.0.3</version>
</dependency>
```

### application.yml

添加eureka相关的配置

```yaml
server:
  port: 80
  context-path: /

eureka:
  client:
    register-with-eureka: false #false 由于注册中心的职责就是维护服务实例，它并不需要去检索服务，所以也设置为false
    service-url:
      defaultZone: http://eureka2001.etjava.com:2001/eureka/,
                    http://eureka2002.etjava.com:2002/eureka/,
                    http://eureka2003.etjava.com:2003/eureka/
```

### SpringCloudConfig添加负载均衡配置

加负载均衡配置 @LoadBalanced

```java
package com.et.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * SpringCloud相关配置
 * @author Administrator
 *
 */
@Configuration
public class SpringCloudConfig {
    /**
     * 调用服务模版
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}

```

### 启动类

```
因为与eureka整合 需要在消费者端的启动类添加@EnableEurekaClient
```

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class StudentConsumerApplication_80 {
 
    public static void main(String[] args) {
        SpringApplication.run(StudentConsumerApplication_80.class,args);
    }
}
```

### 服务提供者端添加应用名称

```
服务提供者microservice-student-provider-1001的application.yml加下配置，指定下应用名称
application:
    name: microservice-student
```

![image-20231013063248992](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310130632200.png)

### 消费者端调用服务时改为通过应用名称进行调用

```java
package com.et.controller;

import java.util.List;

import javax.annotation.Resource;

import com.et.entity.Student;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


/**
 *
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/student")
public class StudentConsumerController {

    private static final String PRE_HOST=" http://MICROSERVICE-STUDENT";

     @Resource
     private RestTemplate restTemplate;

     /**
      * 添加或者修改学生信息
      * @param student
      * @return
      */
     @PostMapping(value="/save")
     private boolean save(@RequestBody Student student){
         return restTemplate.postForObject(PRE_HOST+"/student/save", student, Boolean.class);
     }

     /**
     * 查询学生信息
     * @return
     */
    @SuppressWarnings("unchecked")
    @GetMapping(value="/list")
    public List<Student> list(){
        return restTemplate.getForObject(PRE_HOST+"/student/list", List.class);
    }

    /**
     * 根据id查询学生信息
     * @return
     */
    @GetMapping(value="/get/{id}")
    public Student get(@PathVariable("id") Integer id){
        System.out.println("999");
        return restTemplate.getForObject(PRE_HOST+"/student/get/"+id, Student.class);
    }

    /**
     * 根据id删除学生信息
     * @return
     */
    @GetMapping(value="/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id){
        try{
            restTemplate.getForObject(PRE_HOST+"/student/delete/"+id, Boolean.class);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
```

消费者端添加bootstrap依赖

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bootstrap</artifactId>
    <version>3.0.3</version>
</dependency>
```

### 测试

启动三个eureka，然后再启动服务提供者，再启动服务消费者

![image-20231013065750227](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310130657393.png)

![image-20231013070933257](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310130709847.png)



## Ribbon负载均衡

Ribbon负载均衡是在消费者端进行配置的

```
在微服务架构中，负载均衡是非常重要的组件。负载均衡可以将请求分发到不同的服务实例上，从而提高系统的可用性和稳定性。Ribbon是Spring Cloud中比较重要的负载均衡组件，其核心原理是基于客户端的负载均衡。
Ribbon通过向注册中心获取服务列表，并根据负载均衡策略选择一个可用的服务实例来处理请求。Ribbon支持多种负载均衡算法，例如轮询、随机、最少连接数等，可以根据实际需求选择合适的算法
源码分析
Ribbon的源码比较复杂，其中比较重要的类有：LoadBalancerClient、LoadBalancerInterceptor、RetryRule等。在使用Ribbon时，我们需要将RestTemplate的拦截器替换成LoadBalancerInterceptor，从而实现负载均衡的效果
负载均衡策略
    Ribbon支持多种负载均衡算法，可以根据实际需求选择合适的算法。常见的负载均衡算法有以下几种：
    - 轮询（Round Robin）：按照顺序依次选择服务实例。
    - 随机（Random）：随机选择一个可用的服务实例。
    - 最少连接数（Least Connections）：选择连接数最少的服务实例。
    - 响应时间加权（Response Time Weighted）：根据服务实例的响应时间来进行加权，响应时间越短的实例被选中的概率越大。
    - 一致性哈希（Consistent Hashing）：根据请求的哈希值来选择服务实例。
饥饿加载
Ribbon默认采用懒加载的方式获取服务列表，即在第一次请求时才会去注册中心获取服务列表。这种方式可能会导致第一次请求的响应时间较长。为了避免这种情况，可以采用饥饿加载的方式，在启动时就获取服务列表。
可以通过以下配置来实现饥饿加载：
ribbon:
  eager-load:
    enabled: true
    
注意：Spring Cloud在2020.0版本后，如3.1.3版本就移除了Ribbon，用的是 loadbalancer
```



```
上边搭建了初步例子，但是还没实现真正负载均衡，我们这里要先搞三个服务提供者集群，然后才能演示负载均衡，以及负载均衡策略
新建项目microservice-student-provider-1002，microservice-student-provider-1003
pom.xml，application.yml，以及java类都复制一份，启动类名称对应的改下
yml配置文件有两处要对应的改下，port端口改下，以及服务实例名称改下
```

### 新建1002、1003服务提供者模块

![image-20231014021121125](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310140216754.png)

### 添加依赖

pom.xml

```xml
<dependencies>
        <dependency>
            <groupId>com.et</groupId>
            <artifactId>microservice-common</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>
        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
            <version>2.2.6.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
            <version>3.1.8</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bootstrap</artifactId>
        </dependency>

        <!-- actuator监控引入 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    </dependencies>
```

### 配置文件

applcation.yml

```yaml
server:
  port: 1002
  context-path: /

# 数据源配置
spring:
  application:
    name: microservice-student
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_springcloud
    username: root
    password: Karen@1234
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  thymeleaf:
    cache: false

# eureka注册
eureka:
  instance:
    hostname: localhost  #eureka客户端主机实例名称
    appname: microservice-student  #客户端服务名
    instance-id: microservice-student:1002 #客户端实例名称
    prefer-ip-address: true #显示IP
  client:
    service-url:
      defaultZone: http://eureka2001.etjava.com:2001/eureka/,http://eureka2002.etjava.com:2002/eureka/,http://eureka2003.etjava.com:2003/eureka/ # 集群
      #defaultZone: http://localhost:2001/eureka   #把服务注册到eureka注册中心


management:
  endpoints:
    web:
      exposure:
        include: info #将info端点暴露给web（SpringBoot2.5.0或更高版本中info端点默认隐藏）
  info:
    env:
      enabled: true #是否在info端点中显示环境信息

# 配置info信息
info:
  app.name: microservice-student-provider-1001
  auther.name: microservice-student-provider-1001
  auther.phone: 999
```

![image-20231014022259735](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310140223778.png)

### 复制1001服务提供者中的业务类分别到1002、1003

```java
为了方便查看具体执行了哪个服务提供者，我们再Controller控制器的方法里搞个打印语句
```

#### respository

```java
package com.et.repository;

import com.et.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
 

/**
 * 学生Repository接口
 * @author Administrator
 *
 */
public interface StudentRepository extends JpaRepository<Student, Integer>,JpaSpecificationExecutor<Student>{
 
}
```

#### service

```java
package com.et.service;

import com.et.entity.Student;

import java.util.List;

/**
 * 学生信息Service接口
 * @author Administrator
 *
 */
public interface StudentService {

    /**
     * 添加或者修改学生信息
     * @param student
     */
    public void save(Student student);

    /**
     * 根据id查找学生信息
     * @param id
     * @return
     */
    public Student findById(Integer id);

    /**
     * 查询学生信息
     * @return
     */
    public List<Student> list();

    /**
     * 根据id删除学生信息
     * @param id
     */
    public void delete(Integer id);


}
```

#### service实现类

```java
package com.et.service.impl;

import com.et.entity.Student;
import com.et.repository.StudentRepository;
import com.et.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
 
/**
 * 学生信息Service实现类
 * @author Administrator
 *
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService{
 
    @Resource
    private StudentRepository studentRepository;
     
    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student findById(Integer id) {
        return studentRepository.findById(id).get();
    }
 
    @Override
    public List<Student> list() {
        return studentRepository.findAll();
    }
 
    @Override
    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }
 
}
```

#### controller

```java
package com.et.controller;

import com.et.entity.Student;
import com.et.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
 
/**
 * 服务提供者-学生信息控制器
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/student")
public class StudentProviderController {
 
    @Resource
    private StudentService studentService;
     
    /**
     * 添加或者修改学生信息
     * @param student
     * @return
     */
    @PostMapping(value="/save")
    public boolean save(@RequestBody Student student){
        try{
            studentService.save(student);  
            return true;
        }catch(Exception e){
            return false;
        }
    }
     
    /**
     * 查询学生信息
     * @return
     */
    @GetMapping(value="/list")
    public List<Student> list(){
        System.out.println("===================1002");
        return studentService.list();
    }
     
    /**
     * 根据id查询学生信息
     * @return
     */
    @GetMapping(value="/get/{id}")
    public Student get(@PathVariable("id") Integer id){
        return studentService.findById(id);
    }
     
    /**
     * 根据id删除学生信息
     * @return
     */
    @GetMapping(value="/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id){
        try{
            studentService.delete(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
```

#### 启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderApplication_1002 {
 
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication_1002.class, args);
    }
}
```

#### 测试

```
先启动三个eureka集群，再启动三个服务提供者集群
分别测试三个提供者
http://localhost:1001/student/list
http://localhost:1002/student/list
http://localhost:1003/student/list
再测试下 eureka：
http://eureka2001.etjava.com:2001/
http://eureka2002.etjava.com:2002/
http://eureka2003.etjava.com:2003/
```

![image-20231014023535312](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310140235507.png)

![image-20231014023613771](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310140236202.png)



```
上边测试一切okay的话 再启动服务消费者
多刷新几次 看控制台，我们看到 有默认的轮询策略，访问对应的服务提供者
```

![image-20231014023839575](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310140241412.png)

## Feign

```
声明式服务调用
Feign是一个声明式的Web Service客户端，它使得编写Web Serivce客户端变得更加简单。我们只需要使用Feign来创建一个接口并用注解来配置它既可完成。它具备可插拔的注解支持，包括Feign注解和JAX-RS注解。Feign也支持可插拔的编码器和解码器。Spring Cloud为Feign增加了对Spring MVC注解的支持，还整合了Ribbon和Eureka来提供均衡负载的HTTP客户端实现。

简单理解前面Ribbon调用服务提供者，我们通过restTemplate调用，缺点是，多个地方调用，同一个请求要写多次，不方便统一维护，这时候Feign来了，就直接把请求统一搞一个service作为FeignClient，然后其他调用Controller需要用到的，直接注入service，直接调用service方法即可；同时Feign整合了Ribbon和Eureka，所以要配置负载均衡的话，直接配置Ribbon即可，无其他特殊地方；当然Fiegn也整合了服务容错保护，断路器Hystrix。
```

### Feign的基本使用

修改 microservice-common模块

```
在common项目里建一个service（实际项目肯定是多个service）作为Feign客户端，用Feign客户端来调用服务器提供者，当然可以配置负载均衡；Feign客户端定义的目的，就是为了方便给其他项目调用；

```

#### 添加依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

#### 创建StudentClientService接口

```java
package com.et.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.et.entity.Student;

/**
 * Student Feign接口客户端
 * @author Administrator
 *
 */
@FeignClient(value="MICROSERVICE-STUDENT")
public interface StudentClientService {

    /**
     * 根据id查询学生信息
     * @param id
     * @return
     */
    @GetMapping(value="/student/get/{id}")
    public Student get(@PathVariable("id") Integer id);

    /**
     * 查询学生信息
     * @return
     */
    @GetMapping(value="/student/list")
    public List<Student> list();

    /**
     * 添加或者修改学生信息
     * @param student
     * @return
     */
    @PostMapping(value="/student/save")
    public boolean save(Student student);

    /**
     * 根据id删除学生信息
     * @return
     */
    @GetMapping(value="/student/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id);
}
```

#### 测试

##### 新建一个使用feign的消费者模块

microservice-student-consumer-feign-80

![image-20231014081952756](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310140819283.png)

##### 添加依赖

pom

```xml
 <dependency>
     <groupId>com.et</groupId>
     <artifactId>microservice-common</artifactId>
     <version>0.0.1-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!-- 修改后立即生效，热部署 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <version>2.7.16</version>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
<!--Ribbon-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    <version>3.1.7</version>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
    <version>3.1.8</version>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bootstrap</artifactId>
    <version>3.0.3</version>
</dependency>
```

##### 添加配置文件

application.yml

```yaml
server:
  port: 81
  context-path: /


eureka:
  client:
    register-with-eureka: false #false 由于注册中心的职责就是维护服务实例，它并不需要去检索服务，所以也设置为false
    service-url:
      defaultZone: http://eureka2001.etjava.com:2001/eureka/,
                    http://eureka2002.etjava.com:2002/eureka/,
                    http://eureka2003.etjava.com:2003/eureka/

ribbon:
  eager-load:
    enabled: true #立即加载
```

##### 启动类

添加@EnableFeignClients 开启Feign的支持

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class StudentConsumerApplication_Feign_80 {
 
    public static void main(String[] args) {
        SpringApplication.run(StudentConsumerApplication_Feign_80.class,args);
    }
}
```

##### Controller

现在用Fiegn，所以把restTemplate去掉，改成注入service，调用service方法来实现服务的调用

```java
package com.et.controller;

import java.util.List;


import com.et.entity.Student;
import com.et.service.StudentClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 服务消费者-学生信息控制器
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/student")
public class StudentConsumerFeignController {

    @Autowired
    private StudentClientService studentClientService;

    /**
     * 添加或者修改学生信息
     * @param student
     * @return
     */
    @PostMapping(value="/save")
    public boolean save(Student student){
        return studentClientService.save(student);
    }

    /**
     * 查询学生信息
     * @return
     */
    @GetMapping(value="/list")
    public List<Student> list(){
        return studentClientService.list();
    }

    /**
     * 根据id查询学生信息
     * @return
     */
    @GetMapping(value="/get/{id}")
    public Student get(@PathVariable("id") Integer id){
        return studentClientService.get(id);
    }

    /**
     * 根据id删除学生信息
     * @return
     */
    @GetMapping(value="/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id){
        return studentClientService.delete(id);
    }

}
```

##### config配置类暂时不需要

```java
package com.et.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * SpringCloud相关配置
 * @author Administrator
 *
 */
@Configuration
public class SpringCloudConfig {
//    /**
//     * 调用服务模版
//     * @return
//     */
//    @Bean
//    @LoadBalanced// 该注解3.1.3后的版本 默认采用的是RoundRobinRule，轮询策略
//    public RestTemplate getRestTemplate(){
//        return new RestTemplate();
//    }



}

```

启动测试

为了区分 端口号暂未修改为80

<img src="https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310140831166.png" alt="image-20231014083139107" style="zoom:50%;" />

## Hystrix

![1.jpg](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310140906276.jpeg)

### 简介

```
断路器
hystrix对应的中文名字是“豪猪”，豪猪周身长满了刺，能保护自己不受天敌的伤害，代表了一种防御机制，这与hystrix本身的功能不谋而合，因此Netflix团队将该框架命名为Hystrix，并使用了对应的卡通形象做作为logo
在一个分布式系统里，许多依赖不可避免的会调用失败，比如超时、异常等，如何能够保证在一个依赖出问题的情况下，不会导致整体服务失败，这个就是Hystrix需要做的事情。Hystrix提供了熔断、隔离、Fallback、cache、监控等功能，能够在一个、或多个依赖同时出现问题时保证系统依然可用
Hystrix的引入，可以通过服务熔断和服务降级来解决雪崩效应
```

### 服务雪崩效应

当一个请求依赖多个服务的时候

正常情况下的访问

![20160423115740944.jpg](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310140922846.jpeg)

当请求的服务中出现无法访问、异常、超时等问题时（图中的I节点），那么用户的请求将会被阻塞

![1.jpg](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310140923295.jpeg)

如果多个用户的请求中，都存在无法访问的服务，那么他们都将陷入阻塞的状态中

![2.jpg](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310140923098.jpeg)

### Hystrix服务熔断服务降级

```
Hystrix服务熔断服务降级@HystrixCommand fallbackMethod
熔断机制是应对雪崩效应的一种微服务链路保护机制。
当某个服务不可用或者响应时间超时，会进行服务降级，进而熔断该节点的服务调用，快速返回自定义的错误影响页面信息。
```

#### 新建服务提供者模块1004

microservice-student-provider-hystrix-1004

![image-20231014092732834](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310140927261.png)

#### 添加依赖

```xml
 <dependencies>
     <dependency>
         <groupId>com.et</groupId>
         <artifactId>microservice-common</artifactId>
         <version>0.0.1-SNAPSHOT</version>
     </dependency>

     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-web</artifactId>
     </dependency>
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-data-jpa</artifactId>
     </dependency>
     <dependency>
         <groupId>com.mysql</groupId>
         <artifactId>mysql-connector-j</artifactId>
     </dependency>
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-tomcat</artifactId>
     </dependency>
     <dependency>
         <groupId>com.alibaba</groupId>
         <artifactId>druid</artifactId>
     </dependency>
     <!-- 修改后立即生效，热部署-->
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-devtools</artifactId>
     </dependency>
     <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
         <version>2.2.6.RELEASE</version>
     </dependency>
     <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-config</artifactId>
         <version>3.1.8</version>
     </dependency>
     <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-bootstrap</artifactId>
     </dependency>
     <!-- actuator监控引入 -->
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-actuator</artifactId>
     </dependency>

     <!--Hystrix 在springcloud2021版本中需要单独指定版本号-->
     <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
         <version>2.2.5.RELEASE</version>
     </dependency>
</dependencies>
```

#### 创建配置文件

application.yml

修改下端口和服务名称

```yaml
server:
  port: 1004
  context-path: /

# 数据源配置
spring:
  application:
    name: microservice-student
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_springcloud
    username: root
    password: Karen@1234
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  thymeleaf:
    cache: false

# eureka注册
eureka:
  instance:
    hostname: localhost  #eureka客户端主机实例名称
    appname: microservice-student  #客户端服务名
    instance-id: microservice-student:1004 #客户端实例名称
    prefer-ip-address: true #显示IP
  client:
    service-url:
      defaultZone: http://eureka2001.etjava.com:2001/eureka/,http://eureka2002.etjava.com:2002/eureka/,http://eureka2003.etjava.com:2003/eureka/ # 集群
      #defaultZone: http://localhost:2001/eureka   #把服务注册到eureka注册中心


management:
  endpoints:
    web:
      exposure:
        include: info #将info端点暴露给web（SpringBoot2.5.0或更高版本中info端点默认隐藏）
  info:
    env:
      enabled: true #是否在info端点中显示环境信息

# 配置info信息
info:
  app.name: microservice-student-provider-1004
  auther.name: Jerry
  auther.phone: 999
```

#### 创建启动类

StudentProviderHystrixApplication_1004

添加@EnableCircuitBreaker注解 开启断路器

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker// 开启hystrix
public class ProviderHystrixApplication_1004 {

    public static void main(String[] args) {
        SpringApplication.run(ProviderHystrixApplication_1004.class,args);
    }
}

```

#### Controller测试

```java
package com.et.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务提供者-学生信息控制器
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/student")
public class StudentProviderController {

    /**
     * 获取信息
     * @return
     * @throws InterruptedException
     */
    @ResponseBody
    @GetMapping(value="/getInfo")
    @HystrixCommand(fallbackMethod="getInfoFallback")
    public Map<String,Object> getInfo() throws InterruptedException{
        Thread.sleep(2000);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code", 200);
        map.put("info", "业务数据xxxxx");
        return map;
    }

    public Map<String,Object> getInfoFallback() throws InterruptedException{
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code", 500);
        map.put("info", "系统出错，稍后重试");
        return map;
    }
}
```

#### microservice-student-consumer-80修改

服务消费者中需要添加调用的方法 或是在common模块中添加service

```java
 /**
     * 测试服务熔断降级处理
     */
@GetMapping(value="/getInfo")
@ResponseBody
public Map<String,Object> getInfo(){
    return restTemplate.getForObject(PRE_HOST+"/student/getInfo/", Map.class);
}
```

#### 如果是common模块需要修改service

```java
 /**
     * 测试hystrix服务熔断
     */
@GetMapping(value="/student/getInfo")
public Map<String,Object> getInfo();
```

##### 使用带Feign的调用

```
microservice-student-consumer-feign-80中修改controller 调用getInfo
```

```java
/**
     * 测试服务熔断
     */
@GetMapping(value="/getInfo")
public Map<String,Object> getInfo(){
    return studentClientService.getInfo();
}
```

![image-20231014101538033](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310141015430.png)





#### 测试

```
启动三个eureka，再启动带hystrix的provider，最后启动普通的consumer
正常访问 返回的是 200  业务数据xxxxx 
但是我们这里Thread.sleep(2000) 模拟超时；
这里的话 我们加上@HystrixCommand注解 以及 fallbackMethod
表明这个方法我们再 没有异常以及没有超时(hystrix默认1秒算超时)的情况，才返回正常的业务数据；
否则，进入我们fallback指定的本地方法，我们搞的是500  系统出错，稍后重试，有效的解决雪崩效应，以及返回给用户界面
很好的报错提示信息；
```

![image-20231014100606807](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310141006878.png)

### 调整Hystrix默认超时时间

默认超时时间在com.netflix.hystrix:hystrix-core包下HystrixCommandProperties类中定义 默认1秒

application.yml

```yaml
# 配置Hystrix默认超时时间
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 3000
```



![image-20231014102238837](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310141022141.png)

![image-20231014102409063](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310141024177.png)

### Feign Hystrix整合

整合后 FallbackFactory指定的降级处理类无效

```
Feign Hystrix整合&服务熔断服务降级彻底解耦
上边的代码使用@HystrixCommand+fallbackMethod方法是很不友好的，因为和业务代码高度耦合 不利于维护 因此需要解耦

```

#### 新建服务提供者模块1006

microservice-student-provider-hystrix-1006

#### 添加依赖

```xml
 <dependencies>
     <dependency>
         <groupId>com.et</groupId>
         <artifactId>microservice-common</artifactId>
         <version>0.0.1-SNAPSHOT</version>
     </dependency>

     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-web</artifactId>
     </dependency>
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-data-jpa</artifactId>
     </dependency>
     <dependency>
         <groupId>com.mysql</groupId>
         <artifactId>mysql-connector-j</artifactId>
     </dependency>
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-tomcat</artifactId>
     </dependency>
     <dependency>
         <groupId>com.alibaba</groupId>
         <artifactId>druid</artifactId>
     </dependency>
     <!-- 修改后立即生效，热部署-->
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-devtools</artifactId>
     </dependency>
     <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
         <version>2.2.6.RELEASE</version>
     </dependency>
     <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-config</artifactId>
         <version>3.1.8</version>
     </dependency>
     <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-bootstrap</artifactId>
     </dependency>
     <!-- actuator监控引入 -->
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-actuator</artifactId>
     </dependency>

     <!--Hystrix 在springcloud2021版本中需要单独指定版本号-->
     <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
         <version>2.2.5.RELEASE</version>
     </dependency>
</dependencies>
```

#### 新建Service接口

StudentService

```java
package com.et.service;

import java.util.Map;

public interface StudentService {

    /**
     * 获取信息
     * @return
     */
    public Map<String,Object> getInfo();
}

```

#### Service实现类

```java
package com.et.service.impl;

import com.et.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    @Override
    public Map<String, Object> getInfo() {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code", 200);
        map.put("info", "业务数据xxxxx:1006");
        return map;
    }
}

```

#### StudentProviderController

```java
package com.et;

import com.et.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StudentProviderController {

    @Autowired
    private StudentService studentService;
    /**
     * 获取信息
     * @return
     * @throws InterruptedException
     */
    @ResponseBody
    @GetMapping(value="/getInfo")
    public Map<String,Object> getInfo() throws InterruptedException{
        Thread.sleep(900);
        return studentService.getInfo();
    }
}

```

#### microservice-common模块

##### StudentClientService接口，新增getInfo方法

```java
package com.et.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.et.entity.Student;

/**
 * Student Feign接口客户端
 * @author Administrator
 *
 */
@FeignClient(value="MICROSERVICE-STUDENT")
public interface StudentClientService {

    /**
     * 根据id查询学生信息
     * @param id
     * @return
     */
    @GetMapping(value="/student/get/{id}")
    public Student get(@PathVariable("id") Integer id);

    /**
     * 测试hystrix服务熔断
     */
    @GetMapping(value="/student/getInfo")
    public Map<String,Object> getInfo();

    /**
     * 查询学生信息
     * @return
     */
    @GetMapping(value="/student/list")
    public List<Student> list();

    /**
     * 添加或者修改学生信息
     * @param student
     * @return
     */
    @PostMapping(value="/student/save")
    public boolean save(Student student);

    /**
     * 根据id删除学生信息
     * @return
     */
    @GetMapping(value="/student/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id);
}
```

##### 新建FallbackFactory类，解耦服务熔断服务降级

新建 StudentClientFallbackFactory 类，实现FallbackFactory<StudentClientService>接口

```java
package com.et.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.et.entity.Student;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class StudentClientFallbackFactory implements FallbackFactory<StudentClientService> {

    @Override
    public StudentClientService create(Throwable cause) {
        // TODO Auto-generated method stub
        return new StudentClientService() {

            @Override
            public boolean save(Student student) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public List<Student> list() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Map<String, Object> getInfo() {
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("code", 500);
                map.put("info", "系统出错，稍后重试FallbackFactory");
                return map;
            }

            @Override
            public Student get(Integer id) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean delete(Integer id) {
                // TODO Auto-generated method stub
                return false;
            }
        };
    }

}

```

```
StudentClientService接口的@FeignClient注解加下 fallbackFactory属性 
@FeignClient(value="MICROSERVICE-STUDENT",fallbackFactory=StudentClientFallbackFactory.class)
```

![image-20231014151240501](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310141512177.png)

#### 修改microservice-student-consumer-feign-80 支持Hystrix

```java
/**
     * 测试服务熔断
     */
@GetMapping(value="/getInfo")
public Map<String,Object> getInfo(){
    return studentClientService.getInfo();
}
```

##### application配置文件

```yaml
feign: 
  hystrix: 
    enabled: true
```

#### 测试

```
测试开启三个eureka，以及带hystrix的provider，和带feign,hystrix的consummer。
0.9秒的话，返回正常信息；超过1秒的话，就返回错误提示；
```



![image-20231014225350267](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310142253662.png)

### Feign Hystrix整合之超时时间配置

```
Feign Hystrix整合后，hystrix超时时间配置的话 这里要配置到消费端。
因为feign 也有一个超时时间的设置，当然feign底层是 ribbon的封装，所以 直接配置ribbon，ribbon默认超时也是1秒
所以这里都是强制要求，ribbon的超时时间要大于hystrix的超时时间，否则 hystrix自定义的超时时间毫无意义
这里需要先配置Feign的超时时间 然后在配置Hystrix的超时时间

需要将microservice-student-provider-hystrix-1004里面的hystrix超时配置放到
microservice-student-consumer-feign-80的配置文件中

```

#### 修改Feign-80模块的配置文件

```yaml
server:
  port: 81
  context-path: /


eureka:
  client:
    register-with-eureka: false #false 由于注册中心的职责就是维护服务实例，它并不需要去检索服务，所以也设置为false
    service-url:
      defaultZone: http://eureka2001.etjava.com:2001/eureka/,
                    http://eureka2002.etjava.com:2002/eureka/,
                    http://eureka2003.etjava.com:2003/eureka/

ribbon:
  eager-load:
    enabled: true #立即加载
  ReadTimeout: 10000
  ConnectTimeout: 9000

hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 3000

feign:
  hystrix:
    enabled: true
  circuitbreaker:
    enabled: true

```

#### 测试

```
启动三个eureka,然后启动1006模块，最后启动feign-80模块进行测试
当我们修改了1006模块中的模拟时间后 看下是否还会请求超时导致降级处理
```

![image-20231015000245915](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310150002098.png)

## Hystrix服务监控Dashboard仪表盘

```
Hystrix提供了 准实时的服务调用监控项目Dashboard，能够实时记录通过Hystrix发起的请求执行情况，
可以通过图表的形式展现给用户。
```

配置Dashboard

### 新建模块90

microservice-student-consumer-hystrix-dashboard-90

### 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        <version>2.2.5.RELEASE</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-hystrix-dashboard -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
        <version>2.2.5.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>
```

### application配置文件

```yaml
server:
  port: 90
  servlet:
    context-path: /
```

### 启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@EnableHystrixDashboard
public class StudentConsumerDashBoardApplication_90 {

    public static void main(String[] args) {
        SpringApplication.run(StudentConsumerDashBoardApplication_90.class, args);
    }
}


```

### 启动项目访问90端口

http://localhost:90/hystrix

出现下面页面表示配置完成

![image-20231014200149000](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310142001392.png)

### 被监控端修改1004

```
例如 我们监控1004服务提供者 则需要在1004中添加hystrix相关配置
下面以1004为例
```

#### pom中添加依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
    <version>2.2.5.RELEASE</version>
</dependency>
```

#### 启动类添加Bean

```java
package com.et;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker// 开启hystrix
public class ProviderHystrixApplication_1004 {

    public static void main(String[] args) {
        SpringApplication.run(ProviderHystrixApplication_1004.class,args);
    }

    @Bean
    public ServletRegistrationBean hystrixMetricsStreamServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new HystrixMetricsStreamServlet());
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        return registrationBean;
    }

}

```

#### 测试被监控端

```
访问 localhost:1004/hystrix.stream
如果出现ping 则表示成功
当访问localhost:1004/student/getInfo时 会出现data 如下图则表示被监控端配置完成
```

![image-20231014203549501](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310142035686.png)

### 监控端调整90

#### application配置文件中添加监控路径

```
hystrix:
  dashboard:
    proxy-stream-allow-list: localhost #如果访问的是localhost 就写localhost 如果是127.0.0.1 则写127.0.0.1 否则监控台不会显示任何数据
```

#### 启动类调整

添加ServletRegistrationBean 否则接受不到被监控的对象

```java
package com.et;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@EnableHystrixDashboard
@EnableCircuitBreaker
public class StudentConsumerDashBoardApplication_90 {

    public static void main(String[] args) {
        SpringApplication.run(StudentConsumerDashBoardApplication_90.class, args);
    }

    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}


```

### 测试

```
启动三个eureka，然后再启动microservice-student-provider-hystrix-1004
```

![image-20231014202834897](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310142028020.png)

## Hystrix集群监控turbine

```
Dashboard演示的仅仅是单机服务监控，实际项目基本都是集群，所以这里集群监控用的是turbine
turbine是基于Dashboard的。
为了演示集群 在创建一个1005项目
microservice-student-provider-hystrix-1005
```

### microservice-student-provider-hystrix-1005

```
基于1004的 可以将1004代码复制一份过来
```

#### 新建模块1005

#### 添加依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>springcloud</artifactId>
        <groupId>com.et</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservice-student-provider-hystrix-1005</artifactId>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.et</groupId>
            <artifactId>microservice-common</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>
        <!-- 修改后立即生效，热部署-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
            <version>2.2.6.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
            <version>3.1.8</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bootstrap</artifactId>
        </dependency>
        <!-- actuator监控引入 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!--Hystrix 在springcloud2021版本中需要单独指定版本号-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
            <version>2.2.5.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
            <version>2.2.5.RELEASE</version>
        </dependency>
    </dependencies>

</project>
```

#### 添加配置文件

```yaml
server:
  port: 1005
  context-path: /

# 数据源配置
spring:
  application:
    name: microservice-student
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_springcloud
    username: root
    password: Karen@1234
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  thymeleaf:
    cache: false

# eureka注册
eureka:
  instance:
    hostname: localhost  #eureka客户端主机实例名称
    appname: microservice-student  #客户端服务名
    instance-id: microservice-student-hystrix:1005 #客户端实例名称
    prefer-ip-address: true #显示IP
  client:
    service-url:
      defaultZone: http://eureka2001.etjava.com:2001/eureka/,http://eureka2002.etjava.com:2002/eureka/,http://eureka2003.etjava.com:2003/eureka/ # 集群
      #defaultZone: http://localhost:2001/eureka   #把服务注册到eureka注册中心


management:
  endpoints:
    web:
      exposure:
        include: info #将info端点暴露给web（SpringBoot2.5.0或更高版本中info端点默认隐藏）
  info:
    env:
      enabled: true #是否在info端点中显示环境信息

# 配置info信息
info:
  app.name: microservice-student-provider-1001
  auther.name: Jerry
  auther.phone: 999


# 配置Hystrix默认超时时间
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 3000
```

#### 创建Repository

```java
package com.et.repository;

import com.et.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
 

/**
 * 学生Repository接口
 * @author Administrator
 *
 */
public interface StudentRepository extends JpaRepository<Student, Integer>,JpaSpecificationExecutor<Student>{
 
}
```

#### 创建Service

```java
package com.et.service;

import com.et.entity.Student;

import java.util.List;

/**
 * 学生信息Service接口
 * @author Administrator
 *
 */
public interface StudentService {

    /**
     * 添加或者修改学生信息
     * @param student
     */
    public void save(Student student);

    /**
     * 根据id查找学生信息
     * @param id
     * @return
     */
    public Student findById(Integer id);

    /**
     * 查询学生信息
     * @return
     */
    public List<Student> list();

    /**
     * 根据id删除学生信息
     * @param id
     */
    public void delete(Integer id);


}
```

#### Service实现类

```java
package com.et.service.impl;

import com.et.entity.Student;
import com.et.repository.StudentRepository;
import com.et.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
 
/**
 * 学生信息Service实现类
 * @author Administrator
 *
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService{
 
    @Resource
    private StudentRepository studentRepository;
     
    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student findById(Integer id) {
        return studentRepository.findById(id).get();
    }
 
    @Override
    public List<Student> list() {
        return studentRepository.findAll();
    }
 
    @Override
    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }
 
}
```

#### Controller

```java
package com.et.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务提供者-学生信息控制器
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/student")
public class StudentProviderController {

    /**
     * 获取信息
     * @return
     * @throws InterruptedException
     */
    @ResponseBody
    @GetMapping(value="/getInfo")
    @HystrixCommand(fallbackMethod="getInfoFallback")
    public Map<String,Object> getInfo() throws InterruptedException{
        Thread.sleep(1000);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code", 200);
        map.put("info", "业务数据xxxxx:1005");
        return map;
    }

    public Map<String,Object> getInfoFallback() throws InterruptedException{
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code", 500);
        map.put("info", "系统出错，稍后重试:1005");
        return map;
    }
}
```

#### 启动类

```java
package com.et;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker// 开启hystrix
public class ProviderHystrixApplication_1005 {

    public static void main(String[] args) {
        SpringApplication.run(ProviderHystrixApplication_1005.class,args);
    }

    @Bean
    public ServletRegistrationBean hystrixMetricsStreamServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new HystrixMetricsStreamServlet());
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        return registrationBean;
    }

}

```

### microservice-student-consumer-hystrix-turbine-91

#### 创建91模块

#### 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-turbine</artifactId>
        <version>2.2.5.RELEASE</version>
    </dependency>
</dependencies>
```

#### 添加配置文件

```
server:
  port: 91
  context-path: /

eureka:
  client:
    register-with-eureka: true # 是否注册到服务注册中心
    fetch-registry: true # 需要从注册中心获取各个服务的hystrix信息，需设置为true
    service-url:
      defaultZone: http://eureka2001.etjava.com:2001/eureka/,
                   http://eureka2002.etjava.com:2002/eureka/,
                   http://eureka2003.etjava.com:2003/eureka/

turbine:
  app-config: microservice-student   # 指定要监控的应用名称 多个使用逗号隔开
  cluster-name-expression: "'default'"  #表示集群的名字为default
#  aggregator:
#    cluster-config: default
  combine-host-port: true
  instanceUrlSuffix: /hystrix.stream

spring:
  application:
    name: turbine
```

#### 启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@EnableTurbine
public class StudentConsumerTurbineApplication_91 {

    public static void main(String[] args) {
        SpringApplication.run(StudentConsumerTurbineApplication_91.class, args);
    }

}
```

#### 调整1004模块

```
1004模块是单机监控 需要改为集群
package com.et;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker// 开启hystrix
public class ProviderHystrixApplication_1004 {

    public static void main(String[] args) {
        SpringApplication.run(ProviderHystrixApplication_1004.class,args);
    }

    @Bean
    public ServletRegistrationBean hystrixMetricsStreamServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new HystrixMetricsStreamServlet());
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        return registrationBean;
    }

}

```

![image-20231014221736575](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310142217910.png)

### 测试

```
启动三个eureka，然后把1004  1005 带hystrix的服务都启动；
microservice-student-consumer-80这个也启动，方便测试；
dashboard，turbine启动；
这样的话 http://localhost/student/getInfo 就能调用服务集群；
http://localhost:91/turbine.stream  可以监控数据，实时ping 返回data
```

![image-20231014221826676](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310142218816.png)



![image-20231014221844592](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310142218030.png)





![image-20231014221154351](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310142211446.png)

## Zuul API

在springcloud新版本中已经不支持了 而改用了gateway作为网关

### 简介

```
如下图所示
这里的API 路由网关服务 由Zuul实现，主要就是对外提供服务接口的时候，起到了请求的路由和过滤作用，也因此能够隐藏内部服务的接口细节，从而有利于保护系统的安全性
```

![20181022220910.jpg](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310150004822.jpeg)

## GateWay

### 概述

```
网关(Gateway)又称网间连接器、协议转换器。网关在网络层以上实现网络互连，是复杂的网络互连设备，仅用于两个高层协议不同的网络互连。网关既可以用于广域网互连，也可以用于局域网互连。 网关是一种充当转换重任的计算机系统或设备。使用在不同的通信协议、数据格式或语言，甚至体系结构完全不同的两种系统之间，网关是一个翻译器。与网桥只是简单地传达信息不同，网关对收到的信息要重新打包，以适应目的系统的需求。同层--应用层。


大家都知道，从一个房间走到另一个房间，必然要经过一扇门。同样，从一个网络向另一个网络发送信息，也必须经过一道“关口”，这道关口就是网关。顾名思义，网关（Gateway）就是一个网络连接到另一个网络的“关口”。也就是网络关卡


在微服务架构中，通常一个系统会被拆分为多个微服务，面对这么多微服务客户端应该如何去调用呢？如果没有其他更优方法，我们只能记录每个微服务对应的地址，分别去调用，但是这样会有很多的问题和潜在因素。

客户端多次请求不同的微服务，会增加客户端代码和配置的复杂性，维护成本比价高。

认证复杂，每个微服务可能存在不同的认证方式，客户端去调用，要去适配不同的认证，

存在跨域的请求，调用链有一定的相对复杂性（防火墙 / 浏览器不友好的协议）。

难以重构，随着项目的迭代，可能需要重新划分微服务

为了解决上面的问题，微服务引入了 网关 的概念，网关为微服务架构的系统提供简单、有效且统一的API路由管理，作为系统的统一入口，提供内部服务的路由中转，给客户端提供统一的服务，可以实现一些和业务没有耦合的公用逻辑，主要功能包含认证、鉴权、路由转发、安全策略、防刷、流量控制、监控日志等。

官网：https://spring.io/projects/spring-cloud-gateway
```

网关在微服务中的位置

![img](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310162010925.jpeg)

网关的对比

```
Zuul 1.0 : Netflix开源的网关，使用Java开发，基于Servlet架构构建，便于二次开发。因为基于Servlet内部延迟严重，并发场景不友好，一个线程只能处理一次连接请求。
Zuul 2.0 : 采用Netty实现异步非阻塞编程模型，一个CPU一个线程，能够处理所有的请求和响应，请求响应的生命周期通过事件和回调进行处理，减少线程数量，开销较小
GateWay : 是Spring Cloud的一个全新的API网关项目，替换Zuul开发的网关服务，基于Spring5.0 + SpringBoot2.0 + WebFlux（基于性能的Reactor模式响应式通信框架Netty，异步阻塞模型）等技术开发，性能高于Zuul
```

基本概念

```
路由（Route）是GateWay中最基本的组件之一，表示一个具体的路由信息载体，主要由下面几个部分组成：
id：路由唯一标识，区别于其他的route
uri： 路由指向的目的地URL，客户端请求最终被转发到的微服务
order： 用于多个Route之间的排序，数值越小越靠前，匹配优先级越高
predicate：断言的作用是进行条件判断，只有断言为true，才执行路由
filter: 过滤器用于修改请求和响应信息
```

![img](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310162013794.jpeg)

### helloworld

gateway快速入门

#### 新建模块4001



#### 添加依赖

```xml
<dependencies>
    <!--
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-dependencies</artifactId>
        <version>2021.0.8</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        <version>2.2.5.RELEASE</version>
    </dependency>
</dependencies>
```

#### 创建启动类

启动类上需要添加@EnableEurekaClient

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderApplication_1001 {
 
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication_1001.class, args);
    }
}
```

#### 添加配置文件

application.yml

```
server:
  port: 4001
spring:
  main:
    web-application-type: reactive
  cloud:
    gateway: #配置网关
      routes:
        - id: baidu #自定义ID
          uri: https://www.baidu.com # 访问百度首页
          predicates:
            - Path=/ # 根请求

```

![image-20231016202438717](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310162024978.png)

### 访问注册中心的服务

```
修改上述helloworld案例 配置注册中心及访问注册中心的服务
```

#### 修改application.yml

```yaml
server:
  port: 4001
spring:
  main:
    web-application-type: reactive
  cloud:
    gateway: #配置网关
      discovery:
        locator:
          enabled: true   #开启Eureka服务发现
          lower-case-service-id: true
#      routes:
#        - id: baidu #自定义ID
#          uri: https://www.baidu.com # 访问百度首页
#          predicates:
#            - Path=/ # 根请求
  application:
    name: microservice-gateway
eureka:
  instance:
    hostname: localhost  #eureka客户端主机实例名称
    appname: microservice-gateway  #客户端服务名
    instance-id: microservice-gateway:4001 #客户端实例名称
    prefer-ip-address: true #显示IP
  client:
    service-url:
      defaultZone: http://eureka2001.etjava.com:2001/eureka/,http://eureka2002.etjava.com:2002/eureka/,http://eureka2003.etjava.com:2003/eureka/ # 集群
```

#### 启动测试

```
启动eureka注册中心、1001服务、网关
```

##### 单独访问1001服务提供者

![image-20231016202834741](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310162028475.png)

##### 通过网关进行访问

网关访问时需要根据服务名称进行访问

![image-20231016202911733](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310162029830.png)