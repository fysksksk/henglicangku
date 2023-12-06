package com.pn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

// 开启redis注解缓存
@EnableCaching
// mapper接口扫描器，指明mapper接口所在的包，然后就会自动为mapper接口创建代理对象，并加入到IOC容器中。
@MapperScan(basePackages = "com.pn.mapper")
@SpringBootApplication
public class WarehouseManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseManagerApplication.class, args);
    }

}
