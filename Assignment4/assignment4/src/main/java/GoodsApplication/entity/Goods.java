package GoodsApplication.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "商品实体")
public class Goods {

    @ApiModelProperty("商品序号")
    private String id;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("商品单价")
    private double price;

    //修改
    //库存数量
    @ApiModelProperty("库存数量")
    float stockQuantity;
    //类别
    @ApiModelProperty("商品类别")
    String category;
    //类型
    @ApiModelProperty("商品类型")
    String productType;
    @ApiModelProperty("商品描述")
    String description;



}
