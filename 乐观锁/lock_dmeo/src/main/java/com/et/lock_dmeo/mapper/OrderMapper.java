package com.et.lock_dmeo.mapper;

import com.et.lock_dmeo.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    /**
     * 更新订单
     * @param order
     * @return
     */
    Integer modify(Order order);

    /**
     * 根据订单id查询订单
     * @param id
     * @return
     */
    Order findById(Integer id);
}
