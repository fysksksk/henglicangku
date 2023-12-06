package com.pn.mapper;

import com.pn.entity.Brand;

import java.util.List;

public interface BrandMapper {

    // 查询所有品牌的方法
    public List<Brand> findAllBrand();
}
