package com.pn.service;

import com.pn.dto.AssignAuthDto;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.page.Page;

import java.util.List;

public interface RoleService {

    // 查询所有角色
    public List<Role> queryAllRole();

    // 根据用户id查询用户所分配的角色
    public List<Role> queryRoleByUserId(Integer userId);

    // 分页查询角色
    public Page queryRolePage(Page page, Role role);

    // 添加角色
    public Result saveRole(Role role);

    // 启用或禁用角色状态
    public Result updateRoleState(Role role);

    // 删除角色
    public Result deleteRoleById(Integer roleId);

    // 查询角色所分配的权限的id
    public List<Integer> queryAuthIdsByRid(Integer roleId);

    // 给角色分配权限
    public void saveRoleAuth(AssignAuthDto assignAuthDto);

    // 修改角色的描述
    public Result setRoleDescByRid(Role role);
}
