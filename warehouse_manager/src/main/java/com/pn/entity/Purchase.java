package com.pn.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 采购单表buy_list表的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Purchase {

    private Integer buyId;//采购单id

    private Integer productId;//采购单采购的商品id

    private Integer storeId;//采购单采购的商品所在仓库id

    private Integer buyNum;//预计采购的商品数量

    private Integer factBuyNum;//实际采购的商品数量

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date buyTime;//采购时间

    private Integer supplyId;//采购单采购的商品的供应商id

    private Integer placeId;//采购单采购的商品的产地id

    private String buyUser;//采购人

    private String phone;//采购人联系电话

    private String isIn;//是否生成入库单,1.是,0.否

    //---------------追加属性---------------------------

    private String productName;//商品名称

    private String storeName;//仓库名称

    private String startTime;//搜索起始时间

    private String endTime;//搜索结束时间
}
