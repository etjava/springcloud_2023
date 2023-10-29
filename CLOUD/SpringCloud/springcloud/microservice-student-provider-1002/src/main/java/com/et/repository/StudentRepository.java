package com.et.repository;

import com.et.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
 

/**
 * 学生Repository接口
 * @author Administrator
 *
 */
public interface StudentRepository extends JpaRepository<Student, Integer>,JpaSpecificationExecutor<Student>{
 
}