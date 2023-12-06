package com.pn.controller;

import com.pn.dto.AssignAuthDto;
import com.pn.entity.Auth;
import com.pn.entity.CurrentUser;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.page.Page;
import com.pn.service.AuthService;
import com.pn.service.RoleService;
import com.pn.service.UserRoleService;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/role")
@RestController
public class RoleController {

    @Resource
    private TokenUtils tokenUtils;

    @Resource
    private RoleService roleService;

    // 查询所有角色
    @RequestMapping("/role-list")
    public Result roleList() {
        // 执行业务
        List<Role> roleList = roleService.queryAllRole();
        // 响应
        return Result.ok(roleList);
    }

    // 分页查询角色
    @RequestMapping("/role-page-list")
    public Result roleListPage(Page page, Role role) {
        // 执行业务
        page = roleService.queryRolePage(page, role);
        // 响应
        return Result.ok(page);
    }

    // 添加角色
    @RequestMapping("/role-add")
    public Result addRole(@RequestBody Role role, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        // 拿到当前登录的用户的id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int createBy = currentUser.getUserId();

        role.setCreateBy(createBy);

        // 执行业务
        Result result = roleService.saveRole(role);

        // 响应
        return result;
    }

    // 启用或禁用角色
    @RequestMapping("/role-state-update")
    public Result updateRoleState(@RequestBody Role role) {
        // 执行业务
        Result result = roleService.updateRoleState(role);
        // 响应
        return result;
    }

    // 删除角色
    @RequestMapping("/role-delete/{roleId}")
    public Result deleteRole(@PathVariable Integer roleId) {
        // 执行业务
        Result result = roleService.deleteRoleById(roleId);
        // 响应
        return result;
    }

    // 查询角色所分配的权限
    @RequestMapping("/role-auth")
    public Result roleAuth(Integer roleId) {
        // 执行业务
        List<Integer> authIdList = roleService.queryAuthIdsByRid(roleId);
        // 响应
        return Result.ok(authIdList);
    }

    // 给角色分配菜单权限
    @RequestMapping("/auth-grant")
    public Result grantAuth(@RequestBody AssignAuthDto assignAuthDto) {
        // 执行业务
        roleService.saveRoleAuth(assignAuthDto);
        // 响应
        return Result.ok("权限分配成功！");
    }

    // 修改角色的描述
    @RequestMapping("/role-update")
    public Result updateRole(@RequestBody Role role, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        // 获取当前登录用户的id`
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        role.setUpdateBy(currentUser.getUserId());

        // 执行业务
        Result result = roleService.setRoleDescByRid(role);

        return result;
    }
}
