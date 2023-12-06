package com.pn.service.impl;

import com.pn.entity.UserRole;
import com.pn.mapper.UserRoleMapper;
import com.pn.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;


}
