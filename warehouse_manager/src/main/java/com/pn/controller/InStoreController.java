package com.pn.controller;

import com.pn.entity.InStore;
import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.page.Page;
import com.pn.service.InStoreService;
import com.pn.service.StoreService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/instore")
@RestController
public class InStoreController {

    @Resource
    private InStoreService inStoreService;

    @Resource
    private StoreService storeService;

    // 查询所有仓库
    @RequestMapping("/store-list")
    public Result storeList() {
        List<Store> storeList = storeService.queryAllStore();
        return Result.ok(storeList);
    }

    // 分页查询入库单
    @RequestMapping("/instore-page-list")
    public Result inStoreListPage(Page page, InStore inStore) {
        // 执行业务
        page  = inStoreService.queryInStorePage(page, inStore);
        // 响应
        return Result.ok(page);
    }

    // 确认入库
    @RequestMapping("/instore-confirm")
    public Result confirmInStore(@RequestBody InStore inStore) {
        // 执行业务
        Result result = inStoreService.inStoreConfirm(inStore);
        // 响应
        return result;
    }
}
