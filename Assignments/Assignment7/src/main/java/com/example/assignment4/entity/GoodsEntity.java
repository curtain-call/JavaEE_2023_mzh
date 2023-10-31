package com.example.assignment4.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "商品信息实体")
public class GoodsEntity {
    @ApiModelProperty("商品编号")
    long id;
    @ApiModelProperty("商品名称")
    String name;
    @ApiModelProperty("商品价格")
    long price;
    @ApiModelProperty("商品数量")
    long quantity;
}
