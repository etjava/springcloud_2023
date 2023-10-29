package com.et.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.et.entity.MiaoShaGoods;
import com.et.mapper.MiaoShaGoodsMapper;
import com.et.service.MiaoShaGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiaoShaGoodsServiceImpl extends ServiceImpl<MiaoShaGoodsMapper, MiaoShaGoods> implements MiaoShaGoodsService {

    @Autowired
    private MiaoShaGoodsMapper miaoShaGoodsMapper;

    @Override
    public List<MiaoShaGoods> listAll() {
        return miaoShaGoodsMapper.listAll();
    }

    @Override
    public MiaoShaGoods findById(Integer id) {
        MiaoShaGoods goods = miaoShaGoodsMapper.findById(id);
        Integer miaoShaStatus=0; // 秒杀状态
        Integer remainBeginSecond=0; // 剩余多少秒 开始秒杀
        Integer remainEndSecond=0; // 秒杀结束 剩余多少秒 结束秒杀

        long startTime = goods.getStartTime().getTime();
        long endTime = goods.getEndTime().getTime();
        long currentTime = System.currentTimeMillis();

        System.out.println("startTime:"+startTime);
        System.out.println("endTime:"+endTime);
        System.out.println("currentTime:"+currentTime);

        if(currentTime<startTime){ // 秒杀还没开始 倒计时
            miaoShaStatus=0;
            remainBeginSecond=(int)(startTime-currentTime)/1000;
            remainEndSecond=(int)(endTime-currentTime)/1000;
        }else if(currentTime>endTime){ // 秒杀结束
            miaoShaStatus=2;
            remainBeginSecond=-1;
            remainEndSecond=-1;
        }else{ // 秒杀进行中
            miaoShaStatus=1;
            remainBeginSecond=0;
            remainEndSecond=(int)(endTime-currentTime)/1000;
        }
        goods.setMiaoShaStatus(miaoShaStatus);
        goods.setRemainBeginSecond(remainBeginSecond);
        goods.setRemainEndSecond(remainEndSecond);
        return goods;
    }
}