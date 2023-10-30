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
