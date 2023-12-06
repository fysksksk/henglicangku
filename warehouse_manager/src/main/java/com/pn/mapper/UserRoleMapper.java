package com.pn.mapper;

import com.pn.entity.Role;
import com.pn.entity.UserRole;

import java.util.List;

public interface UserRoleMapper {
    // 根据用户id删除用户已分配的角色
    public int removeUserRoleByUserId(Integer userId);

    // 给用户分配角色
    public int insertUserRole(UserRole userRole);
}
