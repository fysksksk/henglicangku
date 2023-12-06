package com.pn.mapper;

import com.pn.entity.Place;

import java.util.List;

public interface PlaceMapper {

    // 查询所有产地
    public List<Place> findAllPlace();
}
