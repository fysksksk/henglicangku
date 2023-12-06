package com.pn.service;

import com.pn.entity.Product;
import com.pn.entity.Result;
import com.pn.page.Page;

import java.util.List;

public interface ProductService {

    // 分页查询商品
    public Page queryProductPage(Page page, Product product);

    // 添加商品
    public Result saveProduct(Product product);

    // 修改商品上下架
    public Result updateStateByPid(Product product);

    // 删除商品
    public Result deleteProductByIds(List<Integer> productIdList);

    // 修改商品
    public Result setProductById(Product product);
}
