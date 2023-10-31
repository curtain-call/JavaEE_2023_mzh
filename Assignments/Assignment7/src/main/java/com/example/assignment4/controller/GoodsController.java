package com.example.assignment4.controller;

import com.example.assignment4.entity.GoodsEntity;
import com.example.assignment4.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "goods manipulation system")
@RestController
public class GoodsController {
    @Autowired
    GoodsService goodService;

    @ApiOperation("根据名称查货物信息")
    @PostMapping("/{name}")
    public ResponseEntity<List<GoodsEntity>> findGoodsById(@ApiParam("商品名称") @PathVariable String name){
        List<GoodsEntity> result = goodService.findGoods(name);
        if(result==null) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(result);
        }
    }


}
