package com.pn.controller;

import com.pn.entity.*;
import com.pn.page.Page;
import com.pn.service.InStoreService;
import com.pn.service.PurchaseService;
import com.pn.service.StoreService;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/purchase")
@RestController
public class PurchaseController {

    @Resource
    TokenUtils tokenUtils;

    @Resource
    private InStoreService inStoreService;

    @Resource
    private StoreService storeService;

    @Resource
    private PurchaseService purchaseService;

    // 添加采购单
    @RequestMapping("/purchase-add")
    public Result addPurchase(@RequestBody Purchase purchase) {
        // 执行业务
        Result result = purchaseService.savePurchase(purchase);
        // 响应
        return result;
    }

    // 查询所有仓库
    @RequestMapping("/store-list")
    public Result storeList() {
        // 执行业务
        List<Store> storeList = storeService.queryAllStore();
        // 响应
        return Result.ok(storeList);
    }

    // 分页查询采购单
    @RequestMapping("/purchase-page-list")
    public Result purchaseListPage(Page page, Purchase purchase) {
        // 执行业务
        page = purchaseService.queryPurchasePage(page, purchase);
        // 响应
        return Result.ok(page);
    }

    // 删除采购单
    @RequestMapping("/purchase-delete/{buyId}")
    public Result deletePurchase(@PathVariable Integer buyId) {
        // 执行业务
        Result result = purchaseService.deletePurchaseById(buyId);
        // 响应
        return result;
    }

    // 修改采购单
    @RequestMapping("/purchase-update")
    public Result updatePurchase(@RequestBody Purchase purchase) {
        // 执行业务
        Result result = purchaseService.updatePurchaseById(purchase);
        // 响应
        return result;
    }

    // 生成入库单
    @RequestMapping("/in-warehouse-record-add")
    public Result addInStore(@RequestBody Purchase purchase, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        // 拿到当前登录的用户的id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int createBy = currentUser.getUserId();

        // 创建InStore对象封装入库单信息
        InStore inStore = new InStore();
        inStore.setCreateBy(createBy);
        inStore.setStoreId(purchase.getStoreId());
        inStore.setProductId(purchase.getProductId());
        inStore.setInNum(purchase.getFactBuyNum());

        // 执行业务
        Result result = inStoreService.saveInStore(inStore, purchase.getBuyId());

        // 响应
        return result;
    }
}
