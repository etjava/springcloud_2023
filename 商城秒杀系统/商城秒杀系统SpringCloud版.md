# SpringCloud版商城秒杀系统

![image-20231024195155449](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310241953216.png)

## 概述

```
商品秒杀系统是微服务架构体系中的综合练习项目(偏实战性质)
该系统采用前后端分离架构，前端是vue2+vue-element,后端是springboot+springcloud+springcloudalibaba
开发工具：
	IDEA，WebStorm
插件：
	基于docker的redis,rabbitmq
压力测试
	Jmeter
自定义分布式session管理
	session,token存放到redis中
MQ
	RabbitMQ 来实现异步下单
使用Redis作为缓存机制 提高QPS
使用图像验证码提高QPS
使用Nacos作为服务注册中心和配置中心
使用OpenFeign进行服务之间的调用
使用GateWay路由网关进行限流

注意：
	springboot版本需要使用2.3.2.RELEASE 因为需要整合SpringCloud alibaba 而它推荐的版本就是这个
	
	
项目采用循序渐进的方式逐步演化
	即 从单体项目演化到微服务项目
```

## 系统架构示意图

![image-20231024194226746](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310242001948.png)

## 单体目搭建（服务端）

![image-20231024201401540](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310242014620.png)

![image-20231024201438140](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310242014210.png)

### 添加依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.et</groupId>
    <artifactId>seckill-sys</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>seckill-sys</name>
    <description>seckill-sys</description>
    <properties>
        <java.version>1.8</java.version>
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
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.3.2</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.10</version>
        </dependency>

        <!-- spring boot redis 缓存引入 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <!-- lettuce pool 缓存连接池 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.35</version>
        </dependency>

        <dependency>
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
        </dependency>

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.6</version>
        </dependency>

        <!-- rabbmitmq -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>



    </dependencies>

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

### 添加配置文件

```properties
server:
  port: 80
  servlet:
    context-path: /

spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_seckill?serverTimezone=Asia/Shanghai
    username: root
    password: Karen@1234

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true  # 开启驼峰功能  userName  - >  user_name
    auto-mapping-behavior: full  # 自动mapping映射 可关闭
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  mapper-locations: classpath:/mapper/*.xml

```

### 创建用户实体

```java
package com.et.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("t_user")
@Data
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id; // 编号
    private String username; // 用户名
    private String password; // 密码
    private Date registerDate; // 注册日期
    private String address; // 地址
    private String phoneNumber; // 手机号码
    private String name; // 姓名

}
```

### 创建数据库和表

```
数据库 db_seckill

CREATE TABLE `t_user` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `username` varchar(300) DEFAULT NULL,
   `password` varchar(600) DEFAULT NULL,
   `register_date` datetime DEFAULT NULL,
   `address` varchar(900) DEFAULT NULL,
   `phone_number` varchar(60) DEFAULT NULL,
   `name` varchar(90) DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8
```

### 创建Mapper接口

```java
package com.et.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.et.entity.User;

/**
 * 用户Mapper接口
 */
public interface UserMapper extends BaseMapper<User> {
}

```

### 创建Service接口

```java
package com.et.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.et.entity.User;

/**
 * 用户Service层
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    public User findByUserName(String userName);
}
```

### 创建Service接口实现类

```java
package com.et.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.et.entity.User;
import com.et.mapper.UserMapper;
import com.et.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String userName) {
        // 拼装查询条件
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.eq(User::getUsername,userName);
        return userMapper.selectOne(query);
    }
}

```

### 创建Controller

```java
package com.et.controller;

import com.et.entity.User;
import com.et.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制层
 */
@RestController
@RequestMapping("/")
public class LoginContrller {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @return
     */
    @RequestMapping("/login")
    public User login(){
        return userService.findByUserName("etjava");
    }
}
```

### 启动并测试

![image-20231024204705328](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310242047461.png)

小结：

这里使用的是MyBatis-plus 因此有些基本查询操作可以省略（直接调用MP封装好的即可）

另外 启动类上面如果不加@MapperScan 就必须要在每个Mapper接口上添加@Mapper注解 否则Spring扫描不到接口

## 前端搭建

```
前端采用vue 因此需要先进行安装node及vue-cli脚手架

检测本地node环节
node -v 会显示版本


========================
卸载vue
# 卸载最新版本
npm uninstall -g @vue/cli
# 卸载指定版本
npm uninstall -g @vue/cli@3.11.0

# 安装最新版本
npm install -g @vue/cli
# 安装指定版本
npm install -g @vue/cli@3.11.0

在安装vue时 如果一直不成功 需要先安装cnpm 然后使用cnpm来安装vue
npm install cnpm -g

---------------------
npm方式卸载与安装vue
npm uninstall vue
卸载Vue3。

在控制台输入
npm uninstall vue-cli
卸载Vue-cli。

卸载Vue-router 在控制台输入
npm uninstall vue-router

安装Vue2。在控制台输入
npm install vue@2.6.11


安装Vue-cli2。在控制台输入
npm install vue-cli@2.9.6


安装Vue-router2。 在控制台输入
npm install vue-router@0.7.13


更新依赖包
在控制台输入
npm update

初始化项目
vue init webpack 项目名称
```

![image-20231024212600232](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310242126289.png)

### 使用vue脚手架初始化项目

![image-20231024213252264](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310242132581.png)

![image-20231024213335248](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310242133493.png)

![image-20231024213324561](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310242133713.png)

### 使用WebStorm打开项目

![image-20231024213656539](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310242136942.png)

### 运行项目并访问测试

运行使用npm run dev

![image-20231024213727688](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310242137128.png)

![image-20231024213755788](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310242137786.png)

### 登录页面搭建

```
搭建流程
1. APP.vue中除<router-view/> 其余元素去掉 它是用来显示路由过来的其他组件的
2. components中新建一个Login.vue组件
3. router下index.js中引入Login组件并暴露出去
4. 安装element-ui前端框架
	npm i element-ui -S
5. 设计登录页面样式
	参考elementui官网组件
	https://element.eleme.cn/#/zh-CN/component/installation
	
```

#### 登录页面搭建

#### 安装elementui

这里安装完成是2.15版本 在package.json中查看

```
npm i element-ui -S
```

#### main.js中引入elementui前端框架

```js
// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUi from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.config.productionTip = false
Vue.use(ElementUi)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

```

Login.vue

```vue
<template>
  <div class="login-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>秒杀商城-用户登录</span>
      </div>
      <el-form label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="username" placeholder="用户名" style="width: 252px;"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model="password" placeholder="密码" style="width: 252px;"/>
        </el-form-item>
        <el-form-item >
          <el-button type="primary" style="width: 252px;">登录</el-button>
        </el-form-item>
      </el-form>
      <div style="text-align: center">
        Powery by <a href="#">版权信息</a> @Copyright
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () { // 注意 默认都是有空格的
    return {
      username: '',
      password: ''
    }
  }
}
</script>

<style scoped>
.login-container{
  display: flex;/*flex布局*/
  justify-content: center;/*居中*/
  margin-top: 152px;
}
</style>

```

#### index.js中引入Login组件

```js
import Vue from 'vue'
import Router from 'vue-router'
// 导入Login组件
import Login from '@/components/Login'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    }
  ]
})

```

#### App.vue中修改背景图

```vue
<template>
  <div id="bk">
    <div>
      <router-view/>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#bk{
  background:url("../static/images/2.jpg");
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
}
</style>

```

#### 启动并测试

![image-20231024230329613](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310242303469.png)

#### 小结

```
vue只是前端的一个基础框架 可以配合其他前端框架一齐使用 但需要先进行安装
vue要显示一个组件的流程
1 定义需要显示的vue组件
2 在index.js中引入该组件
3 如果是安装的组件 还需要在main.js中引入
```

### 引入axios组件

```
axios组件是用来发送ajax请求的
安装指令
npm install --save axios
```

![image-20231024231116562](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310242311683.png)

#### 使用axios发送登录请求

```
要使用axios需要在使用的组件中(Login.vue) 引入
```

##### Login.vue

```vue
<template>
  <div class="login-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>秒杀商城-用户登录</span>
      </div>
      <el-form label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="username" placeholder="用户名" style="width: 252px;"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model="password" placeholder="密码" style="width: 252px;"/>
        </el-form-item>
        <el-form-item >
          <el-button type="primary" style="width: 252px;" @click="submit()">登录</el-button>
        </el-form-item>
        <p class="errorInfo">{{errorInfo}}</p>
      </el-form>
      <div style="text-align: center;">
        <a href="#">版权信息</a> @Copyright
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Login',
  data () { // 注意 默认都是有空格的
    return {
      username: '',
      password: '',
      errorInfo: ''
    }
  },
  methods: {
    submit () {
      if (this.username.trim() == null || this.username == '') {
        this.errorInfo = '用户名不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      if (this.password.trim() == null || this.password == '') {
        this.errorInfo = '密码不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      // 发送登录请求
      axios.post(
        'http://localhost/login',
        {
          'username': this.username,
          'password': this.password
        })
        .then(response => { 
          console.log(response.data)
        }).catch(error => {
          this.errorInfo = error
        })
    }
  }
}
</script>

<style scoped>
.login-container{
  display: flex;/*flex布局*/
  justify-content: center;/*居中*/
  margin-top: 152px;
}
.errorInfo{
  font-weight: bold;
  text-align: center;
  color: red;
}
</style>

```

![image-20231024235028842](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310242350909.png)

## 服务端处理跨域请求

```java
package com.et.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 处理跨域问题
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                .allowCredentials(true)
                .allowedOrigins("*")
            	.maxAge(3600);
    }
}

```

## 服务端登录请求处理

### controller中接收页面参数并做登录校验

```java
package com.et.controller;

import com.et.entity.User;
import com.et.service.UserService;
import com.et.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制层
 */
@RestController
@RequestMapping("/")
public class LoginContrller {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @return
     */
    @RequestMapping("/login")
    public R login(@RequestBody User user){
        if(user==null){
            return R.error();
        }
        if(StringUtils.isEmpty(user.getUsername())){
            return R.error("用户名不能为空");
        }
        if(StringUtils.isEmpty(user.getPassword())){
            return R.error("密码不能为空");
        }
        User result = userService.findByUserName(user.getUsername());
        if(result==null){
            return R.error("用户不存在");
        }
        if(!result.getPassword().trim().equals(user.getPassword().trim())){
            return R.error("用户名或者密码错误！");
        }
        return R.ok("登录成功！");
    }
}
```

### 返回给前端数据的工具类

```java
package com.et.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据工具类
 */
public class R extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 0);
    }

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
```



### 前端页面修改

Login.vue

在接收到后端返回的数据时进行判读并显示错误信息

```vue
<template>
  <div class="login-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>秒杀商城-用户登录</span>
      </div>
      <el-form label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="username" placeholder="用户名" style="width: 252px;"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model="password" placeholder="密码" style="width: 252px;"/>
        </el-form-item>
        <el-form-item >
          <el-button type="primary" style="width: 252px;" @click="submit()">登录</el-button>
        </el-form-item>
        <p class="errorInfo">{{errorInfo}}</p>
      </el-form>
      <div style="text-align: center;">
        <a href="#">版权信息</a> @Copyright
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Login',
  data () { // 注意 默认都是有空格的
    return {
      username: '',
      password: '',
      errorInfo: ''
    }
  },
  methods: {
    submit () {
      if (this.username.trim() == null || this.username === '') {
        this.errorInfo = '用户名不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      if (this.password.trim() == null || this.password === '') {
        this.errorInfo = '密码不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      // 发送登录请求
      axios.post(
        'http://localhost/login',
        {
          'username': this.username,
          'password': this.password
        })
        .then(response => {
          let data = response.data
          if (data.code === 0) {
            console.log(data)
          } else if (data.code === 500) {
            this.errorInfo = data.msg
          }
        }).catch(error => {
          this.errorInfo = error
        })
    }
  }
}
</script>

<style scoped>
.login-container{
  display: flex;/*flex布局*/
  justify-content: center;/*居中*/
  margin-top: 152px;
}
.errorInfo{
  font-weight: bold;
  text-align: center;
  color: red;
}
</style>

```

![image-20231025001713996](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310250017129.png)

## MD5二次加密

```
密码在网络传输中应避免明文传输 因此 需要先在前端对密码进行加密 后端在判断密码是否正确时 需要对获取到的密码进行加密 这样两者进行比较
两次MD5加密
	客户端MD5加密（添加固定的salt） 发送到服务器，主要为了安全 防止明文被窃取
	服务端再次进行MD5加密(随机salt) 然后和数据库密码匹配，验证是否正确，即使数据库被破解 也无法破解密码

MD5加密工具类
<dependency>
    <groupId>commons-codec</groupId>
    <artifactId>commons-codec</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
</dependency>	
```

### 添加依赖

```xml
<dependency>
    <groupId>commons-codec</groupId>
    <artifactId>commons-codec</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
</dependency>	
```

### 创建加密工具类

```
前端会使用固定salt进行加密 然后后端在根据前端传递的数据 添加随机的salt再次进行加密
```

```java
package com.et.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密工具类
 */
public class Md5Util {

    private static  final String FRONT_SALT="3dfsty"; // 前端加密盐

    private static final String BACK_SALT_BEFORE="3qx2dfdx3"; // 后端加密前置盐

    private static final String BACK_SALT_AFTER="ds2f3dsf5"; // 后端加密后置盐

    /**
     * md5加密
     * @param data
     * @return
     */
    public static String md5(String data){
        return DigestUtils.md5Hex(data);
    }

    /**
     * 前端加盐后md5加密
     * @param frontData
     * @return
     */
    public static String frontMd5(String frontData){
        return md5(FRONT_SALT+frontData);
    }

    /**
     * 后端加盐后md5加密
     * @param backData
     * @return
     */
    public static String backMd5(String backData){
        return md5(BACK_SALT_BEFORE+backData+BACK_SALT_AFTER);
    }

    public static void main(String[] args) {
        System.out.println("前端md5加密："+Md5Util.frontMd5("123456"));

        System.out.println("后端md5加密验证："+backMd5(frontMd5("123456")));
    }


}

```

### 在验证密码时 调用工具类进行加密

![image-20231025182500863](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310251825672.png)

### 前端添加md5加密工具

#### md5.js

```js
var hexcase = 0;
var b64pad  = "";
var chrsz   = 8;

function hex_md5(s){ return binl2hex(core_md5(str2binl(s), s.length * chrsz));}
function b64_md5(s){ return binl2b64(core_md5(str2binl(s), s.length * chrsz));}
function hex_hmac_md5(key, data) { return binl2hex(core_hmac_md5(key, data)); }
function b64_hmac_md5(key, data) { return binl2b64(core_hmac_md5(key, data)); }
function calcMD5(s){ return binl2hex(core_md5(str2binl(s), s.length * chrsz));}

function md5_vm_test()
{
  return hex_md5("abc") == "900150983cd24fb0d6963f7d28e17f72";
}

function core_md5(x, len)
{

  x[len >> 5] |= 0x80 << ((len) % 32);
  x[(((len + 64) >>> 9) << 4) + 14] = len;
  var a =  1732584193;
  var b = -271733879;
  var c = -1732584194;
  var d =  271733878;
  for(var i = 0; i < x.length; i += 16)
  {
    var olda = a;
    var oldb = b;
    var oldc = c;
    var oldd = d;

    a = md5_ff(a, b, c, d, x[i+ 0], 7 , -680876936);
    d = md5_ff(d, a, b, c, x[i+ 1], 12, -389564586);
    c = md5_ff(c, d, a, b, x[i+ 2], 17,  606105819);
    b = md5_ff(b, c, d, a, x[i+ 3], 22, -1044525330);
    a = md5_ff(a, b, c, d, x[i+ 4], 7 , -176418897);
    d = md5_ff(d, a, b, c, x[i+ 5], 12,  1200080426);
    c = md5_ff(c, d, a, b, x[i+ 6], 17, -1473231341);
    b = md5_ff(b, c, d, a, x[i+ 7], 22, -45705983);
    a = md5_ff(a, b, c, d, x[i+ 8], 7 ,  1770035416);
    d = md5_ff(d, a, b, c, x[i+ 9], 12, -1958414417);
    c = md5_ff(c, d, a, b, x[i+10], 17, -42063);
    b = md5_ff(b, c, d, a, x[i+11], 22, -1990404162);
    a = md5_ff(a, b, c, d, x[i+12], 7 ,  1804603682);
    d = md5_ff(d, a, b, c, x[i+13], 12, -40341101);
    c = md5_ff(c, d, a, b, x[i+14], 17, -1502002290);
    b = md5_ff(b, c, d, a, x[i+15], 22,  1236535329);
    a = md5_gg(a, b, c, d, x[i+ 1], 5 , -165796510);
    d = md5_gg(d, a, b, c, x[i+ 6], 9 , -1069501632);
    c = md5_gg(c, d, a, b, x[i+11], 14,  643717713);
    b = md5_gg(b, c, d, a, x[i+ 0], 20, -373897302);
    a = md5_gg(a, b, c, d, x[i+ 5], 5 , -701558691);
    d = md5_gg(d, a, b, c, x[i+10], 9 ,  38016083);
    c = md5_gg(c, d, a, b, x[i+15], 14, -660478335);
    b = md5_gg(b, c, d, a, x[i+ 4], 20, -405537848);
    a = md5_gg(a, b, c, d, x[i+ 9], 5 ,  568446438);
    d = md5_gg(d, a, b, c, x[i+14], 9 , -1019803690);
    c = md5_gg(c, d, a, b, x[i+ 3], 14, -187363961);
    b = md5_gg(b, c, d, a, x[i+ 8], 20,  1163531501);
    a = md5_gg(a, b, c, d, x[i+13], 5 , -1444681467);
    d = md5_gg(d, a, b, c, x[i+ 2], 9 , -51403784);
    c = md5_gg(c, d, a, b, x[i+ 7], 14,  1735328473);
    b = md5_gg(b, c, d, a, x[i+12], 20, -1926607734);
    a = md5_hh(a, b, c, d, x[i+ 5], 4 , -378558);
    d = md5_hh(d, a, b, c, x[i+ 8], 11, -2022574463);
    c = md5_hh(c, d, a, b, x[i+11], 16,  1839030562);
    b = md5_hh(b, c, d, a, x[i+14], 23, -35309556);
    a = md5_hh(a, b, c, d, x[i+ 1], 4 , -1530992060);
    d = md5_hh(d, a, b, c, x[i+ 4], 11,  1272893353);
    c = md5_hh(c, d, a, b, x[i+ 7], 16, -155497632);
    b = md5_hh(b, c, d, a, x[i+10], 23, -1094730640);
    a = md5_hh(a, b, c, d, x[i+13], 4 ,  681279174);
    d = md5_hh(d, a, b, c, x[i+ 0], 11, -358537222);
    c = md5_hh(c, d, a, b, x[i+ 3], 16, -722521979);
    b = md5_hh(b, c, d, a, x[i+ 6], 23,  76029189);
    a = md5_hh(a, b, c, d, x[i+ 9], 4 , -640364487);
    d = md5_hh(d, a, b, c, x[i+12], 11, -421815835);
    c = md5_hh(c, d, a, b, x[i+15], 16,  530742520);
    b = md5_hh(b, c, d, a, x[i+ 2], 23, -995338651);
    a = md5_ii(a, b, c, d, x[i+ 0], 6 , -198630844);
    d = md5_ii(d, a, b, c, x[i+ 7], 10,  1126891415);
    c = md5_ii(c, d, a, b, x[i+14], 15, -1416354905);
    b = md5_ii(b, c, d, a, x[i+ 5], 21, -57434055);
    a = md5_ii(a, b, c, d, x[i+12], 6 ,  1700485571);
    d = md5_ii(d, a, b, c, x[i+ 3], 10, -1894986606);
    c = md5_ii(c, d, a, b, x[i+10], 15, -1051523);
    b = md5_ii(b, c, d, a, x[i+ 1], 21, -2054922799);
    a = md5_ii(a, b, c, d, x[i+ 8], 6 ,  1873313359);
    d = md5_ii(d, a, b, c, x[i+15], 10, -30611744);
    c = md5_ii(c, d, a, b, x[i+ 6], 15, -1560198380);
    b = md5_ii(b, c, d, a, x[i+13], 21,  1309151649);
    a = md5_ii(a, b, c, d, x[i+ 4], 6 , -145523070);
    d = md5_ii(d, a, b, c, x[i+11], 10, -1120210379);
    c = md5_ii(c, d, a, b, x[i+ 2], 15,  718787259);
    b = md5_ii(b, c, d, a, x[i+ 9], 21, -343485551);

    a = safe_add(a, olda);
    b = safe_add(b, oldb);
    c = safe_add(c, oldc);
    d = safe_add(d, oldd);
  }
  return Array(a, b, c, d);

}

function md5_cmn(q, a, b, x, s, t)
{
  return safe_add(bit_rol(safe_add(safe_add(a, q), safe_add(x, t)), s),b);
}
function md5_ff(a, b, c, d, x, s, t)
{
  return md5_cmn((b & c) | ((~b) & d), a, b, x, s, t);
}
function md5_gg(a, b, c, d, x, s, t)
{
  return md5_cmn((b & d) | (c & (~d)), a, b, x, s, t);
}
function md5_hh(a, b, c, d, x, s, t)
{
  return md5_cmn(b ^ c ^ d, a, b, x, s, t);
}
function md5_ii(a, b, c, d, x, s, t)
{
  return md5_cmn(c ^ (b | (~d)), a, b, x, s, t);
}

function core_hmac_md5(key, data)
{
  var bkey = str2binl(key);
  if(bkey.length > 16) bkey = core_md5(bkey, key.length * chrsz);

  var ipad = Array(16), opad = Array(16);
  for(var i = 0; i < 16; i++)
  {
    ipad[i] = bkey[i] ^ 0x36363636;
    opad[i] = bkey[i] ^ 0x5C5C5C5C;
  }

  var hash = core_md5(ipad.concat(str2binl(data)), 512 + data.length * chrsz);
  return core_md5(opad.concat(hash), 512 + 128);
}

function safe_add(x, y)
{
  var lsw = (x & 0xFFFF) + (y & 0xFFFF);
  var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
  return (msw << 16) | (lsw & 0xFFFF);
}

function bit_rol(num, cnt)
{
  return (num << cnt) | (num >>> (32 - cnt));
}

function str2binl(str)
{
  var bin = Array();
  var mask = (1 << chrsz) - 1;
  for(var i = 0; i < str.length * chrsz; i += chrsz)
    bin[i>>5] |= (str.charCodeAt(i / chrsz) & mask) << (i%32);
  return bin;
}

function binl2hex(binarray)
{
  var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
  var str = "";
  for(var i = 0; i < binarray.length * 4; i++)
  {
    str += hex_tab.charAt((binarray[i>>2] >> ((i%4)*8+4)) & 0xF) +
      hex_tab.charAt((binarray[i>>2] >> ((i%4)*8  )) & 0xF);
  }
  return str;
}

function binl2b64(binarray)
{
  var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
  var str = "";
  for(var i = 0; i < binarray.length * 4; i += 3)
  {
    var triplet = (((binarray[i   >> 2] >> 8 * ( i   %4)) & 0xFF) << 16)
      | (((binarray[i+1 >> 2] >> 8 * ((i+1)%4)) & 0xFF) << 8 )
      |  ((binarray[i+2 >> 2] >> 8 * ((i+2)%4)) & 0xFF);
    for(var j = 0; j < 4; j++)
    {
      if(i * 8 + j * 6 > binarray.length * 32) str += b64pad;
      else str += tab.charAt((triplet >> 6*(3-j)) & 0x3F);
    }
  }
  return str ;
}

export {
  hex_md5
}

```

如果添加后出现报Eslint错误 需要关闭Eslint

File -> Settings 

![image-20231025182710612](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310251827610.png)

或是

![image-20231025185457716](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310251854749.png)

#### 登录页面中引用

Login.vue

部分代码

```vue
<script>
import axios from 'axios'
// @表示src目录 在webpack.base.comf.js中定义了'@': resolve('src'),
import { hex_md5 } from '@/util/md5'

export default {
  name: 'Login',
  data () { // 注意 默认都是有空格的
    return {
      username: '',
      password: '',
      errorInfo: ''
    }
  },
  methods: {
    submit () {
      if (this.username.trim() == null || this.username === '') {
        this.errorInfo = '用户名不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      if (this.password.trim() == null || this.password === '') {
        this.errorInfo = '密码不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      let salt = "3dfsty";// 要与服务端设置的前端salt保持一致
      // 发送登录请求
      axios.post(
        'http://localhost/login',
        {
          'username': this.username,
          'password': hex_md5(salt+this.password)
        })
        .then(response => {
          let data = response.data
          if (data.code === 0) {
            console.log(data)
          } else if (data.code === 500) {
            this.errorInfo = data.msg
          }
        }).catch(error => {
          this.errorInfo = error
        })
    }
  }
}
</script>
```

#### 封装请求路径

```
前端页面中经常需要用到请求地址 这里我们统一进行根路径的封装
```

##### src/config目录下新建sys.js文件

```js
export function genServerUrl(path){
  /* path 具体的服务端请求接口路径 */
  return "http://localhost:80/"+path;
}
```

##### Login.vue中调用

```
1. 引入 genServerUrl
2. 发送请求时使用该方法拼装具体的接口请求地址
```

```vue
<template>
  <div class="login-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>秒杀商城-用户登录</span>
      </div>
      <el-form label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="username" placeholder="用户名" style="width: 252px;"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model="password" placeholder="密码" style="width: 252px;"/>
        </el-form-item>
        <el-form-item >
          <el-button type="primary" style="width: 252px;" @click="submit()">登录</el-button>
        </el-form-item>
        <p class="errorInfo">{{errorInfo}}</p>
      </el-form>
      <div style="text-align: center;">
        <a href="#">版权信息</a> @Copyright
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios'
// @表示src目录 在webpack.base.comf.js中定义了'@': resolve('src'),
import { hex_md5 } from '@/util/md5'

import {genServerUrl} from '@/config/sys'

export default {
  name: 'Login',
  data () { // 注意 默认都是有空格的
    return {
      username: '',
      password: '',
      errorInfo: ''
    }
  },
  methods: {
    submit () {
      if (this.username.trim() == null || this.username === '') {
        this.errorInfo = '用户名不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      if (this.password.trim() == null || this.password === '') {
        this.errorInfo = '密码不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      let salt = "3dfsty";// 要与服务端设置的前端salt保持一致
      let 啊 = genServerUrl("login");
      // 发送登录请求
      axios.post(
        啊,
        {
          'username': this.username,
          'password': hex_md5(salt+this.password)
        })
        .then(response => {
          let data = response.data
          if (data.code === 0) {
            console.log(data)
          } else if (data.code === 500) {
            this.errorInfo = data.msg
          }
        }).catch(error => {
          this.errorInfo = error
        })
    }
  }
}
</script>

<style scoped>
.login-container{
  display: flex;/*flex布局*/
  justify-content: center;/*居中*/
  margin-top: 152px;
}
.errorInfo{
  font-weight: bold;
  text-align: center;
  color: red;
}
</style>

```

### 启动并登录测试

![image-20231025185709877](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310251857842.png)



## 自定义Session会话

```
考虑到后期拆分服务 这里不能使用HttpSession管理会话，这里我们自定义Session会话
用户登录成功后将用户信息保存到redis中
```

### Redis安装

```
这里redis安装 我们借助docker进行
1. 拉取redis镜像
2. 启动镜像并指定挂载目录
	redis镜像中默认是没有redis.conf文件的 而该文件中需要配置是否允许远程连接

docker run --name redis-docker -p 6379:6379 -v /home/redis/data:/data -v /home/redis/conf/redis.conf:/etc/redis/config/redis.config --restart=always -it redis --appendonly yes

3. 挂载目录中 redis.conf目录下新建redis.config文件并添加如下内容
protected-mode no
4. 重启或重新运行镜像

```

### UUID工具类

```
用户登录成功后保存到Redis中的token肯定是不能重复的 因此这里需要创建一个UUID工具类来实现不重复的token
```

```java
package com.et.util;

import java.util.Locale;
import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString().toUpperCase(Locale.ROOT).replaceAll("-","");
    }

    public static void main(String[] args) {
        System.out.println(getUUID());
    }
}

```

### 添加redis配置

application.yml

这里只是redis的配置

```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_seckill?serverTimezone=Asia/Shanghai
    username: root
    password: Karen@1234
  redis:
    host: 192.168.199.106
    port: 6379
    password:
    connect-timeout: 10s #连接超时时间
    lettuce: # redis客户端配置
      pool: # 配置连接池
        max-active: 8 # 连接池最大连接数(负数表示没有限制) 默认8
        max-idle: 8 # 连接池中最大连接数 默认8
        max-wait: 200s # 连接池最大阻塞等待时间(负数表示没有限制)默认-1
        min-idle: 0 # 连接池中最小连接数 默认0
```

### Redis配置类

```java
package com.et.controller;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;

/**
 * Redis配置类
 * 
 * @author Administrator
 *
 */
@Configuration
public class RedisConfig {


	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private Integer port;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.lettuce.pool.max-idle}")
	private Integer maxIdle;
	@Value("${spring.redis.lettuce.pool.min-idle}")
	private Integer minIdle;
	@Value("${spring.redis.lettuce.pool.max-active}")
	private Integer maxTotal;
	@Value("${spring.redis.lettuce.pool.max-wait}")
	private Duration maxWaitMillis;


	@Bean
	LettuceConnectionFactory lettuceConnectionFactory() {
		// 连接池配置
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxIdle(maxIdle == null ? 8 : maxIdle);
		poolConfig.setMinIdle(minIdle == null ? 1 : minIdle);
		poolConfig.setMaxTotal(maxTotal == null ? 8 : maxTotal);
		poolConfig.setMaxWaitMillis(maxWaitMillis == null ? 5000L : maxWaitMillis.toMillis());
		LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
				.poolConfig(poolConfig)
				.build();
		// 单机redis
		RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
		redisConfig.setHostName(host);
		redisConfig.setPort(port);
		if (password != null && !"".equals(password)) {
			redisConfig.setPassword(password);
		}

		// redisConfig.setPassword(password);

		return new LettuceConnectionFactory(redisConfig, lettucePoolingClientConfiguration);
	}


	@Bean(name="redisTemplate")
	public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(lettuceConnectionFactory);
		//序列化类
		MyRedisSerializer myRedisSerializer = new MyRedisSerializer();
		//key序列化方式
		template.setKeySerializer(new StringRedisSerializer());
		//value序列化
		template.setValueSerializer(myRedisSerializer);
		//value hashmap序列化
		template.setHashValueSerializer(myRedisSerializer);
		return template;
	}

	@Bean(name="redisTemplate2")
	public RedisTemplate<String, Object> redisTemplate2(LettuceConnectionFactory lettuceConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(lettuceConnectionFactory);
		//序列化类
		MyRedisSerializer myRedisSerializer = new MyRedisSerializer();
		//key序列化方式
		template.setKeySerializer(new StringRedisSerializer());
		//value序列化
		template.setValueSerializer(new StringRedisSerializer());
		return template;
	}

	static class MyRedisSerializer implements RedisSerializer<Object> {

		@Override
		public byte[] serialize(Object o) throws SerializationException {
			return serializeObj(o);
		}

		@Override
		public Object deserialize(byte[] bytes) throws SerializationException {
			return deserializeObj(bytes);
		}

		/**
		 * 序列化
		 * @param object
		 * @return
		 */
		private static byte[] serializeObj(Object object) {
			ObjectOutputStream oos = null;
			ByteArrayOutputStream baos = null;
			try {
				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(object);
				byte[] bytes = baos.toByteArray();
				return bytes;
			} catch (Exception e) {
				throw new RuntimeException("序列化失败!", e);
			}
		}

		/**
		 * 反序列化
		 * @param bytes
		 * @return
		 */
		private static Object deserializeObj(byte[] bytes) {
			if (bytes == null){
				return null;
			}
			ByteArrayInputStream bais = null;
			try {
				bais = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(bais);
				return ois.readObject();
			} catch (Exception e) {
				throw new RuntimeException("反序列化失败!", e);
			}
		}

	}
}
```

### Redis操作的工具类

```java
package com.et.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * @author Administrator
 *
 */
@Component
public class RedisUtil {

	@Resource(name="redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;

	@Resource(name="redisTemplate2")
	private RedisTemplate<String, Object> redisTemplate2;


	//=============================common============================
	/**
	 * 指定缓存失效时间
	 * @param key 键
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean expire(String key,long time){
		try {
			if(time>0){
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 指定缓存失效时间
	 * @param prefix 前缀
	 * @param key 键
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean expire(String prefix,String key,long time){
		try {
			if(time>0){
				redisTemplate.expire(prefix+key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据key 获取过期时间
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public long getExpire(String key){
		return redisTemplate.getExpire(key,TimeUnit.SECONDS);
	}

	/**
	 * 判断key是否存在
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public boolean hasKey(String key){
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除缓存
	 * @param key 可以传一个值 或多个
	 */
	@SuppressWarnings("unchecked")
	public void del(String ... key){
		if(key!=null&&key.length>0){
			if(key.length==1){
				redisTemplate.delete(key[0]);
			}else{
				redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
			}
		}
	}

	//============================String=============================
	/**
	 * 普通缓存获取
	 * @param key 键
	 * @return 值
	 */
	public Object get(String key){
		return key==null?null:redisTemplate.opsForValue().get(key);
	}

	/**
	 * 普通缓存获取
	 * @param prefix 前缀
	 * @param key 键
	 * @return 值
	 */
	public Object get(String prefix,String key){
		return key==null?null:redisTemplate.opsForValue().get(prefix+key);
	}

	/**
	 * 普通缓存放入
	 * @param key 键
	 * @param value 值
	 * @return true成功 false失败
	 */
	public boolean set(String key,Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 普通缓存放入(带前缀)
	 * @param prefix 前缀
	 * @param key 键
	 * @param value 值
	 * @return true成功 false失败
	 */
	public boolean set(String prefix,String key,Object value) {
		try {
			redisTemplate.opsForValue().set(prefix+key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}


	/**
	 * 普通缓存放入(带前缀)
	 * @param prefix 前缀
	 * @param key 键
	 * @param value 值
	 * @return true成功 false失败
	 */
	public boolean set2(String prefix,String key,Object value) {
		try {
			redisTemplate2.opsForValue().set(prefix+key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 普通缓存放入并设置时间
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return true成功 false 失败
	 */
	public boolean set(String key,Object value,long time){
		try {
			if(time>0){
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			}else{
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 普通缓存放入并设置时间
	 * @param prefix 前缀
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return true成功 false 失败
	 */
	public boolean set(String prefix,String key,Object value,long time){
		try {
			if(time>0){
				redisTemplate.opsForValue().set(prefix+key, value, time, TimeUnit.SECONDS);
			}else{
				set(prefix+key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 递增
	 * @param key 键
	 * @param by 要增加几(大于0)
	 * @return
	 */
	public long incr(String key, long delta){
		if(delta<0){
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 递减
	 * @param key 键
	 * @param by 要减少几(小于0)
	 * @return
	 */
	public long decr(String key, long delta){
		if(delta<0){
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate2.opsForValue().increment(key, -delta);
	}

	/**
	 * 递减
	 * @param key 键
	 * @param by 要减少几(小于0)
	 * @return
	 */
	public long decr(String prefix,String key, long delta){
		if(delta<0){
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate2.opsForValue().decrement(prefix+key, delta);
	}

	//================================Map=================================
	/**
	 * HashGet
	 * @param key 键 不能为null
	 * @param item 项 不能为null
	 * @return 值
	 */
	public Object hget(String key,String item){
		return redisTemplate.opsForHash().get(key, item);
	}

	/**
	 * 获取hashKey对应的所有键值
	 * @param key 键
	 * @return 对应的多个键值
	 */
	public Map<Object,Object> hmget(String key){
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * HashSet
	 * @param key 键
	 * @param map 对应多个键值
	 * @return true 成功 false 失败
	 */
	public boolean hmset(String key, Map<String,Object> map){
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * HashSet 并设置时间
	 * @param key 键
	 * @param map 对应多个键值
	 * @param time 时间(秒)
	 * @return true成功 false失败
	 */
	public boolean hmset(String key, Map<String,Object> map, long time){
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if(time>0){
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * @param key 键
	 * @param item 项
	 * @param value 值
	 * @return true 成功 false失败
	 */
	public boolean hset(String key,String item,Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * @param key 键
	 * @param item 项
	 * @param value 值
	 * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * @return true 成功 false失败
	 */
	public boolean hset(String key,String item,Object value,long time) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if(time>0){
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除hash表中的值
	 * @param key 键 不能为null
	 * @param item 项 可以使多个 不能为null
	 */
	public void hdel(String key, Object... item){
		redisTemplate.opsForHash().delete(key,item);
	}

	/**
	 * 判断hash表中是否有该项的值
	 * @param key 键 不能为null
	 * @param item 项 不能为null
	 * @return true 存在 false不存在
	 */
	public boolean hHasKey(String key, String item){
		return redisTemplate.opsForHash().hasKey(key, item);
	}

	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 * @param key 键
	 * @param item 项
	 * @param by 要增加几(大于0)
	 * @return
	 */
	public double hincr(String key, String item,double by){
		return redisTemplate.opsForHash().increment(key, item, by);
	}

	/**
	 * hash递减
	 * @param key 键
	 * @param item 项
	 * @param by 要减少记(小于0)
	 * @return
	 */
	public double hdecr(String key, String item,double by){
		return redisTemplate.opsForHash().increment(key, item,-by);
	}

	//============================set=============================
	/**
	 * 根据key获取Set中的所有值
	 * @param key 键
	 * @return
	 */
	public Set<Object> sGet(String key){
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据value从一个set中查询,是否存在
	 * @param key 键
	 * @param value 值
	 * @return true 存在 false不存在
	 */
	public boolean sHasKey(String key,Object value){
		try {
			return redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将数据放入set缓存
	 * @param key 键
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public long sSet(String key, Object...values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 将set数据放入缓存
	 * @param key 键
	 * @param time 时间(秒)
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public long sSetAndTime(String key,long time,Object...values) {
		try {
			Long count = redisTemplate.opsForSet().add(key, values);
			if(time>0) expire(key, time);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取set缓存的长度
	 * @param key 键
	 * @return
	 */
	public long sGetSetSize(String key){
		try {
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 移除值为value的
	 * @param key 键
	 * @param values 值 可以是多个
	 * @return 移除的个数
	 */
	public long setRemove(String key, Object ...values) {
		try {
			Long count = redisTemplate.opsForSet().remove(key, values);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	//===============================list=================================

	/**
	 * 获取list缓存的内容
	 * @param key 键
	 * @param start 开始
	 * @param end 结束  0 到 -1代表所有值
	 * @return
	 */
	public List<Object> lGet(String key,long start, long end){
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取list缓存的长度
	 * @param key 键
	 * @return
	 */
	public long lGetListSize(String key){
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 通过索引 获取list中的值
	 * @param key 键
	 * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @return
	 */
	public Object lGetIndex(String key,long index){
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean lSet(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean lSet(String key, Object value, long time) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			if (time > 0) expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean lSet(String key, List<Object> value) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean lSet(String key, List<Object> value, long time) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			if (time > 0) expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据索引修改list中的某条数据
	 * @param key 键
	 * @param index 索引
	 * @param value 值
	 * @return
	 */
	public boolean lUpdateIndex(String key, long index,Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 移除N个值为value
	 * @param key 键
	 * @param count 移除多少个
	 * @param value 值
	 * @return 移除的个数
	 */
	public long lRemove(String key,long count,Object value) {
		try {
			Long remove = redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
```

### 封装常用的常量值

```java
public class RedisConstant {

    /**
     * redis token前缀
     */
    public static final String REDIS_TOKEN_PREFIX="tk";

    /**
     * redis 秒杀库存前缀
     */
    public static final String REDIS_STOCK_PREFIX="st";

    /**
     * redis 秒杀商品是否秒杀完标识前缀
     */
    public static final String REDIS_GOODS_MIAOSHA_OVER_PREFIX="over";

    /**
     * redis token有效期30分钟
     */
    public static final Integer REDIS_TOKEN_EXPIRE=30*60;

    /**
     * redis 秒杀商品集合
     */
    public static final String REDIS_MIAOSHA_GOODS="miaosha_goods_list";

    /**
     * redis 秒杀商品集合有效期60分钟
     */
    public static final Integer REDIS_MIAOSHA_GOODS_EXPIRE=60*60;


    /**
     * redis 验证码前缀
     */
    public static final String REDIS_VERIFYCODE_PREFIX="vc";

    /**
     * redis 验证码有效期5分钟
     */
    public static final Integer REDIS_VERIFYCODE_EXPIRE=5*60;
}
```

### Controller中用户登录成功后添加到Redis中

```java
package com.et.controller;

import com.et.constant.RedisConstant;
import com.et.entity.User;
import com.et.service.UserService;
import com.et.util.Md5Util;
import com.et.util.R;
import com.et.util.RedisUtil;
import com.et.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制层
 */
@RestController
@RequestMapping("/")
public class LoginContrller {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户登录
     * @return
     */
    @RequestMapping("/login")
    public R login(@RequestBody User user){
        if(user==null){
            return R.error();
        }
        if(StringUtils.isEmpty(user.getUsername())){
            return R.error("用户名不能为空");
        }
        if(StringUtils.isEmpty(user.getPassword())){
            return R.error("密码不能为空");
        }
        User result = userService.findByUserName(user.getUsername());
        if(result==null){
            return R.error("用户不存在");
        }
        if(!result.getPassword().trim().equals(Md5Util.backMd5(user.getPassword().trim()))){
            return R.error("用户名或者密码错误！");
        }

        String token = UUIDUtil.getUUID();
        // 添加到redis缓存中
        // RedisConstant.REDIS_TOKEN_PREFIX 前缀 与redis的key组合使用 标注redis中这条数据是做什么的
        // 例如 tk3B3A77CEFF504F2CA64FF90BB79B9DB1
        // token redis中key
        // result redis中value
        // RedisConstant.REDIS_TOKEN_EXPIRE 当前token的有效期 30分钟
        redisUtil.set(RedisConstant.REDIS_TOKEN_PREFIX,token,result,RedisConstant.REDIS_TOKEN_EXPIRE);
        return R.ok("登录成功！");
    }
}
```

### 启动测试

前端登录成功 查看redis中缓存是否存在

![image-20231025214805069](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310252148958.png)

## 前端保存用户token

### 后端需要将token传递给前端

Controller中需要将token传递给前端

```java
package com.et.controller;

import com.et.constant.RedisConstant;
import com.et.entity.User;
import com.et.service.UserService;
import com.et.util.Md5Util;
import com.et.util.R;
import com.et.util.RedisUtil;
import com.et.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制层
 */
@RestController
@RequestMapping("/")
public class LoginContrller {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户登录
     * @return
     */
    @RequestMapping("/login")
    public R login(@RequestBody User user){
        if(user==null){
            return R.error();
        }
        if(StringUtils.isEmpty(user.getUsername())){
            return R.error("用户名不能为空");
        }
        if(StringUtils.isEmpty(user.getPassword())){
            return R.error("密码不能为空");
        }
        User result = userService.findByUserName(user.getUsername());
        if(result==null){
            return R.error("用户不存在");
        }
        if(!result.getPassword().trim().equals(Md5Util.backMd5(user.getPassword().trim()))){
            return R.error("用户名或者密码错误！");
        }

        String token = UUIDUtil.getUUID();
        // 添加到redis缓存中
        // RedisConstant.REDIS_TOKEN_PREFIX 前缀 与redis的key组合使用 标注redis中这条数据是做什么的
        // 例如 tk3B3A77CEFF504F2CA64FF90BB79B9DB1
        // token redis中key
        // result redis中value
        // RedisConstant.REDIS_TOKEN_EXPIRE 当前token的有效期 30分钟
        redisUtil.set(RedisConstant.REDIS_TOKEN_PREFIX,token,result,RedisConstant.REDIS_TOKEN_EXPIRE);
        return R.ok(token);
    }
}
```

### 前端保存token

```
Login.vue中 登录成功后 将token保存到sessionStorage中
H5规范保存token有两种方式
	// 保存在本地 永久有效
    //window.localStorage.setItem("token",data.msg);
    // 关闭浏览器失效
    window.sessionStorage.setItem("token",data.msg);
```

Login.vue

```vue
<template>
  <div class="login-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>秒杀商城-用户登录</span>
      </div>
      <el-form label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="username" placeholder="用户名" style="width: 252px;"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model="password" placeholder="密码" style="width: 252px;"/>
        </el-form-item>
        <el-form-item >
          <el-button type="primary" style="width: 252px;" @click="submit()">登录</el-button>
        </el-form-item>
        <p class="errorInfo">{{errorInfo}}</p>
      </el-form>
      <div style="text-align: center;">
        <a href="#">版权信息</a> @Copyright
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios'
// @表示src目录 在webpack.base.comf.js中定义了'@': resolve('src'),
import { hex_md5 } from '@/util/md5'

import {genServerUrl} from '@/config/sys'

export default {
  name: 'Login',
  data () { // 注意 默认都是有空格的
    return {
      username: '',
      password: '',
      errorInfo: ''
    }
  },
  methods: {
    submit () {
      if (this.username.trim() == null || this.username === '') {
        this.errorInfo = '用户名不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      if (this.password.trim() == null || this.password === '') {
        this.errorInfo = '密码不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      let salt = "3dfsty";// 要与服务端设置的前端salt保持一致
      let 啊 = genServerUrl("login");
      // 发送登录请求
      axios.post(
        啊,
        {
          'username': this.username,
          'password': hex_md5(salt+this.password)
        })
        .then(response => {
          let data = response.data
          if (data.code === 0) {
            console.log(data)
            // 保存token信息
            // 保存在本地 永久有效
            //window.localStorage.setItem("token",data.msg);
            // 关闭浏览器失效
            window.sessionStorage.setItem("token",data.msg);
          } else if (data.code === 500) {
            this.errorInfo = data.msg
          }
        }).catch(error => {
          this.errorInfo = error
        })
    }
  }
}
</script>

<style scoped>
.login-container{
  display: flex;/*flex布局*/
  justify-content: center;/*居中*/
  margin-top: 152px;
}
.errorInfo{
  font-weight: bold;
  text-align: center;
  color: red;
}
</style>

```

### 测试

登录后查看浏览器控制台中application中storage

![image-20231025215944929](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310252159887.png)

## token续期

```
前端需要做token的续期 因为我们设置的token是有有效期的，当前端在操作时 有可能会超出token的有效期 此时在请求服务端时会因为token过期导致无法完成请求 因此这里需要对token进行续期操作
续期操作有两种
	1. 前端定时调用后台刷新token 后台接收到请求后会重新将原有的token的有效期进行重置并放入到redis中
	2. 前端每次操作时 都重新获取一次新的token
		这样做会导致redis中存在无效token较多
这里我们采用第一种方式
```

### 后台创建Token操作相关的controller

```java
package com.et.controller;

import com.et.constant.RedisConstant;
import com.et.util.R;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Token操作相关的Controller
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/refreshToken")
    public R refreshToken(HttpServletRequest request){
        String token = request.getHeader("token");
        System.out.println("token设置之前的有效期："+redisUtil.getExpire(RedisConstant.REDIS_TOKEN_PREFIX+token));
        // 续期30分钟
        redisUtil.expire(RedisConstant.REDIS_TOKEN_PREFIX,token,RedisConstant.REDIS_TOKEN_EXPIRE);
        System.out.println("token设置之后的有效期："+redisUtil.getExpire(RedisConstant.REDIS_TOKEN_PREFIX+token));
        // 如果是新生成的token则需要将新生成的token传递给前端
        return R.ok();
    }
}

```

### 前端主页面

```
登录成功后会跳转到前端主页面 然后在主页面上请求token的续期
```

### Main.vue

在components目录下创建Main.vue组件

```vue
<template>
  <el-container>
    <el-header>Header</el-header>
    <el-main>Main</el-main>
    <el-footer>Footer</el-footer>
  </el-container>
</template>

<script>
export default {
  name: 'Main'
}
</script>

<style scoped>

</style>

```

### 配置页面的路由

```
在router下的index.js中配置组件(页面)的路由
```

```js
import Vue from 'vue'
import Router from 'vue-router'
// 导入Login组件
import Login from '@/components/Login'

import Main from '@/components/Main'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/main',
      name: 'Main',
      component: Main
    }
  ]
})

```

### 登录测试

![image-20231025224403058](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310252244437.png)

### 创建Head和Foot组件

```
主页面布局 当然token续期也会放到Head组件中进行操作
类似jsp中的include包含其他页面一样 这里vue也可以使用类似的方式操作
	即 在一个组件中引入其他组件 完成布局操作等
在components/commons下新建两个组件
```

#### Head组件

```vue
<template>
  <div>
  	<img src="static/images/logo.png" width="300px" height="120px">
  </div>
</template>

<script>
export default {
  name: 'Head'
}
</script>

<style scoped>

</style>

```

#### Footer组件

```vue
<template>
  <div>
    <div style="text-align: center;">
      <a href="#">版权信息</a> @Copyright
    </div>
  </div>
</template>

<script>
export default {
  name: 'Footer'
}
</script>

<style scoped>

</style>

```

#### Main.vue中引入

```vue
<template>
  <el-container>
    <el-header height="120px">
      <seckill-head></seckill-head>
    </el-header>
    <el-main>Main</el-main>
    <el-footer class="elfooter">
      <seckill-footer></seckill-footer>
    </el-footer>
  </el-container>
</template>

<script>
import SeckillHead from './commons/Head'
import SeckillFooter from './commons/Footer'
export default {
  name: 'Main',
  components:{
    SeckillHead, // 添加引入的组件 否则上边无法使用
    SeckillFooter
  }
}
</script>

<style scoped>
  .elfooter{
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
  }
</style>

```

#### 测试

![image-20231025232322557](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310252323164.png)

### token续期操作

```
在Head组件中发送token续期操作请求
```

Head.vue

```vue
<template>
  <div>
    <img src="static/images/logo.png" width="300px" height="120px">
  </div>
</template>

<script>
import axios from 'axios'
import {genServerUrl} from '@/config/sys'

export default {
  name: 'Head',
  methods:{
    // 刷新token的接口
    refreshToken: function () {
      let url = genServerUrl('token/refreshToken')
      // 获取前端保存的token
      let token = window.sessionStorage.getItem('token');
      // 设置到axios中
      axios.defaults.headers.common['token'] = token;
      //
      axios.get(url,{})
        .then(response=>{
          if(response.data.code == 0){
            console.log("token刷新成功");
          }
        }).catch(error=>{
          alert(error+" - 刷新token发生异常");
      })
    }
  },
  mounted () {// 页面加载完成执行
    setInterval(this.refreshToken,1*60*1000*10);// 每隔10分钟调用一次刷新token接口
    // setInterval(this.refreshToken,5000);
  }
}
</script>

<style scoped>

</style>

```

### 测试

登录成功后查看浏览器控制台和服务端控制台输出

![image-20231025232856086](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310252329133.png)

![image-20231025232907310](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310252329305.png)

## 拦截器实现鉴权

```
在未登录情况下 直接请求主页面不会存在token 那么当我们请求其他操作时就会出现问题
这里做一个拦截器 然后在拦截器中对token进行验证
```

### 拦截器配置类

```java
package com.et.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.et.constant.RedisConstant;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统拦截器配置类 - 鉴权
 */
public class SysInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    // 在请求处理之前进行拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        System.out.println("请求路径："+path);
        // 只处理请求方法的时候
        if(handler instanceof HandlerMethod){
            // 获取前端传递的token
            String token = request.getHeader("token");
            System.out.println("token = "+token);
            if(StringUtils.isEmpty(token)){
                System.out.println("token不存在");
                throw new RuntimeException("验证签名失败 [签名不存在]");
            }else{
                // 在redis中获取
                Object o = redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX, token);
                if(o!=null){
                    // 验证成功
                    System.out.println("签名验证成功");
                    return true;
                }else{
                    System.out.println("签名验证失败 [签名失效]");
                    throw new RuntimeException("签名验证失败 [签名失效]");
                }
            }
        }else {
            return true;
        }
    }

}

```

### 添加到配置中

```
需要搞成Bean然后让Spring管理 在WebAppConfigurer中添加即可
```

```java
package com.et.config;

import com.et.interceptor.SysInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 处理跨域问题
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                .allowCredentials(true)
                .allowedOrigins("*")
                .maxAge(3600);
    }

    // 让Spring管理拦截器配置类
    @Bean
    public SysInterceptor sysInterceptor(){
        return new SysInterceptor();
    }

    // 注册拦截器

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sysInterceptor())
                .addPathPatterns("/**") // 对所有请求拦截
                .excludePathPatterns("/login","/logout"); // 不进行拦截的请求 多个之间使用逗号隔开 也可以直接传入一个String[]
    }
}

```

### 测试

未登录情况下 直接访问除login和logout外的所有请求都会被拦截

![image-20231025235017391](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310252358503.png)

## 全局异常捕获

```
上述出现异常后 页面直接500 这样是不友好的，因此这里做一个全局的异常捕获 友好的提示用户
异常捕获在服务端进行
```

### 创建异常捕获处理类

```java
package com.et.exception;

import com.et.util.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 */
@ControllerAdvice // 织入
@ResponseBody // 这里需要返回给前端
public class GlobalExceptionHandler {

    /*
    处理所有的异常
     */
    @ExceptionHandler(value = Exception.class)// 要捕获的异常
    public R exceptionHandler(HttpServletRequest request,Exception e){
        System.out.println("捕获到异常 "+e.getMessage());
        return R.error("服务器异常 ["+e.getMessage()+"]<br/>"+
                "异常栈信息 "+e.getStackTrace());
    }
}

```

### 启动并测试

![image-20231026000108895](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310260001787.png)

## 前端登录页面背景图调整

```
上述案例 Login.vue中的背景图是在App.vue中设置的 这样做会导致 登录到主页面时 背景图不会发生任何改变 ，因此我们这里做下调整 改为在Login.vue直接显示
```

Login.vue

```vue
<template>
  <div id="building">
    <div class="login-container">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>秒杀商城-用户登录</span>
        </div>
        <el-form label-width="80px">
          <el-form-item label="用户名">
            <el-input v-model="username" placeholder="用户名" style="width: 252px;"/>
          </el-form-item>
          <el-form-item label="密码">
            <el-input type="password" v-model="password" placeholder="密码" style="width: 252px;"/>
          </el-form-item>
          <el-form-item >
            <el-button type="primary" style="width: 252px;" @click="submit()">登录</el-button>
          </el-form-item>
          <p class="errorInfo">{{errorInfo}}</p>
        </el-form>
        <div style="text-align: center;">
          <a href="#">版权信息</a> @Copyright
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
// @表示src目录 在webpack.base.comf.js中定义了'@': resolve('src'),
import { hex_md5 } from '@/util/md5'

import {genServerUrl} from '@/config/sys'

export default {
  name: 'Login',
  data () { // 注意 默认都是有空格的
    return {
      username: '',
      password: '',
      errorInfo: ''
    }
  },
  methods: {
    submit () {
      if (this.username.trim() == null || this.username === '') {
        this.errorInfo = '用户名不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      if (this.password.trim() == null || this.password === '') {
        this.errorInfo = '密码不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      let salt = "3dfsty";// 要与服务端设置的前端salt保持一致
      let 啊 = genServerUrl("login");
      // 发送登录请求
      axios.post(
        啊,
        {
          'username': this.username,
          'password': hex_md5(salt+this.password)
        })
        .then(response => {
          let data = response.data
          if (data.code === 0) {
            console.log(data)
            // 保存token信息
            // 保存在本地 永久有效
            //window.localStorage.setItem("token",data.msg);
            // 关闭浏览器失效
            window.sessionStorage.setItem("token",data.msg);
            // 跳转到main主页面
            this.$router.replace("/main");
          } else if (data.code === 500) {
            this.errorInfo = data.msg
          }
        }).catch(error => {
          this.errorInfo = error
        })
    }
  }
}
</script>

<style scoped>
.login-container{
  display: flex;/*flex布局*/
  justify-content: center;/*居中*/
  margin-top: 152px;
}
.errorInfo{
  font-weight: bold;
  text-align: center;
  color: red;
}

#building{
  background:url("~@/assets/2.jpg");
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
}
</style>

```

## 秒杀商品接口实现

```
秒杀商品功能设计
	1. 商品
	2. 参与秒杀的商品
秒杀商品分两个表来实现 正常的商品表 goods，秒杀的商品表 goods_miaosha
这是由于秒杀商品页面需要展示原价等商品基础信息 因此这里拆分操作

```

### 表

```sql
USE `db_seckill`;

/*Table structure for table `t_goods` */

DROP TABLE IF EXISTS `t_goods`;

CREATE TABLE `t_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `price` double DEFAULT NULL COMMENT '商品名称',
  `image` varchar(128) DEFAULT NULL COMMENT '商品主图',
  `stock` int(11) DEFAULT NULL COMMENT '商品库存',
  `detail` text COMMENT '商品明细',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `t_goods` */

LOCK TABLES `t_goods` WRITE;

insert  into `t_goods`(`id`,`name`,`price`,`image`,`stock`,`detail`) values (1,'iphone13',4966,'01.jpg',1000,'<div><strong>我们的环保目标。</strong><br><br>\r\n为了达成我们 <a href=\"https://www.apple.com.cn/environment\" data-slot-name=\"main-11\" data-feature-name=\"Astro Link\" data-display-name=\"AOS: environment\">2030 年实现碳中和</a>的目标，iPhone&nbsp;13 不再随附电源适配器和 EarPods。包装盒内随附一根 USB-C 转闪电连接线，可支持快速充电，并兼容 USB-C 电源适配器和电脑端口。<br><br>\r\n我们建议你重复使用现有的 USB-A 转闪电连接线、电源适配器和耳机，它们均兼容这些 iPhone 机型。但如果你需要任何新的 Apple 电源适配器或耳机，也可随时购买。</div>'),(2,'iPhone 15 Pro',9999,'02.jpg',1000,'<div><strong>我们的环保目标。</strong><br><br>\r\n为了达成我们 <a href=\"https://www.apple.com.cn/environment\" data-slot-name=\"main-11\" data-feature-name=\"Astro Link\" data-display-name=\"AOS: environment\">2030 年实现碳中和</a>的目标，iPhone&nbsp;13 不再随附电源适配器和 EarPods。包装盒内随附一根 USB-C 转闪电连接线，可支持快速充电，并兼容 USB-C 电源适配器和电脑端口。<br><br>\r\n我们建议你重复使用现有的 USB-A 转闪电连接线、电源适配器和耳机，它们均兼容这些 iPhone 机型。但如果你需要任何新的 Apple 电源适配器或耳机，也可随时购买。</div>'),(3,'iPad Pro',6799,'03.jpg',1000,'<p class=\"copy-block-paragraph typography-section-copy\">iPadOS 种种新技能加身，让你放开了大展拳脚。多款桌面级 app 现身 iPad，全力工作更高效；台前调度功能堪称多任务处理高手，能将各款 app 前后分层放、调窗口大小<sup class=\"footnote footnote-number\"><a href=\"#footnote-6\" aria-label=\"脚注 5\" data-modal-close=\"\">5</a></sup>；再外接一台分辨率最高达 6K 的显示器，还能拓展出更多空间把要用的 app 都摆<span class=\"nowrap\">出来<sup class=\"footnote footnote-number\"><a href=\"#footnote-7\" aria-label=\"脚注 6\" data-modal-close=\"\">6</a></sup>。</span></p>'),(4,'iPad Air',4799,'04.jpg',1000,'<p class=\"typography-overview-section-copy\">无论是阅读、看片，还是创作，iPad&nbsp;Air\r\n								都能让你沉浸其中。<strong style=\"background-image: linear-gradient(90deg, #f56772 0.00%, #ba6bff 100.00%, var(--copy-color) 200.00%);\">10.9&nbsp;英寸 Liquid&nbsp;视网膜显示屏</strong>具备原彩显示和\r\n								P3&nbsp;广色域等先进技术，还覆有抗反射<span class=\"nowrap\">涂层<sup class=\"footnote footnote-number\"><a href=\"#footnote-2\" aria-label=\"脚注 1\" data-modal-close=\"\">1</a></sup>。</span></p>');

UNLOCK TABLES;

/*Table structure for table `t_goods_miaosha` */

DROP TABLE IF EXISTS `t_goods_miaosha`;

CREATE TABLE `t_goods_miaosha` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `price` double DEFAULT NULL COMMENT '秒杀价格',
  `stock` int(11) DEFAULT NULL COMMENT '秒杀的商品库存',
  `startTime` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `endTime` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `t_goods_miaosha` */

LOCK TABLES `t_goods_miaosha` WRITE;

insert  into `t_goods_miaosha`(`id`,`goods_id`,`price`,`stock`,`startTime`,`endTime`) values (1,1,1999,10,'2023-10-26 05:57:29','2023-10-27 05:57:42'),(2,2,2999,10,'2023-10-26 05:57:29','2023-10-27 05:57:42'),(3,3,3999,10,'2023-10-26 05:57:29','2023-10-27 05:57:42'),(4,4,999,10,'2023-10-26 05:57:29','2023-10-27 05:57:42');

UNLOCK TABLES;
```

### 实体类

#### 商品基础信息实体类

```java
package com.et.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("t_goods")
@Data
public class Goods implements Serializable {

    private Integer id; // 编号
    private String name; // 名称
    private double price; // 价格
    private String image; // 商品图片
    private int stock; // 商品库存
    private String detail; // 商品详情
}

```

#### 商品秒杀实体类

```java
package com.et.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("t_goods_miaosha")
@Data
public class MiaoShaGoods implements Serializable {

    private Integer id; // 编号
    @TableField(select = false)
    private Goods goods; // 关联商品
    private double price; // 秒杀价格
    private Integer stock; // 库存数量
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date startTime; // 秒杀开始时间
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date endTime; // 秒杀结束时间

    // 扩展属性
    private Integer miaoShaStatus=0; // 秒杀状态
    private Integer remainSecond=0; // 剩余多少秒
    private Integer remainEndSecond=0; // 秒杀结束 剩余多少秒

}
```

### Mapper接口

#### 商品基础信息Mapper接口

```java
package com.et.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.et.entity.Goods;

/**
 * 商品Mapper接口
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    public Goods findById(Integer id);
}
```

#### 商品秒杀Mapper接口

```java
package com.et.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.et.entity.MiaoShaGoods;

import java.util.List;

public interface MiaoShaGoodsMapper extends BaseMapper<MiaoShaGoods> {

    /**
     * 查询所有秒杀商品
     * @return
     */
    public List<MiaoShaGoods> listAll();
}
```

#### 商品基础信息Mapper接口映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.et.mapper.GoodsMapper">

    <select id="findById" parameterType="Integer" resultType="Goods">
        select * from t_goods where id=#{id}
    </select>
</mapper>
```

#### 商品秒杀Mapper接口映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.et.mapper.MiaoShaGoodsMapper">

    <resultMap id="SeckillGoodsMap" type="MiaoShaGoods">

        <association property="goods" column="goods_id" select="com.et.mapper.GoodsMapper.findById"></association>
    </resultMap>
    <select id="listAll" parameterType="Integer" resultMap="SeckillGoodsMap">
        select * from t_goods_miaosha
    </select>
</mapper>
```

### Service层

#### 商品秒杀业务层接口

```java
package com.et.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.et.entity.MiaoShaGoods;

import java.util.List;

public interface MiaoShaGoodsService extends IService<MiaoShaGoods> {

    /**
     * 查询所有秒杀商品
     * @return
     */
    public List<MiaoShaGoods> listAll();
}

```

#### 商品秒杀业务层接口实现类

```java
package com.et.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.et.entity.MiaoShaGoods;
import com.et.mapper.MiaoShaGoodsMapper;
import com.et.service.MiaoShaGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiaoShaGoodsServiceImpl extends ServiceImpl<MiaoShaGoodsMapper, MiaoShaGoods> implements MiaoShaGoodsService {

    @Autowired
    private MiaoShaGoodsMapper miaoShaGoodsMapper;

    @Override
    public List<MiaoShaGoods> listAll() {
        return miaoShaGoodsMapper.listAll();
    }
}
```

### 秒杀商品Controller

为了提高查询效率 将参与秒杀的商品信息放到Redis缓存中

```java
package com.et.controller;

import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.service.MiaoShaGoodsService;
import com.et.util.R;
import com.et.util.RedisUtil;
import io.netty.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/miaoShaGoods")
public class MiaoShaGoodsController {

    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 查询所有秒杀商品
     * @return
     */
    @RequestMapping("/findAll")
    public R findAll(){
        List<MiaoShaGoods> miaoShaGoodsList=null;
        Object o=redisUtil.get(RedisConstant.REDIS_MIAOSHA_GOODS);
        if(o==null){
            System.out.println("从数据库里面查询");
            miaoShaGoodsList = miaoShaGoodsService.listAll();
            redisUtil.set(RedisConstant.REDIS_MIAOSHA_GOODS,miaoShaGoodsList,RedisConstant.REDIS_MIAOSHA_GOODS_EXPIRE);
        }else{
            System.out.println("从redis中取值");
            miaoShaGoodsList= (List<MiaoShaGoods>) o;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("data",miaoShaGoodsList);
        return R.ok(map);
    }
}

```

### 服务端测试

为了方便测试 临时注释掉请求拦截

![image-20231026062425668](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310260624891.png)

启动测试

![image-20231026061542308](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310260616850.png)

![image-20231026062837599](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310260628987.png)

## 前端秒杀商品展示 - 列表

```
关于商品图片的展示
服务端做虚拟路径映射 前端展示的时候动态拼接一个具体的图片地址 
```

### 服务端添加虚拟路径映射

WebAppConfigurer配置类中实现addResourceHandlers方法

```java
package com.et.config;

import com.et.interceptor.SysInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 处理跨域问题
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                .allowCredentials(true)
                .allowedOrigins("*")
                .maxAge(3600);
    }

    // 让Spring管理拦截器配置类
    @Bean
    public SysInterceptor sysInterceptor(){
        return new SysInterceptor();
    }

    // 注册拦截器

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sysInterceptor())
                .addPathPatterns("/**") // 对所有请求拦截
                .excludePathPatterns("/login","/logout","/image"); // 不进行拦截的请求 多个之间使用逗号隔开 也可以直接传入一个String[]
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/image/**").
                addResourceLocations("file:D:/miaoshaimges/");// 注意 末尾必须加/否则不生效
    }
}

```

### 前端页面展示

Main.vue中添加商品列表展示

```vue
<template>
  <el-container>
    <el-header height="120px">
      <seckill-head></seckill-head>
    </el-header>
    <el-main>
      <el-table
        :data="tableData"
        style="width: 100%">
        <el-table-column
          prop="goods.name"
          label="商品名称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="goods.image"
          label="商品图片"
          width="180">
          <template slot-scope="scope">
            <img :src="getSrcUrl(scope.row.goods.image)" width="60" height="80"/>
          </template>
        </el-table-column>
        <el-table-column
          width="180"
          prop="goods.price"
          label="商品原价(元)">
        </el-table-column>
        <el-table-column
          width="180"
          prop="price"
          label="秒杀价(元)">
        </el-table-column>
        <el-table-column
          width="180"
          prop="stock"
          label="库存数量">
        </el-table-column>
        <el-table-column
          label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer class="elfooter">
      <seckill-footer></seckill-footer>
    </el-footer>
  </el-container>
</template>

<script>
import SeckillHead from './commons/Head'
import SeckillFooter from './commons/Footer'
import axios from "axios";
import {genServerUrl} from '../config/sys'

export default {
  name: 'Main',
  components:{
    SeckillHead, // 添加引入的组件 否则上边无法使用
    SeckillFooter
  },
  data(){
    return{
      tableData:[]
    }
  },
  methods:{
    getSrcUrl(t){
      return genServerUrl('image/'+t);
    },
    getMiaoShaGoods(){
      let url=genServerUrl("miaoShaGoods/findAll");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;
      axios.get(url,{})
        .then(response=>{
          console.log(response.data.data);
          this.tableData=response.data.data;
        }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    }
  },
  mounted() {
    this.getMiaoShaGoods();
  }
}
</script>

<style scoped>
  .elfooter{
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
  }
</style>

```

![image-20231026072111266](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310260722742.png)

## 秒杀商品详情后台接口开发

```
在秒杀商品接口中添加根据ID查询的方法
```

### MiaoShaGoodsMapper接口

```java
/**
     * 根据ID查询秒杀商品详情
     * @param id
     * @return
     */
public MiaoShaGoods findById(Integer id);
```

### MiaoShaGoodsMapper映射文件

```xml
 <select id="findById" parameterType="Integer" resultMap="SeckillGoodsMap">
     select * from t_goods_miaosha where id=#{id}
</select>
```

### MiaoShaGoodsService接口

```java
/**
     * 根据ID查询秒杀商品详情
     * @param id
     * @return
     */
public MiaoShaGoods findById(Integer id);
```

### MiaoShaGoodsService接口实现类

```java
@Override
public MiaoShaGoods findById(Integer id) {
    return miaoShaGoodsMapper.findById(id);
}
```

### MiaoShaGoodsController

```java
 @RequestMapping("/findById")
public R findById(Integer id){
    MiaoShaGoods miaoShaGoods = miaoShaGoodsService.findById(id);
    Map<String,Object> map=new HashMap<>();
    map.put("data",miaoShaGoods);
    return R.ok(map);
}
```

### 启动测试

测试时 使用浏览器直接进行测试 需要先注释掉拦截器

![image-20231026193436212](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310261934104.png)

![image-20231026193529405](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310261935481.png)

## 前端秒杀商品展示 - 商品详情

前端秒杀商品详情显示实现

### 新建Detail组件

Detail.vue

```vue
<template>
  <el-container>
    <el-header height="120px">
      <seckill-head></seckill-head>
    </el-header>
    <el-main>
     秒杀商品详情
    </el-main>
    <el-footer class="elfooter">
      <seckill-footer></seckill-footer>
    </el-footer>
  </el-container>
</template>

<script>
import SeckillHead from './commons/Head'
import SeckillFooter from './commons/Footer'
import axios from "axios";
import {genServerUrl} from '../config/sys'

export default {
  name: 'Detail',
  components:{
    SeckillHead, // 添加引入的组件 否则上边无法使用
    SeckillFooter
  },
  data(){
    return{
      tableData:[]
    }
  },
  methods:{
    getSrcUrl(t){
      return genServerUrl('image/'+t);
    }
  },
  mounted() {
  }
}
</script>

<style scoped>
  .elfooter{
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
  }
</style>

```

### 配置路由

当点击详情按钮时需要路由到详情页面

router/index.js中配置

```js
import Vue from 'vue'
import Router from 'vue-router'
// 导入Login组件
import Login from '@/components/Login'

import Main from '@/components/Main'
import Detail from '@/components/Detail'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    // 登录成功后路由到主页面
    {
      path: '/main',
      name: 'Main',
      component: Main
    }
    ,
    // 点击详情时路由到详情页
    {
      path: '/detail/:id',// 跳转页面时传递点击的id值过去
      name: 'Detail',
      component: Detail
    }
  ]
})

```

### 秒杀商品列表页中给详情绑定点击事件

Main.vue 用来展示秒杀商品列表

```vue
<template>
  <el-container>
    <el-header height="120px">
      <seckill-head></seckill-head>
    </el-header>
    <el-main>
      <el-table
        :data="tableData"
        style="width: 100%">
        <el-table-column
          prop="goods.name"
          label="商品名称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="goods.image"
          label="商品图片"
          width="180">
          <template slot-scope="scope">
            <img :src="getSrcUrl(scope.row.goods.image)" width="60" height="80"/>
          </template>
        </el-table-column>
        <el-table-column
          width="180"
          prop="goods.price"
          label="商品原价(元)">
        </el-table-column>
        <el-table-column
          width="180"
          prop="price"
          label="秒杀价(元)">
        </el-table-column>
        <el-table-column
          width="180"
          prop="stock"
          label="库存数量">
        </el-table-column>
        <el-table-column
          label="操作">
          <template slot-scope="scope">
            <el-button @click="openDetail(scope.row.id)" type="text" size="small">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer class="elfooter">
      <seckill-footer></seckill-footer>
    </el-footer>
  </el-container>
</template>

<script>
import SeckillHead from './commons/Head'
import SeckillFooter from './commons/Footer'
import axios from "axios";
import {genServerUrl} from '../config/sys'

export default {
  name: 'Main',
  components:{
    SeckillHead, // 添加引入的组件 否则上边无法使用
    SeckillFooter
  },
  data(){
    return{
      tableData:[]
    }
  },
  methods:{
    // 点击商品详情时打开详情页面
    openDetail(id){
      // name值要与路由（index.js）指定的名称一致
      this.$router.push({name:"Detail",params:{id:id}})
    },
    // 获取商品图片 拼接图片地址
    getSrcUrl(t){
      return genServerUrl('image/'+t);
    },
    // 获取秒杀商品列表
    getMiaoShaGoods(){
      let url=genServerUrl("miaoShaGoods/findAll");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;
      axios.get(url,{})
        .then(response=>{
          console.log(response.data.data);
          this.tableData=response.data.data;
        }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    }
  },
  mounted() {
    this.getMiaoShaGoods();
  }
}
</script>

<style scoped>
  .elfooter{
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
  }
</style>

```

### 测试

启动前后端连接测试

![image-20231026195111101](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310261951152.png)

### 完善商品详情页

Detail.vue

```vue
<template>
  <el-container>
    <el-header height="120px">
      <seckill-head></seckill-head>
    </el-header>
    <el-main>
      <el-form label-position="left" label-width="150px">
        <p class="goods_title">商品详情</p>
        <el-form-item label="商品名称">
            {{miaoShaGoods.goods.name}}
        </el-form-item>
        <el-form-item label="商品图片">
          <img :src="getSrcUrl(miaoShaGoods.goods.image)" style="width: 250px;">
        </el-form-item>
        <el-form-item label="商品原价">
          {{"￥"+miaoShaGoods.goods.price}}
        </el-form-item>
        <el-form-item label="抢购价">
          {{"￥"+miaoShaGoods.price}}
        </el-form-item>
        <el-form-item label="库存数量">
          {{miaoShaGoods.stock}}
        </el-form-item>
        <el-form-item label="抢购开始时间">
          {{miaoShaGoods.startTime}}
        </el-form-item>
        <el-form-item label="抢购结束时间">
          {{miaoShaGoods.endTime}}
        </el-form-item>
        <el-form-item>
          <el-button type="success" size="small">立即抢购</el-button>
        </el-form-item>
      </el-form>
    </el-main>
    <el-footer class="elfooter">
      <seckill-footer></seckill-footer>
    </el-footer>
  </el-container>
</template>

<script>
import SeckillHead from './commons/Head'
import SeckillFooter from './commons/Footer'
import axios from "axios";
import {genServerUrl} from '../config/sys'

export default {
  name: 'Detail',
  components:{
    SeckillHead, // 添加引入的组件 否则上边无法使用
    SeckillFooter
  },
  data(){
    return{
      miaoShaGoods:{
        goods:{
          name:'',
          price:'',
          image:'default.jpg'

        }
      }
    }
  },
  methods:{
    getSrcUrl(t){
      return genServerUrl('image/'+t);
    },
    getInfo(){
      let url=genServerUrl("miaoShaGoods/findById");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          id:this.$route.params.id
        }
      })
        .then(response=>{
          console.log(response.data.data);
          this.miaoShaGoods=response.data.data;
        }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    }
  },
  mounted() {
    this.getInfo();
  }
}
</script>

<style scoped>
  .elfooter{
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
  }
  .goods_title{
    font-size: 20px;
    font-weight: bold;
  }
</style>

```

![image-20231026202137158](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310262021260.png)

## 商品秒杀状态

```
秒杀开始之前不应显示立即抢购按钮，当秒杀结束后也不应显示抢购按钮 因此这里需要设置秒杀的状态
    商品秒杀状态属性
    miaoShaStatus
    0 未开始秒杀
    1 秒杀进行中
    2 秒杀结束

    添加扩展字段
    miaoShaStatus 秒杀状态
    remainBeginSecond 剩余多少秒开始秒杀
    remainEndSecond 剩余多少秒秒杀结束
```

### 后端实现

```
后端需要传递秒杀开始和结束的剩余时间
```

#### 修改MiaoShaGoods实体类

```java
package com.et.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("t_goods_miaosha")
@Data
public class MiaoShaGoods implements Serializable {

    private Integer id; // 编号
    @TableField(select = false)
    private Goods goods; // 关联商品
    private double price; // 秒杀价格
    private Integer stock; // 库存数量
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date startTime; // 秒杀开始时间
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date endTime; // 秒杀结束时间

    // 扩展属性
    private Integer miaoShaStatus=0; // 秒杀状态 0 未开始秒杀 1 秒杀进行中 2 秒杀结束
    private Integer remainBeginSecond=0; // 剩余多少秒 开始秒杀
    private Integer remainEndSecond=0; // 秒杀结束 剩余多少秒 结束秒杀
}
```

#### 修改MiaoShaGoodsService接口实现类

```java
package com.et.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.et.entity.MiaoShaGoods;
import com.et.mapper.MiaoShaGoodsMapper;
import com.et.service.MiaoShaGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiaoShaGoodsServiceImpl extends ServiceImpl<MiaoShaGoodsMapper, MiaoShaGoods> implements MiaoShaGoodsService {

    @Autowired
    private MiaoShaGoodsMapper miaoShaGoodsMapper;

    @Override
    public List<MiaoShaGoods> listAll() {
        return miaoShaGoodsMapper.listAll();
    }

    @Override
    public MiaoShaGoods findById(Integer id) {
        MiaoShaGoods goods = miaoShaGoodsMapper.findById(id);
        Integer miaoShaStatus=0; // 秒杀状态
        Integer remainBeginSecond=0; // 剩余多少秒 开始秒杀
        Integer remainEndSecond=0; // 秒杀结束 剩余多少秒 结束秒杀

        long startTime = goods.getStartTime().getTime();
        long endTime = goods.getEndTime().getTime();
        long currentTime = System.currentTimeMillis();

        System.out.println("startTime:"+startTime);
        System.out.println("endTime:"+endTime);
        System.out.println("currentTime:"+currentTime);

        if(currentTime<startTime){ // 秒杀还没开始 倒计时
            miaoShaStatus=0;
            remainBeginSecond=(int)(startTime-currentTime)/1000;
            remainEndSecond=(int)(endTime-currentTime)/1000;
        }else if(currentTime>endTime){ // 秒杀结束
            miaoShaStatus=2;
            remainBeginSecond=-1;
            remainEndSecond=-1;
        }else{ // 秒杀进行中
            miaoShaStatus=1;
            remainBeginSecond=0;
            remainEndSecond=(int)(endTime-currentTime)/1000;
        }
        goods.setMiaoShaStatus(miaoShaStatus);
        goods.setRemainBeginSecond(remainBeginSecond);
        goods.setRemainEndSecond(remainEndSecond);
        return goods;
    }
}
```

### 前端页面展示状态

```
在显示抢购开始时间后面追加秒杀状态
在秒杀开始前和结束后不显示按钮
需要动态刷新倒计时

钩子函数中添加countDown() 用来动态显示秒杀倒计时
在获取到秒杀的商品详情中 动态判断是否隐藏按钮
	每秒检查一次 满足条件则隐藏按钮
```

#### 修改Detail.vue

```vue
<template>
  <el-container>
    <el-header height="120px">
      <seckill-head></seckill-head>
    </el-header>
    <el-main>
      <el-form label-position="left" label-width="150px">
        <p class="goods_title">商品详情</p>
        <el-form-item label="商品名称">
            {{miaoShaGoods.goods.name}}
        </el-form-item>
        <el-form-item label="商品图片">
          <img :src="getSrcUrl(miaoShaGoods.goods.image)" style="width: 250px;">
        </el-form-item>
        <el-form-item label="商品原价">
          {{"￥"+miaoShaGoods.goods.price}}
        </el-form-item>
        <el-form-item label="抢购价">
          {{"￥"+miaoShaGoods.price}}
        </el-form-item>
        <el-form-item label="库存数量">
          {{miaoShaGoods.stock}}
        </el-form-item>
        <el-form-item label="抢购开始时间">
          {{miaoShaGoods.startTime}}
          <span v-show="miaoShaGoods.miaoShaStatus==0">抢购倒计时 {{miaoShaGoods.remainBeginSecond}} s</span>
          <span v-show="miaoShaGoods.miaoShaStatus==1">抢购进行中</span>
          <span v-show="miaoShaGoods.miaoShaStatus==2">抢购结束</span>
        </el-form-item>
        <el-form-item label="抢购结束时间">
          {{miaoShaGoods.endTime}}
        </el-form-item>
        <el-form-item>
          <el-button type="success" v-show="miaoShaGoods.miaoShaStatus==1" size="small">立即抢购</el-button>
        </el-form-item>
      </el-form>
    </el-main>
    <el-footer class="elfooter">
      <seckill-footer></seckill-footer>
    </el-footer>
  </el-container>
</template>

<script>
import SeckillHead from './commons/Head'
import SeckillFooter from './commons/Footer'
import axios from "axios";
import {genServerUrl} from '../config/sys'

export default {
  name: 'Detail',
  components:{
    SeckillHead, // 添加引入的组件 否则上边无法使用
    SeckillFooter
  },
  data(){
    return{
      miaoShaGoods:{
        goods:{
          name:'',
          price:'',
          image:'default.jpg'

        }
      }
    }
  },
  methods:{
    getSrcUrl(t){
      return genServerUrl('image/'+t);
    },
    // 秒杀状态及动态倒计时显示
    countDown(){
      let timeout;
      let res = this.miaoShaGoods.remainBeginSecond;
      if(res==0){
        // 秒杀进行中
        this.miaoShaGoods.miaoShaStatus=1;
        // 销毁倒计时
        if(timeout){
          clearTimeout(timeout);
        }
      }else if(res>0){
        // 秒杀还没开始 显示倒计时
        timeout = setTimeout(()=>{
          this.miaoShaGoods.remainBeginSecond = this.miaoShaGoods.remainBeginSecond-1;
          this.countDown();// 每秒调用一次
        },1000)
      }else{
        // 秒杀结束 这里不会动态隐藏秒杀按钮
        this.miaoShaGoods.miaoShaStatus=2;
      }
    },
    getInfo(){
      let url=genServerUrl("miaoShaGoods/findById");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          id:this.$route.params.id
        }
      })
        .then(response=>{
          console.log(response.data.data);
          this.miaoShaGoods=response.data.data;
          this.countDown();// 显示秒杀状态
          // 动态隐藏秒杀按钮 每秒检测一次状态 当状态满足 则隐藏按钮
          if(this.miaoShaGoods.remainEndSecond>0){
            // 秒杀还没结束 需要动态判断等到结束为止
            setTimeout(()=>{
              this.miaoShaGoods.miaoShaStatus=2;
            },this.miaoShaGoods.remainEndSecond*1000)// 按秒执行

          }
        }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    }
  },
  mounted() {
    this.getInfo();
  }
}
</script>

<style scoped>
  .elfooter{
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
  }
  .goods_title{
    font-size: 20px;
    font-weight: bold;
  }
</style>

```

#### 测试

![image-20231026214601855](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310262146934.png)

![image-20231026214639035](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310262146143.png)

## 订单接口实现

```
执行秒杀时需要下单操作 因此需要先做订单业务的接口
新增一张订单表

CREATE TABLE `t_order` (
   `id` varchar(32) NOT NULL AUTO_INCREMENT,
   `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
   `miaosha_goods_id` int(11) DEFAULT NULL COMMENT '秒杀的商品ID',
   `createDate` datetime DEFAULT NULL COMMENT '创建日期',
   `payDate` datetime DEFAULT NULL COMMENT '支付日期',
   `countNum` int(11) DEFAULT NULL COMMENT '购买数量',
   `totalPrice` double DEFAULT NULL COMMENT '订单总金额',
   `payMethod` varchar(32) DEFAULT NULL COMMENT '支付方式',
   `status` int(11) DEFAULT NULL COMMENT '0:订单生成  1：已支付  2 已发货  3：已收货',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

### 创建订单实体类

```java
package com.et.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品订单模块
 */
@TableName("t_order")
@Data
public class Order implements Serializable {

    private String id; // 编号
    private User user; // 购买用户
    private MiaoShaGoods miaoShaGoods; // 购买的秒杀商品
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date createDate; // 创建日期
    private Date payDate; // 支付日期
    private Integer countNum; // 购买数量
    private double totalPrice; // 订单总金额
    private String payMethod; // 支付方式
    private Integer status; // 订单状态   0:订单生成  1：已支付  2 已发货  3：已收货

}
```

### 用户Mapper中新增根据ID查询的方法

```java
public interface UserMapper extends BaseMapper<User> {

    User findById(Integer id);
}
```

### 用户Mapper映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.et.mapper.UserMapper">

    <select id="findById" parameterType="Integer" resultType="User">
        select * from t_user where id=#{id}
    </select>
</mapper>
```

### 用户Service

```java
User findById(Integer id);
```

### 用户Service实现类

```java
@Override
public User findById(Integer id) {
    return userMapper.findById(id);
}
```



### 创建订单Mapper接口

```java
package com.et.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.et.entity.Order;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    public Order findById(String id);
}

```

### 订单Mapper接口映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.et.mapper.OrderMapper">

    <resultMap id="OrderMap" type="Order">
        <association property="miaoShaGoods" column="miaosha_goods_id" select="com.et.mapper.MiaoShaGoodsMapper.findById"></association>
        <association property="user" column="user_id" select="com.et.mapper.UserMapper.findById"></association>
    </resultMap>
    
    <select id="findById" parameterType="String" resultMap="OrderMap">
        select * from t_order where id=#{id}
    </select>
</mapper>
```

### 订单Service

```java
package com.et.service;

import com.et.entity.User;

public interface OrderService {

    User findById(String id);
}

```

### 订单Service实现类

```java
package com.et.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.et.entity.Order;
import com.et.entity.User;
import com.et.mapper.OrderMapper;
import com.et.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Override
    public Order findById(String id) {
        return orderMapper.findById(id);
    }
}

```

### 订单Controller

```java
package com.et.controller;

import com.et.entity.Order;
import com.et.service.OrderService;
import com.et.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 根据id查询秒杀商品详情
     * @param id
     * @return
     */
    @RequestMapping("/detail")
    public R detail(String id){
        Order order = orderService.findById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("data",order);
        return R.ok(map);
    }
}


```

![image-20231026224352653](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310262243979.png)

## 秒杀接口实现

```
秒杀接口需要处理的业务
	1. 根据token获取用户信息
	2. 判断库存是否充足
	3. 判断用户是否重复秒杀
	4. 减库存 落单 必须在同一事务中 
```

### 前端页面

```
前端页面中点击立即抢购按钮时需要触发秒杀操作
```

Detail.vue

按钮上添加click事件

在methods钩子函数中添加对应的function

```vue
<el-button type="success" @click="exec_miaosha()" v-show="miaoShaGoods.miaoShaStatus==1" size="small">立即抢购</el-button>

 // 执行秒杀
    exec_miaosha(){
      let url=genServerUrl("miaoSha/exec");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          // 注意 key值必须与服务端接收数据的参数名保持一致
          goodsId:this.$route.params.id
        }
      }).then(response=>{

      }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    },
```

### 服务端

```
单独创建一个秒杀的Controller来处理秒杀操作
```

#### 创建MiaoShaController

##### 根据token获取用户信息

```java
package com.et.controller;

import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.entity.User;
import com.et.service.MiaoShaGoodsService;
import com.et.service.MiaoShaService;
import com.et.util.R;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 秒杀操作Controller
 */
@RestController
@RequestMapping("/miaoSha")
public class MiaoShaController {

    @Autowired
    private RedisUtil redisUtil;
    /**
     * 执行秒杀
     * @param request 通过request获取header中的token
     * @param goodsId 商品ID
     * @return
     */
    @RequestMapping("/exec")
    public R exec(HttpServletRequest request,Integer goodsId){

        //1. 根据token获取用户信息
        String token = request.getHeader("token");
        User user = (User) redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX,token);
        System.out.println("token="+token+", "+user);
        //2. 判断库存是否充足
        //3. 判断用户是否重复秒杀
        //4. 减库存 落单 必须在同一事务中
        return R.ok();
    }
}

```

##### 判断库存

```
判断库存时需要根据秒杀商品ID查询秒杀的商品信息 
根据商品ID查询商品信息的方法已经实现了(获取商品详情时实现的) 这里直接使用即可
```

MiaoShaController

```java
package com.et.controller;

import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.entity.User;
import com.et.service.MiaoShaGoodsService;
import com.et.service.MiaoShaService;
import com.et.util.R;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 秒杀操作Controller
 */
@RestController
@RequestMapping("/miaoSha")
public class MiaoShaController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;

    /**
     * 执行秒杀
     * @param request 通过request获取header中的token
     * @param goodsId 商品ID
     * @return
     */
    @RequestMapping("/exec")
    public R exec(HttpServletRequest request,Integer goodsId){

        //1. 根据token获取用户信息
        String token = request.getHeader("token");
        User user = (User) redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX,token);
        System.out.println("token="+token+", "+user);
        //2. 判断库存是否充足
        MiaoShaGoods goods = miaoShaGoodsService.findById(goodsId);
        if(goods.getStock()<=0){
            // 没有库存了
            return R.error("库存不足");
        }
        //3. 判断用户是否重复秒杀
        //4. 减库存 落单 必须在同一事务中
        return R.ok();
    }
}

```

###### 联合前端测试

测试时 修改下数据库中的库存字段值 改为0的时候 查看返回的数据状态

![image-20231026231308831](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310262313845.png)

有库存时返回的状态

![image-20231026231348602](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310262313031.png)

##### 减库存 落单 必须在同一事务中

```
减库存 下单需要在同一事务中 - 即同一个方法中 在方法体上添加Transactional即可
减库存和下单操作因为必须同一事务 因此需要用到订单模块(订单模块上边已经创建出来了)
```

###### 减库存

MiaoShaGoodsMapper中添加减库存方法

```java
/**
     * 减库存操作
     * @param id
     * @return
     */
Integer reduceStock(Integer id);
```

MiaoShaGoodsMapper映射文件

```xml
<update id="reduceStock" parameterType="Integer" >
    update t_goods_miaosha set stock=stock-1 where id=#{id}
</update>
```

新建秒杀操作的Service单独处理秒杀操作

MiaoShaService

```java
package com.et.service;

import com.et.entity.MiaoShaGoods;
import com.et.entity.User;

/**
 * 秒杀操作Service
 */
public interface MiaoShaService {

    /**
     * 执行秒杀 成功返回订单ID 否则返回Null
     * @param user
     * @param miaoShaGoods
     * @return
     */
    String exec(User user, MiaoShaGoods miaoShaGoods);
}

```

秒杀操作处理接口的实现类

```java
package com.et.service.impl;

import com.et.entity.MiaoShaGoods;
import com.et.entity.User;
import com.et.mapper.MiaoShaGoodsMapper;
import com.et.service.MiaoShaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoShaServiceImpl implements MiaoShaService {

    @Autowired
    private MiaoShaGoodsMapper miaoShaGoodsMapper;


    /**
     * 执行秒杀
     * @param user
     * @param miaoShaGoods
     * @return
     */
    @Override
    @Transactional
    public String exec(User user, MiaoShaGoods miaoShaGoods) {
        // 减库存
        Integer affectedRows = miaoShaGoodsMapper.reduceStock(miaoShaGoods.getId());
        if(affectedRows==0){
            return null;
        }
        // 生成订单
        return null;
    }
}

```

MiaoShaController

```java
package com.et.controller;

import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.entity.User;
import com.et.service.MiaoShaGoodsService;
import com.et.service.MiaoShaService;
import com.et.util.R;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 秒杀操作Controller
 */
@RestController
@RequestMapping("/miaoSha")
public class MiaoShaController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;

    @Autowired
    private MiaoShaService miaoShaService;

    /**
     * 执行秒杀
     * @param request 通过request获取header中的token
     * @param goodsId 商品ID
     * @return
     */
    @RequestMapping("/exec")
    public R exec(HttpServletRequest request,Integer goodsId){

        //1. 根据token获取用户信息
        String token = request.getHeader("token");
        User user = (User) redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX,token);
        System.out.println("token="+token+", "+user);
        //2. 判断库存是否充足
        MiaoShaGoods goods = miaoShaGoodsService.findById(goodsId);
        if(goods.getStock()<=0){
            // 没有库存了
            return R.error("库存不足");
        }
        //3. 判断用户是否重复秒杀
        //4. 减库存 落单 必须在同一事务中
        miaoShaService.exec(user,goods);
        return R.ok();
    }
}

```

联合前端测试 通过数据库进行验证库存是否减少

![image-20231026232010062](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310262320279.png)

###### 落单

```
下单是在订单表中添加一条数据 因此这里需要在订单的Mapper中添加insert方法
```

OrderMapper接口

```java
/**
     * 生成订单
     * @param order
     * @return
     */
Integer add(Order order);
```

OrderMapper映射文件

```xml
<insert id="add" parameterType="Order">
    insert  into t_order values(#{id},#{user.id},#{miaoShaGoods.id},now(),#{payDate},#{countNum},#{totalPrice},#{payMethod},#{status})
</insert>
```

DateUtil工具类

因为订单的ID同时作为订单编号 因此使用String类型 这里使用时间戳的形式生成

为了方便使用 单独封装一个工具类

```java
package com.et.util;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author Administrator
 *
 */
public class DateUtil {

	/**
	 * 日期对象转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	/**
	 * 字符串转日期对象
	 * @param str
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date formatString(String str,String format) throws Exception{
		if(StringUtils.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	public static String getCurrentDateStr(){
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSSSSSSSS");
		return sdf.format(date);
	}
	
	public static String getCurrentDatePath()throws Exception{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd/");
		return sdf.format(date);
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(getCurrentDateStr());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

```

MiaoShaServiceImpl

秒杀的实现类中继续添加下单操作

```java
package com.et.service.impl;

import com.et.entity.MiaoShaGoods;
import com.et.entity.Order;
import com.et.entity.User;
import com.et.mapper.MiaoShaGoodsMapper;
import com.et.mapper.OrderMapper;
import com.et.service.MiaoShaService;
import com.et.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoShaServiceImpl implements MiaoShaService {

    @Autowired
    private MiaoShaGoodsMapper miaoShaGoodsMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 执行秒杀
     * @param user
     * @param miaoShaGoods
     * @return
     */
    @Override
    @Transactional
    public String exec(User user, MiaoShaGoods miaoShaGoods) {
        // 减库存
        Integer affectedRows = miaoShaGoodsMapper.reduceStock(miaoShaGoods.getId());
        if(affectedRows==0){
            return null;
        }
        // 生成订单
        Order order = new Order();
        order.setId(DateUtil.getCurrentDateStr());// 订单号
        order.setMiaoShaGoods(miaoShaGoods);
        order.setUser(user);
        order.setPayDate(null);// 还没有支付
        order.setCountNum(1);// 每次秒杀只能秒杀一件商品
        order.setStatus(0);// 0表示生成订单
        order.setTotalPrice(miaoShaGoods.getPrice());
        Integer res = orderMapper.add(order);
        if(res==0){
            return null;
        }
        return order.getId();
    }
}

```

MiaoShaoController

```java
package com.et.controller;

import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.entity.User;
import com.et.service.MiaoShaGoodsService;
import com.et.service.MiaoShaService;
import com.et.util.R;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 秒杀操作Controller
 */
@RestController
@RequestMapping("/miaoSha")
public class MiaoShaController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;

    @Autowired
    private MiaoShaService miaoShaService;

    /**
     * 执行秒杀
     * @param request 通过request获取header中的token
     * @param goodsId 商品ID
     * @return
     */
    @RequestMapping("/exec")
    public R exec(HttpServletRequest request,Integer goodsId){

        //1. 根据token获取用户信息
        String token = request.getHeader("token");
        User user = (User) redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX,token);
        System.out.println("token="+token+", "+user);
        //2. 判断库存是否充足
        MiaoShaGoods goods = miaoShaGoodsService.findById(goodsId);
        if(goods.getStock()<=0){
            // 没有库存了
            return R.error("库存不足");
        }
        //3. 判断用户是否重复秒杀
        //4. 减库存 落单 必须在同一事务中
        String orderId = miaoShaService.exec(user, goods);
        if(orderId!=null){
            Map<String,Object> map = new HashMap<>();
            map.put("orderId",orderId);
            return R.ok(map);
        }else{
            return R.error("下单失败，稍后再试");
        }
    }
}

```

页面获取订单ID

Detail.vue

```vue
// 执行秒杀
    exec_miaosha(){
      let url=genServerUrl("miaoSha/exec");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          // 注意 key值必须与服务端接收数据的参数名保持一致
          goodsId:this.$route.params.id
        }
      }).then(response=>{// 请求成功后的操作
        let data = response.data;
        if(data.code!=0){
          alert(data.msg);
        }else{// 下单成功
          alert(data.orderId);
        }
      }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    },
```

启动测试

![image-20231026234240015](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310262342483.png)

![image-20231026234253563](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310262342609.png)

判断是否重复秒杀

```
同一商品 同一用户只允许抢购一次 这里需要使用用户ID和商品的ID进行验证
```

OrderMapper接口中新增查询方法

```java
/**
     * 根据用户ID和秒杀的商品ID查询订单
     * @param map
     * @return
     */
List<Order> finOrderWithUserIdAndGoodsId(Map<String,Object> map);
```

OrderMapper接口映射文件

```xml
 <select id="finOrderWithUserIdAndGoodsId" parameterType="Map" resultType="Order">
     select * from t_order where user_id=#{userId} and miaosha_goods_id=#{goodsId}
</select>
```

OrderService接口

```java
 /**
     * 根据用户ID和秒杀的商品ID查询订单
     * @param map
     * @return
     */
List<Order> finOrderWithUserIdAndGoodsId(Map<String,Object> map);
```

OrderService接口实现类

```java
@Override
public List<Order> finOrderWithUserIdAndGoodsId(Map<String, Object> map) {
    return orderMapper.finOrderWithUserIdAndGoodsId(map);
}
```

MiaoShaController

```java
package com.et.controller;

import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.entity.Order;
import com.et.entity.User;
import com.et.service.MiaoShaGoodsService;
import com.et.service.MiaoShaService;
import com.et.service.OrderService;
import com.et.util.R;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 秒杀操作Controller
 */
@RestController
@RequestMapping("/miaoSha")
public class MiaoShaController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;

    @Autowired
    private MiaoShaService miaoShaService;

    @Autowired
    private OrderService orderService;

    /**
     * 执行秒杀
     * @param request 通过request获取header中的token
     * @param goodsId 商品ID
     * @return
     */
    @RequestMapping("/exec")
    public R exec(HttpServletRequest request,Integer goodsId){

        //1. 根据token获取用户信息
        String token = request.getHeader("token");
        User user = (User) redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX,token);
        System.out.println("token="+token+", "+user);
        //2. 判断库存是否充足
        MiaoShaGoods goods = miaoShaGoodsService.findById(goodsId);
        if(goods.getStock()<=0){
            // 没有库存了
            return R.error("库存不足");
        }
        //3. 判断用户是否重复秒杀
        Map<String,Object> map2 = new HashMap<>();
        map2.put("userId",user.getId());
        map2.put("goodsId",goods.getId());
        List<Order> orders = orderService.finOrderWithUserIdAndGoodsId(map2);
        if(orders.size()!=0){
            return R.error("已购买过此商品");
        }
        //4. 减库存 落单 必须在同一事务中
        String orderId = miaoShaService.exec(user, goods);
        if(orderId!=null){
            Map<String,Object> map = new HashMap<>();
            map.put("orderId",orderId);
            return R.ok(map);
        }else{
            return R.error("下单失败，稍后再试");
        }
    }
}

```

联合前端测试

![image-20231027000944717](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270009297.png)



## Jmeter测试快速上手

### 介绍

```
Apache JMeter是Apache组织开发的基于Java的压力测试工具
官网： jmeter.apache.org

互联网上经常使用每秒的查询速率来衡量域名系统服务器的机器性能 即 QPS
对应fetches/sec 即每秒的响应请求数 也就是大吞吐量
1 响应时间(RT)
	响应时间指的是系统对请求做出响应的时间，直观上看 这个指标与人对软件性能的转关感受是一致的，因为它完整的
	记录了整个计算机系统处理请求的时间，由于一个系统通常会提供许多功能而不同的处理逻辑也千差万别
	对于单机没有并发操作的应用系统而言 普遍认为响应时间是一个合理且准取得性能指标
2 吞吐量(Throughput)
	吞吐量指的是系统在单位时间内处理请求的数量，对于无并发的应用系统而言 吞吐量与响应时间成严格的反比关系
	实际上此时吞吐量就是响应时间的倒数
3 并发量
	并发用户数 指的是系统可以同时承载的正常使用系统的用户的数量，与吞吐量相比 并发用户数是一个更只管但也更
	笼统的性能指标 实际上并发用户数是一个非常不准确的指标，因为用户不同的使用模式会导致不同用户在单位时间内
	发出不同数量的请求
4 QPS 每秒查询速率
	每秒查询速率是对一个特定的查询服务在规定的时间内所处理流量多少的衡量标准，作为域名系统服务器的机器性能
	经常用每秒查询速率来衡量 应对fetches/sec即每秒的响应请求数也就是最大吞吐能力
```

### JMeter工具介绍

#### 启动JMeter

在解压缩后的bin目录下执行jmeter.bat或jmeter.sh

![image-20231027054346429](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270829378.png)

#### 选择工具显示语言

工具栏中选择Options -> Language

![image-20231027054510271](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270829788.png)

#### 添加线程组

相当于创建一个工程一样 执行每个测试任务时都需要创建不同的线程组

![image-20231027054653322](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270829918.png)



#### 设置并发数量

并发数量也就是线程数量

Ramp-up 如果设置为0 则表示立即执行 默认1 表示1秒内执行

![image-20231027054833906](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270829066.png)

#### 添加取样器 - Http请求

用来设置要压测的服务端接口

![image-20231027055115772](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270829938.png)

![image-20231027055440246](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270829805.png)

#### 配置原件

在原件中可以设置头信息，全局的请求地址等

![image-20231027060700711](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270829766.png)

例如在原件中添加http默认请求地址 那么在查询的请求中就不需要重复设置了

![image-20231027060953826](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270829137.png)

#### 添加监听器

监听器中提供了各种视图

![image-20231027055658042](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281438863.png)

![image-20231027055749582](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270829610.png)

#### 执行测试

注意 每次执行前需要手动清楚各个结果视图的结果 否则会追加

![image-20231027055813917](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270829389.png)

![image-20231027060149683](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830051.png)

### 模拟用户请求秒杀接口

#### 配置步骤

```
1. 如果存在其他请求接口 可以禁用掉其他请求 否则在执行的时候都会被执行
2. 添加请求路径和请求参数
```

![image-20231027061622811](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830491.png)

```
3. 添加请求头信息
	这里需要传递token
	我们可以通过浏览器登录下 登录成功会返回token信息到前端
```

![image-20231027061958561](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830779.png)

![image-20231027062031819](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830656.png)

#### 启动测试并查看报告

![image-20231027062301406](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830258.png)

#### 处理乱码

如果报告中存在中文乱码情况 可以通过添加后置过滤器方式解决

![image-20231027062448443](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830267.png)

添加如下内容

prev.setDataEncoding("utf-8")

![image-20231027062654571](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830418.png)

### Jmeter自定义用户变量模拟多用户

```
多用户采用外部配置文件方式
文件内容使用key,value方式 不是key=value
然后通过添加原件的方式添加外部文件 取值时使用${value1} 方式获取配置文件中的值
```

#### 配置文件内容

user.txt

```
etjava,2AAC649239FC4C099C155BD95CE4BDA9
karen,5E7EBA96EE6F4884A574E010D0385B25
```

![image-20231027064611956](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830413.png)

配置外部文件

添加 -> 配置原件 -> CSV Data Set Config

![image-20231027064814401](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830955.png)

![image-20231027064901352](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830634.png)

头信息管理器中动态获取配置文件中内容

![image-20231027065008602](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830427.png)

执行测试

可以看到有一个用户是秒杀成功的 另个用户提示秒杀过此商品

![image-20231027065201913](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830088.png)

![image-20231027065234045](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830227.png)

### 代码生成多用户

```
这里的多用户需要保存到数据库中同时需要将用户信息及token存放到Redis中 另外用户名和token还需要存放外部文件中 使用逗号隔开 
例如 
	tom,token值
	jerry,token值
```

LoginController

```java
 /**
     * 模拟注册2000用户
     * @return
     * @throws IOException
     */
    @RequestMapping("/register")
    public R register() throws IOException {
        for(int i=1;i<=2000;i++){
             User user = new User();
            user.setUsername("Tom"+i);
            user.setPassword("37cbc2f0be822f5ab96485ac11f3dc98");
            user.setId(i);
            user.setRegisterDate(new Date());
            user.setName("Tom"+i);
            user.setAddress("HAWAI'I USA");
            user.setPhoneNumber("123456");
            // 生成token
            String token = UUIDUtil.getUUID();
            // 写入到数据库
            userService.save(user);
            // 写入到缓存
            redisUtil.set(RedisConstant.REDIS_TOKEN_PREFIX,token,user,RedisConstant.REDIS_TOKEN_EXPIRE);
            // 写入到文件
            writeFile(user,token);
        }

        return R.ok();
    }

    private void writeFile(User user,String token) throws IOException {
        String s = user.getUsername()+","+token;
        FileWriter fw = new FileWriter(new File("D:/users.txt"),true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(s+"\r\n");
        bw.flush();
        bw.close();
    }
```



#### 启动Jemeter测试

##### 添加线程组

![image-20231027081724468](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830197.png)

##### 配置元件

###### 添加http默认请求

![image-20231027081754409](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830925.png)

###### 添加CVS数据文件

![image-20231027082033137](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830456.png)

![image-20231027082055174](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270830957.png)

###### 配置Http头信息

![image-20231027082127425](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270831423.png)

![image-20231027082155348](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270831931.png)

##### 配置取样器

###### 添加http请求

![image-20231027081928795](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270831935.png)

##### 配置监听

###### 查看结果树

![image-20231027082325612](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270831254.png)

###### 聚合报告

![image-20231027082400367](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270831181.png)

执行测试

![image-20231027080444505](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270831350.png)

![image-20231027082440429](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270831045.png)

## 超卖问题

```
超卖问题如下图所示 即 当多个线程在同时执行同一个共享资源时 由于是高并发情况 很有可能会出现A线程拿到了资源 但此时其他线程也跟着进入到执行队列 当A线程执行完成 其他线程并不会在经过判断而是直接对共享资源进行下一步操作
这里我们模拟了两千并发同时抢购1000台手机 出现了负数则表示出现了超卖问题

解决超卖问题常用方式有两种 
    1 乐观锁
    	严格意义上来讲 乐观锁需要侵入到代码中 即 在数据库添加一个版本字段 当A线程拿到数据时连同该数据的版本一并获取到 当对数据做改动时也会一并将版本改动 这样就可以有效的防止 多个线程同时拿到同一条数据然后对数据进行修改
    	超卖问题 做了一个乐观锁的变种 即每次扣减库存时直接使用库存判断即可 当库存大于0时才可以扣减
    2 分布式锁
    	基于Reids缓存或Zookeeper实现 后边会有单独课程来讲
```

![image-20231027082459733](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310270831302.png)

### 方式1 乐观锁

```
在执行扣减库存的SQL语句上添加限定条件 当库存大于0时才可以进行扣减操作
```

### MiaoShaGoodsMapper映射文件

```xml
<update id="reduceStock" parameterType="Integer" >
    update t_goods_miaosha set stock=stock-1 where id=#{id} and stock>0
</update>
```

![image-20231027202942379](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310272029622.png)

### 方式2 分布式锁 - Redis

```
```

### 方式2 分布式锁 - Zookeeper

```
```

## 验证码实现

```
秒杀页面添加验证码 目的是为了流量消峰 缓解并发 
这里的验证码为后端生成 然后发送到前端
```

### 验证码controller

该Controller中需要生成验证码 并将其存入到Redis中 每次刷新验证码 需要更换Redis缓存中的验证码

```java
package com.et.controller;


import com.et.constant.RedisConstant;
import com.et.entity.User;
import com.et.util.R;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


/**
	验证码控制器
 */
@Controller
@RequestMapping("/verifyCode")
public class VerifyCodeController {



    @Autowired
    private RedisUtil redisUtil;


    /**
     * 返回验证码图片
     * @param miaoShaGoodsId
     * @return
     */
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public R get(HttpServletResponse response,String token, Integer miaoShaGoodsId){
        System.out.println("token:"+token);
        System.out.println("miaoShaGoodsId:"+miaoShaGoodsId);
    /*    String token = request.getParameter("token");
        System.out.println("token:"+token);*/
        Object o=redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX,token);
        if(o==null){
            return null;
        }
        if(miaoShaGoodsId==null || miaoShaGoodsId <=0){
            return null;
        }
        try {
            BufferedImage image = this.createVerifyCodeImage(((User) o).getId(), miaoShaGoodsId);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(image,"JPEG",out);
            out.flush();
            out.close();
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return R.error("服务端异常");
        }
    }

    private BufferedImage createVerifyCodeImage(Integer userId,Integer miaoShaGoodsId) {
        int width=80;
        int height=32;
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g=image.getGraphics();
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0,0,width,height);
        g.setColor(Color.black);
        g.drawRect(0,0,width-1,height-1);
        Random rdm=new Random();
        for(int i=0;i<50;i++){
            int x=rdm.nextInt(width);
            int y=rdm.nextInt(height);
            g.drawOval(x,y,0,0);
        }

        String verifyCode=createVerifyCode();
        System.out.println("验证："+verifyCode);
        g.setColor(new Color(0,100,0));
        g.setFont(new Font("Candara",Font.BOLD,24));
        g.drawString(verifyCode+"=",8,24);
        g.dispose();

        int rnd=calc(verifyCode);
        System.out.println("rnd:"+rnd);
        redisUtil.set(RedisConstant.REDIS_VERIFYCODE_PREFIX,userId+","+miaoShaGoodsId,rnd,RedisConstant.REDIS_VERIFYCODE_EXPIRE);

        return image;

    }

    public static void main(String[] args) {
        String verifyCode=createVerifyCode();
        System.out.println(calc(verifyCode));
    }

    /**
     * 计算表达式
     * @param exp
     * @return
     */
    private static int calc(String exp) {
        ScriptEngineManager manager=new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
            return (int) engine.eval(exp);
        } catch (ScriptException e) {
            e.printStackTrace();
            return 0;
        }
    }


    private static char[] ops=new char[]{'+','-'};

    /**
     * + - 运算
     * @return
     */
    private static String createVerifyCode() {
        Random rdm=new Random();
        int num1=rdm.nextInt(10);
        int num2=rdm.nextInt(10);
        char op1=ops[rdm.nextInt(2)];
        System.out.println("op1:"+op1);
        String exp=num1+String.valueOf(op1)+num2;
        System.out.println("exp:"+exp);
        return exp;
    }

}

```

### 前端展示验证码

```
1. 添加img 动态获取服务端传递过来的验证码图片
2. 在秒杀进行中状态的时候进行获取验证码
3. 验证码点击时刷新操作
4. 添加文本框 秒杀时提交验证码
5. 添加重置样式的css文件 因存在其他css样式干扰 需要对样式进行重置
```

Date.vue

```vue
1 添加img 动态获取服务端传递过来的验证码图片
<img :src="verifyCodeImgSrc" id="verifyImg" width="80" height="32" @click="refreshVerifyCode" v-show="miaoShaGoods.miaoShaStatus==1" />
      <el-button type="success" @click="exec_miaosha()" v-show="miaoShaGoods.miaoShaStatus==1" size="small">立即抢购</el-button>
2 在秒杀进行中状态的时候进行获取验证码
// 秒杀状态及动态倒计时显示
    countDown(){
      let timeout;
      let res = this.miaoShaGoods.remainBeginSecond;
      if(res==0){
        // 秒杀进行中
        this.miaoShaGoods.miaoShaStatus=1;
        // 销毁倒计时
        if(timeout){
          clearTimeout(timeout);
        }
        // 秒杀进行中的时候显示验证码
        let imgUrl = genServerUrl("verifyCode/get");
        this.verifyCodeImgSrc=imgUrl+"?miaoShaGoodsId="+this.$route.params.id+"&token="+window.sessionStorage.getItem("token");
      }else if(res>0){
        // 秒杀还没开始 显示倒计时
        timeout = setTimeout(()=>{
          this.miaoShaGoods.remainBeginSecond = this.miaoShaGoods.remainBeginSecond-1;
          this.countDown();// 每秒调用一次
        },1000)
      }else{
        // 秒杀结束 这里不会动态隐藏秒杀按钮
        this.miaoShaGoods.miaoShaStatus=2;
      }
    },
3 验证码点击时刷新操作
// 刷新验证码
refreshVerifyCode(){
    let imgUrl = genServerUrl("verifyCode/get");
    // 添加timestamp参数的目的是为了防止浏览器缓存
    this.verifyCodeImgSrc=imgUrl+"?miaoShaGoodsId="+this.$route.params.id+
    "&token="+window.sessionStorage.getItem("token")+"&timestamp="+new Date();
},

4 添加文本框 秒杀时提交验证码
<img :src="verifyCodeImgSrc" id="verifyImg" width="80" height="32" @click="refreshVerifyCode" v-show="miaoShaGoods.miaoShaStatus==1" />
<input v-show="miaoShaGoods.miaoShaStatus==1"
       style="width: 60px;height: 23px;padding: 4px;border: 1px solid gray;"
       type="text"  id="verifyCode" v-model="verifyCode">
<el-button type="success" @click="exec_miaosha()" v-show="miaoShaGoods.miaoShaStatus==1" size="small">立即抢购</el-button>

提交时需要传递验证码
 // 执行秒杀
    exec_miaosha(){
      if(this.verifyCode==''){
        alert("请填写验证码结果");
        return;
      }
      let url=genServerUrl("miaoSha/exec");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          // 注意 key值必须与服务端接收数据的参数名保持一致
          goodsId:this.$route.params.id,
          verifyCode: this.verifyCode // 
        }
      }).then(response=>{
        let data = response.data;
        if(data.code!=0){
          alert(data.msg);
        }else{// 下单成功
          alert(data.orderId);
        }
      }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    },

5 添加重置样式的css文件 因存在其他css样式干扰 需要对样式进行重置
在main.js中引入border.css、reset.css两个文件
import '@/assets/border.css'
import '@/assets/reset.css'
```

完整代码

```vue
<template>
  <el-container>
    <el-header height="120px">
      <seckill-head></seckill-head>
    </el-header>
    <el-main>
      <el-form label-position="left" label-width="150px">
        <p class="goods_title">商品详情</p>
        <el-form-item label="商品名称">
            {{miaoShaGoods.goods.name}}
        </el-form-item>
        <el-form-item label="商品图片">
          <img :src="getSrcUrl(miaoShaGoods.goods.image)" style="width: 250px;">
        </el-form-item>
        <el-form-item label="商品原价">
          {{"￥"+miaoShaGoods.goods.price}}
        </el-form-item>
        <el-form-item label="抢购价">
          {{"￥"+miaoShaGoods.price}}
        </el-form-item>
        <el-form-item label="库存数量">
          {{miaoShaGoods.stock}}
        </el-form-item>
        <el-form-item label="抢购开始时间">
          {{miaoShaGoods.startTime}}
          <span v-show="miaoShaGoods.miaoShaStatus==0">抢购倒计时 {{miaoShaGoods.remainBeginSecond}} s</span>
          <span v-show="miaoShaGoods.miaoShaStatus==1">抢购进行中</span>
          <span v-show="miaoShaGoods.miaoShaStatus==2">抢购结束</span>
        </el-form-item>
        <el-form-item label="抢购结束时间">
          {{miaoShaGoods.endTime}}
        </el-form-item>
      </el-form>
      <img :src="verifyCodeImgSrc" id="verifyImg" width="80" height="32" @click="refreshVerifyCode" v-show="miaoShaGoods.miaoShaStatus==1" />
      <input v-show="miaoShaGoods.miaoShaStatus==1"
             style="width: 60px;height: 23px;padding: 4px;border: 1px solid gray;"
             type="text"  id="verifyCode" v-model="verifyCode">
      <el-button type="success" @click="exec_miaosha()" v-show="miaoShaGoods.miaoShaStatus==1" size="small">立即抢购</el-button>
    </el-main>
    <el-footer class="elfooter">
      <seckill-footer></seckill-footer>
    </el-footer>
  </el-container>
</template>

<script>
import SeckillHead from './commons/Head'
import SeckillFooter from './commons/Footer'
import axios from "axios";
import {genServerUrl} from '../config/sys'

export default {
  name: 'Detail',
  components:{
    SeckillHead, // 添加引入的组件 否则上边无法使用
    SeckillFooter
  },
  data(){
    return{
      miaoShaGoods:{
        goods:{
          name:'',
          price:'',
          image:'default.jpg'

        }
      },
      verifyCodeImgSrc: '',
      verifyCode: ''
    }
  },
  methods:{
    getSrcUrl(t){
      return genServerUrl('image/'+t);
    },
    // 刷新验证码
    refreshVerifyCode(){
      let imgUrl = genServerUrl("verifyCode/get");
      // 添加timestamp参数的目的是为了防止浏览器缓存
      this.verifyCodeImgSrc=imgUrl+"?miaoShaGoodsId="+this.$route.params.id+
        "&token="+window.sessionStorage.getItem("token")+"&timestamp="+new Date();
    },
    // 执行秒杀
    exec_miaosha(){
      if(this.verifyCode==''){
        alert("请填写验证码结果");
        return;
      }
      let url=genServerUrl("miaoSha/exec");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          // 注意 key值必须与服务端接收数据的参数名保持一致
          goodsId:this.$route.params.id,
          verifyCode: this.verifyCode
        }
      }).then(response=>{
        let data = response.data;
        if(data.code!=0){
          alert(data.msg);
        }else{// 下单成功
          alert(data.orderId);
        }
      }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    },
    // 秒杀状态及动态倒计时显示
    countDown(){
      let timeout;
      let res = this.miaoShaGoods.remainBeginSecond;
      if(res==0){
        // 秒杀进行中
        this.miaoShaGoods.miaoShaStatus=1;
        // 销毁倒计时
        if(timeout){
          clearTimeout(timeout);
        }
        // 秒杀进行中的时候显示验证码
        let imgUrl = genServerUrl("verifyCode/get");
        this.verifyCodeImgSrc=imgUrl+"?miaoShaGoodsId="+this.$route.params.id+"&token="+window.sessionStorage.getItem("token");
      }else if(res>0){
        // 秒杀还没开始 显示倒计时
        timeout = setTimeout(()=>{
          this.miaoShaGoods.remainBeginSecond = this.miaoShaGoods.remainBeginSecond-1;
          this.countDown();// 每秒调用一次
        },1000)
      }else{
        // 秒杀结束 这里不会动态隐藏秒杀按钮
        this.miaoShaGoods.miaoShaStatus=2;
      }
    },
    getInfo(){
      let url=genServerUrl("miaoShaGoods/findById");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          id:this.$route.params.id
        }
      })
        .then(response=>{
          console.log(response.data.data);
          this.miaoShaGoods=response.data.data;
          this.countDown();// 显示秒杀状态
          // 动态隐藏秒杀按钮
          if(this.miaoShaGoods.remainEndSecond>0){
            // 秒杀还没结束 需要动态判断等到结束为止
            setTimeout(()=>{
              this.miaoShaGoods.miaoShaStatus=2;
            },this.miaoShaGoods.remainEndSecond*1000)// 按秒执行

          }
        }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    }
  },
  mounted() {
    this.getInfo();// 页面加载完成后执行
  }
}
</script>

<style scoped>
  .elfooter{
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
  }
  .goods_title{
    font-size: 20px;
    font-weight: bold;
  }
</style>

```

### 页面效果

![image-20231027225014052](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310272250133.png)

### 服务端在执行秒杀前对验证码进行校验

```
MiaoShaController
```

```java
package com.et.controller;

import com.alibaba.druid.util.StringUtils;
import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.entity.Order;
import com.et.entity.User;
import com.et.service.MiaoShaGoodsService;
import com.et.service.MiaoShaService;
import com.et.service.OrderService;
import com.et.util.R;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 秒杀操作Controller
 */
@RestController
@RequestMapping("/miaoSha")
public class MiaoShaController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;

    @Autowired
    private MiaoShaService miaoShaService;

    @Autowired
    private OrderService orderService;

    /**
     * 执行秒杀
     * @param request 通过request获取header中的token
     * @param goodsId 商品ID
     * @return
     */
    @RequestMapping("/exec")
    public R exec(HttpServletRequest request,Integer goodsId,String verifyCode){
        // 判断验证码
        if(StringUtils.isEmpty(verifyCode)){
            return R.error("验证码不能为空");
        }
        //1. 根据token获取用户信息
        String token = request.getHeader("token");
        User user = (User) redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX,token);
        System.out.println("token="+token+", "+user);
        MiaoShaGoods goods = miaoShaGoodsService.findById(goodsId);
        // 校验验证码的合法性
        Object rnd = redisUtil.get(RedisConstant.REDIS_VERIFYCODE_PREFIX,user.getId()+","+goods.getId());
        if(rnd==null){
            return R.error("验证码过期");
        }else if(!verifyCode.equals(String.valueOf(rnd))){
            return R.error("验证码错误");
        }
        //2. 判断库存是否充足
        if(goods.getStock()<=0){
            // 没有库存了
            return R.error("库存不足");
        }
        //3. 判断用户是否重复秒杀
        Map<String,Object> map2 = new HashMap<>();
        map2.put("userId",user.getId());
        map2.put("goodsId",goods.getId());
        List<Order> orders = orderService.finOrderWithUserIdAndGoodsId(map2);
        if(orders.size()!=0){
            return R.error("已购买过此商品");
        }
        //4. 减库存 落单 必须在同一事务中
        String orderId = miaoShaService.exec(user, goods);
        if(orderId!=null){
            Map<String,Object> map = new HashMap<>();
            map.put("orderId",orderId);
            return R.ok(map);
        }else{
            return R.error("下单失败，稍后再试");
        }
    }
}

```

### 综合测试验证码

![image-20231027230219940](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310272302053.png)

## 引入RabbitMQ秒杀接口异步化32

```
优化秒杀接口
如果要提高性能 必然减少数据库的操作频率 至少在秒杀期间不能出现大量的数据库操作
这里我们引入MQ来进一步对秒杀的性能进行优化
```

### 添加rabbitmq依赖

```xml
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

### RabbitMQ配置类

```
配置简单的交换机和消息队列
```

```java
package com.et.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MQ配置类
 */
@Configuration
public class MQConfig {

    // 队列名称
    public static final String MIAOSHA_QUEUE="miaosha_queue";

    /**
     * 定义消息队列
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue(MIAOSHA_QUEUE);
    }
}

```

### 封装队列中消息的实体类

```
队列中存放用户信息和秒杀商品ID
```

```java
package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiaoShaMessage {
    private User user;
    private Integer miaoShaGoodsId;
}

```

### 创建对象序列化工具类

```
对象存入到消息队列中需要经过序列化 这里我们自己封装一个工具类
```

```java
package com.et.util;

import com.alibaba.fastjson.JSON;

/**
 * 对象序列化工具类
 */
public class BeanUtil {

    /**
     * 将任意类型转换成字符串
     * @param value
     * @param <T>
     * @return
     */
    public static <T> String beanToString(T value) {
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return value + "";
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return value + "";
        }else {
            return JSON.toJSONString(value);
        }
    }


    /**
     * 把一个字符串转换成bean对象
     * @param str
     * @param <T>
     * @return
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

}
```

### 创建消息的发送器

```java
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

```

### 创建消息接收者

消息接收者中 在接收到消息后就执行验证库存，减库存，下单操作

但无法直接返回消息，后边我们在提供一个接口给到前端进行轮询 查看是否秒杀成功

```java
package com.et.rabbitmq;

import com.et.config.MQConfig;
import com.et.entity.MiaoShaGoods;
import com.et.entity.MiaoShaMessage;
import com.et.entity.Order;
import com.et.entity.User;
import com.et.service.MiaoShaGoodsService;
import com.et.service.MiaoShaService;
import com.et.service.OrderService;
import com.et.util.BeanUtil;
import com.et.util.R;
import com.et.util.RedisUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息接收者
 */
@Service
public class MQReceiver {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;

    @Autowired
    private MiaoShaService miaoShaService;

    @Autowired
    private OrderService orderService;

    // 监听队列消息 只要存在就会自动获取
    @RabbitListener(queues = {MQConfig.MIAOSHA_QUEUE})
    public void receive(String message){
        // 将消息转为对象
        MiaoShaMessage miaoShaMessage = BeanUtil.stringToBean(message, MiaoShaMessage.class);
        User user = miaoShaMessage.getUser();
        Integer goodsId = miaoShaMessage.getMiaoShaGoodsId();
        //2. 判断库存是否充足
        MiaoShaGoods goods = miaoShaGoodsService.findById(goodsId);
        if(goods.getStock()<=0){
            // 没有库存了
            System.out.println("库存不足");
            return;
        }
        //3. 判断用户是否重复秒杀
        Map<String,Object> map2 = new HashMap<>();
        map2.put("userId",user.getId());
        map2.put("goodsId",goods.getId());
        List<Order> orders = orderService.finOrderWithUserIdAndGoodsId(map2);
        if(orders.size()!=0){
            System.out.println("已购买过此商品");
            return;
        }
        //4. 减库存 落单 必须在同一事务中
        String orderId = miaoShaService.exec(user, goods);
    }
}

```

### 执行秒杀Controller中调整

```java
package com.et.controller;

import com.alibaba.druid.util.StringUtils;
import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.entity.MiaoShaMessage;
import com.et.entity.Order;
import com.et.entity.User;
import com.et.rabbitmq.MQSender;
import com.et.service.MiaoShaGoodsService;
import com.et.service.MiaoShaService;
import com.et.service.OrderService;
import com.et.util.R;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 秒杀操作Controller
 */
@RestController
@RequestMapping("/miaoSha")
public class MiaoShaController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;

    @Autowired
    private MiaoShaService miaoShaService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MQSender mqSender;

    /**
     * 执行秒杀
     * @param request 通过request获取header中的token
     * @param goodsId 商品ID
     * @return
     */
    @RequestMapping("/exec")
    public R exec(HttpServletRequest request,Integer goodsId,String verifyCode){
        // 判断验证码
        if(StringUtils.isEmpty(verifyCode)){
            return R.error("验证码不能为空");
        }
        //1. 根据token获取用户信息
        String token = request.getHeader("token");
        User user = (User) redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX,token);
        System.out.println("token="+token+", "+user);
        MiaoShaGoods goods = miaoShaGoodsService.findById(goodsId);
        // 校验验证码的合法性
        Object rnd = redisUtil.get(RedisConstant.REDIS_VERIFYCODE_PREFIX,user.getId()+","+goods.getId());
        if(rnd==null){
            return R.error("验证码过期");
        }else if(!verifyCode.equals(String.valueOf(rnd))){
            return R.error("验证码错误");
        }
        // 封装商品和用户信息发送到队列中
        MiaoShaMessage msg = new MiaoShaMessage(user,goodsId);
        mqSender.sendMiaoShaMsg(msg);
        return R.ok("排队抢购中");

        /*

         */

        // 下边的操作迁移至消息接收者中进行处理
        /*//2. 判断库存是否充足
        if(goods.getStock()<=0){
            // 没有库存了
            return R.error("库存不足");
        }
        //3. 判断用户是否重复秒杀
        Map<String,Object> map2 = new HashMap<>();
        map2.put("userId",user.getId());
        map2.put("goodsId",goods.getId());
        List<Order> orders = orderService.finOrderWithUserIdAndGoodsId(map2);
        if(orders.size()!=0){
            return R.error("已购买过此商品");
        }
        //4. 减库存 落单 必须在同一事务中
        String orderId = miaoShaService.exec(user, goods);
        if(orderId!=null){
            Map<String,Object> map = new HashMap<>();
            map.put("orderId",orderId);
            return R.ok(map);
        }else{
            return R.error("下单失败，稍后再试");
        }*/
    }

    /**
     * 秒杀结果查询
     * @param request
     * @param goodsId
     * @return
     *      大于0 秒杀成功 返回订单ID，
     *      0 秒杀还没结束 还在排队中
     *      -1 秒杀失败
     *
     */
    @RequestMapping("/result")
    public R result(HttpServletRequest request,Integer goodsId){
        //1. 根据token获取用户信息
        String token = request.getHeader("token");
        User user = (User) redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX,token);
        System.out.println("token="+token+", "+user);
        String result = miaoShaService.getMiaoShaResult(user.getId(),goodsId);
        Map<String,Object> map = new HashMap<>();
        map.put("result",result);
        return R.ok(map);
    }
}

```

### MiaoShaService接口添加查询商品是否秒杀完成结果

```java
/**
     * 获取秒杀结果
     * @param id
     * @param goodsId
     * @return
     */
String getMiaoShaResult(Integer id, Integer goodsId);
```

### MiaoShaService接口实现类

```java
 @Override
public String getMiaoShaResult(Integer userId, Integer goodsId) {
    Map<String,Object> map = new HashMap<>();
    map.put("userId",userId);
    map.put("goodsId",goodsId);
    List<Order> orderList = orderMapper.finOrderWithUserIdAndGoodsId(map);
    if(orderList.size()!=0){
        return orderList.get(0).getId();
    }else{
        // 查询商品是否秒杀完成 从Redis中判断商品是否秒杀完成
        boolean over = true;
        if(over){
            return "-1";
        }else{
            return "0";
        }
    }
}
```

### 配置RabbitMQ连接

```yaml
spring:
  rabbitmq:
    host: 192.168.199.106
    port: 5672
    username: admin
    password: admin
```

### RabbitMQ安装

```
docker run -it --rm --name rabbitmq -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management
```

测试

![image-20231028052411449](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280525327.png)

### 前端秒杀结果轮询实现

```
在data.code返回0的时候 进行轮询
因为现在已经改为了后端将秒杀数据先放到队列中 然后在从队列进行判断是否秒杀成功
```

![image-20231028052650215](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280526497.png)

完整代码

```vue
<template>
  <el-container>
    <el-header height="120px">
      <seckill-head></seckill-head>
    </el-header>
    <el-main>
      <el-form label-position="left" label-width="150px">
        <p class="goods_title">商品详情</p>
        <el-form-item label="商品名称">
            {{miaoShaGoods.goods.name}}
        </el-form-item>
        <el-form-item label="商品图片">
          <img :src="getSrcUrl(miaoShaGoods.goods.image)" style="width: 250px;">
        </el-form-item>
        <el-form-item label="商品原价">
          {{"￥"+miaoShaGoods.goods.price}}
        </el-form-item>
        <el-form-item label="抢购价">
          {{"￥"+miaoShaGoods.price}}
        </el-form-item>
        <el-form-item label="库存数量">
          {{miaoShaGoods.stock}}
        </el-form-item>
        <el-form-item label="抢购开始时间">
          {{miaoShaGoods.startTime}}
          <span v-show="miaoShaGoods.miaoShaStatus==0">抢购倒计时 {{miaoShaGoods.remainBeginSecond}} s</span>
          <span v-show="miaoShaGoods.miaoShaStatus==1">抢购进行中</span>
          <span v-show="miaoShaGoods.miaoShaStatus==2">抢购结束</span>
        </el-form-item>
        <el-form-item label="抢购结束时间">
          {{miaoShaGoods.endTime}}
        </el-form-item>
      </el-form>
      <img :src="verifyCodeImgSrc" id="verifyImg" width="80" height="32" @click="refreshVerifyCode" v-show="miaoShaGoods.miaoShaStatus==1" />
      <input v-show="miaoShaGoods.miaoShaStatus==1"
             style="width: 60px;height: 23px;padding: 4px;border: 1px solid gray;"
             type="text"  id="verifyCode" v-model="verifyCode">
      <el-button type="success" @click="exec_miaosha()"
                 v-show="miaoShaGoods.miaoShaStatus==1" size="small">立即抢购</el-button>
      <p style="color: red">{{miaoShaResult}}</p>
    </el-main>
    <el-footer class="elfooter">
      <seckill-footer></seckill-footer>
    </el-footer>
  </el-container>
</template>

<script>
import SeckillHead from './commons/Head'
import SeckillFooter from './commons/Footer'
import axios from "axios";
import {genServerUrl} from '../config/sys'

export default {
  name: 'Detail',
  components:{
    SeckillHead, // 添加引入的组件 否则上边无法使用
    SeckillFooter
  },
  data(){
    return{
      miaoShaGoods:{
        goods:{
          name:'',
          price:'',
          image:'default.jpg'

        }
      },
      verifyCodeImgSrc: '',
      verifyCode: '',
      miaoShaResult: ''
    }
  },
  methods:{
    getSrcUrl(t){
      return genServerUrl('image/'+t);
    },
    // 刷新验证码
    refreshVerifyCode(){
      let imgUrl = genServerUrl("verifyCode/get");
      // 添加timestamp参数的目的是为了防止浏览器缓存
      this.verifyCodeImgSrc=imgUrl+"?miaoShaGoodsId="+this.$route.params.id+
        "&token="+window.sessionStorage.getItem("token")+"&timestamp="+new Date();
    },
    // 执行秒杀
    exec_miaosha(){
      if(this.verifyCode==''){
        alert("请填写验证码结果");
        return;
      }
      let url=genServerUrl("miaoSha/exec");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          // 注意 key值必须与服务端接收数据的参数名保持一致
          goodsId:this.$route.params.id,
          verifyCode: this.verifyCode
        }
      }).then(response=>{
        let data = response.data;
        if(data.code!=0){
          alert(data.msg);
        }else{// 下单成功
          //alert(data.orderId);
          this.miaoShaGoods.miaoShaStatus=4;
          this.miaoShaResult=data.msg;
          this.getMiaoShaResult(this.$route.params.id);// 轮询获取秒杀状态
        }
      }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    },
    // 获取秒杀状态
    getMiaoShaResult(miaoShaGoodsId){
      let url=genServerUrl("miaoSha/result");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          // 注意 key值必须与服务端接收数据的参数名保持一致
          goodsId:this.$route.params.id
        }
      }).then(response=>{
        let data=response.data;
        if(data.code!=0){
          alert(data.msg);
        }else{
          let result=data.result;
          if(result=="0"){  // 排队中，继续轮询
            setTimeout(function (){
              this.getMiaoShaResult(miaoShaGoodsId)
            },50);
          }else if(result=="-1"){
            this.miaoShaResult="秒杀失败";
          }else{
            alert("秒杀成功,订单号："+result);
          }
        }
      }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    },
    // 秒杀状态及动态倒计时显示
    countDown(){
      let timeout;
      let res = this.miaoShaGoods.remainBeginSecond;
      if(res==0){
        // 秒杀进行中
        this.miaoShaGoods.miaoShaStatus=1;
        // 销毁倒计时
        if(timeout){
          clearTimeout(timeout);
        }
        // 秒杀进行中的时候显示验证码
        let imgUrl = genServerUrl("verifyCode/get");
        this.verifyCodeImgSrc=imgUrl+"?miaoShaGoodsId="+this.$route.params.id+"&token="+window.sessionStorage.getItem("token");
      }else if(res>0){
        // 秒杀还没开始 显示倒计时
        timeout = setTimeout(()=>{
          this.miaoShaGoods.remainBeginSecond = this.miaoShaGoods.remainBeginSecond-1;
          this.countDown();// 每秒调用一次
        },1000)
      }else{
        // 秒杀结束 这里不会动态隐藏秒杀按钮
        this.miaoShaGoods.miaoShaStatus=2;
      }
    },
    getInfo(){
      let url=genServerUrl("miaoShaGoods/findById");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          id:this.$route.params.id
        }
      })
        .then(response=>{
          console.log(response.data.data);
          this.miaoShaGoods=response.data.data;
          this.countDown();// 显示秒杀状态
          // 动态隐藏秒杀按钮
          if(this.miaoShaGoods.remainEndSecond>0){
            // 秒杀还没结束 需要动态判断等到结束为止
            setTimeout(()=>{
              this.miaoShaGoods.miaoShaStatus=2;
            },this.miaoShaGoods.remainEndSecond*1000)// 按秒执行

          }
        }).catch(error=>{
         alert(error+"-请联系管理员");
      })
    }
  },
  mounted() {
    this.getInfo();// 页面加载完成后执行
  }
}
</script>

<style scoped>
  .elfooter{
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
  }
  .goods_title{
    font-size: 20px;
    font-weight: bold;
  }
</style>

```

![image-20231028061436674](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280614853.png)

换个账号登录再去抢购

![image-20231028064912965](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280649024.png)

这里的测试不会出现秒杀成提示，因为后端返回的result是写的固定值 下一步我们需要将其改为动态的就可以了

![image-20231028065109050](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280651027.png)

## 秒杀接口进一步优化 - 秒杀商品信息放redis中判断

```
1. 创建一个当项目启动时就去加载秒杀商品库存和是否秒杀完成标识到redis中
2. 执行秒杀的controller中验证库存(这里的库存是从redis中获取的)
```

### 创建StartupRanner

项目启动时就去加载秒杀商品库存和是否秒杀完成标识到redis中

```java
package com.et.starup;

import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.service.MiaoShaGoodsService;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 项目启动时去加载秒杀商品信息及秒杀是否完成的状态到redis中
 */
@Component("starupRunner")
public class StarupRunner implements CommandLineRunner {

    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public void run(String... args) throws Exception {
        List<MiaoShaGoods> goodsList = miaoShaGoodsService.listAll();
        System.out.println("启动时加载秒杀商品库存信息");
        for (MiaoShaGoods goods : goodsList) {
            System.out.println("[ "+goods.getId()+","+goods.getStock()+" ]");
            // 库存放到redis中
            // set2 方法是使用redis的序列化 因此我们需要使用redis的减库存操作
            redisUtil.set2(RedisConstant.REDIS_STOCK_PREFIX,goods.getId()+"",goods.getStock()+"");
            // 将秒杀状态存放到redis中 默认没有秒杀完
            redisUtil.set(RedisConstant.REDIS_GOODS_MIAOSHA_OVER_PREFIX,goods.getId()+"",false);
        }
    }
}

```

![image-20231028071949471](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280719639.png)

### 修改执行秒杀的Controller

```
执行秒杀的controller中验证库存(这里的库存是从redis中获取的)
操作步骤
1. 在redis中取出是否秒杀完成的状态标记 key是商品ID，value是boolean值 默认是false 没有秒杀完成
2. 对redis中的库存进行-1操作 decr()
3. 在消息队列中判断库存操作时 根据条件改变redis中的商品是否秒杀完成状态

```

#### MiaoShaController

![image-20231028074641550](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280746613.png)

完整代码

```java
package com.et.controller;

import com.alibaba.druid.util.StringUtils;
import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.entity.MiaoShaMessage;
import com.et.entity.Order;
import com.et.entity.User;
import com.et.rabbitmq.MQSender;
import com.et.service.MiaoShaGoodsService;
import com.et.service.MiaoShaService;
import com.et.service.OrderService;
import com.et.util.R;
import com.et.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 秒杀操作Controller
 */
@RestController
@RequestMapping("/miaoSha")
public class MiaoShaController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;

    @Autowired
    private MiaoShaService miaoShaService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MQSender mqSender;

    /**
     * 执行秒杀
     * @param request 通过request获取header中的token
     * @param goodsId 商品ID
     * @return
     */
    @RequestMapping("/exec")
    public R exec(HttpServletRequest request,Integer goodsId,String verifyCode){
        // 判断验证码
        if(StringUtils.isEmpty(verifyCode)){
            return R.error("验证码不能为空");
        }
        //1. 根据token获取用户信息
        String token = request.getHeader("token");
        User user = (User) redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX,token);
        System.out.println("token="+token+", "+user);
        MiaoShaGoods goods = miaoShaGoodsService.findById(goodsId);
        // 校验验证码的合法性
        Object rnd = redisUtil.get(RedisConstant.REDIS_VERIFYCODE_PREFIX,user.getId()+","+goods.getId());
        if(rnd==null){
            return R.error("验证码过期");
        }else if(!verifyCode.equals(String.valueOf(rnd))){
            return R.error("验证码错误");
        }

        // 验证库存 - 取redis中的数据
        boolean isOver = (boolean)redisUtil.get(RedisConstant.REDIS_GOODS_MIAOSHA_OVER_PREFIX,goodsId+"");
        if(isOver){
            return R.ok("秒杀结束");
        }
        // redis中减库存操作 -1操作
        long stock = redisUtil.decr(RedisConstant.REDIS_STOCK_PREFIX,goodsId+"",1);
        if(stock<0){
            return R.error("秒杀失败，商品库存不足 谢谢惠顾");
        }
        // redis中当库存-1操作后 库存不足时 将秒杀是否完成状态设置为true 表示已经秒杀完成了
        // 在MQReceive中判断库存时进行设置


        // 封装商品和用户信息发送到队列中
        MiaoShaMessage msg = new MiaoShaMessage(user,goodsId);
        mqSender.sendMiaoShaMsg(msg);
        return R.ok("排队抢购中");
    }

    /**
     * 秒杀结果查询
     * @param request
     * @param goodsId
     * @return
     *      大于0 秒杀成功 返回订单ID，
     *      0 秒杀还没结束 还在排队中
     *      -1 秒杀失败
     *
     */
    @RequestMapping("/result")
    public R result(HttpServletRequest request,Integer goodsId){
        //1. 根据token获取用户信息
        String token = request.getHeader("token");
        User user = (User) redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX,token);
        System.out.println("token="+token+", "+user);
        String result = miaoShaService.getMiaoShaResult(user.getId(),goodsId);
        Map<String,Object> map = new HashMap<>();
        map.put("result",result);
        return R.ok(map);
    }
}

```

#### MQReceiver 消息接收者

![image-20231028073908872](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280739946.png)

完整代码

```java
package com.et.rabbitmq;

import com.et.config.MQConfig;
import com.et.constant.RedisConstant;
import com.et.entity.MiaoShaGoods;
import com.et.entity.MiaoShaMessage;
import com.et.entity.Order;
import com.et.entity.User;
import com.et.service.MiaoShaGoodsService;
import com.et.service.MiaoShaService;
import com.et.service.OrderService;
import com.et.util.BeanUtil;
import com.et.util.R;
import com.et.util.RedisUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息接收者
 */
@Service
public class MQReceiver {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;

    @Autowired
    private MiaoShaService miaoShaService;

    @Autowired
    private OrderService orderService;

    // 监听队列消息 只要存在就会自动获取
    @RabbitListener(queues = {MQConfig.MIAOSHA_QUEUE})
    public void receive(String message){
        // 将消息转为对象
        MiaoShaMessage miaoShaMessage = BeanUtil.stringToBean(message, MiaoShaMessage.class);
        User user = miaoShaMessage.getUser();
        Integer goodsId = miaoShaMessage.getMiaoShaGoodsId();
        //2. 判断库存是否充足
        MiaoShaGoods goods = miaoShaGoodsService.findById(goodsId);
        if(goods.getStock()<=0){
            // 没有库存了
            System.out.println("库存不足");
            // 设置redis中的秒杀商品 库存不足 默认false表示没有秒杀完成
            redisUtil.set(RedisConstant.REDIS_GOODS_MIAOSHA_OVER_PREFIX,goodsId+"",true);
            return;
        }
        //3. 判断用户是否重复秒杀
        Map<String,Object> map2 = new HashMap<>();
        map2.put("userId",user.getId());
        map2.put("goodsId",goods.getId());
        List<Order> orders = orderService.finOrderWithUserIdAndGoodsId(map2);
        if(orders.size()!=0){
            System.out.println("已购买过此商品");
            return;
        }
        //4. 减库存 落单 必须在同一事务中
        String orderId = miaoShaService.exec(user, goods);
    }
}

```

### 启动测试

由于启动时会加载数据到redis中 先设置下秒杀商品的库存 然后在启动

![image-20231028074317633](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280743677.png)

![image-20231028074332371](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280743431.png)

![image-20231028074345392](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280743324.png)

页面执行秒杀操作

执行前需要确保订单表中已经被清空

秒杀成功显示抢购中

![image-20231028074900538](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280749721.png)

redis中的库存变为0 因为只写了一个商品进行秒杀

![image-20231028074921201](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280749927.png)

![image-20231028075012755](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280750889.png)

### 前端优化

```
当秒杀成功时 前端还是会显示验证码及抢购按钮 这里应该隐藏
优化步骤
1. 点了按钮就将其隐藏
	在执行秒杀操作的时候
2. 轮询获取秒杀状体时 当秒杀成功时隐藏按钮
```

<img src="https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280802722.png" alt="image-20231028080234675" style="zoom:50%;" />

<img src="https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280757187.png" alt="image-20231028075729077" style="zoom:50%;" />

#### 完整代码 - script

```vue
<template>
  <el-container>
    <el-header height="120px">
      <seckill-head></seckill-head>
    </el-header>
    <el-main>
      <el-form label-position="left" label-width="150px">
        <p class="goods_title">商品详情</p>
        <el-form-item label="商品名称">
            {{miaoShaGoods.goods.name}}
        </el-form-item>
        <el-form-item label="商品图片">
          <img :src="getSrcUrl(miaoShaGoods.goods.image)" style="width: 250px;">
        </el-form-item>
        <el-form-item label="商品原价">
          {{"￥"+miaoShaGoods.goods.price}}
        </el-form-item>
        <el-form-item label="抢购价">
          {{"￥"+miaoShaGoods.price}}
        </el-form-item>
        <el-form-item label="库存数量">
          {{miaoShaGoods.stock}}
        </el-form-item>
        <el-form-item label="抢购开始时间">
          {{miaoShaGoods.startTime}}
          <span v-show="miaoShaGoods.miaoShaStatus==0">抢购倒计时 {{miaoShaGoods.remainBeginSecond}} s</span>
          <span v-show="miaoShaGoods.miaoShaStatus==1">抢购进行中</span>
          <span v-show="miaoShaGoods.miaoShaStatus==2">抢购结束</span>
        </el-form-item>
        <el-form-item label="抢购结束时间">
          {{miaoShaGoods.endTime}}
        </el-form-item>
      </el-form>
      <img :src="verifyCodeImgSrc" id="verifyImg" width="80" height="32" @click="refreshVerifyCode" v-show="miaoShaGoods.miaoShaStatus==1" />
      <input v-show="miaoShaGoods.miaoShaStatus==1"
             style="width: 60px;height: 23px;padding: 4px;border: 1px solid gray;"
             type="text"  id="verifyCode" v-model="verifyCode">
      <el-button type="success" @click="exec_miaosha()"
                 v-show="miaoShaGoods.miaoShaStatus==1" size="small">立即抢购</el-button>
      <p style="color: red">{{miaoShaResult}}</p>
    </el-main>
    <el-footer class="elfooter">
      <seckill-footer></seckill-footer>
    </el-footer>
  </el-container>
</template>

<script>
import SeckillHead from './commons/Head'
import SeckillFooter from './commons/Footer'
import axios from "axios";
import {genServerUrl} from '../config/sys'

export default {
  name: 'Detail',
  components:{
    SeckillHead, // 添加引入的组件 否则上边无法使用
    SeckillFooter
  },
  data(){
    return{
      miaoShaGoods:{
        goods:{
          name:'',
          price:'',
          image:'default.jpg'

        }
      },
      verifyCodeImgSrc: '',
      verifyCode: '',
      miaoShaResult: ''
    }
  },
  methods:{
    getSrcUrl(t){
      return genServerUrl('image/'+t);
    },
    // 刷新验证码
    refreshVerifyCode(){
      let imgUrl = genServerUrl("verifyCode/get");
      // 添加timestamp参数的目的是为了防止浏览器缓存
      this.verifyCodeImgSrc=imgUrl+"?miaoShaGoodsId="+this.$route.params.id+
        "&token="+window.sessionStorage.getItem("token")+"&timestamp="+new Date();
    },
    // 执行秒杀
    exec_miaosha(){
      if(this.verifyCode==''){
        alert("请填写验证码结果");
        return;
      }
      let url=genServerUrl("miaoSha/exec");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          // 注意 key值必须与服务端接收数据的参数名保持一致
          goodsId:this.$route.params.id,
          verifyCode: this.verifyCode
        }
      }).then(response=>{
        let data = response.data;
        this.miaoShaGoods.miaoShaStatus=4;// 隐藏验证码 按钮
        if(data.code!=0){
          alert(data.msg);
          this.miaoShaResult=data.msg;// 给出提示
        }else{// 下单成功
          //alert(data.orderId);
          this.miaoShaGoods.miaoShaStatus=4;
          this.miaoShaResult=data.msg;
          this.getMiaoShaResult(this.$route.params.id);// 轮询获取秒杀状态
        }
      }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    },
    // 获取秒杀状态
    getMiaoShaResult(miaoShaGoodsId){
      let url=genServerUrl("miaoSha/result");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;
      axios.get(url,{
        params:{
          // 注意 key值必须与服务端接收数据的参数名保持一致
          goodsId:this.$route.params.id
        }
      }).then(response=>{
        let data=response.data;
        //alert("res=>"+data.code);
        if(data.code!=0){
          alert(data.msg);
        }else{
          let result=data.result;
          // alert("res = >"+result);
          if(result=="0"){  // 排队中，继续轮询
            setTimeout(() =>{// 箭头符号为全局的 function() 局部的 只会执行一次
              this.getMiaoShaResult(miaoShaGoodsId)
            },50);
          }else if(result=="-1"){
            this.miaoShaResult="秒杀失败";
          }else{
            alert("秒杀成功,订单号："+result);
            this.miaoShaGoods.miaoShaStatus=4;// 隐藏按钮和验证码
          }
        }
      }).catch(error=>{
        alert(error+"-请联系管理员");
      })
    },
    // 秒杀状态及动态倒计时显示
    countDown(){
      let timeout;
      let res = this.miaoShaGoods.remainBeginSecond;
      if(res==0){
        // 秒杀进行中
        this.miaoShaGoods.miaoShaStatus=1;
        // 销毁倒计时
        if(timeout){
          clearTimeout(timeout);
        }
        // 秒杀进行中的时候显示验证码
        let imgUrl = genServerUrl("verifyCode/get");
        this.verifyCodeImgSrc=imgUrl+"?miaoShaGoodsId="+this.$route.params.id+"&token="+window.sessionStorage.getItem("token");
      }else if(res>0){
        // 秒杀还没开始 显示倒计时
        timeout = setTimeout(()=>{
          this.miaoShaGoods.remainBeginSecond = this.miaoShaGoods.remainBeginSecond-1;
          this.countDown();// 每秒调用一次
        },1000)
      }else{
        // 秒杀结束 这里不会动态隐藏秒杀按钮
        this.miaoShaGoods.miaoShaStatus=2;
      }
    },
    getInfo(){
      let url=genServerUrl("miaoShaGoods/findById");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          id:this.$route.params.id
        }
      })
        .then(response=>{
          console.log(response.data.data);
          this.miaoShaGoods=response.data.data;
          this.countDown();// 显示秒杀状态
          // 动态隐藏秒杀按钮
          if(this.miaoShaGoods.remainEndSecond>0){
            // 秒杀还没结束 需要动态判断等到结束为止
            setTimeout(()=>{
              this.miaoShaGoods.miaoShaStatus=2;
            },this.miaoShaGoods.remainEndSecond*1000)// 按秒执行

          }
        }).catch(error=>{
         alert(error+"-请联系管理员");
      })
    }
  },
  mounted() {
    this.getInfo();// 页面加载完成后执行
  }
}
</script>

<style scoped>
  .elfooter{
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
  }
  .goods_title{
    font-size: 20px;
    font-weight: bold;
  }
</style>

```

![image-20231028080318535](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280803109.png)

## 订单详情页面

秒杀成功后先跳转到订单详情页

### 创建OrderInfo.vue

```vue
<template>
  <el-container>
    <el-header height="120px">
      <seckill-head></seckill-head>
    </el-header>
    <el-main>
      <el-form label-position="left" label-width="150px">
        <p class="goods_title">订单详情</p>
      </el-form>
    </el-main>
    <el-footer class="elfooter">
      <seckill-footer></seckill-footer>
    </el-footer>
  </el-container>
</template>

<script>
import SeckillHead from './commons/Head'
import SeckillFooter from './commons/Footer'
import axios from "axios";
import {genServerUrl} from '../config/sys'

export default {
  name: 'OrderInfo',
  components:{
    SeckillHead, // 添加引入的组件 否则上边无法使用
    SeckillFooter
  },
  data(){

  },
  methods:{
    getSrcUrl(t){
      return genServerUrl('image/'+t);
    },
    getInfo(){
      let url=genServerUrl("miaoShaGoods/findById");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          id:this.$route.params.id
        }
      }).then(response=>{
          console.log(response.data.data);

        }).catch(error=>{
         alert(error+"-请联系管理员");
      })
    }
  },
  mounted() {
    this.getInfo();// 页面加载完成后执行
  }
}
</script>

<style scoped>
  .elfooter{
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
  }
  .goods_title{
    font-size: 20px;
    font-weight: bold;
  }
</style>

```

### 配置路由 

src/route/index.js

```js
import Vue from 'vue'
import Router from 'vue-router'
// 导入Login组件
import Login from '@/components/Login'

import Main from '@/components/Main'
import Detail from '@/components/Detail'
import OrderInfo from '@/components/OrderInfo'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    // 登录成功后路由到主页面
    {
      path: '/main',
      name: 'Main',
      component: Main
    }
    ,
    // 点击详情时路由到详情页
    {
      path: '/detail/:id',// 跳转页面时传递点击的id值过去
      name: 'Detail',
      component: Detail
    },
    // 秒杀成功跳转到订单信息页面
    {
      path: '/orderInfo/:id',// 跳转页面时传递点击的id值过去
      name: 'OrderInfo',
      component: OrderInfo
    }
  ]
})

```

### Detail.vue中在秒杀成功时做一个跳转

![image-20231028081804478](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280818653.png)

### 测试

![image-20231028081547046](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280815973.png)

### OrderInfo.vue

```vue
<template>
  <el-container>
    <el-header height="120px">
      <seckill-head></seckill-head>
    </el-header>
    <el-main>
      <el-form label-position="left" label-width="150px">
        <p class="goods_title">订单详情</p>
        <el-form-item label="订单编号">
          {{this.order.id}}
        </el-form-item>
        <el-form-item label="商品名称">
          {{this.order.miaoShaGoods.goods.name}}
        </el-form-item>
        <el-form-item label="商品图片">
          <img :src="getSrcUrl(this.order.miaoShaGoods.goods.image)" style="width: 250px;">
        </el-form-item>
        <el-form-item label="订单价格">
          {{"￥"+this.order.totalPrice}}
        </el-form-item>
        <el-form-item label="订单状态">
          <span v-show="this.order.status==0">待支付</span>
          <span v-show="this.order.status==1">已支付</span>
          <span v-show="this.order.status==2">待发货</span>
          <span v-show="this.order.status==3">已收获</span>

        </el-form-item>
        <el-form-item label="收货人">
          {{this.order.user.name}}
        </el-form-item>
        <el-form-item label="联系电话">
          {{this.order.user.phoneNumber}}
        </el-form-item>
        <el-form-item label="收获地址">
          {{this.order.user.address}}
        </el-form-item>
      </el-form>
      <el-button type="success" @click="exec_miaosha()" size="small">立即支付</el-button>
    </el-main>
    <el-footer class="elfooter">
      <seckill-footer></seckill-footer>
    </el-footer>
  </el-container>
</template>

<script>
import SeckillHead from './commons/Head'
import SeckillFooter from './commons/Footer'
import axios from "axios";
import {genServerUrl} from '../config/sys'

export default {
  name: 'OrderInfo',
  components:{
    SeckillHead, // 添加引入的组件 否则上边无法使用
    SeckillFooter
  },
  data(){
    return{
      order:{
        miaoShaGoods:{
          goods: {
            name:'',
            image:'default.jpg'
          }
        },
        user:{
          name:'',
          phoneNumber:'',
          address:''
        }
      }

    }
  },
  methods:{
    getSrcUrl(t){
      return genServerUrl('image/'+t);
    },
    getInfo(){
      let url=genServerUrl("order/detail");
      let token=window.sessionStorage.getItem("token");
      axios.defaults.headers.common['token']=token;

      axios.get(url,{
        params:{
          id:this.$route.params.id
        }
      }).then(response=>{
          console.log(response.data.data);
          this.order=response.data.data;
        }).catch(error=>{
         alert(error+"-请联系管理员");
      })
    }
  },
  mounted() {
    this.getInfo();// 页面加载完成后执行
  }
}
</script>

<style scoped>
  .elfooter{
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
  }
  .goods_title{
    font-size: 20px;
    font-weight: bold;
  }
</style>

```

测试

![image-20231028084824691](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280848895.png)



## 分布式拆分

```
按功能进行拆分服务 后面的数字表示每个模块的端口
1. seckill-common 公共模块 存放一些工具类、实体类等
2. seckill-user-81 用户模块 负责登录验证之类的操作
3. seckill-order-8082 订单模块 秒杀商品的订单信息
4. seckill-goods-8081 商品模块 
5. seckill-gateway-80 网关
6. seckill-exec-8083 秒杀执行操作模块
```

![image-20231028092726190](C:\Users\etjav\AppData\Roaming\Typora\typora-user-images\image-20231028092726190.png)

### 新建父项目

![image-20231028092700128](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310280927863.png)

#### 添加依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <modules>
        <module>seckill-common</module>
    </modules>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.et</groupId>
    <artifactId>springcloud-seckill-sys</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>pom</packaging>
    <name>springcloud-seckill-sys</name>
    <description>springcloud-seckill-sys</description>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <druid.version>1.1.10</druid.version>
        <spring-cloud.version>Hoxton.SR8</spring-cloud.version>
        <springboot.version>2.3.2.RELEASE</springboot.version>
        <springcloudalibaba.version>2.2.4.RELEASE</springcloudalibaba.version>
        <mybatis-plus.version>3.3.2</mybatis-plus.version>
        <fastjson.version>1.2.35</fastjson.version>
        <commons-lang3.version>3.6</commons-lang3.version>
        <seckill-common.version>0.0.1-SNAPSHOT</seckill-common.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
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
            <!-- 连接池 -->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>${druid.version}</version>
            </dependency>
            <!-- mybatis-plus -->
            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-boot-starter</artifactId>
                <version>${mybatis-plus.version}</version>
            </dependency>

            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>fastjson</artifactId>
                <version>${fastjson.version}</version>
            </dependency>

            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-lang3</artifactId>
                <version>${commons-lang3.version}</version>
            </dependency>

            <dependency>
                <groupId>com.et</groupId>
                <artifactId>seckill-common</artifactId>
                <version>${seckill-common.version}</version>
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

### 公共模块

seckill-comm

#### 新建模块

#### 添加依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>springcloud-seckill-sys</artifactId>
        <groupId>com.et</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>seckill-common</artifactId>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- 服务注册/发现-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <!-- 配置中心 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>

        <!-- spring boot redis 缓存引入 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <!-- lettuce pool 缓存连接池 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
        </dependency>

        <dependency>
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
        </dependency>

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </dependency>

        <!-- rabbmitmq -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
    </dependencies>

</project>
```

#### 迁移所有实体类

![image-20231028140712965](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281407345.png)

#### 迁移配置类、常量类、全局异常处理、工具类、拦截器

<img src="https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281410130.png" alt="image-20231028141051827" style="zoom:50%;" />

![image-20231028191657901](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281916713.png)

### 创建用户模块

seckill-user-81

![image-20231028141423599](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281414790.png)

#### 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>com.et</groupId>
        <artifactId>seckill-common</artifactId>
    </dependency>
</dependencies>
```

#### 添加配置文件

```yaml
server:
  port: 81
  servlet:
    context-path: /

spring:
  rabbitmq:
    host: 192.168.199.106
    port: 5672
    username: admin
    password: admin
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_seckill?serverTimezone=Asia/Shanghai
    username: root
    password: Karen@1234
  redis:  # redis配置
    host: 192.168.199.106 # IP
    port: 6379  # 端口
    password:  # 密码
    connect-timeout: 10s  # 连接超时时间
    lettuce: # lettuce redis客户端配置
      pool:  # 连接池配置
        max-active: 8  # 连接池最大连接数（使用负值表示没有限制） 默认 8
        max-wait: 200s  # 连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
        max-idle: 8 # 连接池中的最大空闲连接 默认 8
        min-idle: 0 # 连接池中的最小空闲连接 默认 0

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true  # 开启驼峰功能  userName  - >  user_name
    auto-mapping-behavior: full  # 自动mapping映射
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  mapper-locations: classpath:/mapper/*.xml
  type-aliases-package: com.et.entity


```

#### 迁移用户相关的功能

```
controller
    LoginController
    TokenController
mapper
    UserMapper
service
    UserService
	UserServiceImpl
SeckillUserApplication

```

<img src="https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281426971.png" alt="image-20231028142656706" style="zoom:50%;" />

### 创建订单模块

seckill-order-8082

![image-20231028142841392](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281428329.png)

#### 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>com.et</groupId>
        <artifactId>seckill-common</artifactId>
    </dependency>
</dependencies>
```

#### 添加配置文件

```yaml
server:
  port: 8082
  servlet:
    context-path: /

spring:
  rabbitmq:
    host: 192.168.199.106
    port: 5672
    username: admin
    password: admin
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_seckill?serverTimezone=Asia/Shanghai
    username: root
    password: Karen@1234
  redis:  # redis配置
    host: 192.168.199.106 # IP
    port: 6379  # 端口
    password:  # 密码
    connect-timeout: 10s  # 连接超时时间
    lettuce: # lettuce redis客户端配置
      pool:  # 连接池配置
        max-active: 8  # 连接池最大连接数（使用负值表示没有限制） 默认 8
        max-wait: 200s  # 连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
        max-idle: 8 # 连接池中的最大空闲连接 默认 8
        min-idle: 0 # 连接池中的最小空闲连接 默认 0

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true  # 开启驼峰功能  userName  - >  user_name
    auto-mapping-behavior: full  # 自动mapping映射
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  mapper-locations: classpath:/mapper/*.xml
  type-aliases-package: com.et.entity


```

#### 迁移订单相关功能

```
controller
	OrderController
service
	OrderService
	OrderServiceImpl
mapper
	GoodsMapper
	MiaoShaGoodsMapper
	OrderMapper
	UserMapper
```

<img src="https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281437878.png" alt="image-20231028143733534" style="zoom:50%;" />

### 创建商品模块

seckill-goods-8081

![image-20231028144700588](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281447977.png)

#### 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>com.et</groupId>
        <artifactId>seckill-common</artifactId>
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
  rabbitmq:
    host: 192.168.199.106
    port: 5672
    username: admin
    password: admin
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_seckill?serverTimezone=Asia/Shanghai
    username: root
    password: Karen@1234
  redis:  # redis配置
    host: 192.168.199.106 # IP
    port: 6379  # 端口
    password:  # 密码
    connect-timeout: 10s  # 连接超时时间
    lettuce: # lettuce redis客户端配置
      pool:  # 连接池配置
        max-active: 8  # 连接池最大连接数（使用负值表示没有限制） 默认 8
        max-wait: 200s  # 连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
        max-idle: 8 # 连接池中的最大空闲连接 默认 8
        min-idle: 0 # 连接池中的最小空闲连接 默认 0

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true  # 开启驼峰功能  userName  - >  user_name
    auto-mapping-behavior: full  # 自动mapping映射
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  mapper-locations: classpath:/mapper/*.xml
  type-aliases-package: com.et.entity


```

#### 迁移商品相关的功能

```
controller
	MiaoShaGoodsController
mapper
	MiaoShaGoodsMapper
service
	MiaoShaGoodsService
starup
	StartupRunner // 项目启动时加载数据到redis
```

![image-20231028152443968](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281524178.png)

### 创建秒杀操作模块

seckill-exec-8083

![image-20231028150608662](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281506861.png)

#### 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>com.et</groupId>
        <artifactId>seckill-common</artifactId>
    </dependency>
</dependencies>
```

#### 添加配置文件

```yaml
server:
  port: 8083
  servlet:
    context-path: /

spring:
  rabbitmq:
    host: 192.168.199.106
    port: 5672
    username: admin
    password: admin
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_seckill?serverTimezone=Asia/Shanghai
    username: root
    password: Karen@1234
  redis:  # redis配置
    host: 192.168.199.106 # IP
    port: 6379  # 端口
    password:  # 密码
    connect-timeout: 10s  # 连接超时时间
    lettuce: # lettuce redis客户端配置
      pool:  # 连接池配置
        max-active: 8  # 连接池最大连接数（使用负值表示没有限制） 默认 8
        max-wait: 200s  # 连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
        max-idle: 8 # 连接池中的最大空闲连接 默认 8
        min-idle: 0 # 连接池中的最小空闲连接 默认 0

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true  # 开启驼峰功能  userName  - >  user_name
    auto-mapping-behavior: full  # 自动mapping映射
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  mapper-locations: classpath:/mapper/*.xml
  type-aliases-package: com.et.entity


```

#### 迁移秒杀操作相关的功能

```
controller
	LoginContrller
    MiaoShaController
    MiaoShaGoodsController
    OrderController
    VerifyCodeController
mapper
	GoodsMapper
    MiaoShaGoodsMapper
    OrderMapper
    UserMapper
service
	MiaoShaGoodsService
    MiaoShaService
    OrderService
    UserService
rabbitmq
	MQReceiver
	MQSender

```



![image-20231028151115341](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281511634.png)

## Nacos服务注册与发现

### 单个Nacos

这里采用本地 直接启动即可

### 修改服务配置文件

每个服务的配置文件添加如下配置

注意 服务名称不能相同 即 spring.application.name

```yaml
spring:
  application:
    name: seckill-goods 
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
```

### 修改启动类

```
每个服务的启动类上需要添加@EnableDiscoveryClient // 发现服务
```

```java
package com.et;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.et.mapper")
@EnableDiscoveryClient // 发现服务
public class SeckillGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillGoodsApplication.class, args);
    }

}

```

### 启动测试

![image-20231028155322697](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281553831.png)

![image-20231028155901243](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281559552.png)

### 登录测试

仅登录 因为其他模块没有关联上 数据查询不到的

![image-20231028160632212](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281606799.png)

## Nacos配置中心

```
每个服务都村子啊相同的配置 例如数据库连接，mybatis整合等 另外这里我们分两部分 一部分是统一不需要变动的放到配置中心 然后在项目启动的时候进行加载
一部分是有变动的 例如服务的名称 端口号 也放到配置中心 单独给每个服务进行配置(可以保留到项目中 也可以完全配置到配置中心)
```

### 抽离配置文件 添加到配置中心

注意：公用配置的内容在同一个组中的 只是DataID不同 

在配置管理 -> 配置列表中进行配置

![image-20231028164216812](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281642717.png)

#### seckill-base组

##### redis.yml

```yaml
spring:
  redis:  # redis配置
    host: 192.168.199.106 # IP
    port: 6379  # 端口
    password:  # 密码
    connect-timeout: 10s  # 连接超时时间
    lettuce: # lettuce redis客户端配置
      pool:  # 连接池配置
        max-active: 8  # 连接池最大连接数（使用负值表示没有限制） 默认 8
        max-wait: 200s  # 连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
        max-idle: 8 # 连接池中的最大空闲连接 默认 8
        min-idle: 0 # 连接池中的最小空闲连接 默认 0
```

![image-20231028163813819](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281638950.png)



##### mysql.yml

```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_seckill?serverTimezone=Asia/Shanghai
    username: root
    password: Karen@1234
```

![image-20231028163857312](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281638618.png)

##### rabbitmq.yml

```yaml
spring:
  rabbitmq:
    host: 192.168.199.106
    port: 5672
    username: admin
    password: admin
```

![image-20231028164057943](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281640044.png)

##### mybatis-plus.yml

```yaml
mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true  # 开启驼峰功能  userName  - >  user_name
    auto-mapping-behavior: full  # 自动mapping映射
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  mapper-locations: classpath:/mapper/*.xml
  type-aliases-package: com.et.entity
```

![image-20231028164134239](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281641592.png)

#### seckill-user组

##### server.yml

```yaml
server:
  port: 81
  servlet:
    context-path: /
```

![image-20231028164443564](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281644717.png)

##### sys.yml

```yaml
spring:
  jackson: 
    time-zone: Asia/Shanghai
  application:
    name: seckill-user
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
```

![image-20231028164524978](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281645110.png)

#### seckill-order组

##### server.yml

```yaml
server:
  port: 8082
  servlet:
    context-path: /
```

![image-20231028164604581](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281646054.png)

##### sys.yml

```yaml
spring:
  jackson: 
    time-zone: Asia/Shanghai
  application:
    name: seckill-order
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
```

![image-20231028164634623](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281646750.png)

#### seckill-goods组

##### server.yml

```yaml
server:
  port: 8081
  servlet:
    context-path: /
```

##### sys.yml

```yaml
spring:
  jackson: 
    time-zone: Asia/Shanghai
  application:
    name: seckill-goods
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
```

#### seckill-exec组

##### server.yml

```yaml
server:
  port: 8083
  servlet:
    context-path: /
```

![image-20231028164826803](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281648027.png)

##### sys.yml

```yaml
spring:
  jackson: 
    time-zone: Asia/Shanghai
  application:
    name: seckill-exec
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
```

![image-20231028164857715](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281648834.png)

### 项目配置调整

```
使用bootstrap.properties配置文件
	其加载的优先级高于application 它会在项目启动时就去加载里面的配置
注意：
	服务名称是不同的，除此外 每个模块对应配置中心不同的组
	添加了bootstrap.properties配置后 原有的application.yml就可以删除了
```

#### seckill-user模块

```properties
spring.application.name=seckill-user
spring.cloud.nacos.config.server-addr=127.0.0.1:8848


spring.cloud.nacos.config.extension-configs[0].data-id=server.yml
spring.cloud.nacos.config.extension-configs[0].group=seckill-user
spring.cloud.nacos.config.extension-configs[0].refresh=true

spring.cloud.nacos.config.extension-configs[1].data-id=sys.yml
spring.cloud.nacos.config.extension-configs[1].group=seckill-user
spring.cloud.nacos.config.extension-configs[1].refresh=true


spring.cloud.nacos.config.extension-configs[2].data-id=redis.yml
spring.cloud.nacos.config.extension-configs[2].group=seckill-base
spring.cloud.nacos.config.extension-configs[2].refresh=true

spring.cloud.nacos.config.extension-configs[3].data-id=mysql.yml
spring.cloud.nacos.config.extension-configs[3].group=seckill-base
spring.cloud.nacos.config.extension-configs[3].refresh=true

spring.cloud.nacos.config.extension-configs[4].data-id=rabbitmq.yml
spring.cloud.nacos.config.extension-configs[4].group=seckill-base
spring.cloud.nacos.config.extension-configs[4].refresh=true

spring.cloud.nacos.config.extension-configs[5].data-id=mybatis-plus.yml
spring.cloud.nacos.config.extension-configs[5].group=seckill-base
spring.cloud.nacos.config.extension-configs[5].refresh=true
```

#### seckill-order模块

```properties
spring.application.name=seckill-order
spring.cloud.nacos.config.server-addr=127.0.0.1:8848

spring.cloud.nacos.config.extension-configs[0].data-id=server.yml
spring.cloud.nacos.config.extension-configs[0].group=seckill-order
spring.cloud.nacos.config.extension-configs[0].refresh=true

spring.cloud.nacos.config.extension-configs[1].data-id=sys.yml
spring.cloud.nacos.config.extension-configs[1].group=seckill-order
spring.cloud.nacos.config.extension-configs[1].refresh=true

spring.cloud.nacos.config.extension-configs[2].data-id=redis.yml
spring.cloud.nacos.config.extension-configs[2].group=seckill-base
spring.cloud.nacos.config.extension-configs[2].refresh=true

spring.cloud.nacos.config.extension-configs[3].data-id=mysql.yml
spring.cloud.nacos.config.extension-configs[3].group=seckill-base
spring.cloud.nacos.config.extension-configs[3].refresh=true

spring.cloud.nacos.config.extension-configs[4].data-id=rabbitmq.yml
spring.cloud.nacos.config.extension-configs[4].group=seckill-base
spring.cloud.nacos.config.extension-configs[4].refresh=true

spring.cloud.nacos.config.extension-configs[5].data-id=mybatis-plus.yml
spring.cloud.nacos.config.extension-configs[5].group=seckill-base
spring.cloud.nacos.config.extension-configs[5].refresh=true
```

#### seckill-goods模块

```properties
spring.application.name=seckill-goods
spring.cloud.nacos.config.server-addr=127.0.0.1:8848

spring.cloud.nacos.config.extension-configs[0].data-id=server.yml
spring.cloud.nacos.config.extension-configs[0].group=seckill-goods
spring.cloud.nacos.config.extension-configs[0].refresh=true

spring.cloud.nacos.config.extension-configs[1].data-id=sys.yml
spring.cloud.nacos.config.extension-configs[1].group=seckill-goods
spring.cloud.nacos.config.extension-configs[1].refresh=true

spring.cloud.nacos.config.extension-configs[2].data-id=redis.yml
spring.cloud.nacos.config.extension-configs[2].group=seckill-base
spring.cloud.nacos.config.extension-configs[2].refresh=true

spring.cloud.nacos.config.extension-configs[3].data-id=mysql.yml
spring.cloud.nacos.config.extension-configs[3].group=seckill-base
spring.cloud.nacos.config.extension-configs[3].refresh=true

spring.cloud.nacos.config.extension-configs[4].data-id=rabbitmq.yml
spring.cloud.nacos.config.extension-configs[4].group=seckill-base
spring.cloud.nacos.config.extension-configs[4].refresh=true

spring.cloud.nacos.config.extension-configs[5].data-id=mybatis-plus.yml
spring.cloud.nacos.config.extension-configs[5].group=seckill-base
spring.cloud.nacos.config.extension-configs[5].refresh=true
```

#### seckill-exec模块

```properties
spring.application.name=seckill-goods
spring.cloud.nacos.config.server-addr=127.0.0.1:8848

spring.cloud.nacos.config.extension-configs[0].data-id=server.yml
spring.cloud.nacos.config.extension-configs[0].group=seckill-goods
spring.cloud.nacos.config.extension-configs[0].refresh=true

spring.cloud.nacos.config.extension-configs[1].data-id=sys.yml
spring.cloud.nacos.config.extension-configs[1].group=seckill-goods
spring.cloud.nacos.config.extension-configs[1].refresh=true

spring.cloud.nacos.config.extension-configs[2].data-id=redis.yml
spring.cloud.nacos.config.extension-configs[2].group=seckill-base
spring.cloud.nacos.config.extension-configs[2].refresh=true

spring.cloud.nacos.config.extension-configs[3].data-id=mysql.yml
spring.cloud.nacos.config.extension-configs[3].group=seckill-base
spring.cloud.nacos.config.extension-configs[3].refresh=true

spring.cloud.nacos.config.extension-configs[4].data-id=rabbitmq.yml
spring.cloud.nacos.config.extension-configs[4].group=seckill-base
spring.cloud.nacos.config.extension-configs[4].refresh=true

spring.cloud.nacos.config.extension-configs[5].data-id=mybatis-plus.yml
spring.cloud.nacos.config.extension-configs[5].group=seckill-base
spring.cloud.nacos.config.extension-configs[5].refresh=true
```

## 前端访问调整·1

```
由于后端采用的是微服务 那么前端在调用时 需要调用不同的服务 因此这里的访问需要根据不同模块区分调用
1. 调整封装的访问地址 根据不同模块封装不同的访问地址
2. 后边添加了网关后需要再次做出调整 
```

### sys.js中添加根据模块请求不同地址

```js
// 服务拆分后 需要根据不同业务调用不同夫妇
export function genServerUrl(path,model){
  /* path 具体的服务端请求接口路径 */
  if(model=="user"){ // 用户模块
    return "http://localhost:81/user/"+path;
  }
}

```

### Login.vue组件修改

登录时获取请求地址的地方添加指定的请求模块名称

![image-20231028183301420](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281833984.png)

源码

```vue
methods: {
    submit () {
      if (this.username.trim() == null || this.username === '') {
        this.errorInfo = '用户名不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      if (this.password.trim() == null || this.password === '') {
        this.errorInfo = '密码不能为空'
        return
      } else {
        this.errorInfo = ''
      }
      let salt = "3dfsty";// 要与服务端设置的前端salt保持一致
      let 啊 = genServerUrl("login","user");
      // 发送登录请求
      axios.post(
        啊,
        {
          'username': this.username,
          'password': hex_md5(salt+this.password)
        })
        .then(response => {
          let data = response.data
          if (data.code === 0) {
            console.log(data)
            // 保存token信息
            // 保存在本地 永久有效
            //window.localStorage.setItem("token",data.msg);
            // 关闭浏览器失效
            window.sessionStorage.setItem("token",data.msg);
            // 跳转到main主页面
            this.$router.replace("/main");
          } else if (data.code === 500) {
            this.errorInfo = data.msg
          }
        }).catch(error => {
          this.errorInfo = error
        })
    }
  }
```

### 后台 common模块修改

common模块中有请求拦截器 之前拦截的是/login 现在添加了user的前缀 这里需要添加上

![image-20231028183517027](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281835808.png)

源码

```java
@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(sysInterceptor())
        .addPathPatterns("/**") // 对所有请求拦截
        .excludePathPatterns("/user/login","/logout","/image","/verifyCode/get"); // 不进行拦截的请求 多个之间使用逗号隔开 也可以直接传入一个String[]
}
```

### 测试登录

![image-20231028183617333](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281836586.png)

### 异常处理

```
在启动项服务端项目时如果出现Consider defining a bean named 'redisTemplate2' in your configuration.异常 是因为common模块中少迁移了RedisConfig配置类 迁移过去就可以了
```

### 前端其他请求封装

```
所有请求都按照上述方式进行封装
需要注意的是不同的请求对应不同的后端模块

1. user模块 端口81
	用户登录 user/login
	token续期
2. goods模块 端口 8081
	miaoShaGoods相关的所有请求都在这个模块下
	图片请求 image
3. order模块 端口8082
	订单相关的请求 ordr
4. exec模块 端口8083
	执行秒杀操作的请求 miaoSha
	验证码请求 verifyCode
	
需要修改的组件
Head.vue 
	token续期
Login.vue
	用户登录
Main.vue
	图片请求
	商品列表请求
Detail.vue
	图片请求
	商品列表请求
	商品详情请求
	验证码请求
	秒杀操作请求
	秒杀结果请求
	秒杀商品请求
OrderInfo2.vue
	图片请求
	订单详情请求
```

#### sys.js

```js
// 服务拆分之前只需要调用同一端口
/* export function genServerUrl(path){
  /!* path 具体的服务端请求接口路径 *!/
  return "http://localhost:80/"+path;
} */

// 服务拆分后 需要根据不同业务调用不同夫妇
export function genServerUrl(path,model){
  /* path 具体的服务端请求接口路径 */
  if(model=="user"){ // 用户模块
    return "http://localhost:81/user/"+path;
  } else if(model=="miaoShaGoods"){
    return "http://localhost:8081/miaoShaGoods/"+path;
  } else if(model=="image"){
    return "http://localhost:8081/"+path;
  } else if(model=="verifyCode"){
    return "http://localhost:8083/verifyCode/"+path;
  } else if(model=="miaoSha"){
    return "http://localhost:8083/miaoSha/"+path;
  } else if(model=="order"){
    return "http://localhost:8082/order/"+path;
  } else if(model=="token"){
    return "http://localhost:81/token/"+path;
  }
}

```

全部修改完成后进行测试

从登录到下单结束

<img src="https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310281942430.png" alt="image-20231028194047740" style="zoom:50%;" />

## openfeign远程调用

```
一个服务的执行需要调用另外一个服务时 不应该使用包含的关系 应该使用远程调用的方式
例如 秒杀模块中获取秒杀结果的时候 调用的是order相关的信息，这里应该调用order模块中的方法 而不是自己在实现一个查询订单的方法

使用openfeign 需要导入依赖
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>

定义远程调用接口
启动类上开启openfeign的客户端
@EnableFeignClient(basePackages="com.et.feign")
```

### 实现远程调用

```
以秒杀后获取秒杀结果为例 实现下远程调用
1、 秒杀模块中的获取秒杀结果的操作放到order模块的servie中
2、 
```

#### 将秒杀模块中获取秒杀结果集的方法放到order中

![image-20231028200833215](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310282008346.png)

orderService接口实现

```java
@Override
public String getMiaoShaResult(Integer userId, Integer goodsId) {
    Map<String,Object> map = new HashMap<>();
    map.put("userId",userId);
    map.put("goodsId",goodsId);
    List<Order> orderList = orderMapper.finOrderWithUserIdAndGoodsId(map);
    if(orderList.size()!=0){
        return orderList.get(0).getId();
    }else{
        // TODO 查询商品是否秒杀完成 从Redis中判断商品是否秒杀完成
        boolean isOver = (boolean)redisUtil.get(RedisConstant.REDIS_GOODS_MIAOSHA_OVER_PREFIX,goodsId+"");
        if(isOver){
            return "-1";// 没有库存了
        }else{
            return "0";// 排队中
        }
    }
}
```

#### 在order模块中添加controller请求

```
OrderController中添加获取秒杀结果的请求
需要注意的是 请求的参数需要使用@RequestParam方式接收 否则远程调用时接收不到参数

```

OrderController

```java
/**
     * 查询秒杀结果 - 远程接口调用(openfeign)
     * @param userId
     * @param goodsId
     * @return
     */
@RequestMapping("/getMiaoShaResult")
public String getMiaoShaResult(@RequestParam("userId") Integer userId, @RequestParam("goodsId") Integer goodsId){
    System.out.println("获取秒杀结果："+userId+" ,"+goodsId);
    return orderService.getMiaoShaResult(userId,goodsId);
}
```

#### 秒杀模块中新建feign接口

feign包下新建OrderFeignService

OrderFeignService接口中需要添加要调用的远程的方法 必须要与远程接口中的保持一致

```java
package com.et.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 远程调用客户端
 */
// seckill-order 为被调用的服务的名称
@FeignClient("seckill-order")
public interface OrderFeignService {
    /**
     * 查询秒杀结果
     * @param userId
     * @param goodsId
     * @return
     */
    // 请求路径 调用的哪个模块 就写具体模块的请求地址
    @RequestMapping("/order/getMiaoShaResult")
    public String getMiaoShaResult(@RequestParam("userId") Integer userId, @RequestParam("goodsId") Integer goodsId);
}

```

#### 秒杀模块中 启动类添加开启Feign客户端

```java
package com.et;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.et.mapper")
@EnableDiscoveryClient // 发现服务
@EnableFeignClients("com.et.feign")
public class SeckillExecApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillExecApplication.class, args);
    }

}

```

#### 修改秒杀模块Controller

```
在秒杀模块的controller中 获取秒杀结果的地方 改为远程调用
```

```java
@RequestMapping("/result")
public R result(HttpServletRequest request,Integer goodsId){
    //1. 根据token获取用户信息
    String token = request.getHeader("token");
    User user = (User) redisUtil.get(RedisConstant.REDIS_TOKEN_PREFIX,token);
    System.out.println("token="+token+", "+user);
    //        String result = miaoShaService.getMiaoShaResult(user.getId(),goodsId);
    // 远程调用 - 调用Order模块中的方法
    String result = orderFeignService.getMiaoShaResult(user.getId(),goodsId);
    Map<String,Object> map = new HashMap<>();
    map.put("result",result);
    return R.ok(map);
}
```

#### 修改Common模块中的鉴权

```
秒杀模块中的远程调用 因为是内部调用 不需要鉴权(鉴权的话 需要额外添加header信息) 因此 在common模块中添加放开该请求的鉴权即可
```

#### 启动测试

需要启动所有的服务 然后进行测试

![image-20231028203727405](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310282037543.png)



![image-20231028203826035](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310282038192.png)

## 多实例集群

```
当一个服务的压力较大的时候 我们可以将其分为多个相同的服务同时提供服务

Nacos要求 一个服务多个实例时 除端口外 其余必须保持一致 - 服务名称也必须相同

```

### 新建seckill-order2模块

与seckill-order模块完全相同即可 只bootstrap.properties里面获取端口配置改为server2.yml即可

![image-20231028211231209](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310282112819.png)

### Nacos配置中心

添加一个新的配置 server2.yml

```yaml
server:
  port: 8085
  servlet:
    context-path: /
```

![image-20231028211052376](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310282110521.png)

![image-20231028211429955](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310282114003.png)

### 测试

为了查看请求走的是哪个服务 我们在获取秒杀结果的controller中添加不同的打印语句 

![image-20231028211602796](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310282116073.png)

![image-20231028211614776](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310282116910.png)

![image-20231028211809055](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310282118127.png)

![image-20231028211913758](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310282119909.png)

## GateWay网关限流

```
配置gateway网关时 需要注意 去掉spring-boot-starter-web依赖 因为gateway中已经存在一个了 如果带着会启动报org.springframework.http.code.ServerCodecConfigure that could not be found

gateway中可以配置跨域请求，我们在项目中就可以不用在配置了
因为所有的请求都走网关 因此 前端请求都改为相同端口即可
```

### 创建gateway模块

![image-20231028213342559](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310282133543.png)

### 添加依赖

```xml
<dependencies>
    <!-- 服务注册/发现-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <!-- 配置中心 -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>

    <!-- spring boot redis 缓存引入 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <!-- lettuce pool 缓存连接池 -->
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-pool2</artifactId>
    </dependency>
</dependencies>
```

### 添加配置文件

```yaml
server:
  port: 3001
  servlet:
      context-path: "/"

spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_seckill?serverTimezone=Asia/Shanghai
    username: root
    password: Karen@1234
  redis:  # redis配置
    host: 192.168.199.106 # IP
    port: 6379  # 端口
    password:  # 密码
    connect-timeout: 10s  # 连接超时时间
    lettuce: # lettuce redis客户端配置
      pool:  # 连接池配置
        max-active: 8  # 连接池最大连接数（使用负值表示没有限制） 默认 8
        max-wait: 200s  # 连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
        max-idle: 8 # 连接池中的最大空闲连接 默认 8
        min-idle: 0 # 连接池中的最小空闲连接 默认 0
  jackson:
    time-zone: Asia/Shanghai
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
    gateway:
      globalcors:
        cors-configurations:
          '[/**]':
              allowCredentials: true
              allowedHeaders: "*"
              allowedOrigins: "*"
              allowedMethods: "*"
      routes:
        - id: user_router
          uri: lb://seckill-user
          predicates:
            - Path=/user/**

        - id: goods_router
          uri: lb://seckill-goods
          predicates:
            - Path=/goods/** , /image/**

        - id: miaoShaGoods_router
          uri: lb://seckill-goods
          predicates:
            - Path=/miaoShaGoods/**

        - id: miaosha_router
          uri: lb://seckill-exec
          predicates:
            - Path=/miaoSha/**, /verifyCode/**
          filters:
            - name: RequestRateLimiter  # 限流过滤器
              args:
                redis-rate-limiter.replenishRate: 1   # 令牌桶每秒填充速率
                redis-rate-limiter.burstCapacity: 2   # 令牌桶总容量
                redis-rate-limiter.requestedTokens: 1  # 一个请求需要消费的令牌数
                key-resolver: "#{@pathKeyResolver}"

        - id: order_router
          uri: lb://seckill-order
          predicates:
            - Path=/order/**


  application:
    name: seckill-gateway
```



### 创建启动类

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SeckillGateWayApp {

    public static void main(String[] args) {
        SpringApplication.run(SeckillGateWayApp.class,args);
    }
}

```

### 网关配置类

```java
package com.et.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * 网关配置类 - 限流规则配置类
 */
@Configuration
public class KeyResolverConfiguration {

    @Bean
    public KeyResolver pathKeyResolver(){

        /*return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                return Mono.just(exchange.getRequest().getURI().getPath());
            }
        };*/
        return exchange -> Mono.just(exchange.getRequest().getURI().getPath());
    }
}

```

### 前端配置

我们上边配置了一个前端访问调整1 这里因为统一走网关 因此在将端口改为统一的

其实也可以把之前的前端业务请求恢复回去 只是修改下访问的端口即可

sys.js

```js
// 服务拆分之前只需要调用同一端口
/* export function genServerUrl(path){
  /!* path 具体的服务端请求接口路径 *!/
  return "http://localhost:80/"+path;
} */

// 服务拆分后 需要根据不同业务调用不同服务 - 配置网关之前
/* export function genServerUrl(path,model){
  /!* path 具体的服务端请求接口路径 *!/
  if(model=="user"){ // 用户模块
    return "http://localhost:81/user/"+path;
  } else if(model=="miaoShaGoods"){
    return "http://localhost:8081/miaoShaGoods/"+path;
  } else if(model=="image"){
    return "http://localhost:8081/"+path;
  } else if(model=="verifyCode"){
    return "http://localhost:8083/verifyCode/"+path;
  } else if(model=="miaoSha"){
    return "http://localhost:8083/miaoSha/"+path;
  } else if(model=="order"){
    return "http://localhost:8082/order/"+path;
  } else if(model=="token"){
    return "http://localhost:81/token/"+path;
  } */

// 配置网关之后 - 所有请求都需要走网关 因此端口定义为统一的 3001
export function genServerUrl(path,model){
  /* path 具体的服务端请求接口路径 */
  if(model=="user"){ // 用户模块
    return "http://localhost:3001/user/"+path;
  } else if(model=="miaoShaGoods"){
    return "http://localhost:3001/miaoShaGoods/"+path;
  } else if(model=="image"){
    return "http://localhost:3001/"+path;
  } else if(model=="verifyCode"){
    return "http://localhost:3001/verifyCode/"+path;
  } else if(model=="miaoSha"){
    return "http://localhost:3001/miaoSha/"+path;
  } else if(model=="order"){
    return "http://localhost:3001/order/"+path;
  } else if(model=="token"){
    return "http://localhost:3001/token/"+path;
  }
}

```

### 启动测试

![image-20231028214241683](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310282142157.png)

![image-20231028215043073](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310282150230.png)
