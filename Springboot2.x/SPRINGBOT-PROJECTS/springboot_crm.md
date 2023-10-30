# CRM客户关系管理系统

## 简述

```
客户关系管理系统，用于方便管理客户与公司或销售之间的关系管理

技术栈：JDK1.8、Springboot+Mybatis+mysql

```

## 新建项目

![image-20230929190904476](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291909660.png)

我们这里只添加web 剩余的手动添加

![image-20230929190942973](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291909331.png)

## 手动添加依赖

由于我们采用jsp作为前端技术 这里需要添加jsp、servlet、jstl相关的依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.16</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.et</groupId>
    <artifactId>crm</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>crm</name>
    <description>crm</description>
    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <spring-boot.version>2.6.13</spring-boot.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
        </dependency>

        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.2.2</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.21</version>
        </dependency>


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
            <version>2.6.1</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>

        <dependency>
            <groupId>net.sf.json-lib</groupId>
            <artifactId>json-lib</artifactId>
            <version>2.4</version>
            <classifier>JDK15</classifier>
        </dependency>
        <dependency>
            <groupId>commons-lang</groupId>
            <artifactId>commons-lang</artifactId>
            <version>2.6</version>
        </dependency>

        <dependency>
            <groupId>net.sf.ezmorph</groupId>
            <artifactId>ezmorph</artifactId>
            <version>1.0.6</version>
        </dependency>
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
        <dependency>
            <groupId>com.squareup.okhttp3</groupId>
            <artifactId>okhttp</artifactId>
        </dependency>

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
    </dependencies>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring-boot.version}</version>
                <configuration>
                    <mainClass>com.et.App</mainClass>
                    <skip>true</skip>
                </configuration>
                <executions>
                    <execution>
                        <id>repackage</id>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>

```

## 添加jsp支持

springboot项目默认是没有webapp目录的 我们需要手动添加并关联

1 src下级目录新建webapp及webapp/WEB-INF目录

![image-20230929204238966](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230929204238966.png)

2 file-> project Structure -> Modules 选中webapp

![image-20230929192758484](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291927742.png)

## 创建包结构

![image-20230929204329015](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230929204329015.png)

## 添加springboot配置文件

```xml
server:
  port: 80
# datasource
spring:
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
    view:
      suffix: .jsp
      prefix: /WEB-INF/views/
  datasource:
    druid:
      driver-class-name: com.mysql.jdbc.Driver
      url: jdbc:mysql://localhost:3306/db_crm?useUnicode=true&characterEncoding=utf-8
      username: root
      password: Karen@1234


#mybatis
mybatis:
  #mapper映射文件的位置，指向的是resources目录下的
  mapper-locations: classpath:/mapper/*.xml
  # 开启驼峰命名
  map-underscore-to-camel-case: true
  # 别名
  type-aliases-package: com.et.entity
# logging
logging:
  level:
    com:
      et: debug


```

## 数据库设计

### 用户表

t_user

```sql
CREATE TABLE `t_user` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `userName` varchar(20) DEFAULT NULL,
   `password` varchar(20) DEFAULT NULL,
   `trueName` varchar(20) DEFAULT NULL,
   `email` varchar(30) DEFAULT NULL,
   `phone` varchar(20) DEFAULT NULL,
   `roleName` varchar(20) DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8
```

### 产品表

t_product

```sql
CREATE TABLE `t_product` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `productName` varchar(300) DEFAULT NULL,-- 产品名称
   `model` varchar(150) DEFAULT NULL,-- 型号
   `unit` varchar(60) DEFAULT NULL,-- 单位
   `price` float DEFAULT NULL,-- 单价
   `store` double DEFAULT NULL,-- 库存
   `remark` varchar(3000) DEFAULT NULL,-- 描述
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8
```

### 字典表

t_datadic

```sql
CREATE TABLE `t_datadic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dataDicName` varchar(50) DEFAULT NULL,
  `dataDicValue` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_t_datadic` (`dataDicValue`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `t_datadic` */

insert  into `t_datadic`(`id`,`dataDicName`,`dataDicValue`) values (1,'客户等级','普通客户'),(2,'客户等级','重点开发客户'),(3,'客户等级','大客户'),(4,'客户等级','合作伙伴'),(5,'客户等级','战略合作伙伴'),(6,'服务类型','咨询'),(7,'服务类型','建议'),(8,'服务类型','投诉');
```

### 销售机会表

t_sale_chance

```sql
CREATE TABLE `t_sale_chance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chanceSource` varchar(300) DEFAULT NULL,-- 销售机会来源
  `customerName` varchar(100) DEFAULT NULL,-- 客户名称
  `cgjl` int(11) DEFAULT NULL,-- 成功几率
  `overview` varchar(300) DEFAULT NULL,-- 概要
  `linkMan` varchar(100) DEFAULT NULL,-- 联系人
  `linkPhone` varchar(100) DEFAULT NULL,-- 联系事件
  `description` varchar(1000) DEFAULT NULL,-- 描述
  `createMan` varchar(100) DEFAULT NULL,-- 创建人
  `createTime` datetime DEFAULT NULL,-- 创建时间
  `assignMan` varchar(100) DEFAULT NULL,-- 分配人
  `assignTime` datetime DEFAULT NULL,-- 分配时间
  `state` int(11) DEFAULT NULL,-- 分配状态
  `devResult` int(11) DEFAULT NULL,-- 开发状态
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `t_sale_chance` */

insert  into `t_sale_chance`(`id`,`chanceSource`,`customerName`,`cgjl`,`overview`,`linkMan`,`linkPhone`,`description`,`createMan`,`createTime`,`assignMan`,`assignTime`,`state`,`devResult`) values (1,'主动来找的','风软科技',100,'采购笔记本意向','张先生','137234576543','。。。','Jack','2023-01-01 00:00:00','3','2023-05-24 16:15:00',1,2),(2,'','1',12,'','','','','12',NULL,'3','2023-05-25 11:21:00',1,1),(3,NULL,'7',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0);
```

### 客户开发计划表

t_cus_dev_plan

```sql
CREATE TABLE `t_cus_dev_plan` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `saleChanceId` int(11) DEFAULT NULL,
   `planItem` varchar(100) DEFAULT NULL,
   `planDate` date DEFAULT NULL,
   `exeAffect` varchar(100) DEFAULT NULL,
   PRIMARY KEY (`id`),
   KEY `FK_t_cus_dev_plan` (`saleChanceId`),
   CONSTRAINT `FK_t_cus_dev_plan` FOREIGN KEY (`saleChanceId`) REFERENCES `t_sale_chance` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8

insert  into `t_cus_dev_plan`(`id`,`saleChanceId`,`planItem`,`planDate`,`exeAffect`) values (1,1,'测试计划项','2023-01-01','好'),(4,1,'haha','2023-05-20','en啊'),(5,1,'ss','2023-05-13',''),(6,16,'2121','2023-05-28',''),(7,16,'21121','2023-05-19',''),(8,19,'21','2023-05-28',''),(9,2,'1','2023-05-27','2'),(10,2,'2','2023-05-28',''),(11,21,'好','2023-06-09','额'),(12,22,'联系客户，介绍产品','2023-06-01','有点效果'),(13,22,'请客户吃饭，洽谈','2023-06-07','成功了'),(14,15,'洽谈','2023-06-09','可以');
```

### 客户表

t_customer

```sql
CREATE TABLE `t_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `khno` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `cusManager` varchar(20) DEFAULT NULL,
  `level` varchar(30) DEFAULT NULL,
  `myd` varchar(30) DEFAULT NULL,
  `xyd` varchar(30) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `postCode` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `webSite` varchar(20) DEFAULT NULL,
  `yyzzzch` varchar(50) DEFAULT NULL,
  `fr` varchar(20) DEFAULT NULL,
  `zczj` varchar(20) DEFAULT NULL,
  `nyye` varchar(20) DEFAULT NULL,
  `khyh` varchar(50) DEFAULT NULL,
  `khzh` varchar(50) DEFAULT NULL,
  `dsdjh` varchar(50) DEFAULT NULL,
  `gsdjh` varchar(50) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `t_customer` */

insert  into `t_customer`(`id`,`khno`,`name`,`area`,`cusManager`,`level`,`myd`,`xyd`,`address`,`postCode`,`phone`,`fax`,`webSite`,`yyzzzch`,`fr`,`zczj`,`nyye`,`khyh`,`khzh`,`dsdjh`,`gsdjh`,`state`) values (1,'KH21321321','北京大牛科技','北京','小张','战略合作伙伴','☆☆☆','☆☆☆','北京海淀区双榆树东里15号','100027','010-62263393','010-62263393','www.daniu.com','420103000057404','张三','1000','5000','中国银行 ','6225231243641','4422214321321','4104322332',0),(16,'KH20150526073022','风驰科技','北京','小红','大客户','☆','☆','321','21','321','321','321','321','321','','21','321','321','321','3213',0),(17,'KH20150526073023','巨人科技',NULL,'小丽','普通客户',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(18,'KH20150526073024','新人科技',NULL,NULL,'重点开发客户',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(19,'KH20150526073025','好人集团',NULL,NULL,'合作伙伴',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,'KH20150526073026','新浪',NULL,NULL,'大客户',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(21,'KH20150526073027','百度',NULL,NULL,'大客户',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
```

### 客户联系人表

t_customer_linkman

```sql
CREATE TABLE `t_customer_linkman` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cusId` int(11) DEFAULT NULL,
  `linkName` varchar(20) DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL,
  `zhiwei` varchar(50) DEFAULT NULL,
  `officePhone` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `t_customer_linkman` */

insert  into `t_customer_linkman`(`id`,`cusId`,`linkName`,`sex`,`zhiwei`,`officePhone`,`phone`) values (1,1,'1','男','321','321','21321'),(2,1,'2','女','21','321','321'),(4,1,'3','女','4','5','6'),(5,1,'33','男','44','55','66'),(6,1,'张三','男','经理','21321','32132121'),(7,1,'是','女','发送','2321','321321');

```

### 客户交往记录表

```sql
CREATE TABLE `t_customer_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cusId` int(11) DEFAULT NULL,
  `contactTime` date DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `overview` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_customer_contact` */

insert  into `t_customer_contact`(`id`,`cusId`,`contactTime`,`address`,`overview`) values (1,1,'2023-05-14','1','2'),(2,1,'2023-05-06','1','2');

```

### 客户订单表

```sql
CREATE TABLE `t_customer_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cusId` int(11) DEFAULT NULL,
  `orderNo` varchar(40) DEFAULT NULL,
  `orderDate` date DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `t_customer_order` */

insert  into `t_customer_order`(`id`,`cusId`,`orderNo`,`orderDate`,`address`,`state`) values (1,1,'DD11213','2023-02-01','11',1),(2,16,'DD11212','2023-01-02','22',1),(3,16,'DD21321','2023-02-02','22',1),(4,17,'DD2121','2022-02-03','ss',1);

```

### 订单明细表

```sql
CREATE TABLE `t_order_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) DEFAULT NULL,
  `goodsName` varchar(100) DEFAULT NULL,
  `goodsNum` int(11) DEFAULT NULL,
  `unit` varchar(20) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `sum` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `t_order_details` */

insert  into `t_order_details`(`id`,`orderId`,`goodsName`,`goodsNum`,`unit`,`price`,`sum`) values (1,1,'联想笔记本',2,'台',4900,9800),(2,1,'惠普音响',4,'台',200,800),(3,2,'罗技键盘',10,'个',90,900),(4,3,'艾利鼠标',20,'个',20,400),(5,3,'东芝U盘',5,'个',105,525),(6,4,'充电器',1,'个',30,30);

```

### 客户流失表

```sql
CREATE TABLE `t_customer_loss` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cusNo` varchar(40) DEFAULT NULL,
  `cusName` varchar(20) DEFAULT NULL,
  `cusManager` varchar(20) DEFAULT NULL,
  `lastOrderTime` date DEFAULT NULL,
  `confirmLossTime` date DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `lossreason` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `t_customer_loss` */

insert  into `t_customer_loss`(`id`,`cusNo`,`cusName`,`cusManager`,`lastOrderTime`,`confirmLossTime`,`state`,`lossreason`) values (1,'1','大风科技','小张','2023-02-01','2023-05-01',1,'11'),(3,'KH20150526073022','鸟人科技','小红','2023-02-02','2023-05-01',1,'公司迁地址'),(4,'KH20150526073023','321','小丽','2023-02-03',NULL,1,NULL);

```

### 暂缓流失措施表

`t_customer_reprieve` 

```sql
CREATE TABLE `t_customer_reprieve` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lossId` int(11) DEFAULT NULL,-- 暂缓流失ID
  `measure` varchar(500) DEFAULT NULL,-- 具体操作
  PRIMARY KEY (`id`)
) 
```

### 客户服务表

t_customer_service

```sql
CREATE TABLE `t_customer_service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serveType` varchar(30) DEFAULT NULL,
  `overview` varchar(500) DEFAULT NULL,
  `customer` varchar(30) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `servicerequest` varchar(500) DEFAULT NULL,
  `createPeople` varchar(100) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `assigner` varchar(100) DEFAULT NULL,
  `assignTime` datetime DEFAULT NULL,
  `serviceProce` varchar(500) DEFAULT NULL,
  `serviceProcePeople` varchar(20) DEFAULT NULL,
  `serviceProceTime` datetime DEFAULT NULL,
  `serviceProceResult` varchar(500) DEFAULT NULL,
  `myd` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `t_customer_service` */

insert  into `t_customer_service`(`id`,`serveType`,`overview`,`customer`,`state`,`servicerequest`,`createPeople`,`createTime`,`assigner`,`assignTime`,`serviceProce`,`serviceProcePeople`,`serviceProceTime`,`serviceProceResult`,`myd`) values (1,'咨询','咨询下Think pad价格','大浪技术','已归档','。。。测试','Jack','2023-06-03 00:00:00','小红','2023-06-03 00:00:00','s','Jack','2023-06-04 00:00:00','OK','☆☆☆☆'),(2,'咨询','321','1','已归档','321','Jack','2023-06-03 00:00:00',NULL,NULL,'sss','Jack','2023-06-04 00:00:00','OK','☆☆☆'),(3,'咨询','21','21','已归档','1','Jack','2023-06-03 00:00:00','小红','2023-06-03 00:00:00','sds','Jack','2023-06-04 00:00:00','OK','☆☆☆☆'),(6,'咨询','321','21','已归档','321','Jack','2023-06-03 00:00:00','小红','2023-06-04 00:00:00','ds','Jack','2023-06-04 00:00:00','OK','☆☆☆'),(7,'咨询','s','222','已归档','ss','Jack','2023-06-04 00:00:00','小明','2023-06-04 00:00:00','ss','Jack','2023-06-04 00:00:00','OK','☆☆'),(8,'建议','4','3','已处理','5','Jack','2023-06-04 00:00:00','小张','2023-06-04 00:00:00','111','Jack','2023-06-04 00:00:00',NULL,NULL),(9,'投诉','2','1','已归档','3','Jack','2023-06-04 00:00:00','小明','2023-06-04 00:00:00','333','Jack','2023-06-04 00:00:00','OK','☆☆☆☆☆'),(10,'建议','32','32','新创建','32','Jack','2023-06-04 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,'建议','21','21','已归档','21','Jack','2023-06-04 00:00:00','小明','2023-06-11 00:00:00','fds','Jack','2023-06-11 00:00:00','d','☆☆☆'),(12,'建议','fda','大牛科技','已归档','fda','Jack','2023-06-10 00:00:00','小红','2023-06-10 00:00:00','fda','Jack','2023-06-10 00:00:00','good','☆☆☆☆☆'),(13,'咨询','咨询下Think pad价格。。','大众科技','已归档','发达龙卷风大。。。。','Jack','2023-06-11 00:00:00','小红','2023-06-11 00:00:00','。。。\r\n1，2\r\n，3。。。','Jack','2023-06-11 00:00:00','OK','☆☆☆☆☆'),(14,'咨询','11','sss','新创建','22','Jack','2023-08-24 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL);

```



## 用户登录功能

### 创建User实体类

```java
package com.et.entity;

/**
 * 用户实体
 * @author Administrator
 *
 */
public class User {

	private Integer id; // 编号
	private String userName; // 用户名
	private String password; // 密码
	private String trueName; // 真实姓名
	private String email; // 邮件
	private String phone; // 联系电话
	private String roleName; // 角色名称 系统管理员 销售主管 客户经理 高管

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


}

```

### 创建DAO层

```java
package com.et.dao;

import com.et.entity.User;

/**
 * 用户Dao接口
 * @author Administrator
 *
 */
@Mapper
public interface UserDao {

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public User login(User user);
}

```

### 创建User的映射文件

UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.et.dao.UserDao">

	<resultMap type="User" id="UserResult">
		<result property="id" column="id"/>
		<result property="userName" column="userName"/>
		<result property="password" column="password"/>
		<result property="trueName" column="trueName"/>
		<result property="email" column="email"/>
		<result property="phone" column="phone"/>
		<result property="roleName" column="roleName"/>
	</resultMap>

	<select id="login" parameterType="User" resultMap="UserResult">
		select * from t_user where userName=#{userName} and password=#{password} and roleName=#{roleName}
	</select>
</mapper>
```

### 添加日志记录文件

log4j.properties

```prop
##配置文件log4j.properties
### 设置###
log4j.rootLogger=debug,stdout,D,E
### 输出信息到控制抬 ###
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=[%-5p] %d{yyyy-MM-dd HH:mm:ss,SSS} method:%l%n%m%n
### 输出DEBUG 级别以上的日志到=E://Java//LogTest2//logs/error.log ###
log4j.appender.D=org.apache.log4j.DailyRollingFileAppender
log4j.appender.D.File=D:/crm.log
log4j.appender.D.Append=true
log4j.appender.D.Threshold=DEBUG
log4j.appender.D.layout=org.apache.log4j.PatternLayout
log4j.appender.D.layout.ConversionPattern=%-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
### 输出ERROR 级别以上的日志到=E://Java//LogTest2//logs/error.log ###
log4j.appender.E=org.apache.log4j.DailyRollingFileAppender
log4j.appender.E.File=D:/crm.log
log4j.appender.E.Append=true
log4j.appender.E.Threshold=ERROR
log4j.appender.E.layout=org.apache.log4j.PatternLayout
log4j.appender.E.layout.ConversionPattern=%-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
```



### 用户Service接口

```java
package com.et.service;

import com.et.entity.User;

/**
 * 用户Service接口
 * @author Administrator
 *
 */
public interface UserService {

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public User login(User user);
}

```

### 用户Service实现类

```java
package com.et.service.impl;

import com.et.dao.UserDao;
import com.et.entity.User;
import com.et.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户Service实现类
 * @author Administrator
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;

	@Override
	public User login(User user) {
		return userDao.login(user);
	}
}

```



### static目录下添加需要用到的静态资源

引入Jquery库、页面用到的图片等

![image-20230929193652176](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291936248.png)

### 新建登录页面

WEB-INF/views/login.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>客户关系管理系统登录</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
    <STYLE type=text/css>
        BODY {
            TEXT-ALIGN: center;
            PADDING-BOTTOM: 0px;
            BACKGROUND-COLOR: #ddeef2;
            MARGIN: 0px;
            PADDING-LEFT: 0px;
            PADDING-RIGHT: 0px;
            PADDING-TOP: 0px
        }

        A:link {
            COLOR: #000000;
            TEXT-DECORATION: none
        }

        A:visited {
            COLOR: #000000;
            TEXT-DECORATION: none
        }

        A:hover {
            COLOR: #ff0000;
            TEXT-DECORATION: underline
        }

        A:active {
            TEXT-DECORATION: none
        }

        .input {
            BORDER-BOTTOM: #ccc 1px solid;
            BORDER-LEFT: #ccc 1px solid;
            LINE-HEIGHT: 20px;
            WIDTH: 182px;
            HEIGHT: 20px;
            BORDER-TOP: #ccc 1px solid;
            BORDER-RIGHT: #ccc 1px solid
        }

        .input1 {
            BORDER-BOTTOM: #ccc 1px solid;
            BORDER-LEFT: #ccc 1px solid;
            LINE-HEIGHT: 20px;
            WIDTH: 120px;
            HEIGHT: 20px;
            BORDER-TOP: #ccc 1px solid;
            BORDER-RIGHT: #ccc 1px solid;
        }
    </STYLE>
    <script type="text/javascript">
        function login(){
            var userName=$("#userName").val();
            var password=$("#password").val();
            var roleName=$("#roleName").val();
            if(userName==null||userName==""){
                alert("用户名不能为空！");
                return;
            }
            if(password==null||password==""){
                alert("密码不能为空！");
                return;
            }
            if(roleName==null||roleName==""){
                alert("请选择用户类型！");
                return;
            }
            $("#adminlogin").submit();

        }
    </script>
</head>
<body>
<FORM id=adminlogin  method=post
      name=adminlogin action="${pageContext.request.contextPath}/user/login"  >
    <DIV></DIV>
    <TABLE style="MARGIN: auto; WIDTH: 100%; HEIGHT: 100%" border=0
           cellSpacing=0 cellPadding=0>
        <TBODY>
        <TR>
            <TD height=150>&nbsp;</TD>
        </TR>
        <TR style="HEIGHT: 254px">
            <TD>
                <DIV style="MARGIN: 0px auto; WIDTH: 936px"><IMG
                        style="DISPLAY: block" src="${pageContext.request.contextPath}/images/body_03.jpg"></DIV>
                <DIV style="BACKGROUND-COLOR: #278296">
                    <DIV style="MARGIN: 0px auto; WIDTH: 936px">
                        <DIV
                                style="BACKGROUND: url(${pageContext.request.contextPath}/images/body_05.jpg) no-repeat; HEIGHT: 155px">
                            <DIV
                                    style="TEXT-ALIGN: left; WIDTH: 265px; FLOAT: right; HEIGHT: 125px; _height: 95px">
                                <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
                                    <TBODY>
                                    <TR>
                                        <TD style="HEIGHT: 45px"><INPUT type="text" class=input value="${user.userName }" name="userName" id="userName"></TD>
                                    </TR>
                                    <TR>
                                        <TD><INPUT type="password" class=input value="${user.password }" name="password" id="password"/></TD>
                                    </TR>
                                    <TR>
                                        <td>
                                            <select id="roleName" name="roleName" class="input" style="margin-top: 15px;height: 24px">
                                                <option value="">请选择用户类型...</option>
                                                <option value="系统管理员"  ${'系统管理员'==user.roleName?'selected':'' }>系统管理员</option>
                                                <option value="销售主管"  ${'销售主管'==user.roleName?'selected':'' }>销售主管</option>
                                                <option value="客户经理"  ${'客户经理'==user.roleName?'selected':'' }>客户经理</option>
                                                <option value="高管"  ${'高管'==user.roleName?'selected':'' }>高管</option>
                                            </select>
                                        </td>
                                    </TR>
                                    </TBODY>
                                </TABLE>
                            </DIV>
                            <DIV style="HEIGHT: 1px; CLEAR: both"></DIV>
                            <DIV style="WIDTH: 380px; FLOAT: right; CLEAR: both">
                                <TABLE border=0 cellSpacing=0 cellPadding=0 width=300>
                                    <TBODY>

                                    <TR>
                                        <TD width=100 align=right><INPUT
                                                style="BORDER-RIGHT-WIDTH: 0px; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px"
                                                id=btnLogin src="${pageContext.request.contextPath}/images/btn1.jpg" type=image name=btnLogin onclick="javascript:login();return false;"></TD>
                                        <TD width=100 align=middle><INPUT
                                                style="BORDER-RIGHT-WIDTH: 0px; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px"
                                                id=btnReset src="${pageContext.request.contextPath}/images/btn2.jpg" type=image name=btnReset onclick="javascript:adminlogin.reset();return false;"></TD>
                                    </TR>
                                    </TBODY>
                                </TABLE>
                            </DIV>
                        </DIV>
                    </DIV>
                </DIV>
                <DIV style="MARGIN: 0px auto; WIDTH: 936px"><IMG
                        src="${pageContext.request.contextPath}/images/body_06.jpg"></DIV>
            </TD>
        </TR>
        <TR style="HEIGHT: 30%">
            <TD>&nbsp;</TD>
        </TR>
        </TBODY>
    </TABLE>
</FORM>
</body>
</html>
<script type=text/javascript>
    if('${errorMsg}'!=''){
        alert('${errorMsg}');
    }
</script>
```

### 新建LoginController

```java
package com.et.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String hello(Model model) {
        return "login";
    }
}

```

![image-20230929204651439](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292056782.png)

### 登录功能完善

#### 新建UserController

```java
package com.et.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.et.entity.User;
import com.et.service.UserService;

/**
 * 用户Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	/**
	 * 用户登录
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String login(User user,HttpServletRequest request)throws Exception{
		User resultUser=userService.login(user);
		if(resultUser==null){
			request.setAttribute("user", user);
			request.setAttribute("errorMsg", "用户名或密码错误！");
			return "login";
		}else{
			HttpSession session=request.getSession();
			session.setAttribute("currentUser", resultUser);
			return "main";
		}
	}
}
```

![image-20230829221420558](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292101580.png)

### 新建系统主页面

main.jsp

系统主页面采用easyui作为主体框架 需要新增自定义图标（thems/usericons）及设定主页logo

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%--<%
    if(session.getAttribute("currentUser")==null){
        response.sendRedirect("login.jsp");
        return;
    }
%>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">

    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">

        var url;

        function openTab(text,url,iconCls){
            if($("#tabs").tabs("exists",text)){
                $("#tabs").tabs("select",text);
            }else{
                var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${pageContext.request.contextPath}/page/"+url+"'></iframe>";
                $("#tabs").tabs("add",{
                    title:text,
                    iconCls:iconCls,
                    closable:true,
                    content:content
                });
            }
        }

        function openPasswordModifyDialog(){
            $("#dlg").dialog("open").dialog("setTitle","修改密码");
            url="${pageContext.request.contextPath}/user/modifyPassword?id=${currentUser.id}";
        }

        function modifyPassword(){
            $("#fm").form("submit",{
                url:url,
                onSubmit:function(){
                    var oldPassword=$("#oldPassword").val();
                    var newPassword=$("#newPassword").val();
                    var newPassword2=$("#newPassword2").val();
                    if(!$(this).form("validate")){
                        return false;
                    }
                    if(oldPassword!='${currentUser.password}'){
                        $.messager.alert("系统提示","用户原密码输入错误！");
                        return false;
                    }
                    if(newPassword!=newPassword2){
                        $.messager.alert("系统提示","确认密码输入错误！");
                        return false;
                    }
                    return true;
                },
                success:function(result){
                    var result=eval('('+result+')');
                    if(result.success){
                        $.messager.alert("系统提示","密码修改成功，下一次登录生效！");
                        resetValue();
                        $("#dlg").dialog("close");
                    }else{
                        $.messager.alert("系统提示","密码修改失败！");
                        return;
                    }
                }
            });
        }

        function closePasswordModifyDialog(){
            resetValue();
            $("#dlg").dialog("close");
        }

        function resetValue(){
            $("#oldPassword").val("");
            $("#newPassword").val("");
            $("#newPassword2").val("");
        }

        function logout(){
            $.messager.confirm("系统提示","您确定要退出系统吗？",function(r){
                if(r){
                    window.location.href='${pageContext.request.contextPath}/user/logout';
                }
            });
        }
    </script>
</head>
<body class="easyui-layout">
<div region="north" style="height: 78px;background-color: #E0ECFF">
    <table style="padding: 5px" width="100%">
        <tr>
            <td width="50%">
                <img alt="logo" src="${pageContext.request.contextPath}/images/bglogo.png">
            </td>
            <td valign="bottom" align="right" width="50%">
                <font size="3">&nbsp;&nbsp;<strong>欢迎：</strong>${currentUser.userName }</font>【${currentUser.trueName }】【${currentUser.roleName }】
            </td>
        </tr>
    </table>
</div>
<div region="center">
    <div class="easyui-tabs" fit="true" border="false" id="tabs">
        <div title="首页" data-options="iconCls:'icon-home'">
            <div align="center" style="padding-top: 100px"><font color="red" size="10">欢迎使用</font></div>
        </div>
    </div>
</div>
<div region="west" style="width: 200px" title="导航菜单" split="true">
    <div class="easyui-accordion" data-options="fit:true,border:false">
        <div title="营销管理" data-options="selected:true,iconCls:'icon-yxgl'" style="padding: 10px">
            <a href="javascript:openTab('营销机会管理','saleChanceManage.jsp','icon-yxjhgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-yxjhgl'" style="width: 150px">营销机会管理</a>
            <a href="javascript:openTab('客户开发计划','cusdevplanManage.jsp','icon-khkfjh')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-khkfjh'" style="width: 150px">客户开发计划</a>
        </div>
        <div title="客户管理"  data-options="iconCls:'icon-khgl'" style="padding:10px;">
            <a href="javascript:openTab('客户信息管理','customerManage.jsp','icon-khxxgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-khxxgl'" style="width: 150px;">客户信息管理</a>
            <a href="javascript:openTab('客户流失管理','customerLossManage.jsp','icon-khlsgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-khlsgl'" style="width: 150px;">客户流失管理</a>
        </div>
        <div title="服务管理" data-options="iconCls:'icon-fwgl'" style="padding:10px">
            <a href="javascript:openTab('服务创建','customerServiceCreate.jsp','icon-fwcj')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-fwcj'" style="width: 150px;">服务创建</a>
            <a href="javascript:openTab('服务分配','customerServiceAssign.jsp','icon-fwfp')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-fwfp'" style="width: 150px;">服务分配</a>
            <a href="javascript:openTab('服务处理','customerServiceProce.jsp','icon-fwcl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-fwcl'" style="width: 150px;">服务处理</a>
            <a href="javascript:openTab('服务反馈','customerServiceFeedback.jsp','icon-fwfk')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-fwfk'" style="width: 150px;">服务反馈</a>
            <a href="javascript:openTab('服务归档','customerServiceFile.jsp','icon-fwgd')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-fwgd'" style="width: 150px;">服务归档</a>
        </div>
        <div title="统计报表"  data-options="iconCls:'icon-tjbb'" style="padding:10px">
            <a href="javascript:openTab('客户贡献分析','khgxfx.jsp','icon-khgxfx')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-khgxfx'" style="width: 150px;">客户贡献分析</a>
            <a href="javascript:openTab('客户构成分析','khgcfx.jsp','icon-khgcfx')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-khgcfx'" style="width: 150px;">客户构成分析</a>
            <a href="javascript:openTab('客户服务分析','khfwfx.jsp','icon-khfwfx')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-khfwfx'" style="width: 150px;">客户服务分析</a>
            <a href="javascript:openTab('客户流失分析','khlsfx.jsp','icon-khlsfx')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-khlsfx'" style="width: 150px;">客户流失分析</a>
        </div>
        <div title="基础数据管理"  data-options="iconCls:'icon-jcsjgl'" style="padding:10px">
            <a href="javascript:openTab('数据字典管理','dataDicManage.jsp','icon-sjzdgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-sjzdgl'" style="width: 150px;">数据字典管理</a>
            <a href="javascript:openTab('产品信息查询','productSearch.jsp','icon-cpxxgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cpxxgl'" style="width: 150px;">产品信息查询</a>
            <a href="javascript:openTab('用户信息管理','userManage.jsp','icon-user')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-user'" style="width: 150px;">用户信息管理</a>
        </div>
        <div title="系统管理"  data-options="iconCls:'icon-item'" style="padding:10px">
            <a href="javascript:openPasswordModifyDialog()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-modifyPassword'" style="width: 150px;">修改密码</a>
            <a href="javascript:logout()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-exit'" style="width: 150px;">安全退出</a>
        </div>
    </div>
</div>
<div region="south" style="height: 25px;padding: 5px" align="center">
    版本所有
</div>

<div id="dlg" class="easyui-dialog" style="width:400px;height:250px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">

    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>用户名：</td>
                <td><input type="text" id="userName" name="userName" readonly="readonly" value="${currentUser.userName }" style="width: 200px"/></td>
            </tr>
            <tr>
                <td>原密码：</td>
                <td><input type="password" id="oldPassword" name="oldPassword" class="easyui-validatebox" required="true" style="width: 200px"/></td>
            </tr>
            <tr>
                <td>新密码：</td>
                <td><input type="password" id="newPassword" name="newPassword" class="easyui-validatebox" required="true" style="width: 200px"/></td>
            </tr>
            <tr>
                <td>确认新密码：</td>
                <td><input type="password" id="newPassword2" name="newPassword2" class="easyui-validatebox" required="true" style="width: 200px"/></td>
            </tr>
        </table>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:modifyPassword()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closePasswordModifyDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

</body>
</html>
```

登录测试

![image-20230929210103129](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292101355.png)

## 基础数据管理

### 工具类封装

#### PageBean分页工具类

```java
package com.et.util;

/**
 * 分页Model类
 * @author 
 *
 */
public class PageBean {

	private int page; // 第几页
	private int pageSize; // 每页记录数
	private int start;  // 起始页
	
	
	public PageBean(int page, int pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		// 与mysql分页相关的计算
		// 例如 第一页 每页10条数据
		// limit 0,10
		return (page-1)*pageSize;
	}
	
	
}

```

#### ResponseUtil

向前端发送json数据

```java
package com.et.util;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


public class ResponseUtil {

	public static void write(HttpServletResponse response,Object o)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(o.toString());
		out.flush();
		out.close();
	}
}

```

#### StringUtil

```java
package com.et.util;

/**
 * 字符串工具类
 * @author 
 *
 */
public class StringUtil {

	/**
	 * 判断是否是空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null||"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断是否不是空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if((str!=null)&&!"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 格式化模糊查询
	 * @param str
	 * @return
	 */
	public static String formatLike(String str){
		if(isNotEmpty(str)){
			return "%"+str+"%";
		}else{
			return null;
		}
	}
}

```

#### DateUtil

处理日期格式

```java
package com.et.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	
	public static Date formatString(String str,String format) throws Exception{
		if(StringUtil.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	public static String getCurrentDateStr()throws Exception{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(date);
	}
}

```

#### DateJsonValueProcessor

处理json中有日期工具类

```
package com.et.util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;

/**
 * json-lib 日期处理类
 * @author Administrator
 *
 */
public class DateJsonValueProcessor implements JsonValueProcessor{

	private String format;  
	
    public DateJsonValueProcessor(String format){  
        this.format = format;  
    }  
    
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		if(value == null)  
        {  
            return "";  
        }  
        if(value instanceof java.sql.Timestamp)  
        {  
            String str = new SimpleDateFormat(format).format((java.sql.Timestamp)value);  
            return str;  
        }  
        if (value instanceof java.util.Date)  
        {  
            String str = new SimpleDateFormat(format).format((java.util.Date) value);  
            return str;  
        }  
          
        return value.toString(); 
	}

}

```

### 用户信息管理-列表

#### UserDao

添加查询用户信息列表

```java
package com.et.dao;

import com.et.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户Dao接口
 * @author Administrator
 *
 */
@Mapper
public interface UserDao {

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public User login(User user);

	/**
	 * 查询用户集合
	 * @param map
	 * @return
	 */
	public List<User> find(Map<String,Object> map);

	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
}

```

#### UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.et.dao.UserDao">

    <resultMap type="User" id="UserResult">
        <result property="id" column="id"/>
        <result property="userName" column="userName"/>
        <result property="password" column="password"/>
        <result property="trueName" column="trueName"/>
        <result property="email" column="email"/>
        <result property="phone" column="phone"/>
        <result property="roleName" column="roleName"/>
    </resultMap>

    <select id="login" parameterType="User" resultMap="UserResult">
        select * from t_user where userName=#{userName} and password=#{password} and roleName=#{roleName}
    </select>

    <select id="find" parameterType="Map" resultMap="UserResult">
        select * from t_user
        <where>
            <if test="userName!=null and userName!='' ">
                and userName like #{userName}
            </if>
            <if test="trueName!=null and trueName!='' ">
                and trueName like #{trueName}
            </if>
            <if test="email!=null and email!='' ">
                and email like #{email}
            </if>
        </where>
        <if test="start!=null and size!=null">
            limit #{start},#{size}
        </if>
    </select>

    <select id="getTotal" parameterType="Map" resultType="Long">
        select count(*) from t_user
        <where>
            <if test="userName!=null and userName!='' ">
                and userName like #{userName}
            </if>
            <if test="trueName!=null and trueName!='' ">
                and trueName like #{trueName}
            </if>
            <if test="email!=null and email!='' ">
                and email like #{email}
            </if>
        </where>
    </select>
</mapper>
```

#### UserService

```java
package com.et.service;

import com.et.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 用户Service接口
 * @author Administrator
 *
 */
public interface UserService {

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public User login(User user);

	/**
	 * 查询用户集合
	 * @param map
	 * @return
	 */
	public List<User> find(Map<String,Object> map);

	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
}

```

#### UserServiceImpl

```java
@Override
	public List<User> find(Map<String, Object> map) {
		return userDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return userDao.getTotal(map);
	}
```

#### UserController

添加查询用户列表数据接口

```java
package com.et.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.et.entity.User;
import com.et.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	/**
	 * 用户登录
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String login(User user,HttpServletRequest request)throws Exception{
		User resultUser=userService.login(user);
		if(resultUser==null){
			request.setAttribute("user", user);
			request.setAttribute("errorMsg", "用户名或密码错误！");
			return "login";
		}else{
			HttpSession session=request.getSession();
			session.setAttribute("currentUser", resultUser);
			return "main";
		}
	}

	/**
	 * 分页条件查询用户
	 * @param page
	 * @param rows
	 * @param s_user
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void list(@RequestParam(value="page",required=false)String page, @RequestParam(value="rows",required=false)String rows, User s_user, HttpServletResponse response)throws Exception{
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		if (StringUtil.isEmpty(rows)){
			rows="10";
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userName", StringUtil.formatLike(s_user.getUserName()));
		map.put("trueName", StringUtil.formatLike(s_user.getTrueName()));
		map.put("email", StringUtil.formatLike(s_user.getEmail()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<User> userList=userService.find(map);
		Long total=userService.getTotal(map);
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(userList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
	}
}
```

#### userManager.jsp

webapp/pages/

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">

        var url;
        // 查询用户信息
        function searchUser(){
            $("#dg").datagrid('load',{
                "userName":$("#s_userName").val(),
                "trueName":$("#s_trueName").val(),
                "email":$("#s_email").val()
            });

            <%--$.post("${pageContext.request.contextPath}/user/list",{--%>
            <%--    userName:$("#s_userName").val(),--%>
            <%--    trueName:$("#s_trueName").val(),--%>
            <%--    email:$("#s_email").val()--%>
            <%--},function(result){--%>
            <%--    $('#dg').datagrid('loadData', result);--%>
            <%--},"json");--%>
        }
        // 打开弹窗
        function openUserAddDialog(){
            $("#dlg").dialog("open").dialog("setTitle","添加用户信息");
            url="${pageContext.request.contextPath}/user/save";
        }
        // 打开修改弹窗
        function openUserModifyDialog(){
            var selectedRows=$("#dg").datagrid("getSelections");
            if(selectedRows.length!=1){
                $.messager.alert("系统提示","请选择一条要编辑的数据！");
                return;
            }
            var row=selectedRows[0];
            $("#dlg").dialog("open").dialog("setTitle","编辑用户信息");
            $("#fm").form("load",row);
            url="${pageContext.request.contextPath}/user/save?id="+row.id;
        }
        // 保存用户信息
        function saveUser(){
            $("#fm").form("submit",{
                url:url,
                onSubmit:function(){
                    if($("#roleName").combobox("getValue")==""){
                        $.messager.alert("系统提示","请选择用户角色！");
                        return false;
                    }
                    return $(this).form("validate");
                },
                success:function(result){
                    var result=eval('('+result+')');
                    if(result.success){
                        $.messager.alert("系统提示","保存成功！");
                        resetValue();
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
                    }else{
                        $.messager.alert("系统提示","保存失败！");
                        return;
                    }
                }
            });
        }
        // 重置窗口
        function resetValue(){
            $("#userName").val("");
            $("#password").val("");
            $("#trueName").val("");
            $("#email").val("");
            $("#phone").val("");
            $("#roleName").combobox("setValue","");
        }
        // 关闭弹窗
        function closeUserDialog(){
            $("#dlg").dialog("close");
            resetValue();
        }

        function deleteUser(){
            var selectedRows=$("#dg").datagrid("getSelections");
            if(selectedRows.length==0){
                $.messager.alert("系统提示","请选择要删除的数据！");
                return;
            }
            var strIds=[];
            for(var i=0;i<selectedRows.length;i++){
                strIds.push(selectedRows[i].id);
            }
            var ids=strIds.join(",");
            $.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
                if(r){
                    $.post("${pageContext.request.contextPath}/user/delete",{ids:ids},function(result){
                        if(result.success){
                            $.messager.alert("系统提示","数据已成功删除！");
                            $("#dg").datagrid("reload");
                        }else{
                            $.messager.alert("系统提示","数据删除失败，请联系系统管理员！");
                        }
                    },"json");
                }
            });
        }
    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 1px">
<table id="dg" title="用户管理" class="easyui-datagrid"
       fitColumns="true" scrollbarSize="0" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/user/list" fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">编号</th>
        <th field="userName" width="50" align="center">用户名</th>
        <th field="password" width="50" align="center">密码</th>
        <th field="trueName" width="50" align="center">真实姓名</th>
        <th field="email" width="50" align="center">邮件</th>
        <th field="phone" width="50" align="center">联系电话</th>
        <th field="roleName" width="50" align="center">角色</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        <a href="javascript:openUserAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
        <a href="javascript:openUserModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
        <a href="javascript:deleteUser()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
    </div>
    <div>
        &nbsp;用户名：&nbsp;<input type="text" id="s_userName" size="20" onkeydown="if(event.keyCode==13) searchUser()"/>
        &nbsp;真实姓名：&nbsp;<input type="text" id="s_trueName" size="20" onkeydown="if(event.keyCode==13) searchUser()"/>
        &nbsp;Email：&nbsp;<input type="text" id="s_email" size="20" onkeydown="if(event.keyCode==13) searchUser()"/>
        <a href="javascript:searchUser()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>

<div id="dlg" class="easyui-dialog" modal="true" style="width:620px;height:250px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">

    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>用户名：</td>
                <td><input type="text" id="userName" name="userName" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>密码</td>
                <td><input type="text" id="password" name="password" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
            </tr>
            <tr>
                <td>真实姓名：</td>
                <td><input type="text" id="trueName" name="trueName" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>邮箱：</td>
                <td><input type="text" id="email" name="email" class="easyui-validatebox" validType="email" required="true"/>&nbsp;<font color="red">*</font></td>
            </tr>
            <tr>
                <td>联系电话：</td>
                <td><input type="text" id="phone" name="phone" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>用户角色</td>
                <td>
                    <select class="easyui-combobox" id="roleName" name="roleName" style="width: 154px" editable="false" panelHeight="auto">
                        <option value="">请选择角色...</option>
                        <option value="系统管理员">系统管理员</option>
                        <option value="销售主管">销售主管</option>
                        <option value="客户经理">客户经理</option>
                        <option value="高管">高管</option>
                    </select>
                    &nbsp;<font color="red">*</font>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:saveUser()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeUserDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
</body>
</html>
```

![image-20230929211800318](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292118878.png)

### 用户信息管理-添加、修改

#### UserDao

```java
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public int update(User user);

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public int add(User user);
```

#### UserMapper.xml

```xml
 <insert id="add" parameterType="User">
        insert into t_user values(null,#{userName},#{password},#{trueName},#{email},#{phone},#{roleName})
    </insert>

    <update id="update" parameterType="User">
        update t_user
        <set>
            <if test="userName!=null and userName!='' ">
                userName=#{userName},
            </if>
            <if test="password!=null and password!='' ">
                password=#{password},
            </if>
            <if test="trueName!=null and trueName!='' ">
                trueName=#{trueName},
            </if>
            <if test="email!=null and email!='' ">
                email=#{email},
            </if>
            <if test="phone!=null and phone!='' ">
                phone=#{phone},
            </if>
            <if test="roleName!=null and roleName!='' ">
                roleName=#{roleName},
            </if>
        </set>
        where id=#{id}
    </update>
```

#### UserService

```java
/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public int update(User user);

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public int add(User user);
```

#### UserServiceImpl

```java
@Override
	public int update(User user) {
		return userDao.update(user);
	}

	@Override
	public int add(User user) {
		return userDao.add(user);
	}
```

#### UserController

```java
/**
	 * 添加或者修改用户
	 * @param user
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public void save(User user,HttpServletResponse response)throws Exception{
		int resultTotal=0; // 操作的记录条数
		if(user.getId()==null){
			resultTotal=userService.add(user);
		}else{
			resultTotal=userService.update(user);
		}
		JSONObject result=new JSONObject();
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
	}
```

![image-20230929212400557](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292124844.png)



### 删除用户信息

#### UserDao

```java
/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
```

#### UserMapper.xml

```java
 <delete id="delete" parameterType="Integer">
        delete from t_user where id=#{id}
    </delete>
```

#### UserService

```java
/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
```

#### UserServiceImpl

```java
@Override
	public int delete(Integer id) {
		return userDao.delete(id);
	}
```

#### UserController

```java
/**
	 * 删除用户
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="ids")String ids,HttpServletResponse response)throws Exception{
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			userService.delete(Integer.parseInt(idsStr[i]));
		}
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
	}
```

![image-20230929212911380](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292129734.png)



### 产品信息-列表

```
产品信息只做展示，不做信息维护（增删改）操作
```

#### pom.xml

为了方便开发 借助lombok插件动态生成getter & setter方法

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.26</version>
</dependency>
```

#### Product

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 产品实体
 * @author Administrator
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	private Integer id; // 编号
	private String productName; // 产品名称
	private String model; // 型号
	private String unit; // 单位
	private Float price; // 价格
	private Integer store; // 库存
	private String remark; // 备注
}

```

#### ProductDao

```java
package com.et.dao;

import com.et.entity.DataDic;
import com.et.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 产品Dao接口
 * @author Administrator
 *
 */
@Mapper
public interface ProductDao {

	
	/**
	 * 查询产品集合
	 * @param map
	 * @return
	 */
	public List<Product> find(Map<String,Object> map);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);

	/**
	 * 修改数据字典
	 * @param dataDic
	 * @return
	 */
	public int update(DataDic dataDic);

	/**
	 * 添加数据字典
	 * @param dataDic
	 * @return
	 */
	public int add(DataDic dataDic);

	/**
	 * 删除数据字典
	 * @param id
	 * @return
	 */
	public int delete(Integer id);

}

```

#### ProductMapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.et.dao.ProductDao">

    <resultMap type="Product" id="ProductResult">
        <result property="id" column="id"/>
        <result property="productName" column="productName"/>
        <result property="model" column="model"/>
        <result property="unit" column="unit"/>
        <result property="price" column="price"/>
        <result property="store" column="store"/>
        <result property="remark" column="remark"/>
    </resultMap>

    <select id="find" parameterType="Map" resultMap="ProductResult">
        select * from t_product
        <where>
            <if test="productName!=null and productName!='' ">
                and productName like #{productName}
            </if>
        </where>
        <if test="start!=null and size!=null">
            limit #{start},#{size}
        </if>
    </select>

    <select id="getTotal" parameterType="Map" resultType="Long">
        select count(*) from t_product
        <where>
            <if test="productName!=null and productName!='' ">
                and productName like #{productName}
            </if>
        </where>
    </select>

</mapper>
```

#### ProductService

```java
package com.et.service;

import com.et.entity.Product;

import java.util.List;
import java.util.Map;

/**
 * 产品Service接口
 * @author Administrator
 *
 */
public interface ProductService {

	/**
	 * 查询产品集合
	 * @param map
	 * @return
	 */
	public List<Product> find(Map<String,Object> map);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
}

```

#### ProductServiceImpl

```java
package com.et.service.impl;

import com.et.dao.ProductDao;
import com.et.entity.Product;
import com.et.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 产品Service实现类
 * @author Administrator
 *
 */
@Service("productService")
public class ProductServiceImpl implements ProductService{

	@Resource
	private ProductDao productDao;
	
	@Override
	public List<Product> find(Map<String, Object> map) {
		return productDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return productDao.getTotal(map);
	}

}

```

#### ProductController

```java
package com.et.controller;

import com.et.entity.PageBean;
import com.et.entity.Product;
import com.et.service.ProductService;
import com.et.util.ResponseUtil;
import com.et.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Resource
	private ProductService productService;
	
	
	/**
	 * 分页条件查询产品
	 * @param page
	 * @param rows
	 * @param s_product
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,Product s_product,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("productName", StringUtil.formatLike(s_product.getProductName()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Product> productList=productService.find(map);
		Long total=productService.getTotal(map);
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(productList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
}

```

#### productSearch.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
  <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
  <script type="text/javascript">


    function searchProduct(){
      $("#dg").datagrid('load',{
        "productName":$("#s_productName").val()
      });
    }

  </script>
  <title>Insert title here</title>
</head>
<body style="margin: 1px">
<table id="dg" title="产品信息查询" class="easyui-datagrid" scrollbarSize="0"
       fitColumns="true" pagination="true" rownumbers="true" scrollbarSize="0"
       url="${pageContext.request.contextPath}/product/list" fit="true" toolbar="#tb">
  <thead>
  <tr>
    <th field="cb" checkbox="true" align="center"></th>
    <th field="id" width="50" align="center">编号</th>
    <th field="productName" width="200" align="center">产品名称</th>
    <th field="model" width="100" align="center">型号</th>
    <th field="unit" width="50" align="center">单位</th>
    <th field="price" width="80" align="center">价格</th>
    <th field="store" width="80" align="center">库存</th>
    <th field="remark" width="200" align="center">备注</th>
  </tr>
  </thead>
</table>
<div id="tb">
  <div>
    &nbsp;产品名称：&nbsp;<input type="text" id="s_productName" size="20" onkeydown="if(event.keyCode==13) searchProduct()"/>
    <a href="javascript:searchProduct()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
  </div>
</div>

</body>
</html>
```

![image-20230929214559841](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292146225.png)



### 数据字典-列表/添加、修改、删除

```
一般的应系统中 会将常用的一些值作为一个字典 这样做的目的是方便维护和管理
例如 性别（只有男或女）
可以给字典继续追加值 也可以新增字典
```

#### DataDic

字典实体类

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据字典实体类
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataDic {

	private Integer id; // 编号
	private String dataDicName; // 数据字典名称
	private String dataDicValue; // 数据字典值
	
}

```

#### DataDicDao

```java
package com.et.dao;

import com.et.entity.DataDic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 数据字典Dao接口
 * @author Administrator
 *
 */
@Mapper
public interface DataDicDao {

	
	/**
	 * 查询数据字典集合
	 * @param map
	 * @return
	 */
	public List<DataDic> find(Map<String,Object> map);
	
	/**
	 * 查询所有的数据字典名称集合
	 * @return
	 */
	public List<DataDic> findAll();
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);

	/**
	 * 修改数据字典
	 * @param dataDic
	 * @return
	 */
	public int update(DataDic dataDic);

	/**
	 * 添加数据字典
	 * @param dataDic
	 * @return
	 */
	public int add(DataDic dataDic);
	/**
	 * 删除数据字典
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
}

```

#### DataDicMapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.et.dao.DataDicDao">

    <resultMap type="DataDic" id="DataDicResult">
        <result property="id" column="id"/>
        <result property="dataDicName" column="dataDicName"/>
        <result property="dataDicValue" column="dataDicValue"/>
    </resultMap>

    <select id="find" parameterType="Map" resultMap="DataDicResult">
        select * from t_dataDic
        <where>
            <if test="dataDicName!=null and dataDicName!='' ">
                and dataDicName = #{dataDicName}
            </if>
            <if test="dataDicValue!=null and dataDicValue!='' ">
                and dataDicValue like #{dataDicValue}
            </if>
        </where>
        <if test="start!=null and size!=null">
            limit #{start},#{size}
        </if>
    </select>

    <select id="findAll" resultMap="DataDicResult">
        select distinct dataDicName from t_dataDic
    </select>

    <select id="getTotal" parameterType="Map" resultType="Long">
        select count(*) from t_dataDic
        <where>
            <if test="dataDicName!=null and dataDicName!='' ">
                and dataDicName = #{dataDicName}
            </if>
            <if test="dataDicValue!=null and dataDicValue!='' ">
                and dataDicValue like #{dataDicValue}
            </if>
        </where>
    </select>

    <insert id="add" parameterType="DataDic">
        insert into t_dataDic values(null,#{dataDicName},#{dataDicValue})
    </insert>

    <update id="update" parameterType="DataDic">
        update t_dataDic
        <set>
            <if test="dataDicName!=null and dataDicName!='' ">
                dataDicName=#{dataDicName},
            </if>
            <if test="dataDicValue!=null and dataDicValue!='' ">
                dataDicValue=#{dataDicValue},
            </if>
        </set>
        where id=#{id}
    </update>

    <delete id="delete" parameterType="Integer">
        delete from t_dataDic where id=#{id}
    </delete>

</mapper>
```

#### DataDicService

```java
package com.et.service;

import com.et.entity.DataDic;

import java.util.List;
import java.util.Map;

/**
 * 数据字典Service接口
 * @author Administrator
 *
 */
public interface DataDicService {

	/**
	 * 查询数据字典集合
	 * @param map
	 * @return
	 */
	public List<DataDic> find(Map<String,Object> map);
	
	/**
	 * 查询所有的数据字典名称集合
	 * @return
	 */
	public List<DataDic> findAll();
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);

	/**
	 * 修改数据字典
	 * @param dataDic
	 * @return
	 */
	public int update(DataDic dataDic);

	/**
	 * 添加数据字典
	 * @param dataDic
	 * @return
	 */
	public int add(DataDic dataDic);

	/**
	 * 删除数据字典
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
}

```

#### DataDicServiceImpl

```java
package com.et.service.impl;

import com.et.dao.DataDicDao;
import com.et.entity.DataDic;
import com.et.service.DataDicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 数据字典Service实现类
 * @author Administrator
 *
 */
@Service("dataDicService")
public class DataDicServiceImpl implements DataDicService{

	@Resource
	private DataDicDao dataDicDao;
	
	@Override
	public List<DataDic> find(Map<String, Object> map) {
		return dataDicDao.find(map);
	}

	@Override
	public List<DataDic> findAll() {
		return dataDicDao.findAll();
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return dataDicDao.getTotal(map);
	}

	@Override
	public int update(DataDic dataDic) {
		return dataDicDao.update(dataDic);
	}

	@Override
	public int add(DataDic dataDic) {
		return dataDicDao.add(dataDic);
	}

	@Override
	public int delete(Integer id) {
		return dataDicDao.delete(id);
	}


}

```

#### DataDicController

```java
package com.et.controller;

import com.et.entity.DataDic;
import com.et.entity.PageBean;
import com.et.service.DataDicService;
import com.et.util.ResponseUtil;
import com.et.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/dataDic")
public class DataDicController {
	
	@Resource
	private DataDicService dataDicService;
	
	
	/**
	 * 分页条件查询数据字典
	 * @param page
	 * @param rows
	 * @param s_dataDic
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,DataDic s_dataDic,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("dataDicName", s_dataDic.getDataDicName());
		map.put("dataDicValue", StringUtil.formatLike(s_dataDic.getDataDicValue()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<DataDic> dataDicList=dataDicService.find(map);
		Long total=dataDicService.getTotal(map);
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(dataDicList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 根据数据字典名称查找，用户下拉框
	 * @param dataDicName
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dataDicComboList")
	public String dataDicComboList(String dataDicName,HttpServletResponse response)throws Exception{
		JSONArray jsonArray=new JSONArray();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("dataDicName", dataDicName);
		List<DataDic> dataDicList=dataDicService.find(map);
		JSONArray rows=JSONArray.fromObject(dataDicList);
		jsonArray.addAll(rows);
		ResponseUtil.write(response, jsonArray);
		return null;
	}
	
	/**
	 * 查询所有的数据字典名称
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDataDicName")
	public String dataDicNameComboList(HttpServletResponse response)throws Exception{
		JSONArray jsonArray=new JSONArray();
		List<DataDic> dataDicList=dataDicService.findAll();
		JSONArray rows=JSONArray.fromObject(dataDicList);
		jsonArray.addAll(rows);
		ResponseUtil.write(response, jsonArray);
		return null;
	}

	/**
	 * 添加或者修改数据字典
	 * @param dataDic
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(DataDic dataDic,HttpServletResponse response)throws Exception{
		int resultTotal=0; // 操作的记录条数
		if(dataDic.getId()==null){
			resultTotal=dataDicService.add(dataDic);
		}else{
			resultTotal=dataDicService.update(dataDic);
		}
		JSONObject result=new JSONObject();
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 删除数据字典
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="ids")String ids,HttpServletResponse response)throws Exception{
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			dataDicService.delete(Integer.parseInt(idsStr[i]));
		}
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}


}

```

#### dataDicManage.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">

        var url;

        function searchDataDic(){
            $("#dg").datagrid('load',{
                "dataDicName":$("#s_dataDicName").combobox("getValue"),
                "dataDicValue":$("#s_dataDicValue").val()
            });
        }

        function openDataDicAddDialog(){
            $("#dlg").dialog("open").dialog("setTitle","添加数据字典信息");
            url="${pageContext.request.contextPath}/dataDic/save";
        }

        function openDataDicModifyDialog(){
            var selectedRows=$("#dg").datagrid("getSelections");
            if(selectedRows.length!=1){
                $.messager.alert("系统提示","请选择一条要编辑的数据！");
                return;
            }
            var row=selectedRows[0];
            $("#dlg").dialog("open").dialog("setTitle","编辑数据字典信息");
            $("#fm").form("load",row);
            url="${pageContext.request.contextPath}/dataDic/save?id="+row.id;
        }

        function saveDataDic(){
            var dataDicName=$("#dataDicName").combobox("getText");
            var dataDicValue=$("#dataDicValue").val();
            if(dataDicName==""){
                $.messager.alert("系统提示","数据字典名不能为空！");
                return;
            }
            if(dataDicValue==""){
                $.messager.alert("系统提示","数据字典值不能为空！");
                return;
            }
            $.post(url,{dataDicName:dataDicName,dataDicValue:dataDicValue},function(result){
                if(result.success){
                    $.messager.alert("系统提示","保存成功！");
                    $("#dlg").dialog("close");
                    $("#dg").datagrid("reload");
                    resetValue();
                }else{
                    $.messager.alert("系统提示","保存失败！");
                }
            },"json");
        }

        function resetValue(){
            $("#dataDicName").combobox("setValue","");
            $("#dataDicValue").val("");
        }

        function closeDataDicDialog(){
            $("#dlg").dialog("close");
            resetValue();
        }

        function deleteDataDic(){
            var selectedRows=$("#dg").datagrid("getSelections");
            if(selectedRows.length==0){
                $.messager.alert("系统提示","请选择要删除的数据！");
                return;
            }
            var strIds=[];
            for(var i=0;i<selectedRows.length;i++){
                strIds.push(selectedRows[i].id);
            }
            var ids=strIds.join(",");
            $.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
                if(r){
                    $.post("${pageContext.request.contextPath}/dataDic/delete",{ids:ids},function(result){
                        if(result.success){
                            $.messager.alert("系统提示","数据已成功删除！");
                            $("#dg").datagrid("reload");
                        }else{
                            $.messager.alert("系统提示","数据删除失败，请联系系统管理员！");
                        }
                    },"json");
                }
            });
        }

    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 1px">
<table id="dg" title="数据字典管理" class="easyui-datagrid" scrollbarSize="0"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/dataDic/list" fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">编号</th>
        <th field="dataDicName" width="100" align="center">数据字典名</th>
        <th field="dataDicValue" width="100" align="center">数据字典值</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        <a href="javascript:openDataDicAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
        <a href="javascript:openDataDicModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
        <a href="javascript:deleteDataDic()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
    </div>
    <div>
        &nbsp;数据字典名：&nbsp;<input class="easyui-combobox" id="s_dataDicName" data-options="panelHeight:'auto',editable:false,valueField:'dataDicName',textField:'dataDicName',url:'${pageContext.request.contextPath}/dataDic/findDataDicName'"/>
        &nbsp;数据字典值：&nbsp;<input type="text" id="s_dataDicValue" size="20" onkeydown="if(event.keyCode==13) searchDataDic()"/>
        <a href="javascript:searchDataDic()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>

<div id="dlg" class="easyui-dialog" modal="true" style="width:640px;height:150px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">

    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>数据字典名：</td>
                <td><input class="easyui-combobox" id="dataDicName" name="dataDicName" data-options="panelHeight:'auto',valueField:'dataDicName',textField:'dataDicName',url:'${pageContext.request.contextPath}/dataDic/findDataDicName'"/>&nbsp;<font color="red">*</font></td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>数据字典值：</td>
                <td><input type="text" id="dataDicValue" name="dataDicValue" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
            </tr>
        </table>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:saveDataDic()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeDataDicDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
</body>
</html>
```

![image-20230929215532258](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292155751.png)



## 销售管理(CRUD)

### 销售机会管理

#### SaleChance实体类

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 销售机会实体
 * @author Administrator
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleChance {

	private Integer id; // 编号
	private String chanceSource; // 机会来源
	private String customerName; // 客户名称
	private int cgjl; // 成功几率
	private String overView; // 概要
	private String linkMan; // 联系人
	private String linkPhone; // 联系电话
	private String description; // 机会描述
	private String createMan; // 创建人 
	private Date createTime; // 创建时间
	private String assignMan; // 指派人
	private Date assignTime; // 指派时间
	
	private Integer state; // 分配状态 0 未分配 1 已分配
	private Integer devResult; // 客户开发状态 0 未开发 1 开发中 2 开发成功 3 开发失败

	
}

```



#### SaleChanceDao

```java
package com.et.dao;

import com.et.entity.SaleChance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 销售机会Dao接口
 * @author Administrator
 *
 */
@Mapper
public interface SaleChanceDao {

	
	/**
	 * 查询销售机会集合
	 * @param map
	 * @return
	 */
	public List<SaleChance> find(Map<String,Object> map);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);

	/**
	 * 添加销售机会
	 * @param saleChance
	 * @return
	 */
	public int add(SaleChance saleChance);

	/**
	 * 修改销售机会
	 * @param saleChance
	 * @return
	 */
	public int update(SaleChance saleChance);

	/**
	 * 删除销售机会
	 * @param id
	 * @return
	 */
	public int delete(Integer id);

	/**
	 * 根据id查找实体
	 * @param id
	 * @return
	 */
	public SaleChance findById(Integer id);
}

```



#### SaleChanceMapper

```java
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.et.dao.SaleChanceDao">

    <resultMap type="SaleChance" id="SaleChanceResult">
        <result property="id" column="id"/>
        <result property="chanceSource" column="chanceSource"/>
        <result property="cgjl" column="cgjl"/>
        <result property="overView" column="overView"/>
        <result property="linkMan" column="linkMan"/>
        <result property="linkPhone" column="linkPhone"/>
        <result property="description" column="description"/>
        <result property="createMan" column="createMan"/>
        <result property="createTime" column="createTime"/>
        <result property="assignMan" column="assignMan"/>
        <result property="assignTime" column="assignTime"/>
        <result property="state" column="state"/>
        <result property="devResult" column="devResult"/>
    </resultMap>

    <select id="find" parameterType="Map" resultMap="SaleChanceResult">
        select * from t_sale_chance
        <where>
            <if test="customerName!=null and customerName!='' ">
                and customerName like #{customerName}
            </if>
            <if test="overView!=null and overView!='' ">
                and overView like #{overView}
            </if>
            <if test="createMan!=null and createMan!='' ">
                and createMan like #{createMan}
            </if>
            <if test="state!=null ">
                and state = #{state}
            </if>
            <if test="devResult!=null ">
                and devResult = #{devResult}
            </if>
        </where>
        <if test="start!=null and size!=null">
            limit #{start},#{size}
        </if>
    </select>

    <select id="getTotal" parameterType="Map" resultType="Long">
        select count(*) from t_sale_chance
        <where>
            <if test="customerName!=null and customerName!='' ">
                and customerName like #{customerName}
            </if>
            <if test="overView!=null and overView!='' ">
                and overView like #{overView}
            </if>
            <if test="createMan!=null and createMan!='' ">
                and createMan like #{createMan}
            </if>
            <if test="state!=null">
                and state = #{state}
            </if>
            <if test="devResult!=null ">
                and devResult = #{devResult}
            </if>
        </where>
    </select>

    <insert id="add" parameterType="SaleChance">
        insert into t_sale_chance values(null,#{chanceSource},#{customerName},#{cgjl},#{overView},#{linkMan},#{linkPhone},#{description},#{createMan},#{createTime},#{assignMan},#{assignTime},#{state},#{devResult})
    </insert>

    <update id="update" parameterType="SaleChance">
        update t_sale_chance
        <set>
            <if test="chanceSource!=null and chanceSource!='' ">
                chanceSource = #{chanceSource},
            </if>
            <if test="customerName!=null and customerName!='' ">
                customerName = #{customerName},
            </if>
            <if test="cgjl!=0">
                cgjl = #{cgjl},
            </if>
            <if test="overView!=null and overView!='' ">
                overView = #{overView},
            </if>
            <if test="linkMan!=null and linkMan!='' ">
                linkMan = #{linkMan},
            </if>
            <if test="linkPhone!=null and linkPhone!='' ">
                linkPhone = #{linkPhone},
            </if>
            <if test="description!=null and description!='' ">
                description = #{description},
            </if>
            <if test="createMan!=null and createMan!='' ">
                createMan = #{createMan},
            </if>
            <if test="createTime!=null">
                createTime = #{createTime},
            </if>
            <if test="assignMan!=null and assignMan!='' ">
                assignMan = #{assignMan},
            </if>
            <if test="assignTime!=null">
                assignTime = #{assignTime},
            </if>
            <if test="state!=null">
                state = #{state},
            </if>
            <if test="devResult!=null">
                devResult = #{devResult},
            </if>
        </set>
        where id=#{id}
    </update>

    <delete id="delete" parameterType="Integer">
        delete from t_sale_chance where id=#{id}
    </delete>

    <select id="findById" parameterType="Integer" resultMap="SaleChanceResult">
        select * from t_sale_chance where id=#{id}
    </select>
</mapper>
```

#### SaleChanceService

```java
package com.et.service;

import com.et.entity.SaleChance;

import java.util.List;
import java.util.Map;

/**
 * 销售机会Service接口
 * @author Administrator
 *
 */
public interface SaleChanceService {

	/**
	 * 查询销售机会集合
	 * @param map
	 * @return
	 */
	public List<SaleChance> find(Map<String,Object> map);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);

	/**
	 * 添加销售机会
	 * @param saleChance
	 * @return
	 */
	public int add(SaleChance saleChance);

	/**
	 * 修改销售机会
	 * @param saleChance
	 * @return
	 */
	public int update(SaleChance saleChance);

	/**
	 * 删除销售机会
	 * @param id
	 * @return
	 */
	public int delete(Integer id);

	/*
	* 根据ID查询销售机会
	*
	* */
	SaleChance findById(int parseInt);
}

```

#### SaleChanceServiceImpl

```java
package com.et.service.impl;

import com.et.dao.SaleChanceDao;
import com.et.entity.SaleChance;
import com.et.service.SaleChanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 销售机会Service实现类
 * @author Administrator
 *
 */
@Service("saleChanceService")
public class SaleChanceServiceImpl implements SaleChanceService{

	@Resource
	private SaleChanceDao saleChanceDao;
	
	@Override
	public List<SaleChance> find(Map<String, Object> map) {
		return saleChanceDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return saleChanceDao.getTotal(map);
	}

	@Override
	public int add(SaleChance saleChance) {
		return saleChanceDao.add(saleChance);
	}

	@Override
	public int update(SaleChance saleChance) {
		return saleChanceDao.update(saleChance);
	}

	@Override
	public int delete(Integer id) {
		return saleChanceDao.delete(id);
	}

	/*
	 * 根据ID查询销售机会
	 *
	 * */
	public SaleChance findById(int parseInt){
		return saleChanceDao.findById(parseInt);
	}
}

```

#### DateJsonValueProcessor

jsonlib 处理日期工具类

```java
package com.et.util;

import java.text.SimpleDateFormat;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * json-lib 日期处理类
 * @author Administrator
 *
 */
public class DateJsonValueProcessor implements JsonValueProcessor{

	private String format;

    public DateJsonValueProcessor(String format){
        this.format = format;
    }

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		if(value == null)
        {
            return "";
        }
        if(value instanceof java.sql.Timestamp)
        {
            String str = new SimpleDateFormat(format).format((java.sql.Timestamp)value);
            return str;
        }
        if (value instanceof java.util.Date)
        {
            String str = new SimpleDateFormat(format).format((java.util.Date) value);
            return str;
        }

        return value.toString();
	}

}

```

#### SaleChanceController

```java
package com.et.controller;

import com.et.entity.SaleChance;
import com.et.service.SaleChanceService;
import com.et.util.DateJsonValueProcessor;
import com.et.util.PageBean;
import com.et.util.ResponseUtil;
import com.et.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 销售机会Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/saleChance")
public class SaleChanceController {
	
	@Resource
	private SaleChanceService saleChanceService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}


	/**
	 * 分页条件查询销售机会
	 * @param page
	 * @param rows
	 * @param s_saleChance
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,SaleChance s_saleChance,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("customerName", StringUtil.formatLike(s_saleChance.getCustomerName()));
		map.put("overView", StringUtil.formatLike(s_saleChance.getOverView()));
		map.put("createMan", StringUtil.formatLike(s_saleChance.getCreateMan()));
		map.put("state", s_saleChance.getState());
		map.put("devResult", s_saleChance.getDevResult());// 开发状态
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<SaleChance> saleChanceList=saleChanceService.find(map);
		Long total=saleChanceService.getTotal(map);
		JSONObject result=new JSONObject();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
		JSONArray jsonArray=JSONArray.fromObject(saleChanceList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
	}

	/**
	 * 添加或者修改销售机会
	 * @param saleChance
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(SaleChance saleChance,HttpServletResponse response)throws Exception{
		int resultTotal=0; // 操作的记录条数
		if(StringUtil.isNotEmpty(saleChance.getAssignMan())){
			saleChance.setState(1);
		}else{
			saleChance.setState(0); // 默认未分配状态
		}
		if(saleChance.getId()==null){
			saleChance.setDevResult(0); // 添加的时候，默认是未开发状态
			resultTotal=saleChanceService.add(saleChance);
		}else{
			resultTotal=saleChanceService.update(saleChance);
		}
		JSONObject result=new JSONObject();
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 删除销售机会
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="ids")String ids,HttpServletResponse response)throws Exception{
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			saleChanceService.delete(Integer.parseInt(idsStr[i]));
		}
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 通过ID查找实体
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	public String findById(@RequestParam(value="id")String id,HttpServletResponse response)throws Exception{
		SaleChance saleChance=saleChanceService.findById(Integer.parseInt(id));
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
		JSONObject jsonObject=JSONObject.fromObject(saleChance, jsonConfig);
		ResponseUtil.write(response, jsonObject);
		return null;
	}

}

```

#### saleChanceManage.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    <script type="text/javascript">

        var url;

        $(function(){
            $("#assignMan").combobox({
                onSelect:function(record){
                    if(record.trueName!=''){
                        $("#assignTime").val(getCurrentDateTime());
                    }else{
                        $("#assignTime").val("");
                    }
                }
            });
        });

        function searchSaleChance(){
            $("#dg").datagrid('load',{
                "customerName":$("#s_customerName").val(),
                "overView":$("#s_overView").val(),
                "createMan":$("#s_createMan").val(),
                "state":$("#s_state").combobox("getValue")
            });
        }

        function formatState(val,row){
            if(val==1){
                return "已分配";
            }else{
                return "未分配";
            }
        }

        function openSaleChanceAddDialog(){
            $("#dlg").dialog("open").dialog("setTitle","添加销售机会信息");
            $("#createMan").val('${currentUser.trueName}');
            $("#createTime").val(getCurrentDateTime());
            url="${pageContext.request.contextPath}/saleChance/save";
        }

        function openSaleChanceModifyDialog(){
            var selectedRows=$("#dg").datagrid("getSelections");
            if(selectedRows.length!=1){
                $.messager.alert("系统提示","请选择一条要编辑的数据！");
                return;
            }
            var row=selectedRows[0];
            $("#dlg").dialog("open").dialog("setTitle","编辑销售机会信息");
            $("#fm").form("load",row);
            url="${pageContext.request.contextPath}/saleChance/save?id="+row.id;
        }

        function saveSaleChance(){
            $("#fm").form("submit",{
                url:url,
                onSubmit:function(){
                    return $(this).form("validate");
                },
                success:function(result){
                    var result=eval('('+result+')');
                    if(result.success){
                        $.messager.alert("系统提示","保存成功！");
                        resetValue();
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
                    }else{
                        $.messager.alert("系统提示","保存失败！");
                        return;
                    }
                }
            });
        }

        function resetValue(){
            $("#customerName").val("");
            $("#chanceSource").val("");
            $("#linkMan").val("");
            $("#linkPhone").val("");
            $("#cgjl").numberbox('setValue',"");
            $("#overView").val("");
            $("#description").val("");
            $("#createMan").val("");
            $("#createTime").val("");
            $("#assignMan").combobox("setValue","");
            $("#assignTime").val("");
        }

        function closeSaleChanceDialog(){
            $("#dlg").dialog("close");
            resetValue();
        }

        function deleteSaleChance(){
            var selectedRows=$("#dg").datagrid("getSelections");
            if(selectedRows.length==0){
                $.messager.alert("系统提示","请选择要删除的数据！");
                return;
            }
            var strIds=[];
            for(var i=0;i<selectedRows.length;i++){
                strIds.push(selectedRows[i].id);
            }
            var ids=strIds.join(",");
            $.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
                if(r){
                    $.post("${pageContext.request.contextPath}/saleChance/delete",{ids:ids},function(result){
                        if(result.success){
                            $.messager.alert("系统提示","数据已成功删除！");
                            $("#dg").datagrid("reload");
                        }else{
                            $.messager.alert("系统提示","数据删除失败，请联系系统管理员！");
                        }
                    },"json");
                }
            });
        }

    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 1px">
<table id="dg" title="销售机会信息管理" class="easyui-datagrid" scrollbarSize="0"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/saleChance/list" fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">编号</th>
        <th field="chanceSource" width="200" align="center" hidden="true">机会来源</th>
        <th field="customerName" width="50" align="center">客户名称</th>
        <th field="cgjl" width="50" align="center" hidden="true">成功几率</th>
        <th field="overView" width="200" align="center">概要</th>
        <th field="linkMan" width="100" align="center">联系人</th>
        <th field="linkPhone" width="100" align="center">联系电话</th>
        <th field="description" width="200" align="center" hidden="true">机会描述</th>
        <th field="createMan" width="100" align="center">创建人</th>
        <th field="createTime" width="100" align="center">创建时间</th>
        <th field="assignMan" width="200" align="center" hidden="true">指派人</th>
        <th field="assignTime" width="200" align="center" hidden="true">指派时间</th>
        <th field="state" width="100" align="center" formatter="formatState">状态</th>
        <th field="devResult" width="200" align="center" hidden="true">客户开发状态</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        <a href="javascript:openSaleChanceAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">创建</a>
        <a href="javascript:openSaleChanceModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
        <a href="javascript:deleteSaleChance()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
    </div>
    <div>
        &nbsp;客户名称：&nbsp;<input type="text" id="s_customerName" size="20" onkeydown="if(event.keyCode==13) searchSaleChance()"/>
        &nbsp;概要：&nbsp;<input type="text" id="s_overView" size="20" onkeydown="if(event.keyCode==13) searchSaleChance()"/>
        &nbsp;创建人：&nbsp;<input type="text" id="s_createMan" size="20" onkeydown="if(event.keyCode==13) searchSaleChance()"/>
        &nbsp;分配状态：&nbsp;<select class="easyui-combobox" id="s_state" editable="false" panelHeight="auto" >
        <option value="">请选择...</option>
        <option value="0">未分配</option>
        <option value="1">已分配</option>
    </select>
        <a href="javascript:searchSaleChance()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>

<div id="dlg" class="easyui-dialog" style="width:700px;height:450px;padding: 10px 20px" modal="true"
     closed="true" buttons="#dlg-buttons">

    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>客户名称：</td>
                <td><input type="text" id="customerName" name="customerName" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>机会来源</td>
                <td><input type="text" id="chanceSource" name="chanceSource" /></td>
            </tr>
            <tr>
                <td>联系人：</td>
                <td><input type="text" id="linkMan" name="linkMan" /></td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>联系电话：</td>
                <td><input type="text" id="linkPhone" name="linkPhone" /></td>
            </tr>
            <tr>
                <td>成功几率(%)：</td>
                <td><input type="text" id="cgjl" name="cgjl" class="easyui-numberbox" data-options="min:0,max:100" required="true"/>&nbsp;<font color="red">*</font></td>
                <td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;</td>
            </tr>
            <tr>
                <td>概要：</td>
                <td colspan="4"><input type="text" id="overView" name="overView" style="width: 420px"/></td>
            </tr>
            <tr>
                <td>机会描述：</td>
                <td colspan="4">
                    <textarea rows="5" cols="50" id="description" name="description"></textarea>
                </td>
            </tr>
            <tr>
                <td>创建人：</td>
                <td><input type="text" readonly="readonly" id="createMan" name="createMan" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>创建时间：</td>
                <td><input type="text" id="createTime" name="createTime" readonly="readonly"/>&nbsp;<font color="red">*</font></td>
            </tr>
            <tr>
                <td>指派给：</td>
                <td><input class="easyui-combobox" id="assignMan" name="assignMan" data-options="panelHeight:'auto',editable:false,valueField:'trueName',textField:'trueName',url:'${pageContext.request.contextPath}/user/customerManagerComboList'"/></td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>指派时间：</td>
                <td><input type="text" id="assignTime" name="assignTime" readonly="readonly"/></td>
            </tr>
        </table>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:saveSaleChance()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeSaleChanceDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
</body>
</html>
```

![image-20230929220604418](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292206140.png)



### 客户开发计划

针对已经分配的**销售机会**进行客户开发

#### SaleChanceController

获取列表时添加一个查询条件

​	开发状态的查询条件

```java
/**
	 * 分页条件查询销售机会
	 * @param page
	 * @param rows
	 * @param s_saleChance
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,SaleChance s_saleChance,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("customerName", StringUtil.formatLike(s_saleChance.getCustomerName()));
		map.put("overView", StringUtil.formatLike(s_saleChance.getOverView()));
		map.put("createMan", StringUtil.formatLike(s_saleChance.getCreateMan()));
		map.put("state", s_saleChance.getState());
		map.put("devResult", s_saleChance.getDevResult());// 开发状态
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<SaleChance> saleChanceList=saleChanceService.find(map);
		Long total=saleChanceService.getTotal(map);
		JSONObject result=new JSONObject();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
		JSONArray jsonArray=JSONArray.fromObject(saleChanceList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
	}
```

#### UserController

获取指派人

```java
/**
	 * 获取客户经理信息 下拉框数据用到
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/customerManagerComboList")
	public String customerManagerComboList(HttpServletResponse response)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("roleName", "客户经理");
		List<User> userList=userService.find(map);
		JSONArray row=JSONArray.fromObject(userList);
		ResponseUtil.write(response, row);
		return null;
	}
```



#### SaleCanceMapper

```xml
<select id="find" parameterType="Map" resultMap="SaleChanceResult">
		select * from t_sale_chance
		<where>
			<if test="customerName!=null and customerName!='' ">
				and customerName like #{customerName}
			</if>
			<if test="overView!=null and overView!='' ">
				and overView like #{overView}
			</if>
			<if test="createMan!=null and createMan!='' ">
				and createMan like #{createMan}
			</if>
			<if test="state!=null ">
				and state = #{state}
			</if>
			<if test="devResult!=null ">
				and devResult = #{devResult}
			</if>
		</where>
		<if test="start!=null and size!=null">
			limit #{start},#{size}
		</if>
	</select>

	<select id="getTotal" parameterType="Map" resultType="Long">
		select count(*) from t_sale_chance
		<where>
			<if test="customerName!=null and customerName!='' ">
				and customerName like #{customerName}
			</if>
			<if test="overView!=null and overView!='' ">
				and overView like #{overView}
			</if>
			<if test="createMan!=null and createMan!='' ">
				and createMan like #{createMan}
			</if>
			<if test="state!=null">
				and state = #{state}
			</if>
			<if test="devResult!=null ">
				and devResult = #{devResult}
			</if>
		</where>
	</select>
```

#### common.js

js/commons.js 自定义获取时间的组件

```js
// 获取当前日期时间
function getCurrentDateTime(){
	 var date=new Date();
	 var year=date.getFullYear();
	 var month=date.getMonth()+1;
	 var day=date.getDate();
	 var hours=date.getHours();
	 var minutes=date.getMinutes();
	 var seconds=date.getSeconds();
	 return year+"-"+formatZero(month)+"-"+formatZero(day)+" "+formatZero(hours)+":"+formatZero(minutes)+":"+formatZero(seconds);
 }

// 获取当前日期
function getCurrentDate(){
	 var date=new Date();
	 var year=date.getFullYear();
	 var month=date.getMonth()+1;
	 var day=date.getDate();
	 return year+"-"+formatZero(month)+"-"+formatZero(day);
}


 function formatZero(n){
	 if(n>=0&&n<=9){
		 return "0"+n;
	 }else{
		 return n;
	 }
 }
```



#### cusdevplanManage.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    <script type="text/javascript">



        function searchSaleChance(){
            $("#dg").datagrid('load',{
                "customerName":$("#s_customerName").val(),
                "overView":$("#s_overView").val(),
                "devResult":$("#s_devResult").combobox("getValue")
            });
        }

        function formatState(val,row){
            if(val==1){
                return "已分配";
            }else{
                return "未分配";
            }
        }


        function formatDevResult(val,row){
            if(val==0){
                return "未开发";
            }else if(val==1){
                return "开发中";
            }else if(val==2){
                return "开发成功";
            }else if(val==3){
                return "开发失败";
            }
        }

        function formatAction(val,row){
            if(row.devResult==0||row.devResult==1){
                return "<a href=''>开发</a>";
            }else{
                return "<a href=''>查看详细信息</a>";
            }
        }


    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 1px">
<table id="dg" title="客户开发计划管理" class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/saleChance/list.do?state=1" fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">编号</th>
        <th field="chanceSource" width="200" align="center" hidden="true">机会来源</th>
        <th field="customerName" width="80" align="center">客户名称</th>
        <th field="cgjl" width="50" align="center" hidden="true">成功几率</th>
        <th field="overView" width="150" align="center">概要</th>
        <th field="linkMan" width="80" align="center">联系人</th>
        <th field="linkPhone" width="100" align="center" hidden="true">联系电话</th>
        <th field="description" width="200" align="center" hidden="true">机会描述</th>
        <th field="createMan" width="80" align="center">创建人</th>
        <th field="createTime" width="150" align="center">创建时间</th>
        <th field="assignMan" width="50" align="center" >指派人</th>
        <th field="assignTime" width="150" align="center" >指派时间</th>
        <th field="state" width="100" align="center" formatter="formatState" hidden="true">状态</th>
        <th field="devResult" width="100" align="center" formatter="formatDevResult">客户开发状态</th>
        <th field="a" width="100" align="center" formatter="formatAction">操作</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        &nbsp;客户名称：&nbsp;<input type="text" id="s_customerName" size="20" onkeydown="if(event.keyCode==13) searchSaleChance()"/>
        &nbsp;概要：&nbsp;<input type="text" id="s_overView" size="20" onkeydown="if(event.keyCode==13) searchSaleChance()"/>
        &nbsp;客户开发状态：&nbsp;<select class="easyui-combobox" id="s_devResult" editable="false" panelHeight="auto" >
        <option value="">请选择...</option>
        <option value="0">未开发</option>
        <option value="1">开发中</option>
        <option value="2">开发成功</option>
        <option value="3">开发失败</option>
    </select>
        <a href="javascript:searchSaleChance()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>
</body>
</html>
```

![image-20230830141030506](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309292214761.png)





















## 客户管理(CRUD)

## 服务管理(CRUD)

## 报表统计(CRUD)

## 系统管理(CRUD)