package com.pn.service.impl;

import com.pn.entity.Result;
import com.pn.entity.Store;
import com.pn.mapper.StoreMapper;
import com.pn.page.Page;
import com.pn.service.StoreService;
import com.pn.vo.StoreCountVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Resource
    private StoreMapper storeMapper;

    // 查询所有仓库
    public List<Store> queryAllStore() {
        return storeMapper.findAllStore();
    }

    // 查询每个仓库的商品数量
    public List<StoreCountVo> queryStoreCount() {
        return storeMapper.findStoreCount();
    }

    // 分页查询仓库
    public Page queryStorePage(Page page, Store store) {
        // 查询仓库总行数
        Integer count = storeMapper.findStoreRowCount(store);

        // 分页查询仓库
        List<Store> storeList = storeMapper.findStorePage(page, store);

        // 组装分页信息
        page.setTotalNum(count);
        page.setResultList(storeList);

        return page;
    }

    // 判断仓库编号是否存在
    public Result checkStoreNum(String storeNum) {
        Store store = storeMapper.findStoreByStoreNum(storeNum);
        return Result.ok(store == null);
    }

    // 添加仓库
    @Transactional(rollbackFor = Exception.class)
    public Result addStore(Store store) {
        // 添加仓库
        Integer i = storeMapper.insertStore(store);
        if (i > 0) {
            return Result.ok("仓库添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "仓库添加失败！");
    }

    // 修改仓库信息
    @Transactional(rollbackFor = Exception.class)
    public Result updateStore(Store store) {
        Store storeEntity = storeMapper.findStoreByStoreName(store.getStoreName());
        if (storeEntity != null && storeEntity.getStoreName().equals(store.getStoreName()) && !storeEntity.getStoreNum().equals(store.getStoreNum())) {
            return Result.err(Result.CODE_ERR_BUSINESS, "仓库名已存在！");
        }
        storeMapper.setStoreByStoreNum(store);
        return Result.ok("仓库信息修改成功！");
    }

    // 根据仓库id删除仓库
    @Transactional(rollbackFor = Exception.class)
    public Result deleteStore(Integer storeId) {
        int i = storeMapper.removeStoreByStoreId(storeId);
        if (i > 0) {
            return Result.ok("仓库删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "仓库删除失败！");
    }
}
