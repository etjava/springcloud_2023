package com.et.service.impl;

import com.et.entity.Student;
import com.et.repository.StudentRepository;
import com.et.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生信息Service实现类
 * @author Administrator
 *
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService{


    @Override
    public Map<String, Object> getInfo() {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code", 200);
        map.put("info", "业务数据xxxxx:1006");
        return map;
    }
}