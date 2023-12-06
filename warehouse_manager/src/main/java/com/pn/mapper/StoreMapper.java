package com.pn.mapper;

import com.pn.entity.Store;
import com.pn.page.Page;
import com.pn.vo.StoreCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreMapper {

    // 查询所有仓库
    public List<Store> findAllStore();

    // 查询每个仓库的商品数量
    public List<StoreCountVo> findStoreCount();

    // 查询仓库总行数
    public Integer findStoreRowCount(Store store);

    // 分页查询仓库
    public List<Store> findStorePage(@Param("page") Page page, @Param("store") Store store);

    // 根据仓库编号查询仓库
    public Store findStoreByStoreNum(String storeNum);

    // 添加仓库
    public Integer insertStore(Store store);

    // 根据仓库编号修改仓库信息
    public int setStoreByStoreNum(Store store);

    // 根据仓库名查找仓库
    public Store findStoreByStoreName(String storeName);

    // 根据仓库id删除仓库
    public int removeStoreByStoreId(Integer storeId);
}
