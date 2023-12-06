package com.pn.service.impl;

import com.pn.dto.AssignAuthDto;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.entity.RoleAuth;
import com.pn.mapper.RoleAuthMapper;
import com.pn.mapper.RoleMapper;
import com.pn.mapper.UserMapper;
import com.pn.page.Page;
import com.pn.service.RoleService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

// 指定缓存的名字(数据保存到redis中的键的前缀，一般写类的全路径)
@CacheConfig(cacheNames = "com.pn.service.impl.RoleServiceImpl")
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleAuthMapper roleAuthMapper;

    @Resource
    private RoleMapper roleMapper;

    // 查询所有角色，并放入redis缓存中
    // 指定在redis中的键名
    @Cacheable(key = "'all:role'")
    public List<Role> queryAllRole() {
        return roleMapper.findAllRole();
    }

    // 根据用户id查询用户所分配的角色
    public List<Role> queryRoleByUserId(Integer userId) {
        return roleMapper.findRoleByUserId(userId);
    }

    // 分页查询角色
    public Page queryRolePage(Page page, Role role) {
        // 查询角色行数
        Integer count = roleMapper.findRoleRowCount(role);

        // 分页查询角色
        List<Role> roleList = roleMapper.findRolePage(page, role);

        // 组装分页信息
        page.setTotalNum(count);
        page.setResultList(roleList);

        return page;
    }

    // 添加角色
    // 新增角色后，清除redis中的缓存
    @CacheEvict(key = "'all:role'")
    @Transactional(rollbackFor = Exception.class)
    public Result saveRole(Role role) {
        // 判断角色是否已存在
        List<Role> roleList = roleMapper.findRoleByNameOrCode(role.getRoleName(), role.getRoleCode());
        if (roleList.size() > 0) { // 角色已存在
            return Result.err(Result.CODE_ERR_BUSINESS, "角色已存在！");
        }
        // 角色不存在，添加角色
        int i = roleMapper.insertRole(role);
        if (i > 0) {
            return Result.ok("角色添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "角色添加失败！");
    }

    // 启用或禁用角色状态
    // 修改角色状态后，清除redis中的缓存
    @CacheEvict(key = "'all:role'")
    @Transactional(rollbackFor = Exception.class)
    public Result updateRoleState(Role role) {
        int i = roleMapper.updateRoleStateByRoleId(role.getRoleId(), role.getRoleState());
        if (i > 0) {
            return Result.ok("角色启用或禁用成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "角色启用或禁用失败！");
    }

    // 删除角色
    // 修改角色状态后，清除redis中的缓存
    @CacheEvict(key = "'all:role'")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteRoleById(Integer roleId) {
        int i = roleMapper.removeRoleById(roleId);
        if (i > 0) {
            // 删除角色权限关系
            roleAuthMapper.removeRoleAuthByRoleId(roleId);
            // 返回成功的响应结果对象
            return Result.ok("角色删除成功！");
        }
        // 返回失败的响应结果对象
        return Result.err(Result.CODE_ERR_BUSINESS, "角色删除失败！");
    }

    // 查询角色所分配的权限的id
    public List<Integer> queryAuthIdsByRid(Integer roleId) {
        return roleAuthMapper.findAuthIdsByRid(roleId);
    }

    // 给角色分配权限
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleAuth(AssignAuthDto assignAuthDto) {

        int roleId = assignAuthDto.getRoleId();

        // 删除角色之前分配的所有权限
        roleAuthMapper.removeRoleAuthByRoleId(roleId);

        // 添加角色权限关系
        List<Integer> authIds = assignAuthDto.getAuthIds();
        for (Integer authId : authIds) {
            RoleAuth roleAuth = new RoleAuth();
            roleAuth.setAuthId(authId);
            roleAuth.setRoleId(roleId);
            roleAuthMapper.insertRoleAuth(roleAuth);
        }
    }

    // 修改角色的描述
    @CacheEvict(key = "'all:role'")
    public Result setRoleDescByRid(Role role) {
        int i = roleMapper.setDescByRid(role);
        if (i > 0) {
            return Result.ok("角色修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "角色修改失败！");
    }
}
