package com.et.controller;

import java.util.List;
import java.util.Map;


import com.et.entity.Student;
import com.et.service.StudentClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 测试服务熔断
     */
    @GetMapping(value="/getInfo")
    @ResponseBody
    public Map<String,Object> getInfo() throws InterruptedException {
        return studentClientService.getInfo();
    }

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