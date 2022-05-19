package com.gaofei.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author : gaofee
 * @date : 16:47 2022/5/19
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootApplication
//注册到eureka
@EnableEurekaClient
public class GateApp {
    public static void main(String[] args) {
        SpringApplication.run(GateApp.class, args);
    }
}
