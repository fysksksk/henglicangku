package com.pn.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 入库单表in_store表的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InStore implements Serializable {

    private Integer insId;//入库单id

    private Integer storeId;//仓库id

    private Integer productId;//商品id

    private Integer inNum;//入库数量

    private Integer createBy;//创建入库单的用户id

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间

    private Integer isIn;//是否入库,1.是,0.否

    //-----------------追加的属性--------------------

    private String productName;//商品名称

    private String startTime;//起始时间

    private String endTime;//结束时间

    private String storeName;//仓库名称

    private String userCode;//创建入库单的用户的名称

    private BigDecimal inPrice;//商品入库价格
}
