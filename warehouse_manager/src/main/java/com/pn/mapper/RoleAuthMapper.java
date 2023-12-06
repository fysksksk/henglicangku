package com.pn.mapper;

import com.pn.entity.RoleAuth;

import java.util.List;

public interface RoleAuthMapper {

    // 根据角色id删除角色权限关系
    public int removeRoleAuthByRoleId(Integer roleId);

    // 根据角色id查询角色所分配的权限菜单id
    public List<Integer> findAuthIdsByRid(Integer roleId);

    // 添加角色权限
    public int insertRoleAuth(RoleAuth roleAuth);
}
