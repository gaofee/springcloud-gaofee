package com.gaofei.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author : gaofee
 * @date : 11:20 2022/5/19
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootApplication
//声明开启eureka的客户端
@EnableEurekaClient
public class UserApp {
    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }
}
