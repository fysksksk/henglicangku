package com.pn.mapper;

import com.pn.entity.OutStore;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutStoreMapper {

    // 添加出库单
    public int insertOutStore(OutStore outStore);

    // 查询出库单行数
    public Integer findOutStoreCount(OutStore outStore);

    // 分页查询出库单
    public List<OutStore> findOutStorePage(@Param("page") Page page, @Param("outStore") OutStore outStore);

    // 根据id把出库单状态修改为已出库
    public int setIsOutById(Integer outStoreId);
}
