package com.pn.controller;

import com.pn.dto.AssignRoleDto;
import com.pn.entity.CurrentUser;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.entity.User;
import com.pn.page.Page;
import com.pn.service.RoleService;
import com.pn.service.UserService;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;

    @Resource
    private TokenUtils tokenUtils;

    // 分页查询用户
    @RequestMapping("/user-list")
    public Result userList(Page page, User user) {
        // 分页查询用户信息
        page = userService.queryUserByPage(page, user);
        // 响应
        return Result.ok(page);
    }

    // 添加用户
    @RequestMapping("/addUser")
    public Result addUser(@RequestBody User user, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        // 拿到当前登录用户的id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int createBy = currentUser.getUserId();
        user.setCreateBy(createBy);

        // 添加user
        Result result = userService.saveUser(user);

        // 响应
        return result;
    }

    // 启用或禁用用户
    @RequestMapping("/updateState")
    public Result updateUserState(@RequestBody User user) {
        // 执行业务
        Result result = userService.updateUserState(user);
        // 响应
        return result;
    }

    // 查询用户已分配的角色
    // @RequestMapping将路径占位符{userId}中的值赋值给请求处理方法的变量userId
    @RequestMapping("/user-role-list/{userId}")
    public Result userRoleList(@PathVariable Integer userId) {
        // 执行业务
        List<Role> roleList = roleService.queryRoleByUserId(userId);
        // 响应
        return Result.ok(roleList);
    }

    // 给用户分配角色
    @RequestMapping("/assignRole")
    public Result assignRole(@RequestBody AssignRoleDto assignRoleDto) {
        // 执行业务
        userService.assignRole(assignRoleDto);
        // 响应
        return Result.ok("分配角色成功！");
    }

    // 根据用户id删除单个用户
    @RequestMapping("/deleteUser/{userId}")
    public Result deleteUserById(@PathVariable Integer userId) {
        // 执行业务
        Result result = userService.deleteUserByIds(Arrays.asList(userId));
        // 响应
        return result;
    }

    // 根据用户ids批量删除用户
    @RequestMapping("/deleteUserList")
    public Result deleteUserByIds(@RequestBody List<Integer> userIdList) {
        // 执行业务
        Result result = userService.deleteUserByIds(userIdList);
        // 响应
        return result;
    }

    // 修改用户的名称
    @RequestMapping("updateUser")
    public Result updateUser(@RequestBody User user, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        // 拿到当前登录的用户的id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();

        user.setUpdateBy(userId);

        // 执行业务
        Result result = userService.updateUserNameByUserId(user);

        return result;
    }

    // 重置密码
    @RequestMapping("/updatePwd/{userId}")
    public Result resetPassword(@PathVariable Integer userId) {
        // 执行业务
        Result result = userService.updatePwdByUserId(userId);
        // 响应
        return result;
    }
}
