package com.et.service;

import com.et.entity.MiaoShaGoods;
import com.et.entity.User;

/**
 * 秒杀操作Service
 */
public interface MiaoShaService {

    /**
     * 执行秒杀 成功返回订单ID 否则返回Null
     * @param user
     * @param miaoShaGoods
     * @return
     */
    String exec(User user, MiaoShaGoods miaoShaGoods);

    /**
     * 获取秒杀结果
     * @param id
     * @param goodsId
     * @return
     */
    String getMiaoShaResult(Integer id, Integer goodsId);
}
