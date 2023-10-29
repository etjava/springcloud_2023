package com.et.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.et.entity.Goods;

/**
 * 商品Mapper接口
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    public Goods findById(Integer id);
}