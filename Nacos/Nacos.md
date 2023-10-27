# Nacos

## 简介

```
Nacos /nɑ:kəʊs/ 是 Dynamic Naming and Configuration Service的首字母简称，一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。
Nacos 致力于帮助您发现、配置和管理微服务。Nacos 提供了一组简单易用的特性集，帮助您快速实现动态服务发现、服务配置、服务元数据及流量管理。
Nacos 帮助您更敏捷和容易地构建、交付和管理微服务平台。 Nacos 是构建以“服务”为中心的现代应用架构 (例如微服务范式、云原生范式) 的服务基础设施。
什么是 Nacos？
服务（Service）是 Nacos 世界的一等公民。Nacos 支持几乎所有主流类型的“服务”的发现、配置和管理：
    Kubernetes Service
    gRPC & Dubbo RPC Service
    Spring Cloud RESTful Service
Nacos 的关键特性包括:
	服务发现和服务健康监测
	Nacos 支持基于 DNS 和基于 RPC 的服务发现。服务提供者使用 原生SDK、OpenAPI、
	或一个独立的Agent TODO注册 Service 后，服务消费者可以使用DNS TODO 或HTTP&API查找和发现服务。
	Nacos 提供对服务的实时的健康检查，阻止向不健康的主机或服务实例发送请求。
	Nacos 支持传输层 (PING 或 TCP)和应用层 (如 HTTP、MySQL、用户自定义）的健康检查。 
	对于复杂的云环境和网络拓扑环境中（如 VPC、边缘网络等）服务的健康检查，
	Nacos 提供了 agent 上报模式和服务端主动检测2种健康检查模式。
	Nacos 还提供了统一的健康检查仪表盘，帮助您根据健康状态管理服务的可用性及流量。


https://nacos.io/
```

## 功能介绍

### 动态配置服务

```
动态配置服务能够让我们以中心化，外部化和动态化的方式管理所有环境的配置，动态配置消除了配置变更时重新部署应用和服务的需要，配置中心化管理让实现无状态服务更简单，也更让按需弹性扩展服务更容易
```

![image-20231009193256560](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310091932594.png)

### 服务发现及管理

```
动态服务发现对以服务为中心的应用架构方式非常关键的技术，Nacos支持DNS-Based和RPC-Based模式的服务发现
Nacos同时还提供了实时健康检查，以防止讲请求发往不健康的主机或服务实例，借助Nacos可以更容易地为服务实现断路器
```

![image-20231009193638442](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310091936668.png)

### 动态DNS服务

```
通过支持权重路由 动态DNS服务能让我们轻松实现中间层负载均衡，更灵活的路由策略 流量控制及简单数据中心内网的简单DNS解析服务，动态DNS服务还能让我们更容易的实现以DNS协议为基础的服务发现，以消除耦合到厂商私有服务发现API上的风险
```

## Nacos Server安装

### 下载Nacos

```
可以根据官方文档选择对应的下载和安装方式 ，本案例以简洁快速搭建为基础 因此直接下载最新稳定版即可
官方文档
https://nacos.io/zh-cn/docs/quick-start.html
下载地址
https://github.com/alibaba/nacos/releases/download/2.2.3/nacos-server-2.2.3.zip
```

#### 源码方式

https://gitee.com/codefield2022/nacos



![image-20231009213406608](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092134454.png)

选择对应的版本

![image-20231009213437387](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092134250.png)

下载源码后 然后解压缩并进入到根目录

![image-20231009211217936](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092112959.png)

进入根目录后执行如下命令等待打包完成即可

```
前提：本地必须要配置maven环境
mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U  

```

![image-20231009211345736](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092113990.png)

![image-20231009212401408](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092125738.png)

编译完成后在distribtion\target目录下会生成两个压缩包 zip的为windows版本

![image-20231009212645371](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092126352.png)

#### 安装包方式

##### 下载安装包后解压缩

![image-20231009195816635](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310091958785.png)



### 修改启动配置

```
Nacos默认配置是集群模式 因此我们单机测试时 需要修改默认配置
修改startup.cmd文件
将 MODE="cluster" 改为standalone
```

#### 修改startup.cmd文件

```
set MODE="standalone"
```

![image-20231009200211910](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20231009200211910.png)

### 启动测试

直接双击bin目录下startup.cmd即可

![image-20231009200831978](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092008249.png)

访问地址 ip:8848/nacos

用户名、密码都是nacos

![image-20231009201045402](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092010799.png)

### 修改默认的数据库连接

```
Nacos默认采用内置的以文件形式保存的小型数据库，不适用于企业级开发模式，因此这里需要修改为数据库保存Nacos相关信息
```

#### 默认存储数据

![image-20231009201035493](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092010212.png)

#### 修改为数据库存储

```
修改application.properties文件
打开如下内容 并修改数据库连接信息

#*************** Config Module Related Configurations ***************#
### If use MySQL as datasource:
### Deprecated configuration property, it is recommended to use `spring.sql.init.platform` replaced.
spring.datasource.platform=mysql
# spring.sql.init.platform=mysql

### Count of DB:
db.num=1

### Connect URL of DB:
db.url.0=jdbc:mysql://127.0.0.1:3306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
db.user.0=root
db.password.0=Karen@1234

### Connection pool configuration: hikariCP
db.pool.config.connectionTimeout=30000
db.pool.config.validationTimeout=10000
db.pool.config.maximumPoolSize=20
db.pool.config.minimumIdle=2

```

执行SQL文件

mysql-schema.sql

![image-20231009201446584](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092014988.png)





![image-20231009203056358](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092030893.png)

### 重新启动并登录

![image-20231009231632293](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092316483.png)

## Nacos统一配置中心

### 快速入门

```
Nacos与Springboot集成读取配置
注意 SpringCloud Alibaba版本与Springboot版本是有对应关系的 可参考如下说明
https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E
```

### 搭建父子项目

#### 父项目

![image-20231009232322849](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092323181.png)

##### 添加依赖

```
添加依赖时需要注意三个地方
springboto的版本
springcloud的版本
springcloudalibaba的版本
```

![image-20231009234643915](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092346896.png)

同时还需要springcloudalibaba与nacos的版本对应

![image-20231009234939229](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092349269.png)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.et</groupId>
    <artifactId>nacos_parent</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <spring-cloud.version>Hoxton.SR9</spring-cloud.version>
        <springboot.version>2.3.2.RELEASE</springboot.version>
        <springcloudalibaba.version>2.2.6.RELEASE</springcloudalibaba.version>
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
        </dependencies>
</project>
```

#### 子项目

![image-20231009235059272](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310092351367.png)

##### 添加依赖

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
</dependencies>
```

##### 创建配置文件 application.yml

```yaml
server:
  port: 80
  servlet:
    context-path: /
```

##### 创建启动类

```java
@SpringBootApplication
public class NacosConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class,args);
    }
}
```

##### 测试读取本地配置文件中的内容

读取的是bootstrap.yml

```
springboot的配置文件有三种 
application.yml > application.properties(默认) > bootstrap.yml(优先级最该)
我们后边要读取nacos配置中心时 需要使用bootstrap.yml配置文件 在springboot启动时加载
```

bootstrap.yml

```yaml
user:
  name: tom
  age: 12
```

###### 创建测试Controller

```java
package com.et.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${user.name}")
    private String name;
    @Value("${user.age}")
    private int age;

    @RequestMapping("/getUserInfo")
    public String getUserInfo(){
        return name+"--"+age;
    }
}

```

###### 启动并测试

![image-20231010000800406](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100008963.png)

##### Nacos中配置一个服务

![image-20231010001633321](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100016130.png)



![image-20231010001731434](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100017627.png)



###### 配置读取nacos中的信息

```
bootstrap.yml文件中配置如下内容

user:
  name: tom
  age: 12
spring:
  application:
    name: nacos_conf #配置项目名称
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848 #nacos访问路径
        group: NACOS_CONFIG #组的名称
        name: nacos_config_01 #要读取数据集的ID

```



###### 重新启动并测试

![image-20231010002447526](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100024636.png)



```
当我们修改了nacos配置中心的数据集内容时 nacos会实时通知到我们的应用程序 
但我们的应用程序如果想实时刷新 需要在类体上添加**@RefreshScope** 注解
@RefreshScope是Spring Cloud提供的一种配置文件热加载的注解
```



![image-20231010002922679](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100029816.png)

### 配置中心-数据模型

```
Nacos数据模块的key由三组唯一确定
Namespave默认是空串，公共的命名空间（public） 分组默认是DEFAULT_GROUP

类似maven的GAV坐标，可以定位到唯一的一个数据模型
```

![image-20231010060654449](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100612879.png)

![image-20231010061756678](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100617186.png)

![image-20231010062049320](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100620948.png)

#### 案例0 - 不同数据集

```
相同的命名空间，相同的分组，不同的数据集
```

![image-20231010063804341](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100638478.png)

![image-20231010063826800](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100638476.png)

bootstrap.yml

调用的时候只需要修改数据集的ID即可

```yaml
user:
  name: tom
  age: 12
spring:
  application:
    name: nacos_conf #配置项目名称
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848 #nacos访问路径
        group: DEFAULT_GROUP #组的名称
        name: nacos_config_02 #要读取数据集的ID
        namespace: e5c78c07-74ef-45ed-8389-b1ac2d1cd8c0 #指定命名空间ID
```

![image-20231010063924792](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100639908.png)

#### 案例1 - 不同分组

![image-20231010062502782](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100625794.png)



##### 读取配置

bootstrap.yml

调用时需要指定分组名称（或叫做分组的ID）

```yaml
user:
  name: tom
  age: 12
spring:
  application:
    name: nacos_conf #配置项目名称
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848 #nacos访问路径
        group: DEFAULT_GROUP #组的名称
        name: nacos_config_01 #要读取数据集的ID

```

![image-20231010062610724](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100626831.png)

#### 案例2 - 不同命名空间

新建命名空间

![image-20231010062743420](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100627478.png)

![image-20231010062923012](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100629179.png)

新建命名空间后 可以在配置列表中看到相应的命名空间

可以在不同的命名空间中定义我们的数据模型

![image-20231010063055244](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100630751.png)

例如 在DEV命名空间中新建一个数据集

![image-20231010063240620](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100632207.png)

![image-20231010063307060](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100633147.png)

程序调用的时候需要指定命名空间 不指定会找默认的命名空间

bootstrap.yml

```yaml
user:
  name: tom
  age: 12
spring:
  application:
    name: nacos_conf #配置项目名称
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848 #nacos访问路径
        group: DEFAULT_GROUP #组的名称
        name: nacos_config_01 #要读取数据集的ID
        namespace: e5c78c07-74ef-45ed-8389-b1ac2d1cd8c0 #指定命名空间ID

```

![image-20231010063453711](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310100634881.png)

### 加载多个配置集

```
有时候为了多模块项目的配置共享，我们需要进行配置拆分及加载多配置集
例如 我们有10个业务模块，mysql,redis,rabbitmq等配置都是一样的，为了统一管理 在nacos配置中心需要单独的搞成一个通用配置，然后主项目引入即可
```

#### 案例

创建数据集

```
抽取公共配置
mysql_common.properties
DEFAULT_GROUP
etjava.mysql.common=mysqlconfig

redis_common.properties
DEFAULT_GROUP
etjava.redis.common=redisconfig

业务模块CRM
crm.properties
CRM_GROUP
etjava.crm.config=crm

业务模块OA
oa.properties
OA_GROUP
etjava.oa.config=oa
```

![image-20231010194940047](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310101949844.png)



#### 配置多数据集

```
前提条件: 同一个项目的配置数据集必须要在同一个namespace中

## 配置多数据集读取
# 数据集ID
spring.cloud.nacos.config.extension-configs[0].data-id=mysql_common.properties
# 数据集所在的组名称
spring.cloud.nacos.config.extension-configs[0].group=DEFAULT_GROUP
# 是否动态刷新 默认false
spring.cloud.nacos.config.extension-configs[0].refresh=true

#REDIS
spring.cloud.nacos.config.extension-configs[1].data-id=redis_common.properties
spring.cloud.nacos.config.extension-configs[1].group=DEFAULT_GROUP
spring.cloud.nacos.config.extension-configs[1].refresh=true

#CRM
spring.cloud.nacos.config.extension-configs[2].data-id=crm.properties
spring.cloud.nacos.config.extension-configs[2].group=CRM_GROUP
spring.cloud.nacos.config.extension-configs[2].refresh=true

#OA
spring.cloud.nacos.config.extension-configs[3].data-id=oa.properties
spring.cloud.nacos.config.extension-configs[3].group=OA_GROUP
spring.cloud.nacos.config.extension-configs[3].refresh=true
```

完整的bootstrap.properties

```properties
user.name=Tom
user.age=12
# 配置项目名称
spring.application.name=nacos_conf
# nacos访问路径
spring.cloud.nacos.config.server-addr=127.0.0.1:8848
# 单个数据集时 配置组名
#spring.cloud.nacos.config.group=DEFAULT_GROUP
#要读取数据集的ID
#spring.cloud.nacos.config.name=nacos_config_02
#配置命名空间
spring.cloud.nacos.config.namespace=e5c78c07-74ef-45ed-8389-b1ac2d1cd8c0

## 配置多数据集读取
# 数据集ID
spring.cloud.nacos.config.extension-configs[0].data-id=mysql_common.properties
# 数据集所在的组名称
spring.cloud.nacos.config.extension-configs[0].group=DEFAULT_GROUP
# 是否动态刷新 默认false
spring.cloud.nacos.config.extension-configs[0].refresh=true

#REDIS
spring.cloud.nacos.config.extension-configs[1].data-id=redis_common.properties
spring.cloud.nacos.config.extension-configs[1].group=DEFAULT_GROUP
spring.cloud.nacos.config.extension-configs[1].refresh=true

#CRM
spring.cloud.nacos.config.extension-configs[2].data-id=crm.properties
spring.cloud.nacos.config.extension-configs[2].group=CRM_GROUP
spring.cloud.nacos.config.extension-configs[2].refresh=true

#OA
spring.cloud.nacos.config.extension-configs[3].data-id=oa.properties
spring.cloud.nacos.config.extension-configs[3].group=OA_GROUP
spring.cloud.nacos.config.extension-configs[3].refresh=true

```

完整的bootstrap.yml

```yaml
```



controller中测试

```java
package com.et.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope // 实时刷新
@RequestMapping("/user")
public class UserController {

    @Value("${user.name}")
    private String name;
    @Value("${user.age}")
    private int age;

    @Value("${etjava.mysql.common}")
    private String mysqlconfig;



    @Value("${etjava.redis.common}")
    private String redisconfig;

    @Value("${etjava.crm.config}")
    private String crmconfig;
   @Value("${etjava.oa.config}")
    private String oaconfig;

    @RequestMapping("/getUserInfo")
    public String getUserInfo(){
        return name+"--"+age;
    }


    @RequestMapping("/getInfo")
    public String getInfo(){

        return mysqlconfig+"\n"+redisconfig+"\n"+crmconfig+"\n"+oaconfig;
    }
}

```

![image-20231010201813101](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102018099.png)







### 配置中心其他功能

#### 配置的导入导出功能

```
1. 选中要导出的数据集
2. 选择其他命名空间 然后选择导入
	不需要解压缩 直接上传压缩包即可
```

导出

![image-20231010203649269](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102036713.png)



导入

![image-20231010203618299](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102036755.png)

![image-20231010203717200](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102037369.png)

#### 克隆

![image-20231010203752587](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102037780.png)

选择其他的命名空间

![image-20231010203818165](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102038051.png)

开始克隆

![image-20231010203859425](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20231010203859425.png)

#### 历史版本

```
记录每次修改的操作，可对版本进行回滚操作
```

![image-20231010204119689](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102041742.png)

#### 监听查询

```
监听查询是用来监控连接的所有客户端的
当数据集发生变化时会实时通知到客户端
```

![image-20231010204418747](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102044834.png)



## Nacos服务注册与发现

```
多数时候 分布式项目会根据业务 把项目拆分成多个业务模块项目 然后相互调用
如何相互调用？
这里我们每个项目模块都需要取nacos服务注册中心注册下，记录每个项目自身的地址和端口，然后其他项目就可以通过Nacos找到需要调用的其他模块项目地址了，这样就实现了服务的发现与调用

```



![image-20231010204627835](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102046261.png)

### 服务注册

#### 新建order模块

![image-20231010205112348](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102051050.png)

##### 添加依赖

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

##### 创建配置文件

application.yml

```yaml
server:
  port: 8081
  servlet:
    context-path: /
# 服务注册
spring:
  application:
    name: nacos-order
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
```

##### 创建启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 能够让注册中心发现
public class NacosOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosOrderApplication.class,args);
    }
}

```

##### 运行启动类 发布服务

HttpClientBuild类找不到的解决方法

```
使用nacos注册服务时 如果出现类似 org.apache.http.impl.client.HttpClientBuilder异常 是因为nacos 的 注册中心有引入 ribbon 的 一个 httpclient 依赖 我们在使用nacos-discovery时候出现了冲突

解决方案
在父项目中添加如下内容
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.5<</version>
</dependency>
然后在你的子模块项目中添加版本号
<!--服务注册与发现-->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    <version>2.2.6.RELEASE</version>
</dependency>
```

启动运行会发现nacos服务列表中出现了我们注册的服务

![image-20231010211842850](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102118704.png)

#### 新建stock模块

![image-20231010212002197](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102120548.png)

##### 添加依赖

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

##### 添加配置

```yaml
server:
  port: 8082
  servlet:
    context-path: /
# 服务注册
spring:
  application:
    name: nacos-stock
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
```

##### 创建启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 能够让注册中心发现
public class NacosStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosStockApplication.class,args);
    }
}

```

##### 启动测试

![image-20231010213253106](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102132291.png)

### 服务调用

#### nacos-stock模块中添加测试方法

##### 新建controller

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

#### nacos-order模块调整

订单模块需要调用库存模块

##### 订单模块中引入openfeign依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
    <version>1.4.5.RELEASE</version>
</dependency>
```

##### 启动类上开启feign

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient // 能够让注册中心发现
@EnableFeignClients // 开启Feign客户端
public class NacosOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosOrderApplication.class,args);
    }
}

```

##### 编写测试的controller

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

##### 启动测试

启动stock模块 在启动order模块

```
如果启动提示如下错误 是因为jar包依赖的问题导致
Could not initialize class com.fasterxml.jackson.databind.ObjectMapper
需要将子项目中的openfeign添加到父项目中
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-starter-openfeign</artifactId>
<version>2.2.6.RELEASE</version>
</dependency>
```

![image-20231010222527180](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102225054.png)

调用

![image-20231010222637182](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102226268.png)

### 使用service代替dashbord

因为我们需要运行多个服务 逐个的找启动类太麻烦 直接使用service来统一更简洁些

![image-20231010223333052](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102233088.png)

### 服务负载均衡

```
服务的负载均衡首先 服务肯定是多实例的
要求：服务名称必须相同，服务的端口必须不同
只要服务名称相同 就会自动进行负载，默认是轮询的方式
```

#### 案例

创建nacos-stock2服务模块

与nacos-stock服务模块是相同的 只是端口不同

##### 创建模块

![image-20231010223939304](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102239343.png)

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

#### 创建配置文件

```yaml
server:
  port: 8083
  servlet:
    context-path: /
# 服务注册
spring:
  application:
    name: nacos-stock
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
```

#### 创建启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 能够让注册中心发现
public class NacosStockApplication2 {

    public static void main(String[] args) {
        SpringApplication.run(NacosStockApplication2.class,args);
    }
}

```

#### 创建测试controller

```java
package com.et.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    @RequestMapping("/info")
    public String test(String info){
        return "库存模块接收到的消息2 "+info;
    }
}

```

#### 测试负载

启动两个stock 然后启动order 最后通过order中的方法调用服务

![image-20231010225824364](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102258530.png)

![image-20231010225845349](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102258263.png)

![image-20231010225917163](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102259287.png)



查看服务的订阅者

![image-20231010225948417](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102259508.png)

点击服务详情可以对服务进行编辑

![image-20231010230036761](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102300672.png)

### 服务领域模型

![image-20231010231901409](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102319089.png)

```
自定义服务发布到Nacos后的集群名称
```

application.yml中配置服务集群的名称

stock两个服务除端口号外 其余配置完全相同

```yaml
server:
  port: 8083
  servlet:
    context-path: /
# 服务注册
spring:
  application:
    name: nacos-stock
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        # 服务的命名空间
        namespace: e5c78c07-74ef-45ed-8389-b1ac2d1cd8c0
        cluster-name: ETJAVA-DEV # 自定义服务发布到Nacos后的集群名称
```

![image-20231010231314293](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102313479.png)

#### 调用服务order模块修改配置

添加服务的命名空间 如果不在一个空间内的服务是无法调通的

```yaml
server:
  port: 8081
  servlet:
    context-path: /
# 服务注册
spring:
  application:
    name: nacos-order
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        namespace: e5c78c07-74ef-45ed-8389-b1ac2d1cd8c0 # 指定服务存放的命名空间 默认为public命名空间
```

启动测试

stock,order

![image-20231010231805615](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310102318260.png)



## Nacos高可用集群

```
为了防止一个nacos宕机 可以配置nacos集群模式 实现高可用
配置nacos的集群 需要修改如下内容
以下以三台nacos为例
1. startup.cmd 
	set MODE="standalone" 改为 set MODE="cluster"
2. application.properties文件中修改端口
	复制三份nacos 然后每个application.properties文件中的段都都需要调整
	例如 分别为 8848，8849，8850
		server.port=8848
[3] 数据库集群  - 可选
    ### Count of DB:
    db.num=2
    ### Connect URL of DB:
    db.url.0=jdbc:mysql://127.0.0.1:3306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
    db.user.0=root
    db.password.0=Karen@1234
    
     ### Connect URL of DB:
    db.url.1=jdbc:mysql://192.168.199.106:3306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
    db.user.1=root
    db.password.1=Karen@1234
    
4. 修改访问地址
	本地测试 可以使用127.0.0.1
	nacos.inetutils.ip-address=127.0.0.1
5. 将conf目录下cluster.conf.example改为cluster.conf并添加集群时的访问IP及端口
	127.0.0.1:8840
	127.0.0.1:8846
	127.0.0.1:8852
6. 启动三台nacos并测试
```

![image-20231011185059402](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310111855723.png)

### 集群配置错误解决

```
在配置集群模式时 如果是在同一台机器上进行配置 启动发现端口并未改变（还是8848）最简单的方式就是直接修改target目录下的nacos-server.jar包中的application.properties文件
或尝试官方给出的解决方案
1.修改application.properties配置文件， application.properties: server.port=8848 为 8849 ；nacos.core.auth.enabled=true；nacos.core.auth.server.identity.key=afa4sj；nacos.core.auth.server.identity.value=afa4sj；

2.启动服务，查看端口是否为8849

3.登录nacos管理端，测试鉴权功能

```

### 发布服务

发布和调用服务时 server-addr修改为集群模式 多个地址使用逗号隔开

```yaml
server:
  port: 8083
  servlet:
    context-path: /
# 服务注册
spring:
  application:
    name: nacos-stock
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8840,127.0.0.1:8846,127.0.0.1:8842
        # 自定义服务发布到Nacos后的集群名称
        namespace: e5c78c07-74ef-45ed-8389-b1ac2d1cd8c0
        cluster-name: ETJAVA-DEV
```

![image-20231011190500443](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310111905719.png)



### 调用服务

order模块调用服务

配置文件

```yaml
server:
  port: 8081
  servlet:
    context-path: /
# 服务注册
spring:
  application:
    name: nacos-order
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8840,127.0.0.1:8846,127.0.0.1:8842
        namespace: e5c78c07-74ef-45ed-8389-b1ac2d1cd8c0 # 指定服务存放的命名空间 默认为public命名空间
```

启动并测试

![image-20231011191456710](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310111914023.png)

测试两个down机的情况下是否可以正常调用服务

![image-20231011192245540](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310111922672.png)

![image-20231011192256255](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310111922312.png)
