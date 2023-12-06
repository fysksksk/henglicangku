package com.pn.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCountVo {
    private Integer storeId; // 仓库id
    private String storeName; // 仓库名称
    private Integer totalInvent; // 仓库的商品数量
}
