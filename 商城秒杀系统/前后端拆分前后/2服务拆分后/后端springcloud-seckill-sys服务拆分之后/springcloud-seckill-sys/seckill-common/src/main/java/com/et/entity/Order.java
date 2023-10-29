package com.et.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品订单模块
 */
@TableName("t_order")
@Data
public class Order implements Serializable {

    private String id; // 编号
    private User user; // 购买用户
    private MiaoShaGoods miaoShaGoods; // 购买的秒杀商品
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date createDate; // 创建日期
    private Date payDate; // 支付日期
    private Integer countNum; // 购买数量
    private double totalPrice; // 订单总金额
    private String payMethod; // 支付方式
    private Integer status; // 订单状态   0:订单生成  1：已支付  2 已发货  3：已收货

}