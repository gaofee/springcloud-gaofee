package com.gaofei.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author : gaofee
 * @date : 10:49 2022/5/26
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootApplication
@MapperScan("com.gaofei.auth.mapper")
@EnableEurekaClient
public class AuthApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }
}
