package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 队列消息实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiaoShaMessage {
    private User user;
    private Integer miaoShaGoodsId;
}
