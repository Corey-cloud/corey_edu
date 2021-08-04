package com.atguigu.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by corey on 2021/7/27
 **/

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.atguigu"}) //指定扫描位置
@MapperScan("com.atguigu.cmsservice.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
