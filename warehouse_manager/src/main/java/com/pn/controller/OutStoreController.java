package com.pn.controller;

import com.pn.entity.CurrentUser;
import com.pn.entity.OutStore;
import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.page.Page;
import com.pn.service.OutStoreService;
import com.pn.service.StoreService;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/outstore")
@RestController
public class OutStoreController {

    @Resource
    private StoreService storeService;

    @Resource
    private OutStoreService outStoreService;

    @Resource
    private TokenUtils tokenUtils;

    // 添加出库单
    @RequestMapping("/outstore-add")
    public Result addOutStore(@RequestBody OutStore outStore,
                              @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){

        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即添加出库单的用户id
        int createBy = currentUser.getUserId();
        outStore.setCreateBy(createBy);

        //执行业务
        Result result = outStoreService.saveOutStore(outStore);

        //响应
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

    // 分页查询出库单
    @RequestMapping("/outstore-page-list")
    public Result outStoreListPage(Page page, OutStore outStore) {
        // 执行业务
        page = outStoreService.queryOutStorePage(page, outStore);
        // 响应
        return Result.ok(page);
    }

    // 确认出库
    @RequestMapping("/outstore-confirm")
    public Result confirmOutStore(@RequestBody OutStore outStore) {
        // 执行业务
        Result result = outStoreService.outStoreConfirm(outStore);
        // 响应
        return result;
    }
}
