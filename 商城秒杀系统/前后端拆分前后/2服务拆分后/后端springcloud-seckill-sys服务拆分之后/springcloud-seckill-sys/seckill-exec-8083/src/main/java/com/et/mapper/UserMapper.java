package com.et.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.et.entity.User;

/**
 * 用户Mapper接口
 */
public interface UserMapper extends BaseMapper<User> {

    User findById(Integer id);
}
