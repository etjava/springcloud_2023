# CRM客户关系管理系统



## 概述

```
```

### 技术栈

```
```

## 项目搭建

### 创建maven项目

![image-20230829213728040](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308292137866.png)

### 添加相关依赖

```xml

    <dependencies>
        <!--Servlet - JSP -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>

        <!--数据库驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <!-- 数据库连接池 -->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.2</version>
        </dependency>
        <!--Mybatis-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.2.5</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>1.2.0</version>
        </dependency>
        <!--Spring-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>ognl</groupId>
            <artifactId>ognl</artifactId>
            <version>3.0.8</version>
        </dependency>

        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.16</version>
        </dependency>

        <dependency>
            <groupId>net.sf.json-lib</groupId>
            <artifactId>json-lib</artifactId>
            <version>2.4</version>
            <classifier>JDK15</classifier>
        </dependency>

        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.6</version>
        </dependency>
        <dependency>
            <groupId>commons-beanutils</groupId>
            <artifactId>commons-beanutils</artifactId>
            <version>1.8.0</version>
        </dependency>

        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.4</version>
        </dependency>

        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.16</version>
        </dependency>

        <dependency>
            <groupId>asm</groupId>
            <artifactId>asm-tree</artifactId>
            <version>3.3.1</version>
        </dependency>

        <dependency>
            <groupId>asm</groupId>
            <artifactId>asm-commons</artifactId>
            <version>3.3.1</version>
        </dependency>


    </dependencies>

    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                    <include>**/*.properties</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
```

### 添加web支持

选中项目 右键 add framework support... 然后选择 web Application

![image-20230829213916462](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308292139657.png)

### 创建包结构

![image-20230829214114964](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308292141947.png)

### Spring配置文件

resources目录下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:jee="http://www.springframework.org/schema/jee"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
        http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-4.0.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd">

    <!-- 自动扫描 -->
    <context:component-scan base-package="com.et.service" />

    <!-- 配置数据源 -->
    <bean id="dataSource"
          class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/db_crm?useUnicode=true&amp;characterEncoding=utf-8"/>
        <property name="username" value="root"/>
        <property name="password" value="Karen@1234"/>
    </bean>

    <!-- 配置mybatis的sqlSessionFactory -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
        <!-- 自动扫描mappers.xml文件 -->
        <property name="mapperLocations" value="classpath:com/et/mappers/*.xml"></property>
        <!-- mybatis配置文件 -->
        <property name="configLocation" value="classpath:mybatis-config.xml"></property>
    </bean>

    <!-- DAO接口所在包名，Spring会自动查找其下的类 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.et.dao" />
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"></property>
    </bean>

    <!-- (事务管理)transaction manager, use JtaTransactionManager for global tx -->
    <bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource" />
    </bean>

    <!-- 配置事务通知属性 -->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <!-- 定义事务传播属性 -->
        <tx:attributes>
            <tx:method name="insert*" propagation="REQUIRED" />
            <tx:method name="update*" propagation="REQUIRED" />
            <tx:method name="edit*" propagation="REQUIRED" />
            <tx:method name="save*" propagation="REQUIRED" />
            <tx:method name="add*" propagation="REQUIRED" />
            <tx:method name="new*" propagation="REQUIRED" />
            <tx:method name="set*" propagation="REQUIRED" />
            <tx:method name="remove*" propagation="REQUIRED" />
            <tx:method name="delete*" propagation="REQUIRED" />
            <tx:method name="change*" propagation="REQUIRED" />
            <tx:method name="check*" propagation="REQUIRED" />
            <tx:method name="get*" propagation="REQUIRED" read-only="true" />
            <tx:method name="find*" propagation="REQUIRED" read-only="true" />
            <tx:method name="load*" propagation="REQUIRED" read-only="true" />
            <tx:method name="*" propagation="REQUIRED" read-only="true" />
        </tx:attributes>
    </tx:advice>

    <!-- 配置事务切面 -->
    <aop:config>
        <aop:pointcut id="serviceOperation"
                      expression="execution(* com.et.service.*.*(..))" />
        <aop:advisor advice-ref="txAdvice" pointcut-ref="serviceOperation" />
    </aop:config>
</beans>
```

### mybatis配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<!-- 别名 -->
	<typeAliases>
		<package name="com.et.entity"/>
	</typeAliases>
</configuration>
```

### SpringMVC的配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:context="http://www.springframework.org/schema/context"
    xmlns:jee="http://www.springframework.org/schema/jee"
    xmlns:tx="http://www.springframework.org/schema/tx"
    xsi:schemaLocation="
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
        http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-4.0.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd">

	<!-- 使用注解的包，包括子集 -->
	<context:component-scan base-package="com.et.controller" />

	<!-- 视图解析器 -->
	<bean id="viewResolver"
		class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/" />
		<property name="suffix" value=".jsp"></property>
	</bean>

</beans>
```

### web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <welcome-file-list>
        <welcome-file>login.jsp</welcome-file>
    </welcome-file-list>

    <!-- Spring配置文件 -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>
    <!-- 编码过滤器 -->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <async-supported>true</async-supported>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <!-- Spring监听器 -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <!-- 添加对springmvc的支持 -->
    <servlet>
        <servlet-name>springMVC</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
        <async-supported>true</async-supported>
    </servlet>
    <servlet-mapping>
        <servlet-name>springMVC</servlet-name>
        <url-pattern>*.do</url-pattern>
    </servlet-mapping>
</web-app>
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

### 创建UserController

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
			return "redirect:/main.jsp";
		}
	}
}

```



### web目录下添加需要用到的静态资源

引入Jquery库、页面用到的图片等

![image-20230829221247986](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308292212885.png)

### 新建登录页面

login.jsp

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
	name=adminlogin action="${pageContext.request.contextPath}/user/login.do"  >
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

### 新建系统主页面

main.jsp

为了方便测试登录

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
Main.jsp
</body>
</html>
```

登录测试

![image-20230829221420558](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308292214146.png)

![image-20230829221437305](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308292214602.png)

## 系统主页面搭建

```
系统主页面采用easyui作为主体框架 需要新增自定义图标（thems/usericons）及设定主页logo
```

main.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        // 打开窗口
        // text 窗口标题显示的文本
        // url 窗口需要展示的页面
        // iconCls 窗口的图标
        function openTab(text,url,iconCls){
            if($("#tabs").tabs("exists",text)){
                $("#tabs").tabs("select",text);
            }else{
                var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${pageContext.request.contextPath}/page/"+url+"'></iframe>";
                // 窗口不存在则新增一个窗口
                $("#tabs").tabs("add",{
                    title:text,
                    iconCls:iconCls,
                    closable:true,
                    content:content // 窗口的内容
                });
            }
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
    版本所有 @ Copyright
</div>
</body>
</html>
```

![image-20230830081653846](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308300817695.png)

## 基础数据

### 用户信息管理-列表

#### UserDao

添加查询用户信息列表

```java
package com.et.dao;

import com.et.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 用户Dao接口
 * @author Administrator
 *
 */
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

#### UserMapper

添加对应的查询映射

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
        </where>
    </select>
</mapper>
```

#### PageBean

封装分页工具类

```java
package com.et.entity;

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
		return (page-1)*pageSize;
	}
}

```

#### ResponseUtil

向前端发送数据的工具类

```java
package com.et.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;


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

字符串工具类

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

#### UserService

业务层添加查询用户列表信息方法

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
package com.et.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.et.dao.UserDao;
import com.et.entity.User;
import com.et.service.UserService;

import java.util.List;
import java.util.Map;

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

	@Override
	public List<User> find(Map<String, Object> map) {
		return userDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return userDao.getTotal(map);
	}
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

import com.et.entity.PageBean;
import com.et.util.ResponseUtil;
import com.et.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
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

	private Logger log = Logger.getLogger(UserController.class);

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
			log.error("用户名或密码错误");
			return "login";
		}else{
			HttpSession session=request.getSession();
			session.setAttribute("currentUser", resultUser);
			log.info("登录系统");
			return "redirect:/main.jsp";
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
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userName", StringUtil.formatLike(s_user.getUserName()));
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

#### userManage.jsp

用户信息管理页面

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
        function searchUser(){
            $("#dg").datagrid('load',{
                "userName":$("#s_userName").val()
            });
        }
    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 1px">
<table id="dg" title="用户管理" class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/user/list.do" fit="true" toolbar="#tb">
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
        <a href="javascript:searchUser()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>
</body>
</html>
```

#### 测试

![image-20230830084427368](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308300844593.png)

#### 页面调整

```
去掉右侧空白 scrollbarSize="0"
弹窗遮罩
	在弹窗div上添加modal="true"
```



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

#### UserMapper

```java
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

#### UserService接口

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

#### UserService接口实现

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

#### userManage.jsp

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
                "userName":$("#s_userName").val()
            });
        }
        // 打开弹窗
        function openUserAddDialog(){
            $("#dlg").dialog("open").dialog("setTitle","添加用户信息");
            url="${pageContext.request.contextPath}/user/save.do";
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
            url="${pageContext.request.contextPath}/user/save.do?id="+row.id;
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
    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 1px">
<table id="dg" title="用户管理" class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/user/list.do" fit="true" toolbar="#tb">
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
        <a href="javascript:searchUser()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>

<div id="dlg" class="easyui-dialog" style="width:620px;height:250px;padding: 10px 20px"
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

![image-20230830090725816](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308300907132.png)



![image-20230830090702654](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308300907737.png)

### 用户信息管理-删除

#### UserDao

```java
/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
```

#### UserMapper

```xml
  <delete id="delete" parameterType="Integer">
        delete from t_user where id=#{id}
    </delete>
```

#### UserService接口

```java
/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
```

#### UserService接口实现

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

#### userManage.jsp

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
                "userName":$("#s_userName").val()
            });
        }
        // 打开弹窗
        function openUserAddDialog(){
            $("#dlg").dialog("open").dialog("setTitle","添加用户信息");
            url="${pageContext.request.contextPath}/user/save.do";
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
            url="${pageContext.request.contextPath}/user/save.do?id="+row.id;
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
		// 删除用户信息 - 可批量删除
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
                    $.post("${pageContext.request.contextPath}/user/delete.do",{ids:ids},function(result){
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
       fitColumns="true" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/user/list.do" fit="true" toolbar="#tb">
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
        <a href="javascript:searchUser()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>

<div id="dlg" class="easyui-dialog" style="width:620px;height:250px;padding: 10px 20px"
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

![image-20230830091118059](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308300911163.png)

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

实体类

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

import java.util.List;
import java.util.Map;

import com.et.entity.Product;

/**
 * 产品Dao接口
 * @author Administrator
 *
 */
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

#### ProductService接口

```java
package com.et.service;

import java.util.List;
import java.util.Map;

import com.et.entity.Product;

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

#### ProductService接口实现

```java
package com.et.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.et.dao.ProductDao;
import com.et.entity.Product;
import com.et.service.ProductService;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.et.entity.PageBean;
import com.et.entity.Product;
import com.et.service.ProductService;
import com.et.util.ResponseUtil;
import com.et.util.StringUtil;

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
<table id="dg" title="产品信息查询" class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true" scrollbarSize="0"
       url="${pageContext.request.contextPath}/product/list.do" fit="true" toolbar="#tb">
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

![image-20230830094800192](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308300948578.png)

### 数据字典-列表

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

import java.util.List;
import java.util.Map;

import com.et.entity.DataDic;

/**
 * 数据字典Dao接口
 * @author Administrator
 *
 */
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

</mapper>
```

#### DataDicService接口

```java
package com.et.service;

import java.util.List;
import java.util.Map;

import com.et.entity.DataDic;

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
}

```

#### DataDicService接口实现

```java
package com.et.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.et.dao.DataDicDao;
import com.et.entity.DataDic;
import com.et.service.DataDicService;

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

}

```

#### DataDicController

```java
package com.et.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.et.entity.PageBean;
import com.et.entity.DataDic;
import com.et.service.DataDicService;
import com.et.util.ResponseUtil;
import com.et.util.StringUtil;

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


        function searchDataDic(){
            $("#dg").datagrid('load',{
                "dataDicName":$("#s_dataDicName").combobox("getValue"),
                "dataDicValue":$("#s_dataDicValue").val()
            });
        }

    </script>
    <title>数据字典</title>
</head>
<body style="margin: 1px">
<table id="dg" title="数据字典管理" class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/dataDic/list.do" fit="true" toolbar="#tb">
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
        &nbsp;数据字典名：&nbsp;<input class="easyui-combobox" id="s_dataDicName" data-options="panelHeight:'auto',editable:false,valueField:'dataDicName',textField:'dataDicName',url:'${pageContext.request.contextPath}/dataDic/findDataDicName.do'"/>
        &nbsp;数据字典值：&nbsp;<input type="text" id="s_dataDicValue" size="20" onkeydown="if(event.keyCode==13) searchDataDic()"/>
        <a href="javascript:searchDataDic()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>

</body>
</html>
```

![image-20230830101001170](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301010533.png)

### 数据字典-添加、修改

#### DataDicDao

```java
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
```

#### DataDicMapper

```xml
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


```

#### DataDicService接口

```java
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
```

#### DataDicService实现

```java
@Override
	public int update(DataDic dataDic) {
		return dataDicDao.update(dataDic);
	}

	@Override
	public int add(DataDic dataDic) {
		return dataDicDao.add(dataDic);
	}


```

#### DataDicController

```java
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
            url="${pageContext.request.contextPath}/dataDic/save.do";
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
            url="${pageContext.request.contextPath}/dataDic/save.do?id="+row.id;
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
    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 1px">
<table id="dg" title="数据字典管理" class="easyui-datagrid" scrollbarSize="0"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/dataDic/list.do" fit="true" toolbar="#tb">
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
        &nbsp;数据字典名：&nbsp;<input class="easyui-combobox" id="s_dataDicName" data-options="panelHeight:'auto',editable:false,valueField:'dataDicName',textField:'dataDicName',url:'${pageContext.request.contextPath}/dataDic/findDataDicName.do'"/>
        &nbsp;数据字典值：&nbsp;<input type="text" id="s_dataDicValue" size="20" onkeydown="if(event.keyCode==13) searchDataDic()"/>
        <a href="javascript:searchDataDic()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>

<div id="dlg" class="easyui-dialog" modal="true" style="width:620px;height:150px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">

    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>数据字典名：</td>
                <td><input class="easyui-combobox" id="dataDicName" name="dataDicName" data-options="panelHeight:'auto',valueField:'dataDicName',textField:'dataDicName',url:'${pageContext.request.contextPath}/dataDic/findDataDicName.do'"/>&nbsp;<font color="red">*</font></td>
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

![image-20230830102446649](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301024172.png)

### 数据字典-删除

#### DataDicDao

```java
/**
	 * 删除数据字典
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
```

#### DataDicMapper

```xml
<delete id="delete" parameterType="Integer">
		delete from t_dataDic where id=#{id}
	</delete>
```

#### DataDicService接口

```java
/**
	 * 删除数据字典
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
```

#### DataDicService实现

```java
@Override
	public int delete(Integer id) {
		return dataDicDao.delete(id);
	}
```

#### DataDicController

```java
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
            url="${pageContext.request.contextPath}/dataDic/save.do";
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
            url="${pageContext.request.contextPath}/dataDic/save.do?id="+row.id;
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
                    $.post("${pageContext.request.contextPath}/dataDic/delete.do",{ids:ids},function(result){
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
       url="${pageContext.request.contextPath}/dataDic/list.do" fit="true" toolbar="#tb">
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
        &nbsp;数据字典名：&nbsp;<input class="easyui-combobox" id="s_dataDicName" data-options="panelHeight:'auto',editable:false,valueField:'dataDicName',textField:'dataDicName',url:'${pageContext.request.contextPath}/dataDic/findDataDicName.do'"/>
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
                <td><input class="easyui-combobox" id="dataDicName" name="dataDicName" data-options="panelHeight:'auto',valueField:'dataDicName',textField:'dataDicName',url:'${pageContext.request.contextPath}/dataDic/findDataDicName.do'"/>&nbsp;<font color="red">*</font></td>
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

![image-20230830102645944](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301026410.png)

## 销售管理

### 销售机会

#### 销售机会列表

##### SaleChance

销售机会实体类

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

##### SaleChanceDao

```java
package com.et.dao;

import java.util.List;
import java.util.Map;

import com.et.entity.SaleChance;

/**
 * 销售机会Dao接口
 * @author Administrator
 *
 */
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

}

```

##### SaleChanceMapper

```xml
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
        </where>
    </select>

</mapper>
```

##### SaleChanceService

```java
package com.et.service;

import java.util.List;
import java.util.Map;

import com.et.entity.SaleChance;

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
}

```

##### SaleChanceServiceImpl

```java
package com.et.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.et.dao.SaleChanceDao;
import com.et.entity.SaleChance;
import com.et.service.SaleChanceService;

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

}

```

##### DateJsonValueProcessor

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

##### SaleChanceController

```java
package com.et.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.et.util.DateJsonValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.et.entity.PageBean;
import com.et.entity.SaleChance;
import com.et.service.SaleChanceService;
import com.et.util.ResponseUtil;
import com.et.util.StringUtil;

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
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,SaleChance s_saleChance,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("customerName", StringUtil.formatLike(s_saleChance.getCustomerName()));
		map.put("overView", StringUtil.formatLike(s_saleChance.getOverView()));
		map.put("createMan", StringUtil.formatLike(s_saleChance.getCreateMan()));
		map.put("state", s_saleChance.getState());
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<SaleChance> saleChanceList=saleChanceService.find(map);
		Long total=saleChanceService.getTotal(map);
		JSONObject result=new JSONObject();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
		JSONArray jsonArray=JSONArray.fromObject(saleChanceList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
}

```

##### saleChanceManage.jsp

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

    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 1px">
<table id="dg" title="销售机会信息管理" class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/saleChance/list.do" fit="true" toolbar="#tb">
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

</body>
</html>
```

![image-20230830105011294](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301050055.png)

#### 新增销售机会

##### SaleChanceDao

```java
/**
	 * 添加销售机会
	 * @param saleChance
	 * @return
	 */
	public int add(SaleChance saleChance);
```

##### SaleChanceMapper

```xml
<insert id="add" parameterType="SaleChance">
		insert into t_sale_chance values(null,#{chanceSource},#{customerName},#{cgjl},#{overView},#{linkMan},#{linkPhone},#{description},#{createMan},#{createTime},#{assignMan},#{assignTime},#{state},#{devResult})
	</insert>
```

##### SalceChanceService

```java
/**
	 * 添加销售机会
	 * @param saleChance
	 * @return
	 */
public int add(SaleChance saleChance);
```

##### SaleChanceServiceImpl

```java
@Override
public int add(SaleChance saleChance) {
    return saleChanceDao.add(saleChance);
}
```

##### ScaleChanceController

```java
@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}


/**
	 * 添加或者修改销售机会
	 * @param saleChance
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public void save(SaleChance saleChance,HttpServletResponse response)throws Exception{
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

##### UserController

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

##### common.js

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

##### saleChanceManage.jsp

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
            url="${pageContext.request.contextPath}/saleChance/save.do";
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
            url="${pageContext.request.contextPath}/saleChance/save.do?id="+row.id;
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

    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 1px">
<table id="dg" title="销售机会信息管理" class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/saleChance/list.do" fit="true" toolbar="#tb">
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

<div id="dlg" class="easyui-dialog" style="width:700px;height:450px;padding: 10px 20px"
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
                <td><input type="text" id="createMan" name="createMan" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>创建时间：</td>
                <td><input type="text" id="createTime" name="createTime" />&nbsp;<font color="red">*</font></td>
            </tr>
            <tr>
                <td>指派给：</td>
                <td><input class="easyui-combobox" id="assignMan" name="assignMan" data-options="panelHeight:'auto',editable:false,valueField:'trueName',textField:'trueName',url:'${pageContext.request.contextPath}/user/customerManagerComboList.do'"/></td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>指派时间：</td>
                <td><input type="text" id="assignTime" name="assignTime" /></td>
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

![image-20230830112005157](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301120717.png)

#### 修改销售机会

##### SaleChanceDao

```java
/**
	 * 修改销售机会
	 * @param saleChance
	 * @return
	 */
	public int update(SaleChance saleChance);


```

##### SaleChanceMapper

```java
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
```

##### SalceChanceService

```java
/**
	 * 修改销售机会
	 * @param saleChance
	 * @return
	 */
	public int update(SaleChance saleChance);
```

##### SaleChanceServiceImpl

```java
/**
	 * 修改销售机会
	 * @param saleChance
	 * @return
	 */
	public int update(SaleChance saleChance);
```

##### ScaleChanceController

```java
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
```

jsp中已经添加了修改功能

![image-20230830113413992](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230830113413992.png)

#### 删除销售机会

##### SaleChanceDao

```java
/**
	 * 删除销售机会
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
```

##### SaleChanceMapper

```java
	<delete id="delete" parameterType="Integer">
		delete from t_sale_chance where id=#{id}
	</delete>
```

##### SalceChanceService

```java
/**
	 * 删除销售机会
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
```

##### SaleChanceServiceImpl

```java
	/**
	 * 删除销售机会
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
```

##### ScaleChanceController

```java
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
```

##### saleChanceManage.jsp

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
	 url="${pageContext.request.contextPath}/saleChance/save.do";
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
	 url="${pageContext.request.contextPath}/saleChance/save.do?id="+row.id;
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
			$.post("${pageContext.request.contextPath}/saleChance/delete.do",{ids:ids},function(result){
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
 <table id="dg" title="销售机会信息管理" class="easyui-datagrid"
   fitColumns="true" pagination="true" rownumbers="true"
   url="${pageContext.request.contextPath}/saleChance/list.do" fit="true" toolbar="#tb">
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

 <div id="dlg" class="easyui-dialog" style="width:700px;height:450px;padding: 10px 20px"
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
   			<td><input class="easyui-combobox" id="assignMan" name="assignMan" data-options="panelHeight:'auto',editable:false,valueField:'trueName',textField:'trueName',url:'${pageContext.request.contextPath}/user/customerManagerComboList.do'"/></td>
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

![image-20230830113650495](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301136936.png)

![image-20230830113721729](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301137216.png)

### 客户开发计划

针对已经分配的**销售机会**进行客户开发

#### 客户开发计划列表展示

##### SaleChanceController

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
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
		JSONArray jsonArray=JSONArray.fromObject(saleChanceList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
	}
```

##### SaleCanceMapper

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

##### cusdevplanManage.jsp

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

![image-20230830141030506](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301411929.png)

#### 显示销售机会信息

```
当点击客户开发计划管理“开发”连接时 打开新的tab页 显示销售机会
```

##### SaleChanceDao

```java
/**
	 * 根据id查找实体
	 * @param id
	 * @return
	 */
	public SaleChance findById(Integer id);
```

##### SaleChanceMapper

```xml
<select id="findById" parameterType="Integer" resultMap="SaleChanceResult">
		select * from t_sale_chance where id=#{id}
	</select>
```

##### SaleChanceService

```java
	/*
	* 根据ID查询销售机会
	*
	* */
	SaleChance findById(int parseInt);
```

##### SaleChanceServiceImpl

```java
/*
	 * 根据ID查询销售机会
	 *
	 * */
	public SaleChance findById(int parseInt){
		return saleChanceDao.findById(parseInt);
	}
```

##### SaleChanceController

```java
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
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm"));
		JSONObject jsonObject=JSONObject.fromObject(saleChance, jsonConfig);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
```

##### cusdevplanManage.jsp

```js
  // 在父窗体中打开新的tab页
        function openCusDevPlanTab(id){
            window.parent.openTab('客户开发计划项管理','cusdevplanitemManage.jsp?saleChanceId='+id,'icon-khkfjh');
        }
        // 点击时打开连接
        function formatAction(val,row){
            if(row.devResult==0||row.devResult==1){
                return "<a href='javascript:openCusDevPlanTab("+row.id+")'>开发</a>";
            }else{
                return "<a href=''>查看详细信息</a>";
            }
        }
```

##### cusdevplanitemManage.jsp

被打开的弹窗页

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


 $(function(){

	 $.post("${pageContext.request.contextPath}/saleChance/findById.do",{id:'${param.saleChanceId}'},function(result){
		 $("#customerName").val(result.customerName);
		 $("#chanceSource").val(result.chanceSource);
		 $("#linkMan").val(result.linkMan);
		 $("#linkPhone").val(result.linkPhone);
		 $("#cgjl").val(result.cgjl);
		 $("#overView").val(result.overView);
		 $("#description").val(result.description);
		 $("#createMan").val(result.createMan);
		 $("#createTime").val(result.createTime);
		 $("#assignMan").val(result.assignMan);
		 $("#assignTime").val(result.assignTime);
		},"json");

 });

</script>
<title>Insert title here</title>
</head>
<body style="margin: 15px">

 <div id="p" class="easyui-panel" title="销售机会信息" style="width: 700px;height: 400px;padding: 10px">
 	<table cellspacing="8px">
   		<tr>
   			<td>客户名称：</td>
   			<td><input type="text" id="customerName" name="customerName" readonly="readonly"/></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>机会来源</td>
   			<td><input type="text" id="chanceSource" name="chanceSource" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>联系人：</td>
   			<td><input type="text" id="linkMan" name="linkMan" readonly="readonly"/></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>联系电话：</td>
   			<td><input type="text" id="linkPhone" name="linkPhone" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>成功几率(%)：</td>
   			<td><input type="text" id="cgjl" name="cgjl" readonly="readonly"/></td>
   			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;</td>
   		</tr>
   		<tr>
   			<td>概要：</td>
   			<td colspan="4"><input type="text" id="overView" name="overView" style="width: 420px" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>机会描述：</td>
   			<td colspan="4">
   				<textarea rows="5" cols="50" id="description" name="description" readonly="readonly"></textarea>
   			</td>
   		</tr>
   		<tr>
   			<td>创建人：</td>
   			<td><input type="text" readonly="readonly" id="createMan" name="createMan" /></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>创建时间：</td>
   			<td><input type="text" id="createTime" name="createTime" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>指派给：</td>
   			<td>
   				<input type="text" readonly="readonly" id="assignMan" name="assignMan" />
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>指派时间：</td>
   			<td><input type="text" id="assignTime" name="assignTime" readonly="readonly"/></td>
   		</tr>
 </div>

</body>
</html>
```

![image-20230830144622990](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301446266.png)

#### 开发计划管理(CURD)

需要创建开发计划表（t_cus_dev_plan）

##### CusDevPlan

客户开发计划实体类

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 客户开发计划实体
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CusDevPlan {

	private Integer id; // 编号
	private SaleChance saleChance; // 销售机会
	private String planItem; // 计划项
	private Date planDate; // 计划日期
	private String exeAffect; // 执行效果

}
```

##### CusDevPlanDao

```java
package com.et.dao;

import java.util.List;
import java.util.Map;

import com.et.entity.CusDevPlan;

/**
 * 客户开发计划Dao接口
 * @author Administrator
 *
 */
public interface CusDevPlanDao {

	
	/**
	 * 查询客户开发计划集合
	 * @param map
	 * @return
	 */
	public List<CusDevPlan> find(Map<String,Object> map);
	
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 修改客户开发计划
	 * @param cusDevPlan
	 * @return
	 */
	public int update(CusDevPlan cusDevPlan);
	
	/**
	 * 添加客户开发计划
	 * @param cusDevPlan
	 * @return
	 */
	public int add(CusDevPlan cusDevPlan);
	
	/**
	 * 删除客户开发计划
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
}
```

##### CusDevPlanMapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.et.dao.CusDevPlanDao">

    <resultMap type="CusDevPlan" id="CusDevPlanResult">
        <result property="id" column="id"/>
        <result property="planItem" column="planItem"/>
        <result property="planDate" column="planDate"/>
        <result property="exeAffect" column="exeAffect"/>
        <association property="saleChance" column="saleChanceId" select="com.et.dao.SaleChanceDao.findById"></association>
    </resultMap>

    <select id="find" parameterType="Map" resultMap="CusDevPlanResult">
        select * from t_cus_dev_plan
        <where>
            <if test="saleChanceId!=null and saleChanceId!='' ">
                and saleChanceId = #{saleChanceId}
            </if>
        </where>
        <if test="start!=null and size!=null">
            limit #{start},#{size}
        </if>
    </select>


    <select id="getTotal" parameterType="Map" resultType="Long">
        select count(*) from t_cus_dev_plan
        <where>
            <if test="saleChanceId!=null and saleChanceId!='' ">
                and saleChanceId = #{saleChanceId}
            </if>
        </where>
    </select>

    <insert id="add" parameterType="CusDevPlan">
        insert into t_cus_dev_plan values(null,#{saleChance.id},#{planItem},#{planDate},#{exeAffect})
    </insert>

    <update id="update" parameterType="CusDevPlan">
        update t_cus_dev_plan
        <set>
            <if test="planItem!=null and planItem!='' ">
                planItem=#{planItem},
            </if>
            <if test="planDate!=null">
                planDate=#{planDate},
            </if>
            <if test="exeAffect!=null and exeAffect!='' ">
                exeAffect=#{exeAffect},
            </if>
        </set>
        where id=#{id}
    </update>

    <delete id="delete" parameterType="Integer">
        delete from t_cus_dev_plan where id=#{id}
    </delete>

</mapper> 
```

##### CusDevPlanService

```java
package com.et.service;

import java.util.List;
import java.util.Map;

import com.et.entity.CusDevPlan;

/**
 * 客户开发计划Service接口
 * @author Administrator
 *
 */
public interface CusDevPlanService {

	
	/**
	 * 查询客户开发计划集合
	 * @param map
	 * @return
	 */
	public List<CusDevPlan> find(Map<String,Object> map);
	
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 修改客户开发计划
	 * @param cusDevPlan
	 * @return
	 */
	public int update(CusDevPlan cusDevPlan);
	
	/**
	 * 添加客户开发计划
	 * @param cusDevPlan
	 * @return
	 */
	public int add(CusDevPlan cusDevPlan);
	
	/**
	 * 删除客户开发计划
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
}

```

##### CusDevPlanServiceImpl

```java
package com.et.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.et.dao.CusDevPlanDao;
import com.et.entity.CusDevPlan;
import com.et.service.CusDevPlanService;

/**
 * 客户开发计划Service实现类
 * @author Administrator
 *
 */
@Service("cusDevPlanService")
public class CusDevPlanServiceImpl implements CusDevPlanService{

	@Resource
	private CusDevPlanDao cusDevPlanDao;
	
	@Override
	public List<CusDevPlan> find(Map<String, Object> map) {
		return cusDevPlanDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return cusDevPlanDao.getTotal(map);
	}

	@Override
	public int update(CusDevPlan cusDevPlan) {
		return cusDevPlanDao.update(cusDevPlan);
	}

	@Override
	public int add(CusDevPlan cusDevPlan) {
		return cusDevPlanDao.add(cusDevPlan);
	}

	@Override
	public int delete(Integer id) {
		return cusDevPlanDao.delete(id);
	}

}

```

##### CusDevPlanController

```java
package com.et.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.et.util.DateJsonValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.et.entity.CusDevPlan;
import com.et.service.CusDevPlanService;
import com.et.util.ResponseUtil;

/**
 * 客户开发计划Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/cusDevPlan")
public class CusDevPlanController {
	
	@Resource
	private CusDevPlanService cusDevPlanService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	 
	/**
	 * 分页条件查询客户开发计划
	 * @param page
	 * @param rows
	 * @param s_cusDevPlan
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="saleChanceId",required=false)String saleChanceId,HttpServletResponse response)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("saleChanceId", saleChanceId);
		List<CusDevPlan> cusDevPlanList=cusDevPlanService.find(map);
		JSONObject result=new JSONObject();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"saleChance"});
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray=JSONArray.fromObject(cusDevPlanList,jsonConfig);
		result.put("rows", jsonArray);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 添加或者修改客户开发计划
	 * @param cusDevPlan
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(CusDevPlan cusDevPlan,HttpServletResponse response)throws Exception{
		int resultTotal=0; // 操作的记录条数
		if(cusDevPlan.getId()==null){
			resultTotal=cusDevPlanService.add(cusDevPlan);
		}else{
			resultTotal=cusDevPlanService.update(cusDevPlan);
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
	 * 删除客户开发计划
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="id")String id,HttpServletResponse response)throws Exception{
		cusDevPlanService.delete(Integer.parseInt(id));
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
	
	
}

```

##### cusdevplanitemManage.jsp

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
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.edatagrid.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
<script type="text/javascript">
 

 $(function(){
	
	 $.post("${pageContext.request.contextPath}/saleChance/findById.do",{id:'${param.saleChanceId}'},function(result){
		 $("#customerName").val(result.customerName);
		 $("#chanceSource").val(result.chanceSource);
		 $("#linkMan").val(result.linkMan);
		 $("#linkPhone").val(result.linkPhone);
		 $("#cgjl").val(result.cgjl);
		 $("#overView").val(result.overView);
		 $("#description").val(result.description);
		 $("#createMan").val(result.createMan);
		 $("#createTime").val(result.createTime);
		 $("#assignMan").val(result.assignMan);
		 $("#assignTime").val(result.assignTime);
		},"json");
	 
	 $("#dg").edatagrid({
		url:'${pageContext.request.contextPath}/cusDevPlan/list.do?saleChanceId=${param.saleChanceId}',
		saveUrl:'${pageContext.request.contextPath}/cusDevPlan/save.do?saleChance.id=${param.saleChanceId}',
		updateUrl:'${pageContext.request.contextPath}/cusDevPlan/save.do?saleChance.id=${param.saleChanceId}',
		destroyUrl:'${pageContext.request.contextPath}/cusDevPlan/delete.do'
	 });
 });
 
</script>
<title>Insert title here</title>
</head>
<body style="margin: 15px">
 
 <div id="p" class="easyui-panel" title="销售机会信息" style="width: 700px;height: 400px;padding: 10px">
 	<table cellspacing="8px">
   		<tr>
   			<td>客户名称：</td>
   			<td><input type="text" id="customerName" name="customerName" readonly="readonly"/></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>机会来源</td>
   			<td><input type="text" id="chanceSource" name="chanceSource" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>联系人：</td>
   			<td><input type="text" id="linkMan" name="linkMan" readonly="readonly"/></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>联系电话：</td>
   			<td><input type="text" id="linkPhone" name="linkPhone" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>成功几率(%)：</td>
   			<td><input type="text" id="cgjl" name="cgjl" readonly="readonly"/></td>
   			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;</td>
   		</tr>
   		<tr>
   			<td>概要：</td>
   			<td colspan="4"><input type="text" id="overView" name="overView" style="width: 420px" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>机会描述：</td>
   			<td colspan="4">
   				<textarea rows="5" cols="50" id="description" name="description" readonly="readonly"></textarea>
   			</td>
   		</tr>
   		<tr>
   			<td>创建人：</td>
   			<td><input type="text" readonly="readonly" id="createMan" name="createMan" /></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>创建时间：</td>
   			<td><input type="text" id="createTime" name="createTime" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>指派给：</td>
   			<td>
   				<input type="text" readonly="readonly" id="assignMan" name="assignMan" />
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>指派时间：</td>
   			<td><input type="text" id="assignTime" name="assignTime" readonly="readonly"/></td>
   		</tr>
   	</table>
 </div>
 
 <br/>
 <table id="dg" title="开发计划项" style="width:700px;height:250px"
   toolbar="#toolbar" idField="id" rownumbers="true" fitColumns="true" singleSelect="true">
   <thead>
   	<tr>
   		<th field="id" width="50">编号</th>
   		<th field="planDate" width="50" editor="{type:'datebox',options:{required:true}}">日期</th>
   		<th field="planItem" width="100" editor="{type:'validatebox',options:{required:true}}">计划内容</th>
   		<th field="exeAffect" width="100" editor="{type:'validatebox',options:{required:true}}">执行效果</th>
   	</tr>
   </thead>
 </table>
 
 <div id="toolbar">
 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#dg').edatagrid('addRow')">添加计划</a>
 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#dg').edatagrid('destroyRow')">删除计划</a>
 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#dg').edatagrid('saveRow');$('#dg').edatagrid('reload')">保存计划</a>
 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#dg').edatagrid('cancelRow')">撤销行</a>
 </div>
</body>
</html>
```

![image-20230830150927391](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301510421.png)

##### 功能完善

###### 添加开发计划项时修改开发计划状态

CusDevPlanController

```java
@Resource
	private SaleChanceService saleChanceService;
/**
	 * 添加或者修改客户开发计划
	 * @param cusDevPlan
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(CusDevPlan cusDevPlan,HttpServletResponse response)throws Exception{
		int resultTotal=0; // 操作的记录条数
		if(cusDevPlan.getId()==null){
			SaleChance saleChance=new SaleChance();
			saleChance.setId(cusDevPlan.getSaleChance().getId());
			saleChance.setDevResult(1); // 状态修改成"开发中"
			saleChanceService.update(saleChance);
			resultTotal=cusDevPlanService.add(cusDevPlan);
		}else{
			resultTotal=cusDevPlanService.update(cusDevPlan);
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
```

###### 销售机会开发成功和终止开发功能实现

cusdevplanitemManage.jsp

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
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.edatagrid.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
<script type="text/javascript">
 

 $(function(){
	
	 $.post("${pageContext.request.contextPath}/saleChance/findById.do",{id:'${param.saleChanceId}'},function(result){
		 $("#customerName").val(result.customerName);
		 $("#chanceSource").val(result.chanceSource);
		 $("#linkMan").val(result.linkMan);
		 $("#linkPhone").val(result.linkPhone);
		 $("#cgjl").val(result.cgjl);
		 $("#overView").val(result.overView);
		 $("#description").val(result.description);
		 $("#createMan").val(result.createMan);
		 $("#createTime").val(result.createTime);
		 $("#assignMan").val(result.assignMan);
		 $("#assignTime").val(result.assignTime);
		},"json");
	 
	 $("#dg").edatagrid({
		url:'${pageContext.request.contextPath}/cusDevPlan/list.do?saleChanceId=${param.saleChanceId}',
		saveUrl:'${pageContext.request.contextPath}/cusDevPlan/save.do?saleChance.id=${param.saleChanceId}',
		updateUrl:'${pageContext.request.contextPath}/cusDevPlan/save.do?saleChance.id=${param.saleChanceId}',
		destroyUrl:'${pageContext.request.contextPath}/cusDevPlan/delete.do'
	 });
 });
 
 function updateSaleChanceDevResult(devResult){
	 $.post("${pageContext.request.contextPath}/cusDevPlan/updateSaleChanceDevResult.do",{id:'${param.saleChanceId}',devResult:devResult},function(result){
		 if(result.success){
			 $.messager.alert("系统提示","执行成功！");
		 }else{
			 $.messager.alert("系统提示","执行失败！");
		 }
	 },"json");
 }
 
</script>
<title>Insert title here</title>
</head>
<body style="margin: 15px">
 
 <div id="p" class="easyui-panel" title="销售机会信息" style="width: 700px;height: 400px;padding: 10px">
 	<table cellspacing="8px">
   		<tr>
   			<td>客户名称：</td>
   			<td><input type="text" id="customerName" name="customerName" readonly="readonly"/></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>机会来源</td>
   			<td><input type="text" id="chanceSource" name="chanceSource" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>联系人：</td>
   			<td><input type="text" id="linkMan" name="linkMan" readonly="readonly"/></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>联系电话：</td>
   			<td><input type="text" id="linkPhone" name="linkPhone" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>成功几率(%)：</td>
   			<td><input type="text" id="cgjl" name="cgjl" readonly="readonly"/></td>
   			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;</td>
   		</tr>
   		<tr>
   			<td>概要：</td>
   			<td colspan="4"><input type="text" id="overView" name="overView" style="width: 420px" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>机会描述：</td>
   			<td colspan="4">
   				<textarea rows="5" cols="50" id="description" name="description" readonly="readonly"></textarea>
   			</td>
   		</tr>
   		<tr>
   			<td>创建人：</td>
   			<td><input type="text" readonly="readonly" id="createMan" name="createMan" /></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>创建时间：</td>
   			<td><input type="text" id="createTime" name="createTime" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>指派给：</td>
   			<td>
   				<input type="text" readonly="readonly" id="assignMan" name="assignMan" />
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>指派时间：</td>
   			<td><input type="text" id="assignTime" name="assignTime" readonly="readonly"/></td>
   		</tr>
   	</table>
 </div>
 
 <br/>
 <table id="dg" title="开发计划项" style="width:700px;height:250px"
   toolbar="#toolbar" idField="id" rownumbers="true" fitColumns="true" singleSelect="true">
   <thead>
   	<tr>
   		<th field="id" width="50">编号</th>
   		<th field="planDate" width="50" editor="{type:'datebox',options:{required:true}}">日期</th>
   		<th field="planItem" width="100" editor="{type:'validatebox',options:{required:true}}">计划内容</th>
   		<th field="exeAffect" width="100" editor="{type:'validatebox',options:{required:true}}">执行效果</th>
   	</tr>
   </thead>
 </table>
 
 <div id="toolbar">
 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#dg').edatagrid('addRow')">添加计划</a>
 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#dg').edatagrid('destroyRow')">删除计划</a>
 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#dg').edatagrid('saveRow');$('#dg').edatagrid('reload')">保存计划</a>
 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#dg').edatagrid('cancelRow')">撤销行</a>
 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-kfcg" plain="true" onclick="updateSaleChanceDevResult(2)">开发成功</a>
 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-zzkf" plain="true" onclick="updateSaleChanceDevResult(3)">终止开发</a>
 </div>
</body>
</html>
```

CusDevPalnController

```java
/**
	 * 修改客户开发状态
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateSaleChanceDevResult")
	public String updateSaleChanceDevResult(@RequestParam(value="id")String id,@RequestParam(value="devResult")String devResult,HttpServletResponse response)throws Exception{
		SaleChance saleChance=new SaleChance();
		saleChance.setId(Integer.parseInt(id));
		saleChance.setDevResult(Integer.parseInt(devResult));
		int resultTotal=saleChanceService.update(saleChance);
		JSONObject result=new JSONObject();
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}
```

![image-20230830152619846](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301526360.png)

![image-20230830152636323](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301526749.png)

![image-20230830152657065](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301526646.png)

###### 查看详细信息

cusdevplanManage.jsp

添加打开查看详细信息操作

```jsp
 // 查看开发详细信息 - 父窗体中打开
        function openCusDevPlanTab2(id){
            window.parent.openTab('查看客户开发计划项','cusdevplanitemManage.jsp?saleChanceId='+id+'&show=true','icon-khkfjh');
        }

        function formatAction(val,row){
            if(row.devResult==0||row.devResult==1){
                return "<a href='javascript:openCusDevPlanTab("+row.id+")'>开发</a>";
            }else{
                return "<a href='javascript:openCusDevPlanTab2("+row.id+")'>查看详细信息</a>";
            }
        }
```

cusdevplanitemManage.jsp

隐藏按钮 - 只是查看信息

```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="toolbar">
 	<c:if test="${param.show!='true' }">
	 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#dg').edatagrid('addRow')">添加计划</a>
	 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#dg').edatagrid('destroyRow')">删除计划</a>
	 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#dg').edatagrid('saveRow');$('#dg').edatagrid('reload')">保存计划</a>
	 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#dg').edatagrid('cancelRow')">撤销行</a>
	 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-kfcg" plain="true" onclick="updateSaleChanceDevResult(2)">开发成功</a>
	 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-zzkf" plain="true" onclick="updateSaleChanceDevResult(3)">终止开发</a>
 	</c:if>
 </div>
```

![image-20230830155148131](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301551899.png)

## 客户管理

```sql
创建客户表（t_customer）
```

### 客户信息管理

#### 列表展示

##### Customer

客户实体类

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户实体
 * @author Administrator
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	private Integer id; // 编号
	private String khno; // 客户编号 动态生成
	private String name; // 客户名称
	private String area; // 客户地区
	private String cusManager; // 客户经理
	private String level; // 客户等级
	private String myd; // 客户满意度
	private String xyd; // 客户信用度
	private String address; // 客户地址
	private String postCode; // 邮政编码
	private String phone; // 联系电话
	private String fax; // 传真
	private String webSite; // 网址
	private String yyzzzch; // 营业执照注册号
	private String fr; // 法人
	private String zczj; // 注册资金(万元)
	private String nyye; // 年营业额
	private String khyh; // 开户银行
	private String khzh; // 开户帐号
	private String dsdjh; // 地税登记号
	private String gsdjh; // 国税登记号
	private int state; // 客户状态 0 正常  1 客户流失
	
	
}

```

##### CustomerDao

```java
package com.et.dao;

import java.util.List;
import java.util.Map;

import com.et.entity.Customer;

/**
 * 客户Dao接口
 * @author Administrator
 *
 */
public interface CustomerDao {

	
	/**
	 * 查询客户集合
	 * @param map
	 * @return
	 */
	public List<Customer> find(Map<String,Object> map);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);

}

```

##### CustomerMapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.et.dao.CustomerDao">

    <resultMap type="Customer" id="CustomerResult">
        <result property="id" column="id"/>
        <result property="khno" column="khno"/>
        <result property="name" column="name"/>
        <result property="area" column="area"/>
        <result property="cusManager" column="cusManager"/>
        <result property="level" column="level"/>
        <result property="myd" column="myd"/>
        <result property="xyd" column="xyd"/>
        <result property="address" column="address"/>
        <result property="postCode" column="postCode"/>
        <result property="phone" column="phone"/>
        <result property="fax" column="fax"/>
        <result property="webSite" column="webSite"/>
        <result property="yyzzzch" column="yyzzzch"/>
        <result property="fr" column="fr"/>
        <result property="zczj" column="zczj"/>
        <result property="nyye" column="nyye"/>
        <result property="khyh" column="khyh"/>
        <result property="khzh" column="khzh"/>
        <result property="dsdjh" column="dsdjh"/>
        <result property="gsdjh" column="gsdjh"/>
        <result property="state" column="state"/>
    </resultMap>

    <select id="find" parameterType="Map" resultMap="CustomerResult">
        select * from t_customer
        <where>
            <if test="khno!=null and khno!='' ">
                and khno like #{khno}
            </if>
            <if test="name!=null and name!='' ">
                and name like #{name}
            </if>
        </where>
        <if test="start!=null and size!=null">
            limit #{start},#{size}
        </if>
    </select>

    <select id="getTotal" parameterType="Map" resultType="Long">
        select count(*) from t_customer
        <where>
            <if test="khno!=null and khno!='' ">
                and khno like #{khno}
            </if>
            <if test="name!=null and name!='' ">
                and name like #{name}
            </if>
        </where>
    </select>

</mapper>
```

##### CustomerService

```java
package com.et.service;

import java.util.List;
import java.util.Map;

import com.et.entity.Customer;

/**
 * 客户Service接口
 * @author Administrator
 *
 */
public interface CustomerService {

	/**
	 * 查询客户集合
	 * @param map
	 * @return
	 */
	public List<Customer> find(Map<String,Object> map);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
}

```

##### CustomerServiceImpl

```java
package com.et.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.et.dao.CustomerDao;
import com.et.entity.Customer;
import com.et.service.CustomerService;

/**
 * 客户Service接口
 * @author Administrator
 *
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService{

	@Resource
	private CustomerDao customerDao;
	
	@Override
	public List<Customer> find(Map<String, Object> map) {
		return customerDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return customerDao.getTotal(map);
	}

}

```

##### CustomerController

```java
package com.et.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.et.entity.Customer;
import com.et.entity.PageBean;
import com.et.service.CustomerService;
import com.et.util.ResponseUtil;
import com.et.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 客户Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Resource
	private CustomerService customerService;
	
	
	/**
	 * 分页条件查询客户
	 * @param page
	 * @param rows
	 * @param s_customer
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,Customer s_customer,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("khno", StringUtil.formatLike(s_customer.getKhno()));
		map.put("name", StringUtil.formatLike(s_customer.getName()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Customer> customerList=customerService.find(map);
		Long total=customerService.getTotal(map);
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(customerList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
}

```

##### customerManage.jsp

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
 
 
 function searchCustomer(){
	 $("#dg").datagrid('load',{
		"khno":$("#s_khno").val(),
		"name":$("#s_name").val()
	 });
 }
 
</script>
<title>Insert title here</title>
</head>
<body style="margin: 1px">
 <table id="dg" title="客户信息查询" class="easyui-datagrid"
    pagination="true" rownumbers="true"
   url="${pageContext.request.contextPath}/customer/list.do" fit="true" toolbar="#tb">
   <thead data-options="frozen:true">
		<tr>
			<th field="cb" checkbox="true" align="center"></th>
	 		<th field="id" width="50" align="center" hidden="true">编号</th>
	 		<th field="khno" width="150" align="center">客户编号</th>
	 		<th field="name" width="200" align="center">客户名称</th>
	 		<th field="cusManager" width="100" align="center">客户经理</th>
	 		<th field="level" width="100" align="center">客户等级</th>
	 		<th field="phone" width="100" align="center">联系电话</th>
		</tr>
	</thead>
	<thead>
		<tr>
			<th field="area" width="80" align="center">客户地区</th>	
	 		<th field="myd" width="80" align="center">客户满意度</th>
	 		<th field="xyd" width="80" align="center">客户信用度</th>
	 		<th field="address" width="200" align="center" >客户地址</th>
	 		<th field="postCode" width="100" align="center" >邮政编码</th>
	 		<th field="fax" width="100" align="center" >传真</th>
	 		<th field="webSite" width="100" align="center" >网址</th>
	 		<th field="yyzzzch" width="100" align="center" >营业执照注册号</th>
	 		<th field="fr" width="100" align="center" >法人</th>
	 		<th field="zczj" width="100" align="center" >注册资金(万元)</th>
	 		<th field="nyye" width="100" align="center" >年营业额(万元)</th>
	 		<th field="khyh" width="100" align="center" >开户银行</th>
	 		<th field="khzh" width="100" align="center" >开户帐号</th>
	 		<th field="dsdjh" width="100" align="center" >地税登记号</th>
	 		<th field="gsdjh" width="100" align="center" >国税登记号</th>
		</tr>
	</thead>
 </table>
 <div id="tb">
 	<div>
 		&nbsp;客户编号：&nbsp;<input type="text" id="s_khno" size="20" onkeydown="if(event.keyCode==13) searchCustomer()"/>
 		&nbsp;客户名称：&nbsp;<input type="text" id="s_name" size="20" onkeydown="if(event.keyCode==13) searchCustomer()"/>
 		<a href="javascript:searchCustomer()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
 	</div>
 </div>
 
</body>
</html>
```

![image-20230830160254756](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301602580.png)

#### 添加客户信息

##### 数据字典接口

```
根据字典名称获取对应的数据 用于下拉框
客户信息中 客户等级取自数据字典
```

###### DataDicController

```java
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
```

##### CustomerDao

```java
/**
	 * 添加客户
	 * @param customer
	 * @return
	 */
	public int add(Customer customer);
```

##### CustomerMapper

```xml
<insert id="add" parameterType="Customer">
		insert into t_customer values(null,#{khno},#{name},#{area},#{cusManager},#{level},#{myd},#{xyd},#{address},#{postCode},#{phone},#{fax},#{webSite},#{yyzzzch},#{fr},#{zczj},#{nyye},#{khyh},#{khzh},#{dsdjh},#{gsdjh},0);
	</insert>
```

##### CustomerService

```java
/**
	 * 添加客户
	 * @param customer
	 * @return
	 */
	public int add(Customer customer);
```

##### CustomerServiceImpl

```java
@Override
	public int add(Customer customer) {
		return customerDao.add(customer);
	}
```

##### DateUtil

时间工具类

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



##### CustomerController

```java
/**
	 * 添加或者修改客户
	 * @param user
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Customer customer,HttpServletResponse response)throws Exception{
		int resultTotal=0; // 操作的记录条数
		if(customer.getId()==null){
			customer.setKhno("KH"+DateUtil.getCurrentDateStr()); // 动态生成客户编号
			resultTotal=customerService.add(customer);
		}else{
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
```

##### customerManage.jsp

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

 function searchCustomer(){
	 $("#dg").datagrid('load',{
		"khno":$("#s_khno").val(),
		"name":$("#s_name").val()
	 });
 }
 
 function openCustomerAddDialog(){
	 $("#dlg").dialog("open").dialog("setTitle","添加客户信息");
	 url="${pageContext.request.contextPath}/customer/save.do";
 }
 
 function saveCustomer(){
	 $("#fm").form("submit",{
		url:url,
		onSubmit:function(){
			if($("#area").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户地区！");
				return false;
			}
			if($("#cusManager").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户经理！");
				return false;
			}
			if($("#level").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户等级！");
				return false;
			}
			if($("#myd").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户满意度！");
				return false;
			}
			if($("#xyd").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户信用度！");
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
 
 function resetValue(){
	 $("#name").val("");
	 $("#area").combobox("setValue","");
	 $("#cusManager").combobox("setValue","");
	 $("#level").combobox("setValue","");
	 $("#myd").combobox("setValue","");
	 $("#xyd").combobox("setValue","");
	 $("#address").val("");
	 $("#postCode").val("");
	 $("#phone").val("");
	 $("#fax").val("");
	 $("#webSite").val("");
	 $("#yyzzzch").val("");
	 $("#fr").val("");
	 $("#zczj").val("");
	 $("#nyye").val("");
	 $("#khyh").val("");
	 $("#khzh").val("");
	 $("#dsdjh").val("");
	 $("#gsdjh").val("");
 }
 
 function closeCustomerDialog(){
	 $("#dlg").dialog("close");
	 resetValue();
 }
 
</script>
<title>Insert title here</title>
</head>
<body style="margin: 1px">
 <table id="dg" title="客户信息查询" class="easyui-datagrid"
    pagination="true" rownumbers="true"
   url="${pageContext.request.contextPath}/customer/list.do" fit="true" toolbar="#tb">
   <thead data-options="frozen:true">
		<tr>
			<th field="cb" checkbox="true" align="center"></th>
	 		<th field="id" width="50" align="center" hidden="true">编号</th>
	 		<th field="khno" width="150" align="center">客户编号</th>
	 		<th field="name" width="200" align="center">客户名称</th>
	 		<th field="cusManager" width="100" align="center">客户经理</th>
	 		<th field="level" width="100" align="center">客户等级</th>
	 		<th field="phone" width="100" align="center">联系电话</th>
		</tr>
	</thead>
	<thead>
		<tr>
			<th field="area" width="80" align="center">客户地区</th>	
	 		<th field="myd" width="80" align="center">客户满意度</th>
	 		<th field="xyd" width="80" align="center">客户信用度</th>
	 		<th field="address" width="200" align="center" >客户地址</th>
	 		<th field="postCode" width="100" align="center" >邮政编码</th>
	 		<th field="fax" width="100" align="center" >传真</th>
	 		<th field="webSite" width="100" align="center" >网址</th>
	 		<th field="yyzzzch" width="100" align="center" >营业执照注册号</th>
	 		<th field="fr" width="100" align="center" >法人</th>
	 		<th field="zczj" width="100" align="center" >注册资金(万元)</th>
	 		<th field="nyye" width="100" align="center" >年营业额(万元)</th>
	 		<th field="khyh" width="100" align="center" >开户银行</th>
	 		<th field="khzh" width="100" align="center" >开户帐号</th>
	 		<th field="dsdjh" width="100" align="center" >地税登记号</th>
	 		<th field="gsdjh" width="100" align="center" >国税登记号</th>
		</tr>
	</thead>
 </table>
 <div id="tb">
 	<div>
 		<a href="javascript:openCustomerAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">创建</a>
 		<a href="javascript:openCustomerModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
 		<a href="javascript:deleteCustomer()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
 	</div>
 	<div>
 		&nbsp;客户编号：&nbsp;<input type="text" id="s_khno" size="20" onkeydown="if(event.keyCode==13) searchCustomer()"/>
 		&nbsp;客户名称：&nbsp;<input type="text" id="s_name" size="20" onkeydown="if(event.keyCode==13) searchCustomer()"/>
 		<a href="javascript:searchCustomer()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
 	</div>
 </div>
 
 
  <div id="dlg" class="easyui-dialog" style="width:700px;height:450px;padding: 10px 20px"
   closed="true" buttons="#dlg-buttons">
   
   <form id="fm" method="post">
   	<table cellspacing="8px">
   		<tr>
   			<td>客户名称：</td>
   			<td><input type="text" id="name" name="name" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>地区</td>
   			<td>
   				<select class="easyui-combobox" style="width: 154px" id="area" name="area" editable="false" panelHeight="auto" >
					<option value="">请选择地区...</option>	
					<option value="北京">北京</option>
					<option value="南京">南京</option>	
					<option value="上海">上海</option>	
					<option value="广州">广州</option>	
					<option value="天津">天津</option>				
                </select>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>客户经理：</td>
   			<td>
   				<input class="easyui-combobox" id="cusManager" name="cusManager" data-options="panelHeight:'auto',editable:false,valueField:'trueName',textField:'trueName',url:'${pageContext.request.contextPath}/user/customerManagerComboList.do'"/>&nbsp;<font color="red">*</font>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>客户等级：</td>
   			<td>
   				<input class="easyui-combobox" id="level" name="level" data-options="panelHeight:'auto',editable:false,valueField:'dataDicValue',textField:'dataDicValue',url:'${pageContext.request.contextPath}/dataDic/dataDicComboList.do?dataDicName=客户等级'"/>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>客户满意度：</td>
   			<td>
   				<select class="easyui-combobox" style="width: 154px" id="myd" name="myd" editable="false" panelHeight="auto" >
					<option value="">请选择...</option>	
					<option value="☆">☆</option>	
					<option value="☆☆">☆☆</option>	
					<option value="☆☆☆">☆☆☆</option>	
					<option value="☆☆☆☆">☆☆☆☆</option>		
					<option value="☆☆☆☆☆">☆☆☆☆☆</option>				
                </select>&nbsp;<font color="red">*</font>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>客户信用度</td>
   			<td>
   				<select class="easyui-combobox" id="xyd" name="xyd" style="width: 154px" editable="false" panelHeight="auto">
   					<option value="">请选择...</option>
   					<option value="☆">☆</option>	
					<option value="☆☆">☆☆</option>	
					<option value="☆☆☆">☆☆☆</option>	
					<option value="☆☆☆☆">☆☆☆☆</option>		
					<option value="☆☆☆☆☆">☆☆☆☆☆</option>				
                </select>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>邮政编码：</td>
   			<td><input type="text" id="postCode" name="postCode" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>联系电话：</td>
   			<td><input type="text" id="phone" name="phone" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>传真：</td>
   			<td><input type="text" id="fax" name="fax" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>网址：</td>
   			<td><input type="text" id="webSite" name="webSite" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>客户地址</td>
   			<td colspan="4">
   				<input type="text" id="address" name="address" style="width: 400px" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>营业执照注册号：</td>
   			<td><input type="text" id="yyzzzch" name="yyzzzch" /></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>法人：</td>
   			<td><input type="text" id="fr" name="fr" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>注册资金(万元)：</td>
   			<td><input type="text" id="zczj" name="zczj" /></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>年营业额(万元)：</td>
   			<td><input type="text" id="nyye" name="nyye" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>开户银行：</td>
   			<td><input type="text" id="khyh" name="khyh" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>开户帐号：</td>
   			<td><input type="text" id="khzh" name="khzh" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>地税登记号：</td>
   			<td><input type="text" id="dsdjh" name="dsdjh" /></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>国税登记号：</td>
   			<td><input type="text" id="gsdjh" name="gsdjh" /></td>
   		</tr>
   	</table>
   </form>
 </div>
 
 <div id="dlg-buttons">
 	<a href="javascript:saveCustomer()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
 	<a href="javascript:closeCustomerDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
 </div>
</body>
</html>
```

##### Tomcat乱码解决

```
<Connector connectionTimeout="20000" port="8080" URIEncoding="UTF-8" protocol="HTTP/1.1" redirectPort="8443"/>
```

![image-20230830163509506](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301635968.png)

![image-20230830163418078](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301634344.png)

#### 修改和删除客户信息

##### CustomerDao

```java
/**
	 * 修改客户
	 * @param customer
	 * @return
	 */
	public int update(Customer customer);
	
	/**
	 * 删除客户
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
```

##### CustomerMapper

```xml
<update id="update" parameterType="Customer">
	    update t_customer
	    <set>
	    	<if test="khno!=null and khno!='' ">
				khno=#{khno},
			</if>
			<if test="name!=null and name!='' ">
				name=#{name},
			</if>
			<if test="area!=null and area!='' ">
				area=#{area},
			</if>
			<if test="cusManager!=null and cusManager!='' ">
				cusManager=#{cusManager},
			</if>
			<if test="level!=null and level!='' ">
				level=#{level},
			</if>
			<if test="myd!=null and myd!='' ">
				myd=#{myd},
			</if>
			<if test="xyd!=null and xyd!='' ">
				xyd=#{xyd},
			</if>
			<if test="address!=null and address!='' ">
				address=#{address},
			</if>
			<if test="postCode!=null and postCode!='' ">
				postCode=#{postCode},
			</if>
			<if test="phone!=null and phone!='' ">
				phone=#{phone},
			</if>
			<if test="fax!=null and fax!='' ">
				fax=#{fax},
			</if>
			<if test="webSite!=null and webSite!='' ">
				webSite=#{webSite},
			</if>
			<if test="yyzzzch!=null and yyzzzch!='' ">
				yyzzzch=#{yyzzzch},
			</if>
			<if test="fr!=null and fr!='' ">
				fr=#{fr},
			</if>
			<if test="zczj!=null and zczj!='' ">
				zczj=#{zczj},
			</if>
			<if test="nyye!=null and nyye!='' ">
				nyye=#{nyye},
			</if>
			<if test="khyh!=null and khyh!='' ">
				khyh=#{khyh},
			</if>
			<if test="khzh!=null and khzh!='' ">
				khzh=#{khzh},
			</if>
			<if test="dsdjh!=null and dsdjh!='' ">
				dsdjh=#{dsdjh},
			</if>
			<if test="gsdjh!=null and gsdjh!='' ">
				gsdjh=#{gsdjh},
			</if>
	    </set>
	    where id=#{id}
	</update>
	
	<delete id="delete" parameterType="Integer">
		delete from t_customer where id=#{id}
	</delete>
```

##### CustomerService

```java
/**
	 * 修改客户
	 * @param customer
	 * @return
	 */
	public int update(Customer customer);
	
	/**
	 * 删除客户
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
```

##### CustomerServiceImpl

```java
@Override
	public int update(Customer customer) {
		return customerDao.update(customer);
	}

	@Override
	public int delete(Integer id) {
		return customerDao.delete(id);
	}
```

##### CustomerController

```java
/**
	 * 添加或者修改客户
	 * @param user
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Customer customer,HttpServletResponse response)throws Exception{
		int resultTotal=0; // 操作的记录条数
		if(customer.getId()==null){
			customer.setKhno("KH"+DateUtil.getCurrentDateStr()); // 动态生成客户编号
			resultTotal=customerService.add(customer);
		}else{
			resultTotal=customerService.update(customer);
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
	 * 删除客户
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="ids")String ids,HttpServletResponse response)throws Exception{
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			customerService.delete(Integer.parseInt(idsStr[i]));
		}
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
```

##### customerManage.jsp

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

 function searchCustomer(){
	 $("#dg").datagrid('load',{
		"khno":$("#s_khno").val(),
		"name":$("#s_name").val()
	 });
 }
 
 function openCustomerAddDialog(){
	 $("#dlg").dialog("open").dialog("setTitle","添加客户信息");
	 url="${pageContext.request.contextPath}/customer/save.do";
 }
 
 function openCustomerModifyDialog(){
	 var selectedRows=$("#dg").datagrid("getSelections");
	 if(selectedRows.length!=1){
		 $.messager.alert("系统提示","请选择一条要编辑的数据！");
		 return;
	 }
	 var row=selectedRows[0];
	 $("#dlg").dialog("open").dialog("setTitle","编辑客户信息");
	 $("#fm").form("load",row);
	 url="${pageContext.request.contextPath}/customer/save.do?id="+row.id;
 }
 
 function saveCustomer(){
	 $("#fm").form("submit",{
		url:url,
		onSubmit:function(){
			if($("#area").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户地区！");
				return false;
			}
			if($("#cusManager").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户经理！");
				return false;
			}
			if($("#level").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户等级！");
				return false;
			}
			if($("#myd").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户满意度！");
				return false;
			}
			if($("#xyd").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户信用度！");
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
 
 function resetValue(){
	 $("#name").val("");
	 $("#area").combobox("setValue","");
	 $("#cusManager").combobox("setValue","");
	 $("#level").combobox("setValue","");
	 $("#myd").combobox("setValue","");
	 $("#xyd").combobox("setValue","");
	 $("#address").val("");
	 $("#postCode").val("");
	 $("#phone").val("");
	 $("#fax").val("");
	 $("#webSite").val("");
	 $("#yyzzzch").val("");
	 $("#fr").val("");
	 $("#zczj").val("");
	 $("#nyye").val("");
	 $("#khyh").val("");
	 $("#khzh").val("");
	 $("#dsdjh").val("");
	 $("#gsdjh").val("");
 }
 
 function closeCustomerDialog(){
	 $("#dlg").dialog("close");
	 resetValue();
 }
 
 function deleteCustomer(){
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
			$.post("${pageContext.request.contextPath}/customer/delete.do",{ids:ids},function(result){
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
 <table id="dg" title="客户信息查询" class="easyui-datagrid"
    pagination="true" rownumbers="true"
   url="${pageContext.request.contextPath}/customer/list.do" fit="true" toolbar="#tb">
   <thead data-options="frozen:true">
		<tr>
			<th field="cb" checkbox="true" align="center"></th>
	 		<th field="id" width="50" align="center" hidden="true">编号</th>
	 		<th field="khno" width="150" align="center">客户编号</th>
	 		<th field="name" width="200" align="center">客户名称</th>
	 		<th field="cusManager" width="100" align="center">客户经理</th>
	 		<th field="level" width="100" align="center">客户等级</th>
	 		<th field="phone" width="100" align="center">联系电话</th>
		</tr>
	</thead>
	<thead>
		<tr>
			<th field="area" width="80" align="center">客户地区</th>	
	 		<th field="myd" width="80" align="center">客户满意度</th>
	 		<th field="xyd" width="80" align="center">客户信用度</th>
	 		<th field="address" width="200" align="center" >客户地址</th>
	 		<th field="postCode" width="100" align="center" >邮政编码</th>
	 		<th field="fax" width="100" align="center" >传真</th>
	 		<th field="webSite" width="100" align="center" >网址</th>
	 		<th field="yyzzzch" width="100" align="center" >营业执照注册号</th>
	 		<th field="fr" width="100" align="center" >法人</th>
	 		<th field="zczj" width="100" align="center" >注册资金(万元)</th>
	 		<th field="nyye" width="100" align="center" >年营业额(万元)</th>
	 		<th field="khyh" width="100" align="center" >开户银行</th>
	 		<th field="khzh" width="100" align="center" >开户帐号</th>
	 		<th field="dsdjh" width="100" align="center" >地税登记号</th>
	 		<th field="gsdjh" width="100" align="center" >国税登记号</th>
		</tr>
	</thead>
 </table>
 <div id="tb">
 	<div>
 		<a href="javascript:openCustomerAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">创建</a>
 		<a href="javascript:openCustomerModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
 		<a href="javascript:deleteCustomer()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
 	</div>
 	<div>
 		&nbsp;客户编号：&nbsp;<input type="text" id="s_khno" size="20" onkeydown="if(event.keyCode==13) searchCustomer()"/>
 		&nbsp;客户名称：&nbsp;<input type="text" id="s_name" size="20" onkeydown="if(event.keyCode==13) searchCustomer()"/>
 		<a href="javascript:searchCustomer()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
 	</div>
 </div>
 
 
  <div id="dlg" class="easyui-dialog" style="width:700px;height:450px;padding: 10px 20px"
   closed="true" buttons="#dlg-buttons">
   
   <form id="fm" method="post">
   	<table cellspacing="8px">
   		<tr>
   			<td>客户名称：</td>
   			<td><input type="text" id="name" name="name" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>地区</td>
   			<td>
   				<select class="easyui-combobox" style="width: 154px" id="area" name="area" editable="false" panelHeight="auto" >
					<option value="">请选择地区...</option>	
					<option value="北京">北京</option>
					<option value="南京">南京</option>	
					<option value="上海">上海</option>	
					<option value="广州">广州</option>	
					<option value="天津">天津</option>				
                </select>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>客户经理：</td>
   			<td>
   				<input class="easyui-combobox" id="cusManager" name="cusManager" data-options="panelHeight:'auto',editable:false,valueField:'trueName',textField:'trueName',url:'${pageContext.request.contextPath}/user/customerManagerComboList.do'"/>&nbsp;<font color="red">*</font>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>客户等级：</td>
   			<td>
   				<input class="easyui-combobox" id="level" name="level" data-options="panelHeight:'auto',editable:false,valueField:'dataDicValue',textField:'dataDicValue',url:'${pageContext.request.contextPath}/dataDic/dataDicComboList.do?dataDicName=客户等级'"/>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>客户满意度：</td>
   			<td>
   				<select class="easyui-combobox" style="width: 154px" id="myd" name="myd" editable="false" panelHeight="auto" >
					<option value="">请选择...</option>	
					<option value="☆">☆</option>	
					<option value="☆☆">☆☆</option>	
					<option value="☆☆☆">☆☆☆</option>	
					<option value="☆☆☆☆">☆☆☆☆</option>		
					<option value="☆☆☆☆☆">☆☆☆☆☆</option>				
                </select>&nbsp;<font color="red">*</font>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>客户信用度</td>
   			<td>
   				<select class="easyui-combobox" id="xyd" name="xyd" style="width: 154px" editable="false" panelHeight="auto">
   					<option value="">请选择...</option>
   					<option value="☆">☆</option>	
					<option value="☆☆">☆☆</option>	
					<option value="☆☆☆">☆☆☆</option>	
					<option value="☆☆☆☆">☆☆☆☆</option>		
					<option value="☆☆☆☆☆">☆☆☆☆☆</option>				
                </select>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>邮政编码：</td>
   			<td><input type="text" id="postCode" name="postCode" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>联系电话：</td>
   			<td><input type="text" id="phone" name="phone" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>传真：</td>
   			<td><input type="text" id="fax" name="fax" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>网址：</td>
   			<td><input type="text" id="webSite" name="webSite" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>客户地址</td>
   			<td colspan="4">
   				<input type="text" id="address" name="address" style="width: 400px" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>营业执照注册号：</td>
   			<td><input type="text" id="yyzzzch" name="yyzzzch" /></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>法人：</td>
   			<td><input type="text" id="fr" name="fr" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>注册资金(万元)：</td>
   			<td><input type="text" id="zczj" name="zczj" /></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>年营业额(万元)：</td>
   			<td><input type="text" id="nyye" name="nyye" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>开户银行：</td>
   			<td><input type="text" id="khyh" name="khyh" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>开户帐号：</td>
   			<td><input type="text" id="khzh" name="khzh" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>地税登记号：</td>
   			<td><input type="text" id="dsdjh" name="dsdjh" /></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>国税登记号：</td>
   			<td><input type="text" id="gsdjh" name="gsdjh" /></td>
   		</tr>
   	</table>
   </form>
 </div>
 
 <div id="dlg-buttons">
 	<a href="javascript:saveCustomer()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
 	<a href="javascript:closeCustomerDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
 </div>
</body>
</html>
```

![image-20230830165413548](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301654838.png)

![image-20230830165439039](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301654337.png)

#### 客户联系人管理（CURD）

```
客户中可能存在多个联系人 因此对其进行扩展
新增客户联系人表（t_customer_linkman）
```

##### CustomerDao

需要根据客户id查询客户主体信息

```java
/**
	 * 通过Id查找实体
	 * @param id
	 * @return
	 */
	public Customer findById(Integer id);
```

##### CustomerMapper

```xml
<select id="findById" parameterType="Integer" resultMap="CustomerResult">
		select * from t_customer where id=#{id}
	</select>
```

##### CustomerService

```java
/**
	 * 通过Id查找实体
	 * @param id
	 * @return
	 */
	public Customer findById(Integer id);
```

##### CustomerServiceImpl

```java
@Override
	public Customer findById(Integer id) {
		return customerDao.findById(id);
	}
```

##### CustomerController

```java
/**
	 * 通过ID查找实体
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	public String findById(@RequestParam(value="id")String id,HttpServletResponse response)throws Exception{
		Customer customer=customerService.findById(Integer.parseInt(id));
		JSONObject jsonObject=JSONObject.fromObject(customer);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
```

##### LinkMan

客户联系人实体类

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 联系人实体
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkMan {

	private Integer id; // 编号
	private Customer customer; // 所属客户
	private String linkName; // 姓名
	private String sex; // 性别
	private String zhiwei; // 职位
	private String officePhone; // 办公电话
	private String phone; // 手机

	
}

```

##### LinkManDao

```java
package com.et.dao;

import java.util.List;
import java.util.Map;

import com.et.entity.LinkMan;

/**
 * 联系人Dao接口
 * @author Administrator
 *
 */
public interface LinkManDao {

	
	/**
	 * 查询联系人集合
	 * @param map
	 * @return
	 */
	public List<LinkMan> find(Map<String,Object> map);
	
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 修改联系人
	 * @param linkMan
	 * @return
	 */
	public int update(LinkMan linkMan);
	
	/**
	 * 添加联系人
	 * @param linkMan
	 * @return
	 */
	public int add(LinkMan linkMan);
	
	/**
	 * 删除联系人
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
}

```

##### LinkManMapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.et.dao.LinkManDao">

    <resultMap type="LinkMan" id="LinkManResult">
        <result property="id" column="id"/>
        <result property="linkName" column="linkName"/>
        <result property="sex" column="sex"/>
        <result property="zhiwei" column="zhiwei"/>
        <result property="officePhone" column="officePhone"/>
        <result property="phone" column="phone"/>
        <association property="customer" column="cusId" select="com.et.dao.CustomerDao.findById"></association>
    </resultMap>

    <select id="find" parameterType="Map" resultMap="LinkManResult">
        select * from t_customer_linkman
        <where>
            <if test="cusId!=null and cusId!='' ">
                and cusId = #{cusId}
            </if>
        </where>
        <if test="start!=null and size!=null">
            limit #{start},#{size}
        </if>
    </select>


    <select id="getTotal" parameterType="Map" resultType="Long">
        select count(*) from t_customer_linkman
        <where>
            <if test="cusId!=null and cusId!='' ">
                and cusId = #{cusId}
            </if>
        </where>
    </select>

    <insert id="add" parameterType="LinkMan">
        insert into t_customer_linkman values(null,#{customer.id},#{linkName},#{sex},#{zhiwei},#{officePhone},#{phone})
    </insert>

    <update id="update" parameterType="LinkMan">
        update t_customer_linkman
        <set>
            <if test="linkName!=null and linkName!='' ">
                linkName=#{linkName},
            </if>
            <if test="sex!=null and sex!='' ">
                sex=#{sex},
            </if>
            <if test="zhiwei!=null and zhiwei!='' ">
                zhiwei=#{zhiwei},
            </if>
            <if test="officePhone!=null and officePhone!='' ">
                officePhone=#{officePhone},
            </if>
            <if test="phone!=null and phone!='' ">
                phone=#{phone},
            </if>
        </set>
        where id=#{id}
    </update>

    <delete id="delete" parameterType="Integer">
        delete from t_customer_linkman where id=#{id}
    </delete>

</mapper> 
```

##### LinkManService

```java
package com.et.service;

import java.util.List;
import java.util.Map;

import com.et.entity.LinkMan;

/**
 * 联系人Service接口
 * @author Administrator
 *
 */
public interface LinkManService {

	
	/**
	 * 查询联系人集合
	 * @param map
	 * @return
	 */
	public List<LinkMan> find(Map<String,Object> map);
	
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 修改联系人
	 * @param linkMan
	 * @return
	 */
	public int update(LinkMan linkMan);
	
	/**
	 * 添加联系人
	 * @param linkMan
	 * @return
	 */
	public int add(LinkMan linkMan);
	
	/**
	 * 删除联系人
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
}

```

##### LinkManServiceImpl

```java
package com.et.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.et.dao.LinkManDao;
import com.et.entity.LinkMan;
import com.et.service.LinkManService;

/**
 * 联系人Service实现类
 * @author Administrator
 *
 */
@Service("linkManService")
public class LinkManServiceImpl implements LinkManService{

	@Resource
	private LinkManDao linkManDao;
	
	@Override
	public List<LinkMan> find(Map<String, Object> map) {
		return linkManDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return linkManDao.getTotal(map);
	}

	@Override
	public int update(LinkMan linkMan) {
		return linkManDao.update(linkMan);
	}

	@Override
	public int add(LinkMan linkMan) {
		return linkManDao.add(linkMan);
	}

	@Override
	public int delete(Integer id) {
		return linkManDao.delete(id);
	}

}

```

##### LinkManController

```java
package com.et.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.et.entity.LinkMan;
import com.et.service.LinkManService;
import com.et.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 联系人Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/linkMan")
public class LinkManController {
	
	@Resource
	private LinkManService linkManService;
	
	 
	/**
	 * 分页条件查询联系人
	 * @param page
	 * @param rows
	 * @param s_linkMan
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="cusId",required=false)String cusId,HttpServletResponse response)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cusId", cusId);
		List<LinkMan> linkManList=linkManService.find(map);
		JSONObject result=new JSONObject();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"customer"});
		JSONArray jsonArray=JSONArray.fromObject(linkManList,jsonConfig);
		result.put("rows", jsonArray);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 添加或者修改联系人
	 * @param linkMan
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(LinkMan linkMan,HttpServletResponse response)throws Exception{
		int resultTotal=0; // 操作的记录条数
		if(linkMan.getId()==null){
			resultTotal=linkManService.add(linkMan);
		}else{
			resultTotal=linkManService.update(linkMan);
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
	 * 删除联系人
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="id")String id,HttpServletResponse response)throws Exception{
		linkManService.delete(Integer.parseInt(id));
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
	
	
	
}

```

##### customerManage.jsp

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

 function searchCustomer(){
	 $("#dg").datagrid('load',{
		"khno":$("#s_khno").val(),
		"name":$("#s_name").val()
	 });
 }
 
 function openCustomerAddDialog(){
	 $("#dlg").dialog("open").dialog("setTitle","添加客户信息");
	 url="${pageContext.request.contextPath}/customer/save.do";
 }
 
 function openCustomerModifyDialog(){
	 var selectedRows=$("#dg").datagrid("getSelections");
	 if(selectedRows.length!=1){
		 $.messager.alert("系统提示","请选择一条要编辑的数据！");
		 return;
	 }
	 var row=selectedRows[0];
	 $("#dlg").dialog("open").dialog("setTitle","编辑客户信息");
	 $("#fm").form("load",row);
	 url="${pageContext.request.contextPath}/customer/save.do?id="+row.id;
 }
 
 function saveCustomer(){
	 $("#fm").form("submit",{
		url:url,
		onSubmit:function(){
			if($("#area").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户地区！");
				return false;
			}
			if($("#cusManager").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户经理！");
				return false;
			}
			if($("#level").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户等级！");
				return false;
			}
			if($("#myd").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户满意度！");
				return false;
			}
			if($("#xyd").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户信用度！");
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
 
 function resetValue(){
	 $("#name").val("");
	 $("#area").combobox("setValue","");
	 $("#cusManager").combobox("setValue","");
	 $("#level").combobox("setValue","");
	 $("#myd").combobox("setValue","");
	 $("#xyd").combobox("setValue","");
	 $("#address").val("");
	 $("#postCode").val("");
	 $("#phone").val("");
	 $("#fax").val("");
	 $("#webSite").val("");
	 $("#yyzzzch").val("");
	 $("#fr").val("");
	 $("#zczj").val("");
	 $("#nyye").val("");
	 $("#khyh").val("");
	 $("#khzh").val("");
	 $("#dsdjh").val("");
	 $("#gsdjh").val("");
 }
 
 function closeCustomerDialog(){
	 $("#dlg").dialog("close");
	 resetValue();
 }
 
 function deleteCustomer(){
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
			$.post("${pageContext.request.contextPath}/customer/delete.do",{ids:ids},function(result){
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
  
 function openCustomerLinkMan(){
	 var selectedRows=$("#dg").datagrid("getSelections");
	 if(selectedRows.length!=1){
		 $.messager.alert("系统提示","请选择一条要管理的数据！");
		 return;
	 }
	 window.parent.openTab('客户联系人管理','linkManManage.jsp?cusId='+selectedRows[0].id,'icon-lxr');
 }
</script>
<title>Insert title here</title>
</head>
<body style="margin: 1px">
 <table id="dg" title="客户信息查询" class="easyui-datagrid"
    pagination="true" rownumbers="true"
   url="${pageContext.request.contextPath}/customer/list.do" fit="true" toolbar="#tb">
   <thead data-options="frozen:true">
		<tr>
			<th field="cb" checkbox="true" align="center"></th>
	 		<th field="id" width="50" align="center" hidden="true">编号</th>
	 		<th field="khno" width="150" align="center">客户编号</th>
	 		<th field="name" width="200" align="center">客户名称</th>
	 		<th field="cusManager" width="100" align="center">客户经理</th>
	 		<th field="level" width="100" align="center">客户等级</th>
	 		<th field="phone" width="100" align="center">联系电话</th>
		</tr>
	</thead>
	<thead>
		<tr>
			<th field="area" width="80" align="center">客户地区</th>	
	 		<th field="myd" width="80" align="center">客户满意度</th>
	 		<th field="xyd" width="80" align="center">客户信用度</th>
	 		<th field="address" width="200" align="center" >客户地址</th>
	 		<th field="postCode" width="100" align="center" >邮政编码</th>
	 		<th field="fax" width="100" align="center" >传真</th>
	 		<th field="webSite" width="100" align="center" >网址</th>
	 		<th field="yyzzzch" width="100" align="center" >营业执照注册号</th>
	 		<th field="fr" width="100" align="center" >法人</th>
	 		<th field="zczj" width="100" align="center" >注册资金(万元)</th>
	 		<th field="nyye" width="100" align="center" >年营业额(万元)</th>
	 		<th field="khyh" width="100" align="center" >开户银行</th>
	 		<th field="khzh" width="100" align="center" >开户帐号</th>
	 		<th field="dsdjh" width="100" align="center" >地税登记号</th>
	 		<th field="gsdjh" width="100" align="center" >国税登记号</th>
		</tr>
	</thead>
 </table>
 <div id="tb">
 	<div>
 		<a href="javascript:openCustomerAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">创建</a>
 		<a href="javascript:openCustomerModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
 		<a href="javascript:deleteCustomer()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
 		<a href="javascript:openCustomerLinkMan()" class="easyui-linkbutton" iconCls="icon-lxr" plain="true">联系人管理</a>
 	</div>
 	<div>
 		&nbsp;客户编号：&nbsp;<input type="text" id="s_khno" size="20" onkeydown="if(event.keyCode==13) searchCustomer()"/>
 		&nbsp;客户名称：&nbsp;<input type="text" id="s_name" size="20" onkeydown="if(event.keyCode==13) searchCustomer()"/>
 		<a href="javascript:searchCustomer()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
 	</div>
 </div>
 
 
  <div id="dlg" class="easyui-dialog" style="width:700px;height:450px;padding: 10px 20px"
   closed="true" buttons="#dlg-buttons">
   
   <form id="fm" method="post">
   	<table cellspacing="8px">
   		<tr>
   			<td>客户名称：</td>
   			<td><input type="text" id="name" name="name" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>地区</td>
   			<td>
   				<select class="easyui-combobox" style="width: 154px" id="area" name="area" editable="false" panelHeight="auto" >
					<option value="">请选择地区...</option>	
					<option value="北京">北京</option>
					<option value="南京">南京</option>	
					<option value="上海">上海</option>	
					<option value="广州">广州</option>	
					<option value="天津">天津</option>				
                </select>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>客户经理：</td>
   			<td>
   				<input class="easyui-combobox" id="cusManager" name="cusManager" data-options="panelHeight:'auto',editable:false,valueField:'trueName',textField:'trueName',url:'${pageContext.request.contextPath}/user/customerManagerComboList.do'"/>&nbsp;<font color="red">*</font>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>客户等级：</td>
   			<td>
   				<input class="easyui-combobox" id="level" name="level" data-options="panelHeight:'auto',editable:false,valueField:'dataDicValue',textField:'dataDicValue',url:'${pageContext.request.contextPath}/dataDic/dataDicComboList.do?dataDicName=客户等级'"/>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>客户满意度：</td>
   			<td>
   				<select class="easyui-combobox" style="width: 154px" id="myd" name="myd" editable="false" panelHeight="auto" >
					<option value="">请选择...</option>	
					<option value="☆">☆</option>	
					<option value="☆☆">☆☆</option>	
					<option value="☆☆☆">☆☆☆</option>	
					<option value="☆☆☆☆">☆☆☆☆</option>		
					<option value="☆☆☆☆☆">☆☆☆☆☆</option>				
                </select>&nbsp;<font color="red">*</font>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>客户信用度</td>
   			<td>
   				<select class="easyui-combobox" id="xyd" name="xyd" style="width: 154px" editable="false" panelHeight="auto">
   					<option value="">请选择...</option>
   					<option value="☆">☆</option>	
					<option value="☆☆">☆☆</option>	
					<option value="☆☆☆">☆☆☆</option>	
					<option value="☆☆☆☆">☆☆☆☆</option>		
					<option value="☆☆☆☆☆">☆☆☆☆☆</option>				
                </select>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>邮政编码：</td>
   			<td><input type="text" id="postCode" name="postCode" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>联系电话：</td>
   			<td><input type="text" id="phone" name="phone" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>传真：</td>
   			<td><input type="text" id="fax" name="fax" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>网址：</td>
   			<td><input type="text" id="webSite" name="webSite" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>客户地址</td>
   			<td colspan="4">
   				<input type="text" id="address" name="address" style="width: 400px" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>营业执照注册号：</td>
   			<td><input type="text" id="yyzzzch" name="yyzzzch" /></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>法人：</td>
   			<td><input type="text" id="fr" name="fr" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>注册资金(万元)：</td>
   			<td><input type="text" id="zczj" name="zczj" /></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>年营业额(万元)：</td>
   			<td><input type="text" id="nyye" name="nyye" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>开户银行：</td>
   			<td><input type="text" id="khyh" name="khyh" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>开户帐号：</td>
   			<td><input type="text" id="khzh" name="khzh" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>地税登记号：</td>
   			<td><input type="text" id="dsdjh" name="dsdjh" /></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>国税登记号：</td>
   			<td><input type="text" id="gsdjh" name="gsdjh" /></td>
   		</tr>
   	</table>
   </form>
 </div>
 
 <div id="dlg-buttons">
 	<a href="javascript:saveCustomer()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
 	<a href="javascript:closeCustomerDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
 </div>
</body>
</html>
```

##### linkManManage.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    <script type="text/javascript">


        $(function(){

            $.post("${pageContext.request.contextPath}/customer/findById.do",{id:'${param.cusId}'},function(result){
                $("#khno").val(result.khno);
                $("#name").val(result.name);
            },"json");

            $("#dg").edatagrid({
                url:'${pageContext.request.contextPath}/linkMan/list.do?cusId=${param.cusId}',
                saveUrl:'${pageContext.request.contextPath}/linkMan/save.do?customer.id=${param.cusId}',
                updateUrl:'${pageContext.request.contextPath}/linkMan/save.do',
                destroyUrl:'${pageContext.request.contextPath}/linkMan/delete.do'
            });
        });



    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 15px">

<div id="p" class="easyui-panel" title="客户基本信息" style="width: 700px;height: 100px;padding: 10px">
    <table cellspacing="8px">
        <tr>
            <td>客户编号：</td>
            <td><input type="text" id="khno" name="khno" readonly="readonly"/></td>
            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td>客户名称</td>
            <td><input type="text" id="name" name="name" readonly="readonly"/></td>
        </tr>
    </table>
</div>

<br/>
<table id="dg" title="联系人信息管理" style="width:700px;height:250px"
       toolbar="#toolbar" idField="id" rownumbers="true" fitColumns="true" singleSelect="true">
    <thead>
    <tr>
        <th field="id" width="50">编号</th>
        <th field="linkName" width="100" editor="{type:'validatebox',options:{required:true}}">客户姓名</th>
        <th field="sex" width="50" editor="{type:'combobox',
   			options:{
   				valueField:'id',
   				textField:'name',
   				data:[{id:'男',name:'男'},{id:'女',name:'女'}],
   				required:true,
   				editable:false,
   				panelHeight:'auto'
   		    }}">性别</th>
        <th field="zhiwei" width="100" editor="{type:'validatebox',options:{required:true}}">职位</th>
        <th field="officePhone" width="100" editor="{type:'validatebox',options:{required:true}}">办公电话</th>
        <th field="phone" width="100" editor="{type:'validatebox',options:{required:true}}">手机</th>
    </tr>
    </thead>
</table>

<div id="toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#dg').edatagrid('addRow')">添加计划</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#dg').edatagrid('destroyRow')">删除计划</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#dg').edatagrid('saveRow');$('#dg').edatagrid('reload')">保存计划</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#dg').edatagrid('cancelRow')">撤销行</a>
</div>
</body>
</html>
```

![image-20230830172950472](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301729759.png)

#### 客户交往记录管理(CURD)

```
与客户联系时的日志信息
新增客户交往记录表（t_customer_contact）
```

##### Contact

客户交往实体类

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 交往记录
 * @author Administrator
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

	private Integer id; // 编号
	private Customer customer; // 所属客户
	private Date contactTime; // 交往时间
	private String address; // 交往地点
	private String overView; // 概要
	
	
}

```

##### ContactDao

```java
package com.et.dao;

import java.util.List;
import java.util.Map;

import com.et.entity.Contact;

/**
 * 交往记录Dao接口
 * @author Administrator
 *
 */
public interface ContactDao {

	
	/**
	 * 查询交往记录集合
	 * @param map
	 * @return
	 */
	public List<Contact> find(Map<String,Object> map);
	
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 修改交往记录
	 * @param contact
	 * @return
	 */
	public int update(Contact contact);
	
	/**
	 * 添加交往记录
	 * @param contact
	 * @return
	 */
	public int add(Contact contact);
	
	/**
	 * 删除交往记录
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
}

```

##### ContactMapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.et.dao.ContactDao">

    <resultMap type="Contact" id="ContactResult">
        <result property="id" column="id"/>
        <result property="contactTime" column="contactTime"/>
        <result property="address" column="address"/>
        <result property="overView" column="overView"/>
        <association property="customer" column="cusId" select="com.et.dao.CustomerDao.findById"></association>
    </resultMap>

    <select id="find" parameterType="Map" resultMap="ContactResult">
        select * from t_customer_contact
        <where>
            <if test="cusId!=null and cusId!='' ">
                and cusId = #{cusId}
            </if>
        </where>
        <if test="start!=null and size!=null">
            limit #{start},#{size}
        </if>
    </select>


    <select id="getTotal" parameterType="Map" resultType="Long">
        select count(*) from t_customer_contact
        <where>
            <if test="cusId!=null and cusId!='' ">
                and cusId = #{cusId}
            </if>
        </where>
    </select>

    <insert id="add" parameterType="Contact">
        insert into t_customer_contact values(null,#{customer.id},#{contactTime},#{address},#{overView})
    </insert>

    <update id="update" parameterType="Contact">
        update t_customer_contact
        <set>
            <if test="contactTime!=null">
                contactTime=#{contactTime},
            </if>
            <if test="address!=null and address!='' ">
                address=#{address},
            </if>
            <if test="overView!=null and overView!='' ">
                overView=#{overView},
            </if>
        </set>
        where id=#{id}
    </update>

    <delete id="delete" parameterType="Integer">
        delete from t_customer_contact where id=#{id}
    </delete>

</mapper>
```

##### ContactService

```java
package com.et.service;

import java.util.List;
import java.util.Map;

import com.et.entity.Contact;

/**
 * 交往记录Service接口
 * @author Administrator
 *
 */
public interface ContactService {

	
	/**
	 * 查询交往记录集合
	 * @param map
	 * @return
	 */
	public List<Contact> find(Map<String,Object> map);
	
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 修改交往记录
	 * @param contact
	 * @return
	 */
	public int update(Contact contact);
	
	/**
	 * 添加交往记录
	 * @param contact
	 * @return
	 */
	public int add(Contact contact);
	
	/**
	 * 删除交往记录
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
}

```

##### ContactServiceImpl

```java
package com.et.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.et.dao.ContactDao;
import com.et.entity.Contact;
import com.et.service.ContactService;

/**
 * 交往记录Service实现类
 * @author Administrator
 *
 */
@Service("contactService")
public class ContactServiceImpl implements ContactService{

	@Resource
	private ContactDao contactDao;
	
	@Override
	public List<Contact> find(Map<String, Object> map) {
		return contactDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return contactDao.getTotal(map);
	}

	@Override
	public int update(Contact contact) {
		return contactDao.update(contact);
	}

	@Override
	public int add(Contact contact) {
		return contactDao.add(contact);
	}

	@Override
	public int delete(Integer id) {
		return contactDao.delete(id);
	}

}

```

##### ContactController

```java
package com.et.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.et.util.DateJsonValueProcessor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.et.entity.Contact;
import com.et.service.ContactService;
import com.et.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 交往记录Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/contact")
public class ContactController {
	
	@Resource
	private ContactService contactService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	 
	/**
	 * 分页条件查询交往记录
	 * @param page
	 * @param rows
	 * @param s_contact
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="cusId",required=false)String cusId,HttpServletResponse response)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cusId", cusId);
		List<Contact> contactList=contactService.find(map);
		JSONObject result=new JSONObject();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"customer"});
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray=JSONArray.fromObject(contactList,jsonConfig);
		result.put("rows", jsonArray);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 添加或者修改交往记录
	 * @param contact
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Contact contact,HttpServletResponse response)throws Exception{
		int resultTotal=0; // 操作的记录条数
		if(contact.getId()==null){
			resultTotal=contactService.add(contact);
		}else{
			resultTotal=contactService.update(contact);
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
	 * 删除交往记录
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="id")String id,HttpServletResponse response)throws Exception{
		contactService.delete(Integer.parseInt(id));
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
	
	
	
}

```

##### customerManage.jsp

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

		function searchCustomer(){
			$("#dg").datagrid('load',{
				"khno":$("#s_khno").val(),
				"name":$("#s_name").val()
			});
		}

		function openCustomerAddDialog(){
			$("#dlg").dialog("open").dialog("setTitle","添加客户信息");
			url="${pageContext.request.contextPath}/customer/save.do";
		}

		function openCustomerModifyDialog(){
			var selectedRows=$("#dg").datagrid("getSelections");
			if(selectedRows.length!=1){
				$.messager.alert("系统提示","请选择一条要编辑的数据！");
				return;
			}
			var row=selectedRows[0];
			$("#dlg").dialog("open").dialog("setTitle","编辑客户信息");
			$("#fm").form("load",row);
			url="${pageContext.request.contextPath}/customer/save.do?id="+row.id;
		}

		function saveCustomer(){
			$("#fm").form("submit",{
				url:url,
				onSubmit:function(){
					if($("#area").combobox("getValue")==""){
						$.messager.alert("系统提示","请选择客户地区！");
						return false;
					}
					if($("#cusManager").combobox("getValue")==""){
						$.messager.alert("系统提示","请选择客户经理！");
						return false;
					}
					if($("#level").combobox("getValue")==""){
						$.messager.alert("系统提示","请选择客户等级！");
						return false;
					}
					if($("#myd").combobox("getValue")==""){
						$.messager.alert("系统提示","请选择客户满意度！");
						return false;
					}
					if($("#xyd").combobox("getValue")==""){
						$.messager.alert("系统提示","请选择客户信用度！");
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

		function resetValue(){
			$("#name").val("");
			$("#area").combobox("setValue","");
			$("#cusManager").combobox("setValue","");
			$("#level").combobox("setValue","");
			$("#myd").combobox("setValue","");
			$("#xyd").combobox("setValue","");
			$("#address").val("");
			$("#postCode").val("");
			$("#phone").val("");
			$("#fax").val("");
			$("#webSite").val("");
			$("#yyzzzch").val("");
			$("#fr").val("");
			$("#zczj").val("");
			$("#nyye").val("");
			$("#khyh").val("");
			$("#khzh").val("");
			$("#dsdjh").val("");
			$("#gsdjh").val("");
		}

		function closeCustomerDialog(){
			$("#dlg").dialog("close");
			resetValue();
		}

		function deleteCustomer(){
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
					$.post("${pageContext.request.contextPath}/customer/delete.do",{ids:ids},function(result){
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

		function openCustomerLinkMan(){
			var selectedRows=$("#dg").datagrid("getSelections");
			if(selectedRows.length!=1){
				$.messager.alert("系统提示","请选择一条要管理的数据！");
				return;
			}
			window.parent.openTab('客户联系人管理','linkManManage.jsp?cusId='+selectedRows[0].id,'icon-lxr');
		}

		function openCustomerContact(){
			var selectedRows=$("#dg").datagrid("getSelections");
			if(selectedRows.length!=1){
				$.messager.alert("系统提示","请选择一条要管理的数据！");
				return;
			}
			window.parent.openTab('客户交往记录管理','contactManage.jsp?cusId='+selectedRows[0].id,'icon-jwjl');
		}
	</script>
	<title>Insert title here</title>
</head>
<body style="margin: 1px">
<table id="dg" title="客户信息查询" class="easyui-datagrid"
	   pagination="true" rownumbers="true"
	   url="${pageContext.request.contextPath}/customer/list.do" fit="true" toolbar="#tb">
	<thead data-options="frozen:true">
	<tr>
		<th field="cb" checkbox="true" align="center"></th>
		<th field="id" width="50" align="center" hidden="true">编号</th>
		<th field="khno" width="150" align="center">客户编号</th>
		<th field="name" width="200" align="center">客户名称</th>
		<th field="cusManager" width="100" align="center">客户经理</th>
		<th field="level" width="100" align="center">客户等级</th>
		<th field="phone" width="100" align="center">联系电话</th>
	</tr>
	</thead>
	<thead>
	<tr>
		<th field="area" width="80" align="center">客户地区</th>
		<th field="myd" width="80" align="center">客户满意度</th>
		<th field="xyd" width="80" align="center">客户信用度</th>
		<th field="address" width="200" align="center" >客户地址</th>
		<th field="postCode" width="100" align="center" >邮政编码</th>
		<th field="fax" width="100" align="center" >传真</th>
		<th field="webSite" width="100" align="center" >网址</th>
		<th field="yyzzzch" width="100" align="center" >营业执照注册号</th>
		<th field="fr" width="100" align="center" >法人</th>
		<th field="zczj" width="100" align="center" >注册资金(万元)</th>
		<th field="nyye" width="100" align="center" >年营业额(万元)</th>
		<th field="khyh" width="100" align="center" >开户银行</th>
		<th field="khzh" width="100" align="center" >开户帐号</th>
		<th field="dsdjh" width="100" align="center" >地税登记号</th>
		<th field="gsdjh" width="100" align="center" >国税登记号</th>
	</tr>
	</thead>
</table>
<div id="tb">
	<div>
		<a href="javascript:openCustomerAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">创建</a>
		<a href="javascript:openCustomerModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript:deleteCustomer()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		<a href="javascript:openCustomerLinkMan()" class="easyui-linkbutton" iconCls="icon-lxr" plain="true">联系人管理</a>
		<a href="javascript:openCustomerContact()" class="easyui-linkbutton" iconCls="icon-jwjl" plain="true">交往记录管理</a>
	</div>
	<div>
		&nbsp;客户编号：&nbsp;<input type="text" id="s_khno" size="20" onkeydown="if(event.keyCode==13) searchCustomer()"/>
		&nbsp;客户名称：&nbsp;<input type="text" id="s_name" size="20" onkeydown="if(event.keyCode==13) searchCustomer()"/>
		<a href="javascript:searchCustomer()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
</div>


<div id="dlg" class="easyui-dialog" style="width:700px;height:450px;padding: 10px 20px"
	 closed="true" buttons="#dlg-buttons">

	<form id="fm" method="post">
		<table cellspacing="8px">
			<tr>
				<td>客户名称：</td>
				<td><input type="text" id="name" name="name" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>地区</td>
				<td>
					<select class="easyui-combobox" style="width: 154px" id="area" name="area" editable="false" panelHeight="auto" >
						<option value="">请选择地区...</option>
						<option value="北京">北京</option>
						<option value="南京">南京</option>
						<option value="上海">上海</option>
						<option value="广州">广州</option>
						<option value="天津">天津</option>
					</select>&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td>客户经理：</td>
				<td>
					<input class="easyui-combobox" id="cusManager" name="cusManager" data-options="panelHeight:'auto',editable:false,valueField:'trueName',textField:'trueName',url:'${pageContext.request.contextPath}/user/customerManagerComboList.do'"/>&nbsp;<font color="red">*</font>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>客户等级：</td>
				<td>
					<input class="easyui-combobox" id="level" name="level" data-options="panelHeight:'auto',editable:false,valueField:'dataDicValue',textField:'dataDicValue',url:'${pageContext.request.contextPath}/dataDic/dataDicComboList.do?dataDicName=客户等级'"/>&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td>客户满意度：</td>
				<td>
					<select class="easyui-combobox" style="width: 154px" id="myd" name="myd" editable="false" panelHeight="auto" >
						<option value="">请选择...</option>
						<option value="☆">☆</option>
						<option value="☆☆">☆☆</option>
						<option value="☆☆☆">☆☆☆</option>
						<option value="☆☆☆☆">☆☆☆☆</option>
						<option value="☆☆☆☆☆">☆☆☆☆☆</option>
					</select>&nbsp;<font color="red">*</font>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>客户信用度</td>
				<td>
					<select class="easyui-combobox" id="xyd" name="xyd" style="width: 154px" editable="false" panelHeight="auto">
						<option value="">请选择...</option>
						<option value="☆">☆</option>
						<option value="☆☆">☆☆</option>
						<option value="☆☆☆">☆☆☆</option>
						<option value="☆☆☆☆">☆☆☆☆</option>
						<option value="☆☆☆☆☆">☆☆☆☆☆</option>
					</select>&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td>邮政编码：</td>
				<td><input type="text" id="postCode" name="postCode" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>联系电话：</td>
				<td><input type="text" id="phone" name="phone" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
			</tr>
			<tr>
				<td>传真：</td>
				<td><input type="text" id="fax" name="fax" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>网址：</td>
				<td><input type="text" id="webSite" name="webSite" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
			</tr>
			<tr>
				<td>客户地址</td>
				<td colspan="4">
					<input type="text" id="address" name="address" style="width: 400px" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td>营业执照注册号：</td>
				<td><input type="text" id="yyzzzch" name="yyzzzch" /></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>法人：</td>
				<td><input type="text" id="fr" name="fr" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
			</tr>
			<tr>
				<td>注册资金(万元)：</td>
				<td><input type="text" id="zczj" name="zczj" /></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>年营业额(万元)：</td>
				<td><input type="text" id="nyye" name="nyye" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
			</tr>
			<tr>
				<td>开户银行：</td>
				<td><input type="text" id="khyh" name="khyh" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>开户帐号：</td>
				<td><input type="text" id="khzh" name="khzh" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
			</tr>
			<tr>
				<td>地税登记号：</td>
				<td><input type="text" id="dsdjh" name="dsdjh" /></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>国税登记号：</td>
				<td><input type="text" id="gsdjh" name="gsdjh" /></td>
			</tr>
		</table>
	</form>
</div>

<div id="dlg-buttons">
	<a href="javascript:saveCustomer()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript:closeCustomerDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
</body>
</html>
```

##### contactManage.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    <script type="text/javascript">


        $(function(){

            $.post("${pageContext.request.contextPath}/customer/findById.do",{id:'${param.cusId}'},function(result){
                $("#khno").val(result.khno);
                $("#name").val(result.name);
            },"json");

            $("#dg").edatagrid({
                url:'${pageContext.request.contextPath}/contact/list.do?cusId=${param.cusId}',
                saveUrl:'${pageContext.request.contextPath}/contact/save.do?customer.id=${param.cusId}',
                updateUrl:'${pageContext.request.contextPath}/contact/save.do',
                destroyUrl:'${pageContext.request.contextPath}/contact/delete.do'
            });
        });



    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 15px">

<div id="p" class="easyui-panel" title="客户基本信息" style="width: 700px;height: 100px;padding: 10px">
    <table cellspacing="8px">
        <tr>
            <td>客户编号：</td>
            <td><input type="text" id="khno" name="khno" readonly="readonly"/></td>
            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td>客户名称</td>
            <td><input type="text" id="name" name="name" readonly="readonly"/></td>
        </tr>
    </table>
</div>

<br/>
<table id="dg" title="交往记录信息管理" style="width:700px;height:250px"
       toolbar="#toolbar" idField="id" rownumbers="true" fitColumns="true" singleSelect="true">
    <thead>
    <tr>
        <th field="id" width="50">编号</th>
        <th field="contactTime" width="100" editor="{type:'datebox',options:{required:true}}">交往时间</th>
        <th field="address" width="200" editor="{type:'validatebox',options:{required:true}}">交往地址</th>
        <th field="overView" width="300" editor="{type:'validatebox',options:{required:true}}">概要</th>
    </tr>
    </thead>
</table>

<div id="toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#dg').edatagrid('addRow')">添加</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#dg').edatagrid('destroyRow')">删除</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#dg').edatagrid('saveRow');$('#dg').edatagrid('reload')">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#dg').edatagrid('cancelRow')">撤销行</a>
</div>
</body>
</html>
```

![image-20230830175948643](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301759139.png)

#### 客户历史订单查询

```
记录客户的订单信息
新增表（t_customer_order）
```

##### Order

订单实体类

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 订单实体
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	private Integer id; // 编号
	private Customer customer; // 所属客户
	private String orderNo; // 订单号
	private Date orderDate; // 订购日期
	private String address; // 送货地址
	private Integer state; // 状态 0 未回款 1 已回款
}

```

##### OrderDao

```java
package com.et.dao;

import java.util.List;
import java.util.Map;

import com.et.entity.Order;

/**
 * 订单Dao接口
 * @author Administrator
 *
 */
public interface OrderDao {

	
	/**
	 * 查询订单集合
	 * @param map
	 * @return
	 */
	public List<Order> find(Map<String,Object> map);
	
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	
	
}

```

##### OrderMapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.et.dao.OrderDao">

    <resultMap type="Order" id="OrderResult">
        <result property="id" column="id"/>
        <result property="orderNo" column="orderNo"/>
        <result property="orderDate" column="orderDate"/>
        <result property="address" column="address"/>
        <result property="state" column="state"/>
        <association property="customer" column="cusId" select="com.et.dao.CustomerDao.findById"></association>
    </resultMap>

    <select id="find" parameterType="Map" resultMap="OrderResult">
        select * from t_customer_order
        <where>
            <if test="cusId!=null and cusId!='' ">
                and cusId = #{cusId}
            </if>
        </where>
        <if test="start!=null and size!=null">
            limit #{start},#{size}
        </if>
    </select>


    <select id="getTotal" parameterType="Map" resultType="Long">
        select count(*) from t_customer_order
        <where>
            <if test="cusId!=null and cusId!='' ">
                and cusId = #{cusId}
            </if>
        </where>
    </select>



</mapper> 
```



##### OrderService

```java
package com.et.service;

import java.util.List;
import java.util.Map;

import com.et.entity.Order;

/**
 * 订单Service接口
 * @author Administrator
 *
 */
public interface OrderService {

	/**
	 * 查询订单集合
	 * @param map
	 * @return
	 */
	public List<Order> find(Map<String,Object> map);
	
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	
}

```

##### OrderServiceImpl

```java
package com.et.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.et.dao.OrderDao;
import com.et.entity.Order;
import com.et.service.OrderService;

/**
 * 订单Service实现类
 * @author Administrator
 *
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService{

	@Resource
	private OrderDao orderDao;
	
	@Override
	public List<Order> find(Map<String, Object> map) {
		return orderDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return orderDao.getTotal(map);
	}

}

```

##### OrderController

```java
package com.et.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.et.util.DateJsonValueProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.et.entity.Order;
import com.et.entity.PageBean;
import com.et.service.OrderService;
import com.et.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 订单Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Resource
	private OrderService orderService;
	
	 
	/**
	 * 分页条件查询订单
	 * @param page
	 * @param rows
	 * @param s_order
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,@RequestParam(value="cusId",required=false)String cusId,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cusId", cusId);
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Order> orderList=orderService.find(map);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"customer"});
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject result=new JSONObject();
		Long total=orderService.getTotal(map);
		JSONArray jsonArray=JSONArray.fromObject(orderList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
	
	
	
	
}

```

##### consumerManage.jsp

增加历史订单按钮

```jsp
function openCustomerOrder(){
			var selectedRows=$("#dg").datagrid("getSelections");
			if(selectedRows.length!=1){
				$.messager.alert("系统提示","请选择一条要管理的数据！");
				return;
			}
			window.parent.openTab('客户历史订单查询','orderManage.jsp?cusId='+selectedRows[0].id,'icon-lsdd');
		}

<a href="javascript:openCustomerOrder()" class="easyui-linkbutton" iconCls="icon-jwjl" plain="true">历史订单查看</a>
```

##### orderManage.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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


        $(function(){

            $.post("${pageContext.request.contextPath}/customer/findById.do",{id:'${param.cusId}'},function(result){
                $("#khno").val(result.khno);
                $("#name").val(result.name);
            },"json");


        });

        function formatState(val,row){
            if(val==1){
                return "已回款";
            }else{
                return "未回款";
            }
        }

        function formatAction(val,row){
            return "<a href='javascript:'>查看订单明细</a>"
        }

    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 15px">

<div id="p" class="easyui-panel" title="客户基本信息" style="width: 900px;height: 100px;padding: 10px">
    <table cellspacing="8px">
        <tr>
            <td>客户编号：</td>
            <td><input type="text" id="khno" name="khno" readonly="readonly"/></td>
            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td>客户名称</td>
            <td><input type="text" id="name" name="name" readonly="readonly"/></td>
        </tr>
    </table>
</div>

<br/>
<table id="dg" title="客户历史订单" class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/order/list.do?cusId=${param.cusId}" style="width: 900px;height: 400px"  >
    <thead>
    <tr>
        <th field="id" width="50" align="center">编号</th>
        <th field="orderNo" width="100" align="center">订单号</th>
        <th field="orderDate" width="100" align="center">订购日期</th>
        <th field="address" width="200" align="center">送货地址</th>
        <th field="state" width="50" align="center" formatter="formatState">状态</th>
        <th field="a" width="50" align="center" formatter="formatAction">操作</th>
    </tr>
    </thead>
</table>
</body>
</html>
```

![image-20230830181457880](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301814390.png)

#### 客户历史订单订单明细

```
统计客户的历史订单
新增订单明细表（t_order_details）
```

##### OrderDetails

订单明细实体类

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单购买商品
 * @author Administrator
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {

	private Integer id; // 编号
	private Order order; // 所属订单
	private String goodsName; // 商品名称
	private int goodsNum; // 商品数量
	private String unit; // 单位
	private float price; // 价格
	private float sum; // 总金额
	
	
	
}

```

##### OrderDetailsDao

```java
package com.et.dao;

import java.util.List;
import java.util.Map;

import com.et.entity.OrderDetails;

/**
 * 订单明细Dao接口
 * @author Administrator
 *
 */
public interface OrderDetailsDao {

	
	/**
	 * 查询订单明细集合
	 * @param map
	 * @return
	 */
	public List<OrderDetails> find(Map<String,Object> map);
	
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 获取指定订单的总金额
	 * @param orderId
	 * @return
	 */
	public float getTotalPriceByOrderId(Integer orderId);
	
	
	
}

```

##### OrderDetailsMapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.et.dao.OrderDetailsDao">

    <resultMap type="OrderDetails" id="OrderDetailsResult">
        <result property="id" column="id"/>
        <result property="goodsName" column="goodsName"/>
        <result property="goodsNum" column="goodsNum"/>
        <result property="unit" column="unit"/>
        <result property="price" column="price"/>
        <result property="sum" column="sum"/>
        <association property="order" column="orderId" select="com.et.dao.OrderDao.findById"></association>
    </resultMap>

    <select id="find" parameterType="Map" resultMap="OrderDetailsResult">
        select * from t_order_details
        <where>
            <if test="orderId!=null and orderId!='' ">
                and orderId = #{orderId}
            </if>
        </where>
        <if test="start!=null and size!=null">
            limit #{start},#{size}
        </if>
    </select>


    <select id="getTotal" parameterType="Map" resultType="Long">
        select count(*) from t_order_details
        <where>
            <if test="orderId!=null and orderId!='' ">
                and orderId = #{orderId}
            </if>
        </where>
    </select>

    <select id="getTotalPriceByOrderId" parameterType="Integer" resultType="Float">
        select sum(sum) from t_order_details where orderId=#{orderId}
    </select>
</mapper> 
```

##### OrderDao

需要添加根据商品ID查询商品信息

```java
/**
	 * 通过Id查找实体
	 * @param id
	 * @return
	 */
	public Order findById(Integer id);
```

##### OrderMapper

```xml
<select id="findById" parameterType="Integer" resultMap="OrderResult">
        select * from t_customer_order where id=#{id}
    </select>
```

##### OrderService

```java
/**
	 * 根据订单ID查询订单信息
	 * @param parseInt
	 * @return
	 */
	Order findById(int parseInt);
```

##### OrderServiceImpl

```java
@Override
	public Order findById(int parseInt) {
		return orderDao.findById(parseInt);
	}
```

##### OrderController

````java
/**
	 * 通过ID查找实体
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	public String findById(@RequestParam(value="id")String id,HttpServletResponse response)throws Exception{
		Order order=orderService.findById(Integer.parseInt(id));
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"order"});
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject jsonObject=JSONObject.fromObject(order,jsonConfig);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
````



##### OrderDetailsService

```java
package com.et.service;

import java.util.List;
import java.util.Map;

import com.et.entity.OrderDetails;

/**
 * 订单明细Service接口
 * @author Administrator
 *
 */
public interface OrderDetailsService {

	
	/**
	 * 查询订单明细集合
	 * @param map
	 * @return
	 */
	public List<OrderDetails> find(Map<String,Object> map);
	
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 获取指定订单的总金额
	 * @param orderId
	 * @return
	 */
	public float getTotalPriceByOrderId(Integer orderId);
	
	
	
}

```

##### OrderDetailsServiceImpl

```java
package com.et.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.et.dao.OrderDetailsDao;
import com.et.entity.OrderDetails;
import com.et.service.OrderDetailsService;

/**
 * 订单详情Service实现类
 * @author Administrator
 *
 */
@Service("orderDetailsService")
public class OrderDetailsServiceImpl implements OrderDetailsService{

	@Resource
	private OrderDetailsDao orderDetailsDao;
	
	@Override
	public List<OrderDetails> find(Map<String, Object> map) {
		return orderDetailsDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return orderDetailsDao.getTotal(map);
	}

	@Override
	public float getTotalPriceByOrderId(Integer orderId) {
		return orderDetailsDao.getTotalPriceByOrderId(orderId);
	}

}

```

##### OrderDetailsController

```java
package com.et.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.et.entity.OrderDetails;
import com.et.entity.PageBean;
import com.et.service.OrderDetailsService;
import com.et.util.ResponseUtil;
import com.et.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 订单明细Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/orderDetails")
public class OrderDetailsController {
	
	@Resource
	private OrderDetailsService orderDetailsService;
	
	 
	/**
	 * 分页条件查询订单明细
	 * @param page
	 * @param rows
	 * @param s_orderDetails
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,@RequestParam(value="orderId",required=false)String orderId,HttpServletResponse response)throws Exception{
		if(StringUtil.isEmpty(orderId)){
			return null;
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orderId", orderId);
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<OrderDetails> orderDetailsList=orderDetailsService.find(map);
		Long total=orderDetailsService.getTotal(map);
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(orderDetailsList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 通过订单id获取订单总金额
	 * @param orderId
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTotalPrice")
	public String getTotalPrice(String orderId,HttpServletResponse response)throws Exception{
		float totalMoney=orderDetailsService.getTotalPriceByOrderId(Integer.parseInt(orderId));
		JSONObject result=new JSONObject();
		result.put("totalMoney", totalMoney);
		ResponseUtil.write(response, result);
		return null;
	}
	
}

```

##### orderManage.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
 

 $(function(){
	
	 $.post("${pageContext.request.contextPath}/customer/findById.do",{id:'${param.cusId}'},function(result){
		 $("#khno").val(result.khno);
		 $("#name").val(result.name);
		},"json");
	 
	
 });
 
 function formatState(val,row){
	 if(val==1){
		 return "已回款";
	 }else{
		 return "未回款";
	 }
 }
 
 function formatAction(val,row){
	 return "<a href='javascript:openOrderDetailsDialog("+row.id+")'>查看订单明细</a>"
 }
 
 function openOrderDetailsDialog(orderId){
	 $.post("${pageContext.request.contextPath}/order/findById.do",{id:orderId},function(result){
			$("#fm").form('load',result);
			if(result.state==0){
				$("#state").val("未回款");
			}else if(result.state==1){
				$("#state").val("已回款");
			}
	 },"json");
	 
	 $.post("${pageContext.request.contextPath}/orderDetails/getTotalPrice.do",{orderId:orderId},function(result){
			$("#totalMoney").val(result.totalMoney);
	 },"json");
	 
	 $("#dg2").datagrid('load',{
		 "orderId":orderId
	 });
	 
	 $("#dlg").dialog("open").dialog("setTitle","订单明细");
 }
 
 
 function closeOrderDetailsDialog(){
	 $("#dlg").dialog("close");
 }
</script>
<title>Insert title here</title>
</head>
<body style="margin: 15px">
 
 <div id="p" class="easyui-panel" title="客户基本信息" style="width: 900px;height: 100px;padding: 10px">
 	<table cellspacing="8px">
   		<tr>
   			<td>客户编号：</td>
   			<td><input type="text" id="khno" name="khno" readonly="readonly"/></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>客户名称</td>
   			<td><input type="text" id="name" name="name" readonly="readonly"/></td>
   		</tr>
   	</table>
 </div>
 
 <br/>
 <table id="dg" title="客户历史订单" class="easyui-datagrid"
   fitColumns="true" pagination="true" rownumbers="true"
   url="${pageContext.request.contextPath}/order/list.do?cusId=${param.cusId}" style="width: 900px;height: 400px"  >
   <thead>
   	<tr>
   		<th field="id" width="50" align="center">编号</th>
   		<th field="orderNo" width="100" align="center">订单号</th>
   		<th field="orderDate" width="100" align="center">订购日期</th>
   		<th field="address" width="200" align="center">送货地址</th>
   		<th field="state" width="50" align="center" formatter="formatState">状态</th>
   		<th field="a" width="50" align="center" formatter="formatAction">操作</th>
   	</tr>
   </thead>
 </table>
 
 <div id="dlg" class="easyui-dialog" style="width:700px;height:450px;padding: 10px 20px"
   closed="true" buttons="#dlg-buttons">
   
   <form id="fm" method="post">
   	<table cellspacing="8px">
   		<tr>
   			<td>订单号：</td>
   			<td><input type="text" id="orderNo" name="orderNo" readonly="readonly"/></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>订购日期</td>
   			<td><input type="text" id="orderDate" name="orderDate" readonly="readonly" /></td>
   		</tr>
   		<tr>
   			<td>送货地址：</td>
   			<td><input type="text" id="address" name="address" readonly="readonly"/></td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>总金额</td>
   			<td><input type="text" id="totalMoney" name="totalMoney" readonly="readonly" /></td>
   		</tr>
   		<tr>
   			<td>状态：</td>
   			<td><input type="text" id="state" name="state" readonly="readonly"/></td>
   			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;</td>
   		</tr>
   	</table>
   	<br/>
   	<table id="dg2" title="订购详情" class="easyui-datagrid"
   fitColumns="true" pagination="true" rownumbers="true"
   url="${pageContext.request.contextPath}/orderDetails/list.do" style="width: 600px;height: 220px"  >
   <thead>
   	<tr>
   		<th field="id" width="50" align="center">编号</th>
   		<th field="goodsName" width="150" align="center">商品名称</th>
   		<th field="goodsNum" width="50" align="center">订购数量</th>
   		<th field="unit" width="50" align="center">单位</th>
   		<th field="price" width="50" align="center" >单价(元)</th>
   		<th field="sum" width="80" align="center" >金额(元)</th>
   	</tr>
   </thead>
 </table>
   </form>
 </div>
 
 <div id="dlg-buttons">
 	<a href="javascript:closeOrderDetailsDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
 </div>
</body>
</html>
```

![image-20230830184417964](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308301844687.png)



### 客户流失管理

```
在经过一段时间（默认6个月）未下订单的客户自动转移到客户流失表中 状态为暂缓流式
新增客户流失表（t_customer_loss）
```

#### 客户流失列表展示

##### CustomerLoss

客户流式实体类

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 客户流失实体
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoss {

	private Integer id; // 编号
	private String cusNo; // 客户编号
	private String cusName; // 客户名称
	private String cusManager; // 客户经理
	private Date lastOrderTime; // 上次下单日期
	private Date confirmLossTime; // 确认流失日期
	private Integer state; // 状态 0 暂缓流失 1 确认流失
	private String lossReason; // 流失原因
	
	
}

```

##### CustomerLossDao

```java
package com.et.dao;

import java.util.List;
import java.util.Map;

import com.et.entity.CustomerLoss;

/**
 * 客户流失Dao接口
 * @author Administrator
 *
 */
public interface CustomerLossDao {

	
	/**
	 * 查询客户流失集合
	 * @param map
	 * @return
	 */
	public List<CustomerLoss> find(Map<String,Object> map);
	
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	
}

```

##### CustomerLossMapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.et.dao.CustomerLossDao">

    <resultMap type="CustomerLoss" id="CustomerLossResult">
        <result property="id" column="id"/>
        <result property="cusNo" column="cusNo"/>
        <result property="cusName" column="cusName"/>
        <result property="cusManager" column="cusManager"/>
        <result property="lastOrderTime" column="lastOrderTime"/>
        <result property="confirmLossTime" column="confirmLossTime"/>
        <result property="state" column="state"/>
        <result property="lossReason" column="lossReason"/>
    </resultMap>

    <select id="find" parameterType="Map" resultMap="CustomerLossResult">
        select * from t_customer_loss
        <where>
            <if test="cusName!=null and cusName!='' ">
                and cusName like #{cusName}
            </if>
            <if test="cusManager!=null and cusManager!='' ">
                and cusManager like #{cusManager}
            </if>
            <if test="state!=null">
                and state = #{state}
            </if>
        </where>
        <if test="start!=null and size!=null">
            limit #{start},#{size}
        </if>
    </select>


    <select id="getTotal" parameterType="Map" resultType="Long">
        select count(*) from t_customer_loss
        <where>
            <if test="cusName!=null and cusName!='' ">
                and cusName like #{cusName}
            </if>
            <if test="cusManager!=null and cusManager!='' ">
                and cusManager like #{cusManager}
            </if>
            <if test="state!=null">
                and state = #{state}
            </if>
        </where>
    </select>


</mapper>
```

##### CustomerLossService

```java
package com.et.service;

import java.util.List;
import java.util.Map;

import com.et.entity.CustomerLoss;

/**
 * 客户流失Service接口
 * @author Administrator
 *
 */
public interface CustomerLossService {

	/**
	 * 查询客户流失集合
	 * @param map
	 * @return
	 */
	public List<CustomerLoss> find(Map<String,Object> map);
	
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
}

```

##### CustomerLossServiceImpl

```java
package com.et.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.et.dao.CustomerLossDao;
import com.et.entity.CustomerLoss;
import com.et.service.CustomerLossService;

/**
 * 客户流失Service实现类
 * @author Administrator
 *
 */
@Service("customerLossService")
public class CustomerLossServiceImpl implements CustomerLossService{

	@Resource
	private CustomerLossDao customerLossDao;
	
	@Override
	public List<CustomerLoss> find(Map<String, Object> map) {
		return customerLossDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return customerLossDao.getTotal(map);
	}

}

```

##### CustomerLossController

```java
package com.et.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.et.util.DateJsonValueProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.et.entity.CustomerLoss;
import com.et.entity.PageBean;
import com.et.service.CustomerLossService;
import com.et.util.ResponseUtil;
import com.et.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 客户流失Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/customerLoss")
public class CustomerLossController {
	
	@Resource
	private CustomerLossService customerLossService;
	
	 
	/**
	 * 分页条件查询客户流失
	 * @param page
	 * @param rows
	 * @param s_customerLoss
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,CustomerLoss s_customerLoss,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cusName", StringUtil.formatLike(s_customerLoss.getCusName()));
		map.put("cusManager", StringUtil.formatLike(s_customerLoss.getCusManager()));
		map.put("state", s_customerLoss.getState());
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<CustomerLoss> customerLossList=customerLossService.find(map);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject result=new JSONObject();
		Long total=customerLossService.getTotal(map);
		JSONArray jsonArray=JSONArray.fromObject(customerLossList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
	

}

```

##### customerLossManage.jsp

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


        function searchCustomerLoss(){
            $("#dg").datagrid('load',{
                "cusName":$("#s_cusName").val(),
                "cusManager":$("#s_cusManager").val(),
                "state":$("#s_state").combobox("getValue")
            });
        }

        function formatState(val,row){
            if(val==1){
                return "确认流失";
            }else{
                return "暂缓流失";
            }
        }

    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 1px">
<table id="dg" title="客户流失管理" class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/customerLoss/list.do" fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">编号</th>
        <th field="cusNo" width="50" align="center" hidden="true">客户编号</th>
        <th field="cusName" width="100" align="center">客户名称</th>
        <th field="cusManager" width="100" align="center">客户经理</th>
        <th field="lastOrderTime" width="100" align="center">上次下单日期</th>
        <th field="confirmLossTime" width="100" align="center">确认流失日期</th>
        <th field="state" width="80" align="center" formatter="formatState">客户状态</th>
        <th field="lossReason" width="200" align="center">流失原因</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>

    </div>
    <div>
        &nbsp;客户名称：&nbsp;<input type="text" id="s_cusName" size="20" onkeydown="if(event.keyCode==13) searchCustomerLoss()"/>
        &nbsp;客户经理：&nbsp;<input type="text" id="s_cusManager" size="20" onkeydown="if(event.keyCode==13) searchCustomerLoss()"/>
        &nbsp;客户状态：&nbsp;<select class="easyui-combobox" id="s_state" editable="false" panelHeight="auto" >
        <option value="">请选择...</option>
        <option value="0">暂缓流失</option>
        <option value="1">确认流失</option>
    </select>
        <a href="javascript:searchCustomerLoss()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>


</body>
</html>
```

![image-20230830204339396](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308302153264.png)

#### 添加流式数据

```
添加流式数据采用的是每天定时执行查询 将满足条件的放到流失表中
quarzt定时任务

```

##### CustomerLossDao

添加流失数据

```java
/**
	 * 添加客户流失记录
	 * @param customerLoss
	 * @return
	 */
	public int add(CustomerLoss customerLoss);
```

##### CustomerLossMapper

```xml
<insert id="add" parameterType="CustomerLoss">
        insert into t_customer_loss values(null,#{cusNo},#{cusName},#{cusManager},#{lastOrderTime},null,0,null);
    </insert>
```

##### OrderDao

```
/**
	 * 查找指定客户的最近的订单
	 * @param cusId
	 * @return
	 */
	public Order findLastOrderByCusId(int cusId);
```

##### OrderMapper

```xml
<select id="findLastOrderByCusId" parameterType="Integer" resultMap="OrderResult">
		SELECT * FROM t_customer_order WHERE cusId=#{cusId} ORDER BY orderdate DESC LIMIT 0,1;
	</select>
```

##### CustomerDao

查询流失数据DAO

```java
/**
	 * 查找流失的客户 6个月未下单的客户
	 * @return
	 */
	public List<Customer> findLossCustomer();
```

##### CustomerMapper

```xml
<select id="findLossCustomer" resultMap="CustomerResult">
    	<!-- 剔除六个月内下过订单的用户ID 剩下的就是不满足条件的 同时还得是暂缓流失状态 -->
		SELECT * FROM t_customer t1 WHERE id NOT IN (SELECT cusId FROM t_customer_order WHERE DATE_ADD(orderDate,INTERVAL 6 MONTH) > NOW()) AND t1.state=0;
	</select>
```

##### CustomerService

```java
/**
	 * 查找流失客户，并且添加到流失客户表里
	 */
	public void checkCustomerLoss();
```

##### CustomerServiceImpl

```java
@Resource
	private CustomerLossDao customerLossDao;

	@Resource
	private OrderDao orderDao;

@Override
	public void checkCustomerLoss() {
		List<Customer> customerList=customerDao.findLossCustomer(); // 查找流失客户
		for(Customer c:customerList){
			CustomerLoss customerLoss=new CustomerLoss(); // 实例化客户流失实体
			customerLoss.setCusNo(c.getKhno()); // 客户编号
			customerLoss.setCusName(c.getName()); // 客户名称
			customerLoss.setCusManager(c.getCusManager()); // 客户经理
			Order order=orderDao.findLastOrderByCusId(c.getId()); // 查找指定客户最近的订单
			if(order==null){
				customerLoss.setLastOrderTime(null);
			}else{
				customerLoss.setLastOrderTime(order.getOrderDate()); // 设置最近的下单日期
			}
			customerLossDao.add(customerLoss); // 添加到客户流失表
			c.setState(1); // 客户状态修改成1 流失状态
			customerDao.update(c);
		}
	}
```

##### CustomerMapper

```
更新操作 添加修改客户流失状态字段
<if test="state!=0 ">
state=#{state},
</if>
```

##### 配置定时任务

```
application中

xmlns:task="http://www.springframework.org/schema/task"

xsi:schemaLocation中添加
http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task-4.0.xsd

<context:component-scan base-package="com.et.quartz" />

<!-- 加载定时任务 -->
<task:annotation-driven/>
```

##### FindLossCustomerJob

定时任务调度类

```java
package com.et.quartz;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.et.service.CustomerService;

/**
 * 查找流失客户 定时任务
 * @author Administrator
 *
 */
@Component
public class FindLossCustomerJob {

	@Resource
	private CustomerService customerService; // 客户Service
	
	/**
	 * 每天凌晨2点定期执行
	 */
//	@Scheduled(cron="0 0 2 * * ?")
	@Scheduled(cron = "0 0/1 * * * ?")// 每隔一分钟执行一次
	public void work(){
		customerService.checkCustomerLoss();
	}
}

```

#### 确认流失

```
在确认流失前需要做一些补救措施 如果发现确实流失了 则直接流失即可
补救措施消息需要单独存放
新增暂缓流失措施表`t_customer_reprieve` 
```

##### CustomerLossDao

```java
/**
	 * 通过Id查找实体
	 * @param id
	 * @return
	 */
	public CustomerLoss findById(Integer id);

	/**
	 * 修改客户流失记录
	 * @param customerLoss
	 * @return
	 */
	public int update(CustomerLoss customerLoss);
```

##### CustomerLossMapper

```xml
<select id="findById" parameterType="Integer" resultMap="CustomerLossResult">
		select * from t_customer_loss where id=#{id}
	</select>
	
	<update id="update" parameterType="CustomerLoss">
		update t_customer_loss
		<set>
			<if test="confirmLossTime!=null">
				confirmLossTime=#{confirmLossTime},
			</if>
			<if test="lossReason!=null and lossReason!='' ">
				lossReason=#{lossReason},
			</if>
			<if test="state!=null ">
				state=#{state},
			</if>
		</set>
		where id=#{id}
	</update>
	
```

##### CustomerLossService

```java
/**
	 * 通过Id查找实体
	 * @param id
	 * @return
	 */
	public CustomerLoss findById(Integer id);
	
	/**
	 * 修改客户流失记录
	 * @param customerLoss
	 * @return
	 */
	public int update(CustomerLoss customerLoss);
```

##### CustomerLossServiceImpl

```java
@Override
	public CustomerLoss findById(Integer id) {
		return customerLossDao.findById(id);
	}

	@Override
	public int update(CustomerLoss customerLoss) {
		return customerLossDao.update(customerLoss);
	}
```

##### CustomerLossController

```java
/**
	 * 通过ID查找实体
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	public String findById(@RequestParam(value="id")String id,HttpServletResponse response)throws Exception{
		CustomerLoss customerLoss=customerLossService.findById(Integer.parseInt(id));
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject jsonObject=JSONObject.fromObject(customerLoss,jsonConfig);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	/**
	 * 确认客户流失
	 * @param id
	 * @param lossReason
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/confirmLoss")
	public String confirmLoss(int id,String lossReason,HttpServletResponse response)throws Exception{
		CustomerLoss customerLoss=new CustomerLoss();
		customerLoss.setId(id);
		customerLoss.setLossReason(lossReason);
		customerLoss.setConfirmLossTime(new Date());
		customerLoss.setState(1);
		customerLossService.update(customerLoss);
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
```

##### customerLossMange.jsp

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


        function searchCustomerLoss(){
            $("#dg").datagrid('load',{
                "cusName":$("#s_cusName").val(),
                "cusManager":$("#s_cusManager").val(),
                "state":$("#s_state").combobox("getValue")
            });
        }

        function formatState(val,row){
            if(val==1){
                return "确认流失";
            }else{
                return "暂缓流失";
            }
        }

        function formatAction(val,row){
            if(row.state==1){
                return "客户确认流失";
            }else{
                return "<a href='javascript:openCustomerReprieve("+row.id+")'>暂缓流失</a>";
            }
        }

        function openCustomerReprieve(id){
            window.parent.openTab('客户流失暂缓措施管理','customerReprieveManage.jsp?lossId='+id,'icon-khlsgl');
        }

    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 1px">
<table id="dg" title="客户流失管理" class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/customerLoss/list.do" fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">编号</th>
        <th field="cusNo" width="50" align="center" hidden="true">客户编号</th>
        <th field="cusName" width="100" align="center">客户名称</th>
        <th field="cusManager" width="100" align="center">客户经理</th>
        <th field="lastOrderTime" width="100" align="center">上次下单日期</th>
        <th field="confirmLossTime" width="100" align="center">确认流失日期</th>
        <th field="state" width="80" align="center" formatter="formatState">客户状态</th>
        <th field="lossReason" width="200" align="center">流失原因</th>
        <th field="a" width="100" align="center" formatter="formatAction">操作</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>

    </div>
    <div>
        &nbsp;客户名称：&nbsp;<input type="text" id="s_cusName" size="20" onkeydown="if(event.keyCode==13) searchCustomerLoss()"/>
        &nbsp;客户经理：&nbsp;<input type="text" id="s_cusManager" size="20" onkeydown="if(event.keyCode==13) searchCustomerLoss()"/>
        &nbsp;客户状态：&nbsp;<select class="easyui-combobox" id="s_state" editable="false" panelHeight="auto" >
        <option value="">请选择...</option>
        <option value="0">暂缓流失</option>
        <option value="1">确认流失</option>
    </select>
        <a href="javascript:searchCustomerLoss()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>


</body>
</html>
```

#### 暂缓流失

##### CustomerReprieve

流失措施实体类

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户流失暂缓措施实体类
 * @author Administrator
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReprieve {

	private Integer id; // 编号
	private CustomerLoss customerLoss; // 客户流失
	private String measure; // 暂缓措施
	
	
}

```

##### CustomerReprieveDao

```java
package com.et.dao;

import java.util.List;
import java.util.Map;

import com.et.entity.CustomerReprieve;

/**
 * 客户流失暂缓措施Dao接口
 * @author Administrator
 *
 */
public interface CustomerReprieveDao {

	
	/**
	 * 查询客户流失暂缓措施集合
	 * @param map
	 * @return
	 */
	public List<CustomerReprieve> find(Map<String,Object> map);
	
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 修改客户流失暂缓措施
	 * @param customerReprieve
	 * @return
	 */
	public int update(CustomerReprieve customerReprieve);
	
	/**
	 * 添加客户流失暂缓措施
	 * @param customerReprieve
	 * @return
	 */
	public int add(CustomerReprieve customerReprieve);
	
	/**
	 * 删除客户流失暂缓措施
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
}

```

##### CustomerReprieveMapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.et.dao.CustomerReprieveDao">

    <resultMap type="CustomerReprieve" id="CustomerReprieveResult">
        <result property="id" column="id"/>
        <result property="measure" column="measure"/>
        <association property="customerLoss" column="lossId" select="com.et.dao.CustomerLossDao.findById"></association>
    </resultMap>

    <select id="find" parameterType="Map" resultMap="CustomerReprieveResult">
        select * from t_customer_reprieve
        <where>
            <if test="lossId!=null and lossId!='' ">
                and lossId = #{lossId}
            </if>
        </where>
        <if test="start!=null and size!=null">
            limit #{start},#{size}
        </if>
    </select>


    <select id="getTotal" parameterType="Map" resultType="Long">
        select count(*) from t_customer_reprieve
        <where>
            <if test="lossId!=null and lossId!='' ">
                and lossId = #{lossId}
            </if>
        </where>
    </select>

    <insert id="add" parameterType="CustomerReprieve">
        insert into t_customer_reprieve values(null,#{customerLoss.id},#{measure})
    </insert>

    <update id="update" parameterType="CustomerReprieve">
        update t_customer_reprieve
        <set>
            <if test="measure!=null and measure!='' ">
                measure=#{measure},
            </if>
        </set>
        where id=#{id}
    </update>

    <delete id="delete" parameterType="Integer">
        delete from t_customer_reprieve where id=#{id}
    </delete>

</mapper>
```

##### CustomerReprieveService

```java
package com.et.service;

import java.util.List;
import java.util.Map;

import com.et.entity.CustomerReprieve;

/**
 * 客户流失暂缓措施Service接口
 * @author Administrator
 *
 */
public interface CustomerReprieveService {

	
	/**
	 * 查询客户流失暂缓措施集合
	 * @param map
	 * @return
	 */
	public List<CustomerReprieve> find(Map<String,Object> map);
	
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 修改客户流失暂缓措施
	 * @param customerReprieve
	 * @return
	 */
	public int update(CustomerReprieve customerReprieve);
	
	/**
	 * 添加客户流失暂缓措施
	 * @param customerReprieve
	 * @return
	 */
	public int add(CustomerReprieve customerReprieve);
	
	/**
	 * 删除客户流失暂缓措施
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
	
}

```

##### CustomerReprieveServiceImpl

```java
package com.et.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.et.dao.CustomerReprieveDao;
import com.et.entity.CustomerReprieve;
import com.et.service.CustomerReprieveService;

/**
 * 客户流失暂缓措施Service实现类
 * @author Administrator
 *
 */
@Service("customerReprieveService")
public class CustomerReprieveServiceImpl implements CustomerReprieveService{

	@Resource
	private CustomerReprieveDao CustomerReprieveDao;
	
	@Override
	public List<CustomerReprieve> find(Map<String, Object> map) {
		return CustomerReprieveDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return CustomerReprieveDao.getTotal(map);
	}

	@Override
	public int update(CustomerReprieve customerReprieve) {
		return CustomerReprieveDao.update(customerReprieve);
	}

	@Override
	public int add(CustomerReprieve customerReprieve) {
		return CustomerReprieveDao.add(customerReprieve);
	}

	@Override
	public int delete(Integer id) {
		return CustomerReprieveDao.delete(id);
	}

}

```

##### CustomerReprieveController

```java
package com.et.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.et.entity.CustomerReprieve;
import com.et.service.CustomerReprieveService;
import com.et.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 客户流失暂缓措施Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/customerReprieve")
public class CustomerReprieveController {
	
	@Resource
	private CustomerReprieveService customerReprieveService;
	
	 
	/**
	 * 分页条件查询客户流失暂缓措施
	 * @param page
	 * @param rows
	 * @param s_customerReprieve
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="lossId",required=false)String lossId,HttpServletResponse response)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("lossId", lossId);
		List<CustomerReprieve> customerReprieveList=customerReprieveService.find(map);
		JSONObject result=new JSONObject();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"customerLoss"});
		JSONArray jsonArray=JSONArray.fromObject(customerReprieveList,jsonConfig);
		result.put("rows", jsonArray);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 添加或者修改客户流失暂缓措施
	 * @param customerReprieve
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(CustomerReprieve customerReprieve,HttpServletResponse response)throws Exception{
		int resultTotal=0; // 操作的记录条数
		if(customerReprieve.getId()==null){
			resultTotal=customerReprieveService.add(customerReprieve);
		}else{
			resultTotal=customerReprieveService.update(customerReprieve);
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
	 * 删除客户流失暂缓措施
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="id")String id,HttpServletResponse response)throws Exception{
		customerReprieveService.delete(Integer.parseInt(id));
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
}

```

##### customerReprieveManage.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    <script type="text/javascript">


        $(function(){

            $.post("${pageContext.request.contextPath}/saleChance/findById.do",{id:'${param.saleChanceId}'},function(result){
                $("#customerName").val(result.customerName);
                $("#chanceSource").val(result.chanceSource);
                $("#linkMan").val(result.linkMan);
                $("#linkPhone").val(result.linkPhone);
                $("#cgjl").val(result.cgjl);
                $("#overView").val(result.overView);
                $("#description").val(result.description);
                $("#createMan").val(result.createMan);
                $("#createTime").val(result.createTime);
                $("#assignMan").val(result.assignMan);
                $("#assignTime").val(result.assignTime);
            },"json");

            $("#dg").edatagrid({
                url:'${pageContext.request.contextPath}/cusDevPlan/list.do?saleChanceId=${param.saleChanceId}',
                saveUrl:'${pageContext.request.contextPath}/cusDevPlan/save.do?saleChance.id=${param.saleChanceId}',
                updateUrl:'${pageContext.request.contextPath}/cusDevPlan/save.do?saleChance.id=${param.saleChanceId}',
                destroyUrl:'${pageContext.request.contextPath}/cusDevPlan/delete.do'
            });
        });

        function updateSaleChanceDevResult(devResult){
            $.post("${pageContext.request.contextPath}/cusDevPlan/updateSaleChanceDevResult.do",{id:'${param.saleChanceId}',devResult:devResult},function(result){
                if(result.success){
                    $.messager.alert("系统提示","执行成功！");
                }else{
                    $.messager.alert("系统提示","执行失败！");
                }
            },"json");
        }

    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 15px">

<div id="p" class="easyui-panel" title="销售机会信息" style="width: 700px;height: 400px;padding: 10px">
    <table cellspacing="8px">
        <tr>
            <td>客户名称：</td>
            <td><input type="text" id="customerName" name="customerName" readonly="readonly"/></td>
            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td>机会来源</td>
            <td><input type="text" id="chanceSource" name="chanceSource" readonly="readonly"/></td>
        </tr>
        <tr>
            <td>联系人：</td>
            <td><input type="text" id="linkMan" name="linkMan" readonly="readonly"/></td>
            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td>联系电话：</td>
            <td><input type="text" id="linkPhone" name="linkPhone" readonly="readonly"/></td>
        </tr>
        <tr>
            <td>成功几率(%)：</td>
            <td><input type="text" id="cgjl" name="cgjl" readonly="readonly"/></td>
            <td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;</td>
        </tr>
        <tr>
            <td>概要：</td>
            <td colspan="4"><input type="text" id="overView" name="overView" style="width: 420px" readonly="readonly"/></td>
        </tr>
        <tr>
            <td>机会描述：</td>
            <td colspan="4">
                <textarea rows="5" cols="50" id="description" name="description" readonly="readonly"></textarea>
            </td>
        </tr>
        <tr>
            <td>创建人：</td>
            <td><input type="text" readonly="readonly" id="createMan" name="createMan" /></td>
            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td>创建时间：</td>
            <td><input type="text" id="createTime" name="createTime" readonly="readonly"/></td>
        </tr>
        <tr>
            <td>指派给：</td>
            <td>
                <input type="text" readonly="readonly" id="assignMan" name="assignMan" />
            </td>
            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td>指派时间：</td>
            <td><input type="text" id="assignTime" name="assignTime" readonly="readonly"/></td>
        </tr>
    </table>
</div>

<br/>
<table id="dg" title="开发计划项" style="width:700px;height:250px"
       toolbar="#toolbar" idField="id" rownumbers="true" fitColumns="true" singleSelect="true">
    <thead>
    <tr>
        <th field="id" width="50">编号</th>
        <th field="planDate" width="50" editor="{type:'datebox',options:{required:true}}">日期</th>
        <th field="planItem" width="100" editor="{type:'validatebox',options:{required:true}}">计划内容</th>
        <th field="exeAffect" width="100" editor="{type:'validatebox',options:{required:true}}">执行效果</th>
    </tr>
    </thead>
</table>

<div id="toolbar">
    <c:if test="${param.show!='true' }">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#dg').edatagrid('addRow')">添加计划</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#dg').edatagrid('destroyRow')">删除计划</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#dg').edatagrid('saveRow');$('#dg').edatagrid('reload')">保存计划</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#dg').edatagrid('cancelRow')">撤销行</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-kfcg" plain="true" onclick="updateSaleChanceDevResult(2)">开发成功</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-zzkf" plain="true" onclick="updateSaleChanceDevResult(3)">终止开发</a>
    </c:if>
</div>
</body>
</html>
```

![image-20230830223522875](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308302235180.png)

## 服务管理

```
为客户服务而设立的功能，例如 客户的咨询，投诉等
客户服务表（t_customer_service）
```

### 服务创建

#### CustomerService

客户服务实体类

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 客户服务实体
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerService {

	private Integer id; // 编号
	private String serveType; // 服务类型 1,咨询 2，建议 3，投诉
	private String overview; // 概要
	private String customer; // 客户
	private String state; // 1，新创建 2，已分配 3，已处理 4，已归档
	private String servicerequest; // 服务请求
	private String createPeople; // 创建人
	private Date createTime; // 创建日期
	private String assigner; // 分配人
	private Date assignTime; // 分配日期
	private String serviceProce; // 服务处理
	private String serviceProcePeople; // 服务处理人
	private Date serviceProceTime; // 服务处理日期
	private String serviceProceResult; // 服务处理结果
	private String myd; // 客户满意度
	
}

```

#### CustomerServiceDao

```java
package com.et.dao;

import com.et.entity.CustomerService;

/**
 * 客户服务Dao接口
 * @author Administrator
 *
 */
public interface CustomerServiceDao {

	/**
	 * 添加客户服务
	 * @param customerService
	 * @return
	 */
	public int add(CustomerService customerService);
}

```

#### CustomerServiceMapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.et.dao.CustomerServiceDao">

    <insert id="add" parameterType="CustomerService">
        insert into t_customer_service values(null,#{serveType},#{overview},#{customer},#{state},#{servicerequest},#{createPeople},#{createTime},#{assigner},#{assignTime},#{serviceProce},#{serviceProcePeople},#{serviceProceTime},#{serviceProceResult},#{myd})
    </insert>


</mapper>
```

#### CustomerServiceService

```java
package com.et.service;

import com.et.entity.CustomerService;

/**
 * 客户服务Service接口
 * @author Administrator
 *
 */
public interface CustomerServiceService {

	/**
	 * 添加客户服务
	 * @param customerService
	 * @return
	 */
	public int add(CustomerService customerService);
}

```

#### CustomerServiceServiceImpl

```java
package com.et.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.et.dao.CustomerServiceDao;
import com.et.service.CustomerServiceService;

/**
 * 客服服务Service实现类
 * @author Administrator
 *
 */
@Service("customerServiceService")
public class CustomerServiceServiceImpl implements CustomerServiceService{

	@Resource
	private CustomerServiceDao customerServiceDao;
	
	@Override
	public int add(com.et.entity.CustomerService customerService) {
		return customerServiceDao.add(customerService);
	}
}

```

#### CustomerServiceController

```java
package com.et.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.et.entity.CustomerService;
import com.et.service.CustomerServiceService;
import com.et.util.ResponseUtil;

import net.sf.json.JSONObject;

/**
 * 客服服务Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/customerService")
public class CustomerServiceController {
	
	@Resource
	private CustomerServiceService customerServiceService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	 

	/**
	 * 添加或者修改客服服务
	 * @param contact
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(CustomerService customerService,HttpServletResponse response)throws Exception{
		int resultTotal=0; // 操作的记录条数
		if(customerService.getId()==null){
			resultTotal=customerServiceService.add(customerService);
		}else{
			
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
	

}

```

#### customerServiceCreate.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.edatagrid.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
<script type="text/javascript">
 

 $(function(){
	
	 $("#createTime").val(getCurrentDate());
	 
 });
 
 function resetValue(){
	 $("#serveType").combobox("setValue","");
	 $("#customer").val("");
	 $("#overview").val("");
	 $("#servicerequest").val("");
 }
 
 function saveCustomerService(){
	 var url="${pageContext.request.contextPath}/customerService/save.do";
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
			}else{
				$.messager.alert("系统提示","保存失败！");
				return;
			}
		}
	 });
 }
 
</script>
<title>Insert title here</title>
</head>
<body style="margin: 15px">
 
 <div id="p" class="easyui-panel" title="客服服务创建" style="width: 600px;height: 350px;padding: 10px">
   <form id="fm" method="post">
 	<table cellspacing="8px">
   		<tr>
   			<td>服务类型：</td>
   			<td>
   				<input class="easyui-combobox" id="serveType" name="serveType" data-options="panelHeight:'auto',editable:false,valueField:'dataDicValue',textField:'dataDicValue',url:'${pageContext.request.contextPath}/dataDic/dataDicComboList.do?dataDicName=服务类型'"/>&nbsp;<font color="red">*</font>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>客户</td>
   			<td><input type="text" id="customer" name="customer" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font></td>
   		</tr>
   		<tr>
   			<td>概要：</td>
   			<td colspan="4">
   				<input type="text" id="overview" name="overview" style="width: 419px" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>服务请求：</td>
   			<td colspan="4">
   				<textarea id="servicerequest" name="servicerequest" rows="5" cols="49" class="easyui-validatebox" required="true"></textarea>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>创建人：</td>
   			<td>
   				<input type="hidden" name="state" value="新创建"/>
   				<input type="text" readonly="readonly" id="createPeople" name="createPeople" value="${currentUser.trueName }"/>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>创建时间：</td>
   			<td><input type="text" id="createTime" name="createTime" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td colspan="4"></td>
   			<td>
   				<a href="javascript:saveCustomerService()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>&nbsp;&nbsp;
   				<a href="javascript:resetValue()" class="easyui-linkbutton" iconCls="icon-reset">重置</a>
   			</td>
   		</tr>
   	</table>
   </form>
 </div>
 
 
</body>
</html>
```

![image-20230830225855617](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308302258086.png)

### 服务分配

```
将服务分配给指定的人处理
```

#### CustomerServiceDao

```java
/**
	 * 查询客户服务
	 * @param map
	 * @return
	 */
	public List<CustomerService> find(Map<String,Object> map);
	
	/**
	 * 查询客户服务记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 修改客户服务
	 * @param customerService
	 * @return
	 */
	public int update(CustomerService customerService);
```

#### CustomerServiceMapper

result结果集同样需要补全字段

```xml
<select id="find" parameterType="Map" resultMap="CustomerServiceResult">
        select * from t_customer_service
        <where>
            <if test="state!=null and state!='' ">
                and state = #{state}
            </if>
        </where>
        <if test="start!=null and size!=null">
            limit #{start},#{size}
        </if>
    </select>

    <select id="getTotal" parameterType="Map" resultType="Long">
        select count(*) from t_customer_service
        <where>
            <if test="state!=null and state!='' ">
                and state = #{state}
            </if>
        </where>
    </select>

    <update id="update" parameterType="CustomerService">
        update t_customer_service
        <set>
            <if test="serveType!=null and serveType!='' ">
                serveType = #{serveType},
            </if>
            <if test="overview!=null and overview!='' ">
                overview = #{overview},
            </if>
            <if test="customer!=null and customer!='' ">
                customer = #{customer},
            </if>
            <if test="state!=null and state!='' ">
                state = #{state},
            </if>
            <if test="servicerequest!=null and servicerequest!='' ">
                servicerequest = #{servicerequest},
            </if>
            <if test="createPeople!=null and createPeople!='' ">
                createPeople = #{createPeople},
            </if>
            <if test="createTime!=null  ">
                createTime = #{createTime},
            </if>
            <if test="assigner!=null and assigner!='' ">
                assigner = #{assigner},
            </if>
            <if test="assignTime!=null ">
                assignTime = #{assignTime},
            </if>
            <if test="serviceProce!=null and serviceProce!='' ">
                serviceProce = #{serviceProce},
            </if>
            <if test="serviceProcePeople!=null and serviceProcePeople!='' ">
                serviceProcePeople = #{serviceProcePeople},
            </if>
            <if test="serviceProceTime!=null ">
                serviceProceTime = #{serviceProceTime},
            </if>
            <if test="serviceProceResult!=null and serviceProceResult!='' ">
                serviceProceResult = #{serviceProceResult},
            </if>
            <if test="myd!=null and myd!='' ">
                myd = #{myd},
            </if>
        </set>
        where id=#{id}
    </update>
```

#### CustomerServiceService

```java
/**
	 * 查询客户服务
	 * @param map
	 * @return
	 */
	public List<CustomerService> find(Map<String,Object> map);

	/**
	 * 查询客户服务记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);

	/**
	 * 修改客户服务
	 * @param customerService
	 * @return
	 */
	public int update(CustomerService customerService);
```

#### CustomerServiceServiceImpl

```java
@Override
	public List<com.et.entity.CustomerService> find(Map<String, Object> map) {
		return customerServiceDao.find(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return customerServiceDao.getTotal(map);
	}

	@Override
	public int update(com.et.entity.CustomerService customerService) {
		return customerServiceDao.update(customerService);
	}
```

#### CustomerServiceController

```java
/**
	 * 分页条件查询客户服务
	 * @param page
	 * @param rows
	 * @param s_customer
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,CustomerService s_customerService,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("state", s_customerService.getState());
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<CustomerService> customerServiceList=customerServiceService.find(map);
		Long total=customerServiceService.getTotal(map);
		JSONObject result=new JSONObject();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray=JSONArray.fromObject(customerServiceList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}


	/**
	 * 添加或者修改客服服务
	 * @param contact
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(CustomerService customerService,HttpServletResponse response)throws Exception{
		int resultTotal=0; // 操作的记录条数
		if(customerService.getId()==null){
			resultTotal=customerServiceService.add(customerService);
		}else{
			resultTotal=customerServiceService.update(customerService);
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

```

#### customerServiceAssign.jsp

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

        function openCustomerServiceAssignDialog(){
            var selectedRows=$("#dg").datagrid("getSelections");
            if(selectedRows.length!=1){
                $.messager.alert("系统提示","请选择一条要分配的客户服务数据！");
                return;
            }
            var row=selectedRows[0];
            $("#dlg").dialog("open").dialog("setTitle","分配客服服务");
            $("#fm").form("load",row);
            $("#state").val("已分配");
            $("#assignTime").val(getCurrentDate());
            url="${pageContext.request.contextPath}/customerService/save.do?id="+row.id;
        }

        function saveCustomerService(){
            $("#fm").form("submit",{
                url:url,
                onSubmit:function(){
                    return $(this).form("validate");
                },
                success:function(result){
                    var result=eval('('+result+')');
                    if(result.success){
                        $.messager.alert("系统提示","分配成功！");
                        resetValue();
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
                    }else{
                        $.messager.alert("系统提示","分配失败！");
                        return;
                    }
                }
            });
        }

        function resetValue(){
            $("#assigner").combobox("setValue","");
        }

        function closeUserDialog(){
            $("#dlg").dialog("close");
            resetValue();
        }


    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 1px">
<table id="dg" title="客服服务分配管理" class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/customerService/list.do?state=新创建" fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">编号</th>
        <th field="customer" width="100" align="center">客户</th>
        <th field="overview" width="200" align="center">概要</th>
        <th field="serveType" width="100" align="center">服务类型</th>
        <th field="createPeople" width="100" align="center">创建人</th>
        <th field="createTime" width="100" align="center">创建日期</th>
        <th field="state" width="50" align="center" hidden="true">客服服务状态</th>
        <th field="assigner" width="50" align="center" hidden="true">分配人</th>
        <th field="assignTime" width="50" align="center" hidden="true">分配日期</th>
        <th field="serviceProce" width="50" align="center" hidden="true">服务处理</th>
        <th field="serviceProcePeople" width="50" align="center" hidden="true">服务处理人</th>
        <th field="serviceProceTime" width="50" align="center" hidden="true">服务处理日期</th>
        <th field="serviceProceResult" width="50" align="center" hidden="true">服务处理结果</th>
        <th field="myd" width="50" align="center" hidden="true">客户满意度</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        <a href="javascript:openCustomerServiceAssignDialog()" class="easyui-linkbutton" iconCls="icon-fwfp" plain="true">分配</a>
    </div>
</div>

<div id="dlg" class="easyui-dialog" style="width:750px;height:450px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">

    <form id="fm" method="post">
        <table cellspacing="8px">
            <table cellspacing="8px">
                <tr>
                    <td>服务类型：</td>
                    <td>
                        <input type="text" id="serveType" name="serveType" readonly="readonly"/>
                    </td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td>客户</td>
                    <td><input type="text" id="customer" name="customer" readonly="readonly"/></td>
                </tr>
                <tr>
                    <td>概要：</td>
                    <td colspan="4">
                        <input type="text" id="overview" name="overview" style="width: 419px" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td>服务请求：</td>
                    <td colspan="4">
                        <textarea id="servicerequest" name="servicerequest" rows="5" cols="49" readonly="readonly"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>创建人：</td>
                    <td>
                        <input type="hidden" id="state" name="state" />
                        <input type="text" readonly="readonly" id="createPeople" name="createPeople" />
                    </td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td>创建时间：</td>
                    <td><input type="text" id="createTime" name="createTime" readonly="readonly"/></td>
                </tr>
                <tr>
                    <td>分配给：</td>
                    <td>
                        <input class="easyui-combobox" id="assigner" name="assigner" data-options="panelHeight:'auto',editable:false,valueField:'trueName',textField:'trueName',url:'${pageContext.request.contextPath}/user/customerManagerComboList.do'"/>&nbsp;<font color="red">*</font>
                    </td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td>分配时间：</td>
                    <td>
                        <input type="text" id="assignTime" name="assignTime" readonly="readonly"/>
                    </td>
                </tr>
            </table>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:saveCustomerService()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeCustomerDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
</body>
</html>
```

![image-20230830231616024](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308302316678.png)

### 服务处理

```
服务处理时 只处理已分配的任务
只需要添加处理页面即可，业务逻辑与前边的可通用
	例如 修改状态，展示列表
```

#### customerServiceProce.jsp

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

 function openCustomerServiceProceDialog(){
	 var selectedRows=$("#dg").datagrid("getSelections");
	 if(selectedRows.length!=1){
		 $.messager.alert("系统提示","请选择一条要处理的客户服务数据！");
		 return;
	 }
	 var row=selectedRows[0];
	 $("#dlg").dialog("open").dialog("setTitle","处理客服服务");
	 $("#fm").form("load",row);
	 $("#serviceProceTime").val(getCurrentDate());
	 $("#serviceProcePeople").val('${currentUser.trueName}');	 
	 $("#state").val("已处理");
	 url="${pageContext.request.contextPath}/customerService/save.do?id="+row.id;
 }
 
 function saveCustomerServiceProce(){
	 $("#fm").form("submit",{
		url:url,
		onSubmit:function(){
			return $(this).form("validate");
		},
		success:function(result){
			var result=eval('('+result+')');
			if(result.success){
				$.messager.alert("系统提示","处理成功！");
				resetValue();
				$("#dlg").dialog("close");
				$("#dg").datagrid("reload");
			}else{
				$.messager.alert("系统提示","处理失败！");
				return;
			}
		}
	 });
 }
 
 function resetValue(){
	 $("#serviceProce").val("");
	 $("#serviceProceTime").val("");
 }
 
 function closeCustomerProceDialog(){
	 $("#dlg").dialog("close");
	 resetValue();
 }
 
 
</script>
<title>Insert title here</title>
</head>
<body style="margin: 1px">
 <table id="dg" title="客服服务处理管理" class="easyui-datagrid"
   fitColumns="true" pagination="true" rownumbers="true"
   url="${pageContext.request.contextPath}/customerService/list.do?state=已分配" fit="true" toolbar="#tb">
   <thead>
   	<tr>
   		<th field="cb" checkbox="true" align="center"></th>
   		<th field="id" width="50" align="center">编号</th>
   		<th field="customer" width="100" align="center">客户</th>
   		<th field="overview" width="200" align="center">概要</th>
   		<th field="serveType" width="100" align="center">服务类型</th>
   		<th field="createPeople" width="100" align="center">创建人</th>
   		<th field="createTime" width="100" align="center">创建日期</th>
   		<th field="state" width="50" align="center" hidden="true">客服服务状态</th>
   		<th field="assigner" width="50" align="center" >分配人</th>
   		<th field="assignTime" width="100" align="center" >分配日期</th>
   		<th field="serviceProce" width="100" align="center" hidden="true">服务处理</th>
   		<th field="serviceProcePeople" width="100" align="center" hidden="true">服务处理人</th>
   		<th field="serviceProceTime" width="100" align="center" hidden="true">服务处理日期</th>
   		<th field="serviceProceResult" width="50" align="center" hidden="true">服务处理结果</th>
   		<th field="myd" width="50" align="center" hidden="true">客户满意度</th>
   	</tr>
   </thead>
 </table>
 <div id="tb">
 	<div>
 		<a href="javascript:openCustomerServiceProceDialog()" class="easyui-linkbutton" iconCls="icon-fwcl" plain="true">处理</a>
 	</div>
 </div>
 
 <div id="dlg" class="easyui-dialog" style="width:750px;height:550px;padding: 10px 20px"
   closed="true" buttons="#dlg-buttons">
   
   <form id="fm" method="post">
   	<table cellspacing="8px">
   		<table cellspacing="8px">
   		<tr>
   			<td>服务类型：</td>
   			<td>
   				<input type="text" id="serveType" name="serveType" readonly="readonly"/>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>客户</td>
   			<td><input type="text" id="customer" name="customer" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>概要：</td>
   			<td colspan="4">
   				<input type="text" id="overview" name="overview" style="width: 419px" readonly="readonly"/>
   			</td>
   		</tr>
   		<tr>
   			<td>服务请求：</td>
   			<td colspan="4">
   				<textarea id="servicerequest" name="servicerequest" rows="5" cols="49" readonly="readonly"></textarea>
   			</td>
   		</tr>
   		<tr>
   			<td>创建人：</td>
   			<td>
   				<input type="text" readonly="readonly" id="createPeople" name="createPeople" />
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>创建时间：</td>
   			<td><input type="text" id="createTime" name="createTime" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>分配给：</td>
   			<td>
   				<input  id="assigner" name="assigner" readonly="readonly"/>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>分配时间：</td>
   			<td>
   				<input type="text" id="assignTime" name="assignTime" readonly="readonly"/>
   			</td>
   		</tr>
   		<tr>
   			<td>服务处理：</td>
   			<td colspan="4">
   				<textarea id="serviceProce" name="serviceProce" rows="5" cols="49" class="easyui-validatebox" required="true"></textarea>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>处理人：</td>
   			<td>
   				<input type="hidden" id="state" name="state" />
   				<input  id="serviceProcePeople" name="serviceProcePeople" readonly="readonly"/>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>处理时间：</td>
   			<td>
   				<input type="text" id="serviceProceTime" name="serviceProceTime" readonly="readonly"/>
   			</td>
   		</tr>
   	</table>
   </form>
 </div>
 
 <div id="dlg-buttons">
 	<a href="javascript:saveCustomerServiceProce()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
 	<a href="javascript:closeCustomerProceDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
 </div>
</body>
</html>
```

![image-20230830232740202](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308302327453.png)

### 服务反馈

服务反馈同样只需要添加页面功能即可 服务端功能通用

#### customerServiceFeedback.jsp

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

 function openCustomerServiceFeedbackDialog(){
	 var selectedRows=$("#dg").datagrid("getSelections");
	 if(selectedRows.length!=1){
		 $.messager.alert("系统提示","请选择一条要反馈的客户服务数据！");
		 return;
	 }
	 var row=selectedRows[0];
	 $("#dlg").dialog("open").dialog("setTitle","客服服务反馈");
	 $("#fm").form("load",row);	 
	 $("#state").val("已归档");
	 url="${pageContext.request.contextPath}/customerService/save.do?id="+row.id;
 }
 
 function saveCustomerServiceFeedback(){
	 $("#fm").form("submit",{
		url:url,
		onSubmit:function(){
			if($("#myd").combobox("getValue")==""){
				$.messager.alert("系统提示","请选择客户满意度！");
				return false;
			}
			return $(this).form("validate");
		},
		success:function(result){
			var result=eval('('+result+')');
			if(result.success){
				$.messager.alert("系统提示","反馈成功！");
				resetValue();
				$("#dlg").dialog("close");
				$("#dg").datagrid("reload");
			}else{
				$.messager.alert("系统提示","反馈失败！");
				return;
			}
		}
	 });
 }
 
 function resetValue(){
	 $("#serviceProceResult").val("");
	 $("#myd").combobox("setValue","");
 }
 
 function closeCustomerFeedbackDialog(){
	 $("#dlg").dialog("close");
	 resetValue();
 }
 
 
</script>
<title>Insert title here</title>
</head>
<body style="margin: 1px">
 <table id="dg" title="客服服务反馈管理" class="easyui-datagrid"
   fitColumns="true" pagination="true" rownumbers="true"
   url="${pageContext.request.contextPath}/customerService/list.do?state=已处理" fit="true" toolbar="#tb">
   <thead>
   	<tr>
   		<th field="cb" checkbox="true" align="center"></th>
   		<th field="id" width="50" align="center">编号</th>
   		<th field="customer" width="100" align="center">客户</th>
   		<th field="overview" width="200" align="center">概要</th>
   		<th field="serveType" width="100" align="center">服务类型</th>
   		<th field="createPeople" width="100" align="center">创建人</th>
   		<th field="createTime" width="100" align="center">创建日期</th>
   		<th field="state" width="50" align="center" hidden="true">客服服务状态</th>
   		<th field="assigner" width="50" align="center" >分配人</th>
   		<th field="assignTime" width="100" align="center" >分配日期</th>
   		<th field="serviceProce" width="100" align="center" hidden="true">服务处理</th>
   		<th field="serviceProcePeople" width="100" align="center" >服务处理人</th>
   		<th field="serviceProceTime" width="100" align="center" >服务处理日期</th>
   		<th field="serviceProceResult" width="50" align="center" hidden="true">服务处理结果</th>
   		<th field="myd" width="50" align="center" hidden="true">客户满意度</th>
   	</tr>
   </thead>
 </table>
 <div id="tb">
 	<div>
 		<a href="javascript:openCustomerServiceFeedbackDialog()" class="easyui-linkbutton" iconCls="icon-fwfk" plain="true">客户服务反馈</a>
 	</div>
 </div>
 
 <div id="dlg" class="easyui-dialog" style="width:750px;height:450px;padding: 10px 20px"
   closed="true" buttons="#dlg-buttons">
   
   <form id="fm" method="post">
   	<table cellspacing="8px">
   		<table cellspacing="8px">
   		<tr>
   			<td>服务类型：</td>
   			<td>
   				<input type="text" id="serveType" name="serveType" readonly="readonly"/>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>客户</td>
   			<td><input type="text" id="customer" name="customer" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>概要：</td>
   			<td colspan="4">
   				<input type="text" id="overview" name="overview" style="width: 419px" readonly="readonly"/>
   			</td>
   		</tr>
   		<tr>
   			<td>服务请求：</td>
   			<td colspan="4">
   				<textarea id="servicerequest" name="servicerequest" rows="5" cols="49" readonly="readonly"></textarea>
   			</td>
   		</tr>
   		<tr>
   			<td>创建人：</td>
   			<td>
   				<input type="text" readonly="readonly" id="createPeople" name="createPeople" />
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>创建时间：</td>
   			<td><input type="text" id="createTime" name="createTime" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>分配给：</td>
   			<td>
   				<input  id="assigner" name="assigner" readonly="readonly"/>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>分配时间：</td>
   			<td>
   				<input type="text" id="assignTime" name="assignTime" readonly="readonly"/>
   			</td>
   		</tr>
   		<tr>
   			<td>服务处理：</td>
   			<td colspan="4">
   				<textarea id="serviceProce" name="serviceProce" rows="5" cols="49" class="easyui-validatebox" required="true"></textarea>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   		<tr>
   			<td>处理人：</td>
   			<td>
   				<input  id="serviceProcePeople" name="serviceProcePeople" readonly="readonly"/>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>处理时间：</td>
   			<td>
   				<input type="text" id="serviceProceTime" name="serviceProceTime" readonly="readonly"/>
   			</td>
   		</tr>
   		<tr>
   			<td>处理结果：</td>
   			<td>
   				<input type="hidden" id="state" name="state" />
   				<input  id="serviceProceResult" name="serviceProceResult" class="easyui-validatebox" required="true"/>&nbsp;<font color="red">*</font>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>客户满意度：</td>
   			<td>
   				<select class="easyui-combobox" style="width: 154px" id="myd" name="myd" editable="false" panelHeight="auto" >
					<option value="">请选择...</option>	
					<option value="☆">☆</option>	
					<option value="☆☆">☆☆</option>	
					<option value="☆☆☆">☆☆☆</option>	
					<option value="☆☆☆☆">☆☆☆☆</option>		
					<option value="☆☆☆☆☆">☆☆☆☆☆</option>				
                </select>&nbsp;<font color="red">*</font>
   			</td>
   		</tr>
   	</table>
   </form>
 </div>
 
 <div id="dlg-buttons">
 	<a href="javascript:saveCustomerServiceFeedback()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
 	<a href="javascript:closeCustomerFeedbackDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
 </div>
</body>
</html>
```

![image-20230830233231221](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202308302332607.png)

### 服务归档

在服务处理完成后需要进行归档，其目的是方便查阅

#### CustomerServiceController

获取服务列表时添加检索条件

```java
/**
	 * 分页条件查询客户服务
	 * @param page
	 * @param rows
	 * @param s_customer
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,String createTimefrom,String createTimeto,CustomerService s_customerService,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("customer", StringUtil.formatLike(s_customerService.getCustomer()));
		map.put("overview", StringUtil.formatLike(s_customerService.getOverview()));
		map.put("serveType", s_customerService.getServeType());
		map.put("state", s_customerService.getState());
		map.put("createTimefrom", createTimefrom);
		map.put("createTimeto", createTimeto);
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<CustomerService> customerServiceList=customerServiceService.find(map);
		Long total=customerServiceService.getTotal(map);
		JSONObject result=new JSONObject();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray=JSONArray.fromObject(customerServiceList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
```

#### CustomerServiceMapper

添加检索条件

```xml
<select id="find" parameterType="Map" resultMap="CustomerServiceResult">
		select * from t_customer_service
		<where>
			<if test="customer!=null and customer!='' ">
				and customer like #{customer}
			</if>
			<if test="overview!=null and overview!='' ">
				and overview like #{overview}
			</if>
			<if test="serveType!=null and serveType!='' ">
				and serveType = #{serveType}
			</if>
			<if test="state!=null and state!='' ">
				and state = #{state}
			</if>
			<if test="createTimefrom!=null and createTimefrom!='' ">
				and createTime &gt;= #{createTimefrom}
			</if>
			<if test="createTimeto!=null and createTimeto!='' ">
				and createTime &lt;= #{createTimeto}
			</if>
		</where>
		<if test="start!=null and size!=null">
			limit #{start},#{size}
		</if>
	</select>
	
	<select id="getTotal" parameterType="Map" resultType="Long">
		select count(*) from t_customer_service
		<where>
			<if test="customer!=null and customer!='' ">
				and customer like #{customer}
			</if>
			<if test="overview!=null and overview!='' ">
				and overview like #{overview}
			</if>
			<if test="serveType!=null and serveType!='' ">
				and serveType = #{serveType}
			</if>
			<if test="state!=null and state!='' ">
				and state = #{state}
			</if>
			<if test="createTimefrom!=null and createTimefrom!='' ">
				and createTime &gt;= #{createTimefrom}
			</if>
			<if test="createTimeto!=null and createTimeto!='' ">
				and createTime &lt;= #{createTimeto}
			</if>
		</where>
	</select>
```

### customerServiceFile.jsp

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
 
 function searchCustomerService(){
	 $("#dg").datagrid('load',{
		"customer":$("#s_customer").val(), 
		"overview":$("#s_overview").val(), 
		"serveType":$("#s_serveType").combobox("getValue"), 
		"createTimefrom":$("#s_createTimefrom").datebox("getValue"), 
		"createTimeto":$("#s_createTimeto").datebox("getValue")
	 });
 }
 
 function openCustomerServiceFileDialog(){
	 var selectedRows=$("#dg").datagrid("getSelections");
	 if(selectedRows.length!=1){
		 $.messager.alert("系统提示","请选择一条要查看的客户服务数据！");
		 return;
	 }
	 var row=selectedRows[0];
	 $("#dlg").dialog("open").dialog("setTitle","客服服务详情");
	 $("#fm").form("load",row);	 
	 url="${pageContext.request.contextPath}/customerService/save.do?id="+row.id;
 }
 
 
 function closeCustomerFileDialog(){
	 $("#dlg").dialog("close");
 }
 
 
</script>
<title>Insert title here</title>
</head>
<body style="margin: 1px">
 <table id="dg" title="客服服务归档管理" class="easyui-datagrid"
   fitColumns="true" pagination="true" rownumbers="true"
   url="${pageContext.request.contextPath}/customerService/list.do?state=已归档" fit="true" toolbar="#tb">
   <thead>
   	<tr>
   		<th field="cb" checkbox="true" align="center"></th>
   		<th field="id" width="50" align="center">编号</th>
   		<th field="customer" width="100" align="center">客户</th>
   		<th field="overview" width="200" align="center">概要</th>
   		<th field="serveType" width="100" align="center">服务类型</th>
   		<th field="createPeople" width="100" align="center">创建人</th>
   		<th field="createTime" width="100" align="center">创建日期</th>
   		<th field="state" width="50" align="center" hidden="true">客服服务状态</th>
   		<th field="assigner" width="50" align="center" hidden="true">分配人</th>
   		<th field="assignTime" width="100" align="center" hidden="true">分配日期</th>
   		<th field="serviceProce" width="100" align="center" hidden="true">服务处理</th>
   		<th field="serviceProcePeople" width="100" align="center" hidden="true">服务处理人</th>
   		<th field="serviceProceTime" width="100" align="center" hidden="true">服务处理日期</th>
   		<th field="serviceProceResult" width="50" align="center" hidden="true">服务处理结果</th>
   		<th field="myd" width="50" align="center" hidden="true">客户满意度</th>
   	</tr>
   </thead>
 </table>
 <div id="tb">
 	<div>
 		<a href="javascript:openCustomerServiceFileDialog()" class="easyui-linkbutton" iconCls="icon-fwgd" plain="true">查看客户服务详情</a>
 	</div>
 	<div>
 		&nbsp;客户名称：&nbsp;<input type="text" id="s_customer" size="20" onkeydown="if(event.keyCode==13) searchCustomerService()"/>
 		&nbsp;概要：&nbsp;<input type="text" id="s_overview" size="20" onkeydown="if(event.keyCode==13) searchCustomerService()"/>
 		&nbsp;服务类型：&nbsp;<select class="easyui-combobox" id="s_serveType" editable="false" panelHeight="auto" >
 								<option value="">请选择...</option>	
 								<option value="咨询">咨询</option>
 								<option value="建议">建议</option>
 								<option value="投诉">投诉</option>					
 		                    </select>
 		&nbsp;创建日期：&nbsp;<input type="text" id="s_createTimefrom" class="easyui-datebox" editable="false"/>&nbsp;-&nbsp;<input type="text" id="s_createTimeto" class="easyui-datebox" editable="false"/>&nbsp;
 		<a href="javascript:searchCustomerService()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
 	</div>
 </div>
 
 <div id="dlg" class="easyui-dialog" style="width:750px;height:450px;padding: 10px 20px"
   closed="true" buttons="#dlg-buttons">
   
   <form id="fm" method="post">
   	<table cellspacing="8px">
   		<table cellspacing="8px">
   		<tr>
   			<td>服务类型：</td>
   			<td>
   				<input type="text" id="serveType" name="serveType" readonly="readonly"/>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>客户</td>
   			<td><input type="text" id="customer" name="customer" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>概要：</td>
   			<td colspan="4">
   				<input type="text" id="overview" name="overview" style="width: 419px" readonly="readonly"/>
   			</td>
   		</tr>
   		<tr>
   			<td>服务请求：</td>
   			<td colspan="4">
   				<textarea id="servicerequest" name="servicerequest" rows="5" cols="49" readonly="readonly"></textarea>
   			</td>
   		</tr>
   		<tr>
   			<td>创建人：</td>
   			<td>
   				<input type="text" readonly="readonly" id="createPeople" name="createPeople" />
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>创建时间：</td>
   			<td><input type="text" id="createTime" name="createTime" readonly="readonly"/></td>
   		</tr>
   		<tr>
   			<td>分配给：</td>
   			<td>
   				<input  id="assigner" name="assigner" readonly="readonly"/>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>分配时间：</td>
   			<td>
   				<input type="text" id="assignTime" name="assignTime" readonly="readonly"/>
   			</td>
   		</tr>
   		<tr>
   			<td>服务处理：</td>
   			<td colspan="4">
   				<textarea id="serviceProce" name="serviceProce" rows="5" cols="49" readonly="readonly"></textarea>
   			</td>
   		</tr>
   		<tr>
   			<td>处理人：</td>
   			<td>
   				<input  id="serviceProcePeople" name="serviceProcePeople" readonly="readonly"/>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>处理时间：</td>
   			<td>
   				<input type="text" id="serviceProceTime" name="serviceProceTime" readonly="readonly"/>
   			</td>
   		</tr>
   		<tr>
   			<td>处理结果：</td>
   			<td>
   				<input  id="serviceProceResult" name="serviceProceResult" readonly="readonly"/>
   			</td>
   			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
   			<td>客户满意度：</td>
   			<td>
   				<input  id="myd" name="myd" readonly="readonly"/>
   			</td>
   		</tr>
   	</table>
   </form>
 </div>
 
 <div id="dlg-buttons">
 	<a href="javascript:closeCustomerFileDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
 </div>
</body>
</html>
```

![image-20230831002616803](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230831002616803.png)

## 统计报表

### 客户贡献分析

#### CustomerGx

客户贡献实现类

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户贡献分析实体
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerGx {

	private String name; // 客户名称
	private float gx; // 贡献总金额
}

```

#### CustomerDao

```java
/**
	 * 查询客户贡献
	 * @param map
	 * @return
	 */
	public List<CustomerGx> findCustomerGx(Map<String,Object> map);
	
	/**
	 * 查询客户贡献记录数
	 * @param map
	 * @return
	 */
	public Long getTotalCustomerGx(Map<String,Object> map);

```

#### CustomerMapper

```xml
<resultMap type="CustomerGx" id="CustomerGxResult">
        <result property="name" column="name"/>
        <result property="gx" column="gx"/>
    </resultMap>

<select id="findCustomerGx" parameterType="Map" resultMap="CustomerGxResult">
		SELECT t1.name,SUM(t3.sum) AS gx FROM t_customer t1 LEFT JOIN t_customer_order t2 ON t1.id=t2.cusId LEFT JOIN t_order_details t3 ON t2.id=t3.orderId 
		<where>
			<if test="name!=null and name!='' ">
				and t1.name like #{name}
			</if>
		</where>
		GROUP BY t1.id
		<if test="start!=null and size!=null">
			limit #{start},#{size}
		</if>
	</select>
	
	<select id="getTotalCustomerGx" parameterType="Map" resultType="Long">
		select count(*) from t_customer
		<where>
			<if test="name!=null and name!='' ">
				and name like #{name}
			</if>
		</where>
	</select>
```

#### CustomerService

```java

	/**
	 * 查询客户贡献
	 * @param map
	 * @return
	 */
	public List<CustomerGx> findCustomerGx(Map<String,Object> map);
	
	/**
	 * 查询客户贡献记录数
	 * @param map
	 * @return
	 */
	public Long getTotalCustomerGx(Map<String,Object> map);
```

#### CustomerServiceImpl

```java
@Override
	public List<CustomerGx> findCustomerGx(Map<String, Object> map) {
		return customerDao.findCustomerGx(map);
	}

	@Override
	public Long getTotalCustomerGx(Map<String, Object> map) {
		return customerDao.getTotalCustomerGx(map);
	}
```

#### CustomerController

```java
/**
	 * 查询客户分析贡献
	 * @param page
	 * @param rows
	 * @param s_customer
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findCustomerGx")
	public String findCustomerGx(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,String name,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", StringUtil.formatLike(name));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<CustomerGx> customerGxList=customerService.findCustomerGx(map);
		Long total=customerService.getTotalCustomerGx(map);
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(customerGxList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
```

#### khgxfx.jsp

客户贡献分析

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

 function search(){
	 $("#dg").datagrid('load',{
		"name":$("#s_name").val() 
	 });
 }
 
</script>
<title>Insert title here</title>
</head>
<body style="margin: 1px">
 <table id="dg" title="客户贡献分析" class="easyui-datagrid"
   fitColumns="true" pagination="true" rownumbers="true"
   url="${pageContext.request.contextPath}/customer/findCustomerGx.do" fit="true" toolbar="#tb">
   <thead>
   	<tr>
   		<th field="name" width="300" align="center">客户名称</th>
   		<th field="gx" width="100" align="center">订单总金额(元)</th>
   	</tr>
   </thead>
 </table>
 <div id="tb">
 	<div>
 		&nbsp;客户名称：&nbsp;<input type="text" id="s_name" size="20" onkeydown="if(event.keyCode==13) search()"/>
 		<a href="javascript:search()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
 	</div>
 </div>
</body>
</html>
```

![image-20230831005041031](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230831005041031.png)

### 客户构成分析

分析客户的群体 根据客户表中的客户等级区分

#### CustomerGc

客户构成实体类

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户构成分析实体
 * @author Administrator
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGc {

	private String customerLevel; // 客户等级
	private int customerNum; // 客户数量
		
}

```

#### CustomerDao

```java
/**
	 * 查询客户构成
	 * @return
	 */
	public List<CustomerGc> findCustomerGc();
```

#### CustomerMapper

```xml
<resultMap type="CustomerGc" id="CustomerGcResult">
		<result property="customerLevel" column="customerLevel"/>
		<result property="customerNum" column="customerNum"/>
	</resultMap>

<select id="findCustomerGc" resultMap="CustomerGcResult">
		SELECT LEVEL AS customerLevel ,COUNT(LEVEL) AS customerNum FROM t_customer GROUP BY LEVEL;
	</select>
```

#### CustomerService

```java
/**
	 * 查询客户构成
	 * @return
	 */
	public List<CustomerGc> findCustomerGc();
```

#### CustomerServiceImpl

```java
@Override
	public List<CustomerGc> findCustomerGc() {
		return customerDao.findCustomerGc();
	}
```

#### CustomerController

```java
/**
	 * 查询客户构成分析
	 * @param page
	 * @param rows
	 * @param s_customer
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findCustomerGc")
	public String findCustomerGc(HttpServletResponse response)throws Exception{
		List<CustomerGc> customerGcList=customerService.findCustomerGc();
		JSONArray jsonArray=JSONArray.fromObject(customerGcList);
		ResponseUtil.write(response, jsonArray);
		return null;
	}
```

#### khgcfx.jsp

客户构成分析

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/highcharts4/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/highcharts4/js/highcharts.js" type="text/javascript"></script> 
<script type="text/javascript">

$(function () {
	
	var chart=new Highcharts.Chart({
		chart: {
			renderTo:'container',
            type: 'column',
            events:{
            	load:function(event){
            		// ajax请求后台加载数据
            		$.post("${pageContext.request.contextPath}/customer/findCustomerGc.do",{},function(result){
            			var xArr=new Array();
            			var yArr=new Array();
            			for(var i=0;i<result.length;i++){
            				xArr.push(result[i].customerLevel);
            				yArr.push(result[i].customerNum);
            				chart.xAxis[0].categories=xArr;
            				chart.series[0].setData(yArr);
            			}
            		},"json");
            	}
            }
        },
        title: {
            text: '客户构成分析'
        },
        xAxis: {
        	title:'客户等级',
            categories: [
                'A',
                'B',
                'C'
            ]
        },
        yAxis: {
            title: {
                text: '客户数量'
            }
        },
        series: [{
            name: '客户',
            data: [1,2,3]
        }]
	});
});
 
</script>
<title>Insert title here</title>
</head>
<body style="margin: 1px">
 <div id="container" style="min-width: 800px;height: 400px"></div>
</body>
</html>
```

![image-20230831010602776](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230831010602776.png)

### 客户服务分析

#### CustomerFw

客户服务实体

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户服务分析实体
 * @author Administrator
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFw {

	private String serveType; // 服务类型
	private int num; // 数量
	
	
	
}

```

#### CustomerDao

```java
/**
	 * 查询客户服务分析
	 * @return
	 */
	public List<CustomerFw> findCustomerFw();
```

#### CustomerMapper

```xml
<resultMap type="CustomerFw" id="CustomerFwResult">
		<result property="serveType" column="serveType"/>
		<result property="num" column="num"/>
	</resultMap>

<select id="findCustomerFw" resultMap="CustomerFwResult">
		SELECT serveType , COUNT(serveType) AS num FROM t_customer_service GROUP BY serveType;
	</select>
```

#### CustomerService

```java
/**
	 * 查询客户服务分析
	 * @return
	 */
	public List<CustomerFw> findCustomerFw();
```

#### CustomerServiceImpl

```java
@Override
	public List<CustomerFw> findCustomerFw() {
		return customerDao.findCustomerFw();
	}
```

#### CustomerController

```java
/**
	 * 查询客户服务分析
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findCustomerFw")
	public String findCustomerFw(HttpServletResponse response)throws Exception{
		List<CustomerFw> customerFwList=customerService.findCustomerFw();
		JSONArray jsonArray=JSONArray.fromObject(customerFwList);
		ResponseUtil.write(response, jsonArray);
		return null;
	}
```

#### khfwfx.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/highcharts4/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/highcharts4/js/highcharts.js" type="text/javascript"></script> 
<script type="text/javascript">

$(function () {
	
	var chart=new Highcharts.Chart({
		chart: {
			renderTo:'container',
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            events:{
            	load:function(event){
            		var series=this.series[0];
            		// ajax请求后台加载数据
            		$.post("${pageContext.request.contextPath}/customer/findCustomerFw.do",{},function(result){
            			var xArr=new Array();
            			for(var i=0;i<result.length;i++){
            				xArr[i]=new Array();
            				xArr[i][0]=result[i].serveType;
            				xArr[i][1]=result[i].num;
            			}
            			series.setData(xArr);
            		},"json");
            	}
            }
        },
        title: {
            text: '客服服务分析'
        },
        tooltip: {
        	formatter:function(){
        		return '<b>'+this.point.name+'</b>:'+Highcharts.numberFormat(this.percentage,1)+'% ('+this.y+'个)'	
        	}
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                }
            }
        },
        series: [{
            type: 'pie',
            name: '比例',
            data: [
               
            ]
        }]
	});
});
 
</script>
<title>Insert title here</title>
</head>
<body style="margin: 1px">
 <div id="container" style="min-width: 800px;height: 400px"></div>
</body>
</html>
```

![image-20230831011652408](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230831011652408.png)

### 客户流失分析

客户流失分析 使用之前的功能 重新组建一个页面即可 查询客户流失状态 state=1

#### khlsfx.jsp

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


        function searchCustomerLoss(){
            $("#dg").datagrid('load',{
                "cusName":$("#s_cusName").val(),
                "cusManager":$("#s_cusManager").val()
            });
        }

    </script>
    <title>Insert title here</title>
</head>
<body style="margin: 1px">
<table id="dg" title="客户流失分析" class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/customerLoss/list.do?state=1" fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">编号</th>
        <th field="cusNo" width="50" align="center" hidden="true">客户编号</th>
        <th field="cusName" width="100" align="center">客户名称</th>
        <th field="cusManager" width="100" align="center">客户经理</th>
        <th field="lastOrderTime" width="100" align="center">上次下单日期</th>
        <th field="confirmLossTime" width="100" align="center">确认流失日期</th>
        <th field="state" width="80" align="center" hidden="true">客户状态</th>
        <th field="lossReason" width="200" align="center">流失原因</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>

    </div>
    <div>
        &nbsp;客户名称：&nbsp;<input type="text" id="s_cusName" size="20" onkeydown="if(event.keyCode==13) searchCustomerLoss()"/>
        &nbsp;客户经理：&nbsp;<input type="text" id="s_cusManager" size="20" onkeydown="if(event.keyCode==13) searchCustomerLoss()"/>
        <a href="javascript:searchCustomerLoss()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>

</body>
</html>
```

![image-20230831012422360](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230831012422360.png)



## 系统管理

### 修改密码/安全退出

#### UserController

```java
/**
	 * 修改用户密码
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyPassword")
	public String modifyPassword(Integer id,String newPassword,HttpServletResponse response)throws Exception{
		User user=new User();
		user.setId(id);
		user.setPassword(newPassword);
		int resultTotal=userService.update(user);
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
	 * 用户注销
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session)throws Exception{
		session.invalidate();
		return "redirect:/login.jsp";
	}
```

#### main.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
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
		url="${pageContext.request.contextPath}/user/modifyPassword.do?id=${currentUser.id}";
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
				window.location.href='${pageContext.request.contextPath}/user/logout.do';
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
	版本所有 java知识分享网 <a href="http://www.java1234.com" target="_blank">http://www.java1234.com</a>(2013-2015)
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

![image-20230831013304371](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20230831013304371.png)

### 
