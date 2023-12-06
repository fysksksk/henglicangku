package com.pn.service.impl;

import com.pn.entity.OutStore;
import com.pn.entity.Result;
import com.pn.mapper.OutStoreMapper;
import com.pn.mapper.ProductMapper;
import com.pn.page.Page;
import com.pn.service.OutStoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OutStoreServiceImpl implements OutStoreService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private OutStoreMapper outStoreMapper;

    // 添加出库单
    @Transactional(rollbackFor = Exception.class)
    public Result saveOutStore(OutStore outStore) {
        // 添加
        int i = outStoreMapper.insertOutStore(outStore);
        if (i > 0) {
            return Result.ok("出库单添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "出库单添加失败！");
    }

    // 分页查询出库单
    @Transactional(rollbackFor = Exception.class)
    public Page queryOutStorePage(Page page, OutStore outStore) {
        // 查询出库单行数
        Integer count = outStoreMapper.findOutStoreCount(outStore);

        // 分页查询出库单
        List<OutStore> outStoreList = outStoreMapper.findOutStorePage(page, outStore);

        // 封装分页信息
        page.setResultList(outStoreList);
        page.setTotalNum(count);

        return page;
    }

    // 确认出库
    @Transactional(rollbackFor = Exception.class)
    public Result outStoreConfirm(OutStore outStore) {
        // 判断商品库存是否充足
        int invent = productMapper.findInventById(outStore.getProductId());
        if (invent < outStore.getOutNum()) {
            return Result.err(Result.CODE_ERR_BUSINESS, "商品库存不足！");
        }

        // 修改商品出库状态
        int i = outStoreMapper.setIsOutById(outStore.getOutsId());
        if (i > 0) {
            // 修改商品库存
            int j = productMapper.setInventById(outStore.getProductId(), -outStore.getOutNum());
            if (j > 0) {
                return Result.ok("确认出库成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "确认出库失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "确认出库失败！");
    }
}
