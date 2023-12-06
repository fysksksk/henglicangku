package com.pn.controller;

import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.page.Page;
import com.pn.service.StoreService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/store")
@RestController
public class StoreController {

    @Resource
    private StoreService storeService;

    // 分页查询仓库
    @RequestMapping("/store-page-list")
    public Result storeList(Page page, Store store) {
        // 执行业务
        page = storeService.queryStorePage(page, store);
        // 响应
        return Result.ok(page);
    }

    // 校验仓库编号是否存在
    @RequestMapping("/store-num-check")
    public Result checkStoreNum(String storeNum) {
        // 执行业务
        Result result = storeService.checkStoreNum(storeNum);
        // 响应
        return result;
    }

    // 添加仓库
    @RequestMapping("/store-add")
    public Result addStore(@RequestBody Store store) {
        // 执行业务
        Result result = storeService.addStore(store);
        // 响应
        return result;
    }

    // 修改仓库
    @RequestMapping("/store-update")
    public Result updateStore(@RequestBody Store store) {
        // 执行业务
        Result result = storeService.updateStore(store);
        // 响应
        return result;
    }

    // 删除仓库
    @RequestMapping("/store-delete/{storeId}")
    public Result deleteStore(@PathVariable Integer storeId) {
        // 执行业务
        Result result = storeService.deleteStore(storeId);
        // 响应
        return result;
    }
}
