package com.pn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 产地表place表对应的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Place implements Serializable {

    private Integer placeId;//产地id

    private String placeName;//产地名称

    private String placeNum;//产地代码

    private String introduce;//产地介绍

    private String isDelete;//是否删除状态,0未删除,1删除
}
