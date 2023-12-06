package com.pn.service.impl;

import com.alibaba.fastjson.JSON;
import com.pn.entity.Auth;
import com.pn.mapper.AuthMapper;
import com.pn.service.AuthService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AuthMapper authMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public List<Auth> queryAuthTreeByUid(Integer userId) {
        // 先从redis中查询是否有用户菜单树
        String authTreeJson = stringRedisTemplate.opsForValue().get("authTree" + userId);
        if (StringUtils.hasText(authTreeJson)) {
            // redis中的数据转为数组，并返回
            List<Auth> authTreeList = JSON.parseArray(authTreeJson, Auth.class);
            return authTreeList;
        }
        /*
        redis中没有数据
         */
        // 查询用户权限下的所有菜单
        List<Auth> allAuthList = authMapper.findAuthByUid(userId);
        // 将所有菜单List<Auth>转换为菜单树List<Auth>
        List<Auth> authTreeList = allAuthToAuthTree(allAuthList, 0);
        // 向redis中存储数据
        stringRedisTemplate.opsForValue().set("authTree:" + userId, JSON.toJSONString(authTreeList));
        return authTreeList;
    }

    // 查询所有权限菜单的方法
    public List<Auth> allAuthTree() {
        // 先从redis中查询是否有所有的菜单树
        String authTreeJson = stringRedisTemplate.opsForValue().get("authTree:all");
        if (StringUtils.hasText(authTreeJson)) {
            // redis中的数据转为数组，并返回
            List<Auth> authTreeList = JSON.parseArray(authTreeJson, Auth.class);
            return authTreeList;
        }
        /*
        redis中没有数据
         */
        // 查询所有权限菜单
        List<Auth> allAuthList = authMapper.findAllAuth();
        // 将所有权限菜单树List<Auth>转成权限菜单List<Auth>
        List<Auth> authTreeList = allAuthToAuthTree(allAuthList, 0);
        // 向redis中存储数据
        stringRedisTemplate.opsForValue().set("authTree:all", JSON.toJSONString(authTreeList));
        return authTreeList;
    }

    // 将权限集合转换为权限树
    private List<Auth> allAuthToAuthTree(List<Auth> allAuthList, Integer pid) {
        // 查询出所有一级菜单
        List<Auth> firstLevelAuthList = new ArrayList<Auth>();
        for (Auth auth : allAuthList) {
            if (auth.getParentId() == pid) {
                firstLevelAuthList.add(auth);
            }
        }
        // 拿到每个一级菜单的所有二级菜单
        for (Auth firstAuth : firstLevelAuthList) {
            List<Auth> secondLevelAuthList = allAuthToAuthTree(allAuthList, firstAuth.getAuthId());
            firstAuth.setChildAuth(secondLevelAuthList);
        }
        return firstLevelAuthList;
    }
}
