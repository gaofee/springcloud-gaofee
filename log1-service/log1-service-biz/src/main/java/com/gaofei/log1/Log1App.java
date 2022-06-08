package com.gaofei.log1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author : gaofee
 * @date : 16:00 2022/6/7
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.gaofei.log1.api")

public class Log1App {

    public static void main(String[] args) {
        SpringApplication.run(Log1App.class, args);
    }
}
