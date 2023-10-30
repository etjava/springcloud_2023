# Springboot 快速入门

## 创建Springboot工程

![image-20230929065904351](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309290700209.png)

![image-20230929065923410](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309290700332.png)

## HelloWorld

新建完项目后即可直接通过springboot的启动类进行启动

```java
package com.et;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springboot01Application {

    public static void main(String[] args) {
        SpringApplication.run(Springboot01Application.class, args);
    }

}

```

编写一个返回helloworld的controller

```java
package com.et.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {


    @RequestMapping("/hello")
    public String hello(){
        return "hello world";
    }
}

```



![image-20230929070348957](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309290703133.png)

## springboot的配置文件

### 内置属性、自定义属性、ConfigurationProperties配置

```xml
server.port=8080
server.servlet.context-path=/hello

#自定义属性
helloworld=my first springboot project
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/db_springboot
jdbc.username=root
jdbc.password=123456
```

### controller中获取springboot配置文件中的自定义属性

```java
package com.et.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @Value("${helloworld}")
    private String hello;

    // 获取springboot配置文件中的自定义属性
    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    @RequestMapping("/hello")
    public String hello(){
        return hello;
    }

    @RequestMapping("/show")
    public String show(){
        return jdbcDriver+"\n"+jdbcUrl+'\n'+jdbcUsername+"\n"+jdbcPassword;
    }
}

```

![image-20230929071413022](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309290714065.png)

### @ConfigurationProperties配置注入属性

#### springboot.application

```xml
server.port=8080
server.servlet.context-path=/hello

#自定义属性
helloworld=my first springboot project
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/db_springboot
jdbc.username=root
jdbc.password=123456

mysql.jdbcDriver=com.mysql.jdbc.Driver
mysql.jdbcUrl=jdbc:mysql://localhost:3306/db_springboot
mysql.jdbcUsername=root
mysql.jdbcPassword=123456

```

#### MySqlConfiguration

使用ConfigurationProperties 需要引入对应的依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```



```java
package com.et.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mysql")// 指定前缀
@Data
public class MySqlConfiguration {
    private String jdbcUrl;
    private String jdbcDriver;
    private String jdbcUsername;
    private String jdbcPassword;
}

```

#### controlelr中测试

```java
package com.et.controller;

import com.et.model.MySqlConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @Value("${helloworld}")
    private String hello;

    // 获取springboot配置文件中的自定义属性
    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Autowired
    private MySqlConfiguration mySqlConfiguration;

    @RequestMapping("/hello")
    public String hello(){
        return hello;
    }

    @RequestMapping("/show")
    public String show(){
        return jdbcDriver+"\n"+jdbcUrl+'\n'+jdbcUsername+"\n"+jdbcPassword;
    }

    @RequestMapping("/show2")
    public String show2(){
        return mySqlConfiguration.getJdbcDriver()+"<br/>"+
                mySqlConfiguration.getJdbcUrl()+"<br/>"+
                mySqlConfiguration.getJdbcUsername()+"<br/>"+
                mySqlConfiguration.getJdbcPassword()+"<br/>";
    }
}

```

![image-20230929072958647](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309290729650.png)

## springboot的springmvc支持

```
@RequestMapping映射配置
@Controller处理http请求
@RestController处理ajax请求
@PathVariable获取url中参数
@RequestParam 获取请求参数

RestController和Controller区别
相同点： 都是用来标注当前类是控制器
不同： RestController相当于在方法上添加了RequestBody
RestController用于请求接口 返回json格式数据
Controller通常是用于返回前端页面
```

### Controller

添加thymeleaf支持

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

application.properties

```java
#是否开启缓存，默认true
spring.thymeleaf.cache=true

#检查模板是否存在，默认true
spring.thymeleaf.check-template=true

#检查模板位置是否存在，默认true
spring.thymeleaf.check-template-location=true

#文件编码
spring.thymeleaf.encoding=UTF-8

#模板文件位置
spring.thymeleaf.prefix=classpath:/templates/

#Content-type配置
spring.thymeleaf.servlet.content-type=text/html

#模板文件后缀
spring.thymeleaf.suffix=.html

server.port=8099

```



新建实体类

```java
package com.et.model;

/**
 * @author user1
 */
public class Book
{
    private int id;
    private String name;
    private String author;

    public Book()
    {
    }

    public Book(int id, String name, String author)
    {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    @Override
    public String toString()
    {
        return "Book{" + "id=" + id + ", name='" + name + '\'' + ", author='" + author + '\'' + '}';
    }
}
```



新建controller处理请求并返回到前端页面

```java
package com.et.controller;
 
import com.et.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
 
import java.util.ArrayList;
import java.util.List;
 
/**
 * Created by IntelliJ IDEA.
 *
 * @Author : Shrimpking
 * @create 2023/6/3 11:37
 */
@Controller
public class BookController
{
    @GetMapping("/books")
    public ModelAndView books()
    {
        //图书列表
        List<Book> books = new ArrayList<Book>();
        //创建图书
        Book book1 = new Book(1,"一个王者","橘白");
        //创建图书，并赋值
        Book book2 = new Book();
        book2.setId(2);
        book2.setName("两个棋子");
        book2.setAuthor("未知");
        books.add(book1);
        books.add(book2);
        //创建回传视图对象
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("books",books);
        //设定视图名，也就是前端页面名称
        modelAndView.setViewName("books");
        return modelAndView;
 
    }
}
```

books.html

```java
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<table border="1">
    <tr>
        <th>图书编号</th>
        <th>图书名称</th>
        <th>图书作者</th>
    </tr>
    <tr>
    <tr th:each="book:${books}">
        <td th:text="${book.id}"></td>
        <td th:text="${book.name}"></td>
        <td th:text="${book.author}"></td>
    </tr>
</table>
</body>
</html>
```

### RestController

新建Controller

```java
package com.et.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ajax")
public class TestController {

    @RequestMapping("/show")
    public String show(){
        return "{'name':'Tom','age':18}";
    }
}

```

index.html

使用ajax提交请求并接收返回值

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <script>
        function showMsg(){
            $.post("ajax/show",{},function (res){
                alert(res);
            });
        }
    </script>
</head>
<body>
    <a href="#" onclick="showMsg()">restController</a>
</body>
</html>
```

### @PathVariable

获取请求路径上的参数

例如 获取 /ajax/12

controller中添加获取方法

```java
@RequestMapping("/param1/{id}")
public String pathVariable(@PathVariable("id") Integer id){
    return "{'id':'"+id+"'}";
}
```

index.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <script>
        function showMsg(){
            $.post("ajax/show",{},function (res){
                alert(res);
            });
        }

        function showMsg2(){
            $.post("ajax/param1/1234",{},function (res){
                alert(res);
            });
        }
    </script>
</head>
<body>
    <a href="#" onclick="showMsg()">restController</a>
    <a href="#" onclick="showMsg2()">pathParam</a>
</body>
</html>
```



### @RequestParam

参数列表中获取参数时 使用该注解 可以标注该参数是否可为null

```java
@RequestMapping("/req")
public String requestParam(@RequestParam(value = "id",required = false) Integer id){
    return "{'id':'"+id+"'}";
}
```

index.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <script>
        function showMsg(){
            $.post("ajax/show",{},function (res){
                alert(res);
            });
        }

        function showMsg2(){
            $.post("ajax/param1/1234",{},function (res){
                alert(res);
            });
        }

        function showMsg3(){
            $.post("ajax/req",{'id':5678},function (res){
                alert(res);
            });
        }
    </script>
</head>
<body>
    <a href="#" onclick="showMsg()">restController</a>
    <a href="#" onclick="showMsg2()">pathParam</a>
    <a href="#" onclick="showMsg3()">requestParam</a>
</body>
</html>
```

## Springboot整合SpringData Jpa

添加依赖

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

配置springboot整合springdata jpa

application.yml

```xml
server:
  port: 80
  servlet:
    context-path: /
spring:
  thymeleaf:
    cache: true
    check-template: true
    check-template-location: true
    encoding: UTF-8
    prefix: classpath:/templates/
    servlet:
      content-type: text/html
    suffix: .html

  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_book
    username: root
    password: Karen@1234
  jpa:
    hibernate:
      ddl-auto: update #如果实体类与表中字段不匹配则会重新生成表
    show-sql: true



```



### 新建表对应的实体类

springdata jpa 可以进行逆向生成表 需要通过配置

在启动时会自动创建表 如果已存在且与实体类中的属性一一对应 则不会做任何操作 

如果与实体类中的属性不对应 则会更新表结构

```java
@Entity
@Table(name="t_user")
@Data
public class User {

    @Id
    @GeneratedValue // 自增
    private Integer id;
    @Column(length = 32)
    private String userName;
    @Column(length = 32)
    private String email;
}
```

### 创建Dao层

SpringData Jpa 通常以Repository结尾

```java
package com.et.dao;

import com.et.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// <User,Integer>  User表中的主键是什么类型 这里就是什么类型
public interface UserRespository extends JpaRepository<User,Integer> {
}

```

### 新建Controller实现CRUD操作

#### UserController

```java
package com.et.controller;

import com.et.dao.UserRespository;
import com.et.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRespository userRespository;

    // 跳转添加页面
    @RequestMapping("/userAdd")
    public ModelAndView userAdd(){
        ModelAndView m = new ModelAndView();
        m.setViewName("useradd");
        return m;
    }
    // 跳转修改页面
    @RequestMapping("/preUpdate/{id}")
    public ModelAndView updateUser(@PathVariable("id") Integer id){
        User user = userRespository.getById(id);
        ModelAndView m = new ModelAndView();
        m.addObject("user",user);
        m.setViewName("userupdate");
        return m;
    }

    // 用户列表页面
    @RequestMapping("/list")
    public ModelAndView list(){
        List<User> userList = userRespository.findAll();
        ModelAndView m = new ModelAndView();
        m.addObject("userList",userList);
        m.setViewName("userList");
        return m;
    }

    // 添加用户
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(User user){
        userRespository.save(user);
        return "forward:/user/list";
    }

    // 修改用户
    @GetMapping("/update")
    public String update(User user){
        userRespository.save(user);// 有主键则修改 否则新增
        return "forward:/user/list";
    }

    // 新增用户
    @PostMapping("/save")
    public String save(User user){
        userRespository.save(user);// 有主键则修改 否则新增
        return "forward:/user/list";
    }

    // 删除用户
    @GetMapping("/delete")
    public String delete(Integer id){
        userRespository.deleteById(id);
        return "forward:/user/list";
    }
}

```

#### 页面

##### 用户列表页面 userList.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>用户信息</title>
</head>
<body>
<a href="/user/userAdd">添加用户</a>
<table border="1">
    <tr>
        <th>编号</th>
        <th>名称</th>
        <th>email</th>
        <th>操作</th>
    </tr>
    <tr>
    <tr th:each="user:${userList}">
        <td th:text="${user.id}"></td>
        <td th:text="${user.userName}"></td>
        <td th:text="${user.email}"></td>
        <td>
            <!-- url路径直接传参 例如 /user/preUpdate/1-->
            <a th:href="@{preUpdate/{id}(id=${user.id})}">修改</a>
            <!--问号传参 例如 /user/delete?id=1-->
            <a th:href="@{delete(id=${user.id})}">删除</a>
        </td>
    </tr>
</table>
</body>
</html>
```

##### 用户添加页面 useradd.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="/user/add" method="post">
    用户名：<input type="text" name="userName"><br>
    email：<input type="text" name="email"><br>
    <input type="submit" value="添加用户">
</form>
</body>
</html>
```

##### 用户修改页面 userupdate.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="/user/update" method="get">
    <input type="hidden" name="id" th:value="${user.id}">
    用户名：<input type="text" name="userName" th:value="${user.userName}"><br>
    email：<input type="text" name="email" th:value="${user.email}"><br>
    <input type="submit" value="修改用户">
</form>
</body>
</html>
```

![image-20230929125748926](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291257631.png)

## SpringData JPA 

### @Query自定义查询(本地)

创建Repository接口

```java
public interface UserRespository extends JpaRepository<User,Integer> {

    /*
        nativeQuery 使用本地SQL
        
     */
    @Query(value = "select * from t_user where user_name like CONCAT('%', :userName, '%')",nativeQuery = true)
    List<User> findByName(String userName);
}
```

Controller测试

```java
@ResponseBody
@GetMapping("/findByName")
public List<User> findByName(String userName){
    List<User> list = userRespository.findByName("Jerry");
    return list;
}
```

![image-20230929131507112](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291315286.png)

### 多条件动态拼接语句

```
借助JpaSpecificationExecutor接口中的方法实现
直接继承它即可
```

 Repository接口

```java
package com.et.dao;

import com.et.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// <User,Integer>  User表中的主键是什么类型 这里就是什么类型
public interface UserRespository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {

    /*
        nativeQuery 使用本地SQL

     */
    @Query(value = "select * from t_user where user_name like CONCAT('%', :userName, '%')",nativeQuery = true)
    List<User> findByName(String userName);


}

```

Controller中测试

```java
// 动态拼接SQL
    @ResponseBody
    @GetMapping("/findByName2")
    public List<User> findByName2(User user){



        List<User> list =userRespository.findAll(new Specification<User>() {
            // 动态拼接条件
            // root 封装User对象
            // criteriaBuilder 查询构造器 可以实现模糊匹配，动态拼接等
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if(user.getUserName()!=null){
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("userName"),"%"+user.getUserName()+"%"));
                }
                if(user.getEmail()!=null){
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("email"),"%"+user.getEmail()+"%"));
                }

                return predicate;
            }
        });
        return list;
    }
```



![image-20230929132831305](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291328665.png)

![image-20230929132846128](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291328271.png)

### @Valid 表单验证

```
在实体类中通过@NotNull,@NotEmpty等注解约束字段不能为空
然后在controller中通过@Valid约束入参对象

要使用表单验证 需要添加相关依赖
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

#### 创建实体类

Student

```java
package com.et.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "t_student")
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "姓名不能为空")
    private String name;
    @NotNull(message = "年龄不能为空")
    @Min(value = 18,message = "年龄必须大于18岁")
    private Integer age;
}

```

#### 创建Repository接口

```java
package com.et.dao;

import com.et.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRespository extends JpaRepository<Student,Integer> {
}

```

#### 创建Service层

```java
package com.et.service;

import com.et.model.Student;

public interface StudentService {

    void add(Student student);
}

```

```java
package com.et.service.impl;

import com.et.dao.StudentRespository;
import com.et.model.Student;
import com.et.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRespository studentRespository;
    @Override
    public void add(Student student) {
        studentRespository.save(student);
    }
}

```

#### 创建Controller

```java
package com.et.controller;

import com.et.model.Student;
import com.et.model.User;
import com.et.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("preSaveStudent")
    public ModelAndView preSave(){
        ModelAndView m = new ModelAndView();
        m.setViewName("preAddStudent");
        return m;
    }

    // bindingResult 封装了错误信息
    @RequestMapping("/add")
    public String add(@Valid Student student, BindingResult bindingResult){
       if(bindingResult.hasErrors()){
           // 返回错误信息
           return bindingResult.getFieldError().getDefaultMessage();
       }
        studentService.add(student);
        return "okay";
    }
}

```

#### 新建测试页面

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        function submitData(){

            $.post("/student/add",
                {'name':$("#name").val(),'age':$("#age").val()},
                function (res){
                    alert(res)
                })

        }
    </script>
    <title>添加学生信息</title>
</head>
<body>
    姓名：<input type="text" id="name" name="name"><br>
    年龄：<input type="text" id="age" name="age"><br>
    <input type="button" onclick="submitData()" value="添加">
</body>
</html>
```

![image-20230929142754182](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291427497.png)

![image-20230929142813546](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291428566.png)

## Springboot事务控制

```

例如 从A用户转账到B用户
```

### 创建账户实体 

会自动映射到表

```java
@Entity
@Table(name = "t_account")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增
    private Integer userId;

    private Float blance;// 账户余额
    private String name;
}
```

添加测试数据

![image-20230929134542292](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291345618.png)

### 创建DAO

```java

import com.et.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRespository extends JpaRepository<Account,Integer> {
}
```

### 创建业务层

```java
public interface AccountService {

    // 从A用户转账到B用户
    void transferAcctoun(int fromUser,int toUser,float blance);
}
```

业务层实现类

```java
package com.et.service.impl;

import com.et.dao.AccountRespository;
import com.et.model.Account;
import com.et.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRespository accountRespository;
    
    @Transactional// 添加事务
    @Override
    public void transferAcctoun(int fromUser, int toUser, float blance) {
        // 先获取A用户的余额 然后减掉blance 最后在更新到数据库中
        Account userA = accountRespository.getById(fromUser);
        userA.setBlance(userA.getBlance()-blance);
        accountRespository.save(userA);

        // 模拟异常
        int i = 1/0;

        // 转账到B用户和
        Account userB = accountRespository.getById(toUser);
        userB.setBlance(userB.getBlance()+blance);
        accountRespository.save(userB);
   }
}

```

### controller测试

```java
package com.et.controller;

import com.et.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acc")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @RequestMapping("/transferAcc")
    public String transferAccounts(){
        try{
            // 从A用户到B用户转账1000
            accountService.transferAcctoun(1,2,1000);
            return "okay";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "no okay";
    }
}

```

![image-20230929135424438](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291354428.png)







## springboot AOP切面

```
SpringBoot提供了强大AOP支持，我们前面讲解过AOP面向切面，所以这里具体AOP原理就补具体介绍；
AOP切面主要是切方法，我们一般搞一些日志分析和事务操作，要用到切面，类似拦截器；
@Aspect注解是切面注解类
@Pointcut切点定义
@Before是方法执行前调用
@After是方法执行后调用
@AfterReturning方法执行返回值调用
Service层本身就可以切入事务，所以我们这类搞个常用的 切controller层方法
每个执行controller层的方法 都记录下请求Url，访问者IP 执行类方法参数等信息；
```

以切入日志为例 需要先引入log4j依赖

```xml
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

添加log4j配置文件

```
log4j.rootLogger=debugger,appender1,appender2 
log4j.appender.appender2.Encoding=UTF-8
log4j.appender.appender1.encoding=UTF-8
log4j.appender.File.encoding=UTF-8
log4j.appender.appender1=org.apache.log4j.ConsoleAppender 
log4j.appender.appender2=org.apache.log4j.FileAppender 
log4j.appender.appender2.File=D:/abc.log 

log4j.appender.appender1.layout=org.apache.log4j.TTCCLayout
log4j.appender.appender2.layout=org.apache.log4j.TTCCLayout


```



### 创建切面类

RequestAspect

```java
package com.et.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class RequestAspect {

    private Logger logger=Logger.getLogger(RequestAspect.class);

    // 定义切点 第一个*表示任意返回值类型
    // com.et.controller.*.*(..) 表示controller包下的任意类的任意方法
    // (..) 表示任意参数
    @Pointcut("execution(public * com.et.controller.*.*(..))")
    public void log(){
    }

    @Before("log()")
    public void deoBefore(JoinPoint joinPoint){
        logger.info("方法执行前...");
        ServletRequestAttributes sra=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=sra.getRequest();
        logger.info("url:"+request.getRequestURI());
        logger.info("ip:"+request.getRemoteHost());
        logger.info("method:"+request.getMethod());
        logger.info("class_method:"+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        logger.info("args:"+joinPoint.getArgs());
    }

    @After("log()")
    public void doAfter(JoinPoint joinPoint){
        logger.info("方法执行后...");
    }

    // 如果需要获取方法的返回值 需要指定接收返回值的参数名
    @AfterReturning(returning="result",pointcut="log()")
    public void doAfterReturning(Object result){
        logger.info("执行返回值："+result);
    }
}

```

使用前边的controlelr进行测试即可

测试

![image-20230929145205331](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291452333.png)

```
[http-nio-80-exec-1] INFO com.et.aspect.RequestAspect - 方法执行前...
[http-nio-80-exec-1] INFO com.et.aspect.RequestAspect - url:/student/add
[http-nio-80-exec-1] INFO com.et.aspect.RequestAspect - ip:0:0:0:0:0:0:0:1
[http-nio-80-exec-1] INFO com.et.aspect.RequestAspect - method:POST
[http-nio-80-exec-1] INFO com.et.aspect.RequestAspect - class_method:com.et.controller.StudentController.add
[http-nio-80-exec-1] INFO com.et.aspect.RequestAspect - args:[Ljava.lang.Object;@386754d8
Hibernate: insert into t_student (age, name) values (?, ?)
[http-nio-80-exec-1] INFO com.et.aspect.RequestAspect - 执行返回值：okay
[http-nio-80-exec-1] INFO com.et.aspect.RequestAspect - 方法执行后...

```



## Springboot整合MyBatis

### 新建项目

![image-20230929163212536](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291632167.png)

![image-20230929163317119](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291633508.png)

如果是单体且使用jsp作为前端页面 需要手动添加webapp

```
与src同级目录 新建webapp目录
然后选择file -> project structure... ->facets-> 选中右侧的web下的内容 -> 点击web resourceDriectories的加号 然后弹出选择框 选中我们新建的webapp
```

![image-20230929164548521](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291645992.png)

### 调整pom依赖

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
    <artifactId>springboot_mybatis</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springboot_mybatis</name>
    <description>springboot_mybatis</description>
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
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
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



### 添加springboot的配置文件

整合mybatis

```ymal
server:
  port: 80
# datasource
spring:
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher #路径匹配策略 例如/images/**
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

### 测试

#### 新建User实体

```
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

创建mapper接口

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

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
}

```

#### 创建mapper接口对应的映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.et.mapper.UserDao">

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

    <delete id="delete" parameterType="Integer">
        delete from t_user where id=#{id}
    </delete>
</mapper>
```

#### 创建Service

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

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public int delete(Integer id);
}

```

创建Service的实现类

```java
package com.et.service.impl;

import com.et.mapper.UserDao;
import com.et.entity.User;
import com.et.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

	@Override
	public int update(User user) {
		return userDao.update(user);
	}

	@Override
	public int add(User user) {
		return userDao.add(user);
	}

	@Override
	public int delete(Integer id) {
		return userDao.delete(id);
	}
}

```

#### 创建Controller

```java
package com.et.controller;

import com.et.entity.User;
import com.et.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public List<User> list(){

        return userService.find(null);
    }
}

```

测试

![image-20230929173132197](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291731766.png)

## Springboot整合Swagger

在springboot项目中添加依赖

```xml
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
```

Controller中使用Swagger

```java
package com.et.controller;

import com.et.entity.User;
import com.et.service.UserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@ApiModel(description = "用户信息")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    @ApiOperation(value = "根据 ID 获取用户信息")
    @ApiResponses({ @ApiResponse(code = 200, message = "请求成功", response = User.class),
            @ApiResponse(code = 404, message = "用户不存在") })
    public List<User> list(){
        return userService.find(null);
    }
}

```

创建Swagger配置类

```java
package com.et.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.et.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("接口文档")
                .version("1.0.0")
                .build();
    }
}
```



测试

![image-20230929173758523](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202309291737642.png)

## SpringBoot拦截器

```
web拦截器作用有权限控制，日志记录等等。SpringBoot 提供 HandlerInterceptor方便我们开发；
定义一个自定义拦截器 实现HandlerInterceptor接口，实现三个方法，preHandle是 请求处理之前调用，postHandle是请求处理之后并且视图渲染之前调用，afterCompletion请求结束之后并且视图渲染之后调用；
```



### 自定义Interceptor

```java
package com.et.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
 
/**
 * 自定义拦截器
 * @author Administrator
 *
 */
public class MyInterceptor implements HandlerInterceptor{
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("======== preHandle 请求处理之前调用=================");
        return true;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        System.out.println("======== postHandle 请求处理之后并且视图渲染之前调用=================");
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("======== afterCompletion 请求结束之后并且视图渲染之后调用=================");
    }
 
}
```

### 创建web拦截器配置类

```
在定义一个类继承WebMvcConfigurerAdapter并重写addInterceptors，把自定义拦截器添加到拦截器链中去
```

```java
package com.et.config;

import com.et.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

// 拦截器配置类
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter{
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**"); // 添加拦截器 以及 拦截器规则
        super.addInterceptors(registry);
    }     
}
```

### 配置文件

```
server:
  port: 80
```

### controller测试

```java
package com.et.controller;


import com.et.entity.User;
import com.et.service.UserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@ApiModel("用户信息controller")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    @ApiOperation("查询用户列表信息")
    @ApiResponses({
            @ApiResponse(code = 200,message = "请求成功",response = List.class ),
            @ApiResponse(code = 201,message = "请求失败",response = String.class )
    })
    public List<User> list(String s_username){
        Map<String,Object> map = new HashMap<>();
        map.put("userName",s_username);
        return userService.findAll(map);
    }

    @RequestMapping("/list2")
    @ApiOperation("查询用户列表信息2")
    @ApiResponses({
            @ApiResponse(code = 200,message = "请求成功2",response = List.class ),
            @ApiResponse(code = 201,message = "请求失败2",response = String.class )
    })
    public List<User> list2(String s_username){
        Map<String,Object> map = new HashMap<>();
        map.put("userName",s_username);
        return userService.findAll2(map);
    }
}

```

![image-20231011221033556](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310112210205.png)

![image-20231011221044964](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310112211134.png)
