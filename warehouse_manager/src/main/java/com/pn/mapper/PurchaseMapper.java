package com.pn.mapper;

import com.pn.entity.Purchase;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseMapper {

    // 添加采购单
    public int insertPurchase(Purchase purchase);

    // 查询采购单行数
    public Integer findPurchaseCount(Purchase purchase);

    // 分页查询采购单
    public List<Purchase> findPurchasePage(@Param("page") Page page, @Param("purchase") Purchase purchase);

    // 根据id删除采购单
    public int removePurchaseById(Integer buyId);

    // 根据id修改预计采购数量和实际采购数量
    public int setNumById(Purchase purchase);

    // 根据id修改采购单状态为已入库
    public int setIsInById(Integer buyId);
}
