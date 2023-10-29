package com.et.lock_dmeo.service;

import com.et.lock_dmeo.entity.Order;
import com.et.lock_dmeo.entity.UserAccount;
import com.et.lock_dmeo.mapper.OrderMapper;
import com.et.lock_dmeo.mapper.UserAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    @Transactional
    public boolean recharge(Integer orderId, UserAccount userAccount) {
        System.out.println("==============查询订单==============");
        Order order = orderMapper.findById(orderId);
        // 只有未支付状态才会进行充值
        if(order.getStatus()==0){
            System.out.println("==============未支付状态==============");
            order.setStatus(1);
            System.out.println("==============更新支付状态==============");
            Integer res = orderMapper.modify(order);
            if(res!=0){
                userAccount = userAccountMapper.findById(userAccount.getId());
                System.out.println("==============账户充值==============");
                userAccountMapper.addAmount(order.getAmount(),userAccount.getId(),userAccount.getVersion());
                System.out.println("==============账户充值完成==============");
                return true;
            }else{
                System.out.println("==============更新支付状态失败==============");
                return false;
            }
        }else{
            System.out.println("==============订单已处理 无序再次支付==============");
            return false;
        }
    }
}
