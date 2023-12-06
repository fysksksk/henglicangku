package com.pn.service.impl;

import com.pn.entity.Brand;
import com.pn.mapper.BrandMapper;
import com.pn.service.BrandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Resource
    private BrandMapper brandMapper;

    // 查询所有品牌
    public List<Brand> queryAllBrand() {
        return brandMapper.findAllBrand();
    }
}
