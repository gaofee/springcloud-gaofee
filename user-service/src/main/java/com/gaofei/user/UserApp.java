package com.gaofei.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author : gaofee
 * @date : 11:20 2022/5/19
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootApplication
//声明开启eureka的客户端
@EnableEurekaClient
@MapperScan("com.gaofei.user.mapper")
//开启远程调用
@EnableFeignClients
@EnableTransactionManagement //开启事务管理
public class UserApp {
    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }
}
