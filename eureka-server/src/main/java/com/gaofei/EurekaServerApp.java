package com.gaofei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author : gaofee
 * @date : 11:00 2022/5/19
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootApplication
//声明该服务是一个eureka服务端
@EnableEurekaServer
public class EurekaServerApp {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApp.class, args);
    }
}
