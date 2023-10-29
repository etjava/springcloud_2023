package com.et.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.et.entity.MiaoShaGoods;

import java.util.List;

public interface MiaoShaGoodsMapper extends BaseMapper<MiaoShaGoods> {

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

    /**
     * 减库存操作
     * @param id
     * @return
     */
    Integer reduceStock(Integer id);
}