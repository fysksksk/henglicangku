package com.pn.service.impl;

import com.pn.entity.Purchase;
import com.pn.entity.Result;
import com.pn.mapper.PurchaseMapper;
import com.pn.page.Page;
import com.pn.service.PurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Resource
    private PurchaseMapper purchaseMapper;

    // 添加采购单
    @Transactional(rollbackFor = Exception.class)
    public Result savePurchase(Purchase purchase) {
        // 添加采购单
        int i = purchaseMapper.insertPurchase(purchase);
        if (i > 0) {
            return Result.ok("采购单添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "采购单添加失败！");
    }

    // 分页查询采购单
    public Page queryPurchasePage(Page page, Purchase purchase) {
        // 查询采购单行数
        Integer count = purchaseMapper.findPurchaseCount(purchase);

        // 分页查询采购单
        List<Purchase> purchaseList = purchaseMapper.findPurchasePage(page, purchase);

        // 组装分页信息
        page.setTotalNum(count);
        page.setResultList(purchaseList);

        return page;
    }

    // 删除采购单
    public Result deletePurchaseById(Integer buyId) {
        int i = purchaseMapper.removePurchaseById(buyId);
        if (i > 0) {
            return Result.ok("采购单删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "采购单删除失败！");
    }

    // 修改采购单
    public Result updatePurchaseById(Purchase purchase) {
        int i = purchaseMapper.setNumById(purchase);
        if (i > 0) {
            return Result.ok("采购单修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "采购单修改成功！");
    }
}
