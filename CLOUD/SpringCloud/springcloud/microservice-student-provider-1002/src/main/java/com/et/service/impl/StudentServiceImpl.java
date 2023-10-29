package com.et.service.impl;

import com.et.entity.Student;
import com.et.repository.StudentRepository;
import com.et.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
 
/**
 * 学生信息Service实现类
 * @author Administrator
 *
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService{
 
    @Resource
    private StudentRepository studentRepository;
     
    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student findById(Integer id) {
        return studentRepository.findById(id).get();
    }
 
    @Override
    public List<Student> list() {
        return studentRepository.findAll();
    }
 
    @Override
    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }
 
}