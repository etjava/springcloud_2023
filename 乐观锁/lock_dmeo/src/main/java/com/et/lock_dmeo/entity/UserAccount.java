package com.et.lock_dmeo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户账户实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

    private Integer id;
    private String username;
    private Integer balance;// 余额

    private Integer version;// 当前数据的版本号
}
