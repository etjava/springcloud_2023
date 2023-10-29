package com.et.service;

import com.et.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * 学生信息Service接口
 * @author Administrator
 *
 */
public interface StudentService {


    /**
     * 获取信息
     * @return
     */
    public Map<String,Object> getInfo();

}