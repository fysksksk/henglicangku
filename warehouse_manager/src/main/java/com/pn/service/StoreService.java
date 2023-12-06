package com.pn.service;


import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.page.Page;
import com.pn.vo.StoreCountVo;

import java.util.List;

public interface StoreService {

    // 查询所有仓库
    public List<Store> queryAllStore();

    // 查询每个仓库的商品数量
    public List<StoreCountVo> queryStoreCount();

    // 分页查询仓库
    public Page queryStorePage(Page page, Store store);

    // 校验仓库编号是否存在
    public Result checkStoreNum(String storeNum);

    // 添加仓库
    public Result addStore(Store store);

    // 修改仓库信息
    public Result updateStore(Store store);

    // 根据仓库id删除仓库
    public Result deleteStore(Integer storeId);
}
