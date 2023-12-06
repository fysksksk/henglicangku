package com.pn.service.impl;

import com.pn.dto.AssignRoleDto;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.entity.User;
import com.pn.entity.UserRole;
import com.pn.mapper.RoleMapper;
import com.pn.mapper.UserMapper;
import com.pn.mapper.UserRoleMapper;
import com.pn.page.Page;
import com.pn.service.UserService;
import com.pn.utils.DigestUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    // 根据用户名查询用户信息
    public User queryUserByCode(String userCode) {
        return userMapper.findUserByCode(userCode);
    }

    // 分页查询用户信息
    public Page queryUserByPage(Page page, User user) {
        // 查询用户行数
        Integer count = userMapper.findUserRowCount(user);
        // 分页查询用户信息
        List<User> userList = userMapper.findUserByPage(page, user);
        // 组装分页信息
        page.setResultList(userList);
        page.setTotalNum(count);

        return page;
    }

    // 添加用户的方法
    @Transactional(rollbackFor = Exception.class)
    public synchronized Result saveUser(User user) {
        // 判断账号是否已存在
        User u = userMapper.findUserByCode(user.getUserCode());
        if (u != null) {
            return Result.err(Result.CODE_ERR_BUSINESS, "账号已存在！");
        }
        String password = DigestUtil.hmacSign(user.getUserPwd());
        user.setUserPwd(password);

        int i = userMapper.insertUser(user);
        if (i > 0) {
            return Result.ok("用户添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "用户添加失败");
    }

    // 根据用户id修改用户状态的方法
    public Result updateUserState(User user) {
        int i = userMapper.updateStateByUid(user.getUserId(), user.getUserState());
        if (i > 0) {
            return Result.ok("启用或禁用用户成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "启用或禁用用户失败！");
    }

    // 给用户分配角色
    @Transactional(rollbackFor = Exception.class)
    public synchronized void assignRole(AssignRoleDto assignRoleDto) {
        userRoleMapper.removeUserRoleByUserId(assignRoleDto.getUserId());
        for (String roleName : assignRoleDto.getRoleCheckList()) {
            Integer roleId = roleMapper.findRoleIdByRoleName(roleName);
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(assignRoleDto.getUserId());
            userRoleMapper.insertUserRole(userRole);
        }
    }

    // 删除用户的业务方法
    @Transactional(rollbackFor = Exception.class)
    public Result deleteUserByIds(List<Integer> userIdList) {
        int i = userMapper.setIsDeleteByUids(userIdList);
        if (i > 0) {
            return Result.ok("用户删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "用户删除失败");
    }

    // 根据用户id修改用户昵称
    @Transactional(rollbackFor = Exception.class)
    public Result updateUserNameByUserId(User user) {
        int i = userMapper.updateUserNameByUserId(user);
        if (i > 0) {
            return Result.ok("用户修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "用户修改失败！");
    }

    // 根据用户id修改密码
    @Transactional(rollbackFor = Exception.class)
    public Result updatePwdByUserId(Integer userId) {
        // 给定初始密码123456并加密
        String password = DigestUtil.hmacSign("123456");

        // 根据用户id修改密码
        int i = userMapper.updatePwdByUserId(userId, password);
        if (i > 0) {
            return Result.ok("密码重置成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "密码重置失败！");
    }
}
