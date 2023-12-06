package com.pn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 商品分类表product_type表对应的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductType implements Serializable {

    private Integer typeId;//分类id

    private Integer parentId;//上级分类id

    private String typeCode;//分类代码

    private String typeName;//分类名称

    private String typeDesc;//分类描述

    //自定义List<ProductType>集合属性,用于存储当前分类的所有子级分类
    private List<ProductType> childProductCategory;
}
