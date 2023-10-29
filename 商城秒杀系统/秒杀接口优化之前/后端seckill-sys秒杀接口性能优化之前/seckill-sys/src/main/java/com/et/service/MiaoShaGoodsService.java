package com.et.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.et.entity.MiaoShaGoods;

import java.util.List;

public interface MiaoShaGoodsService extends IService<MiaoShaGoods> {

    /**
     * 查询所有秒杀商品
     * @return
     */
    public List<MiaoShaGoods> listAll();

    /**
     * 根据ID查询秒杀商品详情
     * @param id
     * @return
     */
    public MiaoShaGoods findById(Integer id);
}
