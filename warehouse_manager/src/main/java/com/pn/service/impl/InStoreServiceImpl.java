package com.pn.service.impl;

import com.pn.entity.InStore;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.mapper.InStoreMapper;
import com.pn.mapper.ProductMapper;
import com.pn.mapper.PurchaseMapper;
import com.pn.page.Page;
import com.pn.service.InStoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InStoreServiceImpl implements InStoreService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private PurchaseMapper purchaseMapper;

    @Resource
    private InStoreMapper inStoreMapper;

    // 添加入库单
    @Transactional(rollbackFor = Exception.class)
    public Result saveInStore(InStore inStore, Integer buyId) {
        int i = inStoreMapper.insertInStore(inStore);
        if (i > 0) {
            int j = purchaseMapper.setIsInById(buyId);
            if (j > 0) {
                return Result.ok("入库单添加成功！");
            }
            Result.err(Result.CODE_ERR_BUSINESS, "入库单添加失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "入库单添加失败！");
    }

    // 分页查询入库单
    @Transactional(rollbackFor = Exception.class)
    public Page queryInStorePage(Page page, InStore inStore) {

        // 查询入库单行数
        Integer count = inStoreMapper.findInStoreCount(inStore);

        // 分页查询入库单
        List<InStore> inStoreList = inStoreMapper.findInStorePage(page, inStore);

        // 封装分页信息
        page.setTotalNum(count);
        page.setResultList(inStoreList);

        return page;
    }

    // 确认入库
    @Transactional(rollbackFor = Exception.class)
    public Result inStoreConfirm(InStore inStore) {
        // 修改入库状态
        Integer i = inStoreMapper.setIsInById(inStore.getInsId());
        if (i > 0) {
            int j = productMapper.setInventById(inStore.getProductId(), inStore.getInNum());
            if (j > 0) {
                return Result.ok("确认入库成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "确认入库失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "确认入库失败！");
    }
}
