package com.pn.service.impl;

import com.alibaba.fastjson.JSON;
import com.pn.entity.Place;
import com.pn.mapper.PlaceMapper;
import com.pn.service.PlaceService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private PlaceMapper placeMapper;

    // 查询所有产地
    public List<Place> queryAllPlace() {
        // 先从redis中查询是否有所有的产地
        String placeAll = stringRedisTemplate.opsForValue().get("place:all");
        if (StringUtils.hasText(placeAll)) {
            // redis中的数据转为数组，并返回
            List<Place> placeList = JSON.parseArray(placeAll, Place.class);
            return placeList;
        } else {
            /*
            redis中没有数据
             */
            // 查出所有产地
            List<Place> placeList = placeMapper.findAllPlace();
            stringRedisTemplate.opsForValue().set("place:all", JSON.toJSONString(placeList));
            return placeList;
        }
    }
}
