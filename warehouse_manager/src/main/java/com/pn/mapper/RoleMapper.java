package com.pn.mapper;

import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {

    // 查询所有角色
    public List<Role> findAllRole();

    // 根据用户id查询用户所分配的角色
    public List<Role> findRoleByUserId(Integer userId);

    // 根据角色名查询角色id
    public Integer findRoleIdByRoleName(String roleName);

    // 查询角色行数
    public Integer findRoleRowCount(Role role);

    // 分页查询角色
    public List<Role> findRolePage(@Param("page") Page page, @Param("role") Role role);

    // 根据角色名称或角色代码查询角色
    public List<Role> findRoleByNameOrCode(@Param("roleName") String roleName, @Param("roleCode") String roleCode);

    // 添加角色
    public int insertRole(Role role);

    // 根据角色id修改角色的状态(启用或禁用角色状态，不算修改)
    public int updateRoleStateByRoleId(@Param("roleId") Integer roleId, @Param("roleState") String roleState);

    // 根据角色id删除角色的方法
    public int removeRoleById(Integer roleId);

    // 根据角色id修改角色描述
    public int setDescByRid(Role role);
}
