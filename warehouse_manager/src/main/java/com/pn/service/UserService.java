package com.pn.service;

import com.pn.dto.AssignRoleDto;
import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.page.Page;

import java.util.List;

public interface UserService {

    // 根据用户名查询用户信息
    public User queryUserByCode(String userCode);

    // 分页查询用户信息
    public Page queryUserByPage(Page page, User user);

    // 添加用户的方法
    public Result saveUser(User user);

    // 根据用户id修改用户状态的方法
    public Result updateUserState(User user);

    // 给用户分配角色
    public void assignRole(AssignRoleDto assignRoleDto);

    // 删除用户的业务方法
    public Result deleteUserByIds(List<Integer> userIdList);

    // 根据用户id修改用户昵称
    public Result updateUserNameByUserId(User user);

    // 根据用户id修改密码
    public Result updatePwdByUserId(Integer userId);
}
