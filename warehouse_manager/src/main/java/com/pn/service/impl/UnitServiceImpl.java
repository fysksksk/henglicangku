package com.pn.service.impl;

import com.alibaba.fastjson.JSON;
import com.pn.entity.Supply;
import com.pn.entity.Unit;
import com.pn.mapper.UnitMapper;
import com.pn.service.UnitService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UnitMapper unitMapper;

    public List<Unit> queryAllUnit() {
        // 先从redis中查询是否有所有的供应商
        String unitAll = stringRedisTemplate.opsForValue().get("unit:all");
        if (StringUtils.hasText(unitAll)) {
            // redis中的数据转为数组，并返回
            List<Unit> unitList = JSON.parseArray(unitAll, Unit.class);
            return unitList;
        } else {
            /*
            redis中没有数据
             */
            // 查出所有供应商
            List<Unit> unitList = unitMapper.findAllUnit();
            stringRedisTemplate.opsForValue().set("unit:all", JSON.toJSONString(unitList));
            return unitList;
        }
    }
}
