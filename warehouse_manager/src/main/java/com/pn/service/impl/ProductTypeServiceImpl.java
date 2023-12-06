package com.pn.service.impl;

import com.alibaba.fastjson.JSON;
import com.pn.entity.ProductType;
import com.pn.entity.Result;
import com.pn.mapper.ProductTypeMapper;
import com.pn.service.ProductTypeService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ProductTypeMapper productTypeMapper;

    // 查询商品分类树
    public List<ProductType> productTypeTree() {
        // 先从redis中查询是否有所有的商品分类树
        String productTypeJson = stringRedisTemplate.opsForValue().get("productType:all");
        if (StringUtils.hasText(productTypeJson)) {
            // redis中的数据转为数组，并返回
            List<ProductType> productTypeTree = JSON.parseArray(productTypeJson, ProductType.class);
            return productTypeTree;
        } else {
            /*
            redis中没有数据
             */
            // 查出所有商品分类
            List<ProductType> allProductTypeList = productTypeMapper.findAllProductType();
            // 将所有商品分类转换成商品分类树
            List<ProductType> typeTreeList = allTypeToTypeTree(allProductTypeList, 0);
            stringRedisTemplate.opsForValue().set("productType:all", JSON.toJSONString(typeTreeList));
            return typeTreeList;
        }
    }

    // 校验分类编码是否存在
    public Result checkTypeCode(String typeCode) {
        // 根据分类编码查询分类，并判断是否存在
        ProductType productType = new ProductType();
        productType.setTypeCode(typeCode);
        ProductType prodType = productTypeMapper.findTypeByCodeOrName(productType);

        // 返回值
        return Result.ok(prodType == null);
    }

    // 添加商品分类
    @Transactional(rollbackFor = Exception.class)
    public Result saveProductType(ProductType productType) {
        // 校验分类名称是否已存在
        ProductType prodType = new ProductType();
        prodType.setTypeName(productType.getTypeName());
        ProductType prodCategory = productTypeMapper.findTypeByCodeOrName(prodType);
        if (prodCategory != null) {
            return Result.err(Result.CODE_ERR_BUSINESS, "分类名称已存在！");
        }

        // 添加分类
        int i = productTypeMapper.insertProductType(productType);
        if (i > 0) {
            // 查出所有商品分类
            List<ProductType> allProductTypeList = productTypeMapper.findAllProductType();
            // 将所有商品分类转换成商品分类树
            List<ProductType> typeTreeList = allTypeToTypeTree(allProductTypeList, 0);
            stringRedisTemplate.boundValueOps("productType:all").set(JSON.toJSONString(typeTreeList));
            return Result.ok("商品分类添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "商品分类添加失败！");
    }

    // 删除商品分类
    @Transactional(rollbackFor = Exception.class)
    public Result deleteProductType(Integer typeId) {
        int i = productTypeMapper.removeProductType(typeId);
        if (i > 0) {
            // 查出所有商品分类
            List<ProductType> allProductTypeList = productTypeMapper.findAllProductType();
            // 将所有商品分类转换成商品分类树
            List<ProductType> typeTreeList = allTypeToTypeTree(allProductTypeList, 0);
            stringRedisTemplate.boundValueOps("productType:all").set(JSON.toJSONString(typeTreeList));
            return Result.ok("商品分类删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "商品分类删除失败！");
    }

    // 修改商品分类
    @Transactional(rollbackFor = Exception.class)
    public Result setProductType(ProductType productType) {
        ProductType prodType = new ProductType();
        prodType.setTypeName(productType.getTypeName());
        ProductType prodCategory = productTypeMapper.findTypeByCodeOrName(prodType);
        if (prodCategory != null && !prodCategory.getTypeId().equals(productType.getTypeId())) {
            return Result.err(Result.CODE_ERR_BUSINESS, "商品分类名称已存在！");
        }
        // 修改
        int i = productTypeMapper.setProductTypeById(productType);
        if (i > 0) {
            // 查出所有商品分类
            List<ProductType> allProductTypeList = productTypeMapper.findAllProductType();
            // 将所有商品分类转换成商品分类树
            List<ProductType> typeTreeList = allTypeToTypeTree(allProductTypeList, 0);
            stringRedisTemplate.boundValueOps("productType:all").set(JSON.toJSONString(typeTreeList));
            return Result.ok("商品分类修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "商品分类修改失败！");
    }

    // 将所有商品分类转换成商品分类树(递归)
    private List<ProductType> allTypeToTypeTree(List<ProductType> typeList, Integer pid) {
        // 拿到所有一级分类
        List<ProductType> firstLevelType = new ArrayList<ProductType>();
        for (ProductType productType : typeList) {
            if (productType.getParentId().equals(pid)) {
                firstLevelType.add(productType);
            }
        }

        // 查询每个一级分类下的所有二级分类
        for (ProductType productType : firstLevelType) {
            List<ProductType> secondLevelType = allTypeToTypeTree(typeList, productType.getTypeId());
            productType.setChildProductCategory(secondLevelType);
        }

        return firstLevelType;
    }
}
