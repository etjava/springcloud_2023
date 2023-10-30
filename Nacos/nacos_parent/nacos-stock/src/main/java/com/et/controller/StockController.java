package com.et.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    @RequestMapping("/info")
    public String test(String info){
        return "库存模块接收到的消息 "+info;
    }
}
