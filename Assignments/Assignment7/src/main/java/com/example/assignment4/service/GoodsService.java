package com.example.assignment4.service;

import com.example.assignment4.entity.GoodsEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsService {

    private Map<Long, GoodsEntity> shelf = Collections.synchronizedMap(new HashMap<Long, GoodsEntity>());
//  货架

    public GoodsEntity addGoods(GoodsEntity good){
//        添加货物
        shelf.put(good.getId(), good);
        return good;
    }

    public void deleteGoodsById(long id){
//        删除货物
        shelf.remove(id);
    }

    public void deleteGoodsByName(String name){
//        使用名称删除货物
        for (GoodsEntity good: shelf.values() )
            if (good.getName().equals(name))
                shelf.remove(good.getId());
    }

    public void updateGoods(long id, GoodsEntity good){
//        更新货架状态
        GoodsEntity temp = shelf.get(id);
        temp.setName(good.getName());
        temp.setPrice(good.getPrice());
        temp.setQuantity(good.getQuantity());
    }

    public List<GoodsEntity> findGoods(String name){
        List<GoodsEntity> rtList = new ArrayList<>();
        for (GoodsEntity good: shelf.values()
             ) {
            if (good.getName().equals(name)){
                rtList.add(good);
            }
        }
        return rtList;
    }



}
