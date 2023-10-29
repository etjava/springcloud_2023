package com.et.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("t_goods_miaosha")
@Data
public class MiaoShaGoods implements Serializable {

    private Integer id; // 编号
    @TableField(select = false)
    private Goods goods; // 关联商品
    private double price; // 秒杀价格
    private Integer stock; // 库存数量
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date startTime; // 秒杀开始时间
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date endTime; // 秒杀结束时间

    // 扩展属性
    private Integer miaoShaStatus=0; // 秒杀状态
    private Integer remainBeginSecond=0; // 剩余多少秒 开始秒杀
    private Integer remainEndSecond=0; // 秒杀结束 剩余多少秒 结束秒杀




}