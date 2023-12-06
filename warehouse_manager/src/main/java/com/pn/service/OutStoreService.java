package com.pn.service;

import com.pn.entity.OutStore;
import com.pn.entity.Result;
import com.pn.page.Page;

public interface OutStoreService {

    // 添加出库单
    public Result saveOutStore(OutStore outStore);

    // 分页查询出库单
    public Page queryOutStorePage(Page page, OutStore outStore);

    // 确认出库
    public Result outStoreConfirm(OutStore outStore);
}
