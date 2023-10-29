package com.et.lock_dmeo.service;


import com.et.lock_dmeo.entity.Order;
import com.et.lock_dmeo.entity.UserAccount;

public interface OrderService {

    /**
     * 充值操作
     * @param orderId
     * @param userAccount
     * @return
     */
    boolean recharge(Integer orderId, UserAccount userAccount);

}
