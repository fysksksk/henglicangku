package com.pn.mapper;

import com.pn.entity.Auth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// 可以不加@Mapper注解，在WarehouseManagerApplication上已经加了@MapperScan注解扫描mapper接口
public interface AuthMapper {

    // 根据userId查询用户权限下的所有菜单的方法
    public List<Auth> findAuthByUid(@Param("userId") Integer userId);

    // 查询所有权限菜单的方法
    public List<Auth> findAllAuth();
}
