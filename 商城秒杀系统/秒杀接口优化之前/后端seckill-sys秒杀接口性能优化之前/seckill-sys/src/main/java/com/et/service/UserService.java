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

    User findById(Integer id);
}