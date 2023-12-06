package com.pn.service.impl;

import com.alibaba.fastjson.JSON;
import com.pn.entity.ProductType;
import com.pn.entity.Supply;
import com.pn.mapper.SupplyMapper;
import com.pn.service.SupplyService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SupplyServiceImpl implements SupplyService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private SupplyMapper supplyMapper;

    // 查询所有供应商
    public List<Supply> queryAllSupply() {
        // 先从redis中查询是否有所有的供应商
        String supplyAll = stringRedisTemplate.opsForValue().get("supply:all");
        if (StringUtils.hasText(supplyAll)) {
            // redis中的数据转为数组，并返回
            List<Supply> supplyList = JSON.parseArray(supplyAll, Supply.class);
            return supplyList;
        } else {
            /*
            redis中没有数据
             */
            // 查出所有供应商
            List<Supply> supplyList = supplyMapper.findAllSupply();
            stringRedisTemplate.opsForValue().set("supply:all", JSON.toJSONString(supplyList));
            return supplyList;
        }
    }
}
