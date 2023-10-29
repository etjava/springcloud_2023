package com.et.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Integer id;
    private String orderNo;
    private Double totalPrice;
    private String userInfo;
    private Integer productId;
}
