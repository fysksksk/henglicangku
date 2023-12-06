package com.pn.controller;

import com.pn.entity.Result;
import com.pn.service.StoreService;
import com.pn.vo.StoreCountVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/statistics")
@RestController
public class StatisticsController {

    @Resource
    private StoreService storeService;

    // 统计每个仓库库存
    @RequestMapping("/store-invent")
    public Result storeInvent() {
        List<StoreCountVo> storeCountVoList = storeService.queryStoreCount();
        return Result.ok(storeCountVoList);
    }
}
