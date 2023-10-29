package com.et.controller;

import com.et.service.StudentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private StudentService studentService;
    /**
     * 获取信息
     * @return
     * @throws InterruptedException
     */
    @ResponseBody
    @GetMapping(value="/getInfo")
    //@HystrixCommand(fallbackMethod="getInfoFallback")
    public Map<String,Object> getInfo() throws InterruptedException{
        Thread.sleep(1500);
//        Map<String,Object> map=new HashMap<String,Object>();
//        map.put("code", 200);
//        map.put("info", "业务数据xxxxx:1006");
//        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//        return map;
        return studentService.getInfo();
    }

}