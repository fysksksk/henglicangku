package com.pn.controller;

import com.google.code.kaptcha.Producer;
import com.pn.entity.*;
import com.pn.service.AuthService;
import com.pn.service.UserService;
import com.pn.utils.DigestUtil;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Resource
    private AuthService authService;

    @Resource(name = "captchaProducer")
    private Producer producer;

    @Resource
    private UserService userService;

    @Resource
    private TokenUtils tokenUtils;

    // 注入redis模板
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // 生成验证码图片
    @RequestMapping("/captcha/captchaImage")
    public void captchaImage(HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            // 生成验证码图片里的文字
            String text = producer.createText();
            // 使用验证码文本生成验证码图片，BufferedImage对象就代表生成的验证码图片，在内存中
            BufferedImage image = producer.createImage(text);
            // 将验证码文本作为键保存到redis中 -- 设置过期时间为30分钟
            stringRedisTemplate.opsForValue().set(text, "", 60 * 5, TimeUnit.SECONDS);

            /*
            将验证码响应给前端
             */
            // 设置响应正文image/jpeg
            response.setContentType("image/jpeg");
            out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
            // 刷新
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 登录
    @RequestMapping("/login")
    public Result login(@RequestBody LoginUser loginUser) {
        // 检验验证码是否正确
        String code = loginUser.getVerificationCode();
        if (!stringRedisTemplate.hasKey(code)) {
            return Result.err(Result.CODE_ERR_BUSINESS, "验证码错误！");
        }
        // 根据用户名查询用户信息
        User user = userService.queryUserByCode(loginUser.getUserCode());
        if (user != null) { // 账号存在
            if (user.getUserState().equals(WarehouseConstants.USER_STATE_PASS)) { // 用户已审核
                // 拿到用户输入的密码
                String userPwd = loginUser.getUserPwd();
                // 进行md5加密
                userPwd = DigestUtil.hmacSign(userPwd);
                if (userPwd.equals(user.getUserPwd())) { // 密码正确
                    // 生成 jwt token 并存入到redis中
                    CurrentUser currentUser =
                            new CurrentUser(user.getUserId(), user.getUserCode(), user.getUserName());
                    String token = tokenUtils.loginSign(currentUser, userPwd);
                    // 向客户端响应 jwt token
                    return Result.ok("登录成功！", token);
                } else { // 密码错误
                    return Result.err(Result.CODE_ERR_BUSINESS, "密码错误！");
                }
            } else { // 用户未审核
                return Result.err(Result.CODE_ERR_BUSINESS, "用户未审核！");
            }
        } else { // 用户不存在
            return Result.err(Result.CODE_ERR_BUSINESS, "账号不存在！");
        }
    }

    // 获取当前登录的用户的信息
    @RequestMapping("/curr-user")
    public Result currentUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        // 解析token拿到封装了当前登录用户信息的CurrentUser对象
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        // 响应
        return Result.ok(currentUser);
    }

    // 加载用户权限菜单树
    @RequestMapping("/user/auth-list")
    public Result loadAuthTree(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        // 拿到当前登录的用户的id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();

        // 查询菜单树
        List<Auth> authTreeList = authService.queryAuthTreeByUid(userId);

        // 响应
        return Result.ok(authTreeList);
    }

    // 退出登录
    @RequestMapping("/logout")
    public Result logout(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        // 从redis中删除token的键
        stringRedisTemplate.delete(token);
        // 响应
        return Result.ok("退出系统！");
    }
}
