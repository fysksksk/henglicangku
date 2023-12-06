package com.pn.mapper;

import com.pn.entity.InStore;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InStoreMapper {

    // 添加入库单
    public int insertInStore(InStore inStore);

    // 查询入库单行数
    public Integer findInStoreCount(InStore inStore);

    // 分页查询入库单
    public List<InStore> findInStorePage(@Param("page") Page page, @Param("inStore") InStore inStore);

    // 根据id把入库单状态修改为入库状态
    public Integer setIsInById(Integer inStoreId);
}
