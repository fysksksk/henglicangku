package com.pn.filter;

import com.alibaba.fastjson.JSON;
import com.pn.entity.Result;
import com.pn.utils.WarehouseConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class LoginCheckFilter implements Filter {

    private StringRedisTemplate stringRedisTemplate;

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        /*
        白名单直接放行
         */
        // 创建List<String>存放白名单url接口
        List<String> urlList = new ArrayList<String>();
        urlList.add("/captcha/captchaImage");
        urlList.add("/login");
        urlList.add("/logout");
        urlList.add("/product/img-upload");
        // 过滤拦截器拦截当前请求的url接口
        String url = request.getServletPath();
        if (urlList.contains(url) || url.contains("/img/upload")) { // 白名单放行
            chain.doFilter(request, response);
            return;
        }

        /*
        不是白名单
         */
        // 校验请求中是否带有token，并判断redis中是否存在token
        String token = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        if (StringUtils.hasText(token) && stringRedisTemplate.hasKey(token)) {
            // 有token
            chain.doFilter(request, response);
            return;
        }
        // 没有token，请求不放行，并给前端做出响应
        Result result = Result.err(Result.CODE_ERR_UNLOGINED, "您尚未登录！");
        String jsonStr = JSON.toJSONString(result);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(jsonStr);
        out.flush();
        out.close();
    }
}
