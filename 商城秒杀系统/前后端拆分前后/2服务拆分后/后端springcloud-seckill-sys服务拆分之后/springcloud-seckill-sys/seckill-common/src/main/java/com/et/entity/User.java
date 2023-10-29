package com.et.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("t_user")
@Data
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id; // 编号
    private String username; // 用户名
    private String password; // 密码
    private Date registerDate; // 注册日期
    private String address; // 地址
    private String phoneNumber; // 手机号码
    private String name; // 姓名

}