package com.example.assignment4.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "供应商实体类")
public class SupplierEntity {
    @ApiModelProperty("供应商编号")
    long id;
    @ApiModelProperty("供应商名称")
    String name;
}
