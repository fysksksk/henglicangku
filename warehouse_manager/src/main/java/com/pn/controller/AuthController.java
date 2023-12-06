package com.pn.controller;

import com.pn.entity.Auth;
import com.pn.entity.Result;
import com.pn.service.AuthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    // 查询所有权限菜单树
    @RequestMapping("/auth-tree")
    public Result loadAllAuthTree() {
        // 执行业务
        List<Auth> allAuth = authService.allAuthTree();
        // 响应
        return Result.ok(allAuth);
    }
}
