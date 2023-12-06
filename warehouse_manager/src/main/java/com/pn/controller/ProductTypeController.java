package com.pn.controller;

import com.pn.entity.ProductType;
import com.pn.entity.Result;
import com.pn.service.ProductTypeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/productCategory")
@RestController
public class ProductTypeController {

    @Resource
    private ProductTypeService productTypeService;

    // 查询商品分类树
    @RequestMapping("/product-category-tree")
    public Result productCategoryTree() {
        // 执行业务
        List<ProductType> typeTreeList = productTypeService.productTypeTree();
        // 响应
        return Result.ok(typeTreeList);
    }

    // 校验分类编码是否已存在
    @RequestMapping("/verify-type-code")
    public Result checkTypeCode(String typeCode) {
        // 执行业务
        Result result = productTypeService.checkTypeCode(typeCode);
        // 响应
        return result;
    }

    // 添加商品分类
    @RequestMapping("/type-add")
    public Result addProductType(@RequestBody ProductType productType) {
        // 执行业务
        Result result = productTypeService.saveProductType(productType);
        // 响应
        return result;
    }

    // 删除商品分类
    @RequestMapping("/type-delete/{typeId}")
    public Result typeDelete(@PathVariable Integer typeId) {
        // 执行业务
        Result result = productTypeService.deleteProductType(typeId);
        // 响应
        return result;
    }

    // 修改商品分类
    @RequestMapping("/type-update")
    public Result updateProductType(@RequestBody ProductType productType) {
        // 执行业务
        Result result = productTypeService.setProductType(productType);
        // 响应
        return result;
    }
}
