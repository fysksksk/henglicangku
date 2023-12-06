package com.pn.config;

import com.pn.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@Configuration
public class ServletConfig {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /*
    配置FilterRegistrationBean的bean对象 -- 注册原生Servlet中的过滤器
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        // 创建FilterRegistrationBean的bean对象
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        // 创建自定义的过滤器
        LoginCheckFilter loginCheckFilter = new LoginCheckFilter();
        // 手动注入redis模板
        loginCheckFilter.setStringRedisTemplate(stringRedisTemplate);
        // 将自定义的过滤器注册到FilterRegistrationBean中
        filterRegistrationBean.setFilter(loginCheckFilter);
        // 给过滤器指定拦截的请求
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
