package com.pn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 单位表unit表对应的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Unit implements Serializable {

    private Integer unitId;//单位id

    private String unitName;//单位

    private String unitDesc;//单位描述
}
