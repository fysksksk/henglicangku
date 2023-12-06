package com.pn.service;

import com.pn.entity.ProductType;
import com.pn.entity.Result;

import java.util.List;

public interface ProductTypeService {

    // 查询商品分类树
    public List<ProductType> productTypeTree();

    // 校验分类编码是否存在
    public Result checkTypeCode(String typeCode);

    // 添加商品分类
    public Result saveProductType(ProductType productType);

    // 删除商品分类
    public Result deleteProductType(Integer typeId);

    // 修改商品分类
    public Result setProductType(ProductType productType);
}
