package com.et.lock_dmeo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 充值订单实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Integer id;
    private Integer userId;
    private Integer amount;// 充值金额
    private Date orderDate; // 下单时间
    private Integer status; // 充值状态 0未支付 1已支付
}
