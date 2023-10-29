package com.et.lock_dmeo.mapper;

import com.et.lock_dmeo.entity.UserAccount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAccountMapper {

    /**
     * 模拟充值操作
     * @param balance
     * @return
     */
    Integer addAmount(Integer balance,Integer id,Integer version);

    UserAccount findById(Integer id);
}
