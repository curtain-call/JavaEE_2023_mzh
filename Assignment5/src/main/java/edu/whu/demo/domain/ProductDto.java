package edu.whu.demo.domain;

import lombok.Data;

import java.util.List;

//保留原有Product字段，增加suppliers字段
@Data
public class ProductDto extends Product{
    List<Supplier>suppliers;
}
