package com.pn.mapper;

import com.pn.entity.Supply;
import sun.plugin.javascript.navig.Link;

import java.util.List;

public interface SupplyMapper {

    // 查询所有供应商
    public List<Supply> findAllSupply();
}
