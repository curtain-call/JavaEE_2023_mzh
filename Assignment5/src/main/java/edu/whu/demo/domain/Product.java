package edu.whu.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jiaxy
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    //产品Id
    @TableId(value="id",type= IdType.AUTO)
    int id;
    //产品名称
    String name;
    //产品单价
    float price;
    //库存数量
    float stockQuantity;
    //产品类别
    String category;
    //产品类型
    String productType;
    //产品描述
    String description;






}
