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
        Thread.sleep(50);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code", 200);
        map.put("info", "业务数据xxxxx:1004");
        return map;
    }

    public Map<String,Object> getInfoFallback() throws InterruptedException{
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code", 500);
        map.put("info", "系统出错，稍后重试:1004");
        return map;
    }
}