package com.et.lock_dmeo.controller;

import com.et.lock_dmeo.entity.UserAccount;
import com.et.lock_dmeo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户充值Controller
 */
@RestController
@RequestMapping("/")
public class RechargeController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/exec")
    public boolean exec(){
        Integer orderId=1;// 模拟用户
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1);
        return orderService.recharge(orderId,userAccount);
    }
}
