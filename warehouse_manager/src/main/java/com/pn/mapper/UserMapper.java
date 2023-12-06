package com.pn.mapper;

import com.pn.entity.User;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    // 根据用户名查询用户信息
    public User findUserByCode(@Param("userCode") String userCode);

    // 查询用户行数的方法
    public Integer findUserRowCount(User user);

    // 分页查询用户的方法
    public List<User> findUserByPage(@Param("page") Page page, @Param("user") User user);

    // 添加用户的方法
    public int insertUser(User user);

    // 根据用户id修改用户状态的方法
    public int updateStateByUid(@Param("userId") Integer userId, @Param("userState") String userState);

    // 根据用户ids把用户的状态设置为删除状态
    public int setIsDeleteByUids(List<Integer> userIdList);

    // 根据用户id修改用户昵称
    public int updateUserNameByUserId(User user);

    // 根据用户id修改密码
    public int updatePwdByUserId(@Param("userId") Integer userId, @Param("password") String password);
}
