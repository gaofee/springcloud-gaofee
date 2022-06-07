package com.gaofei.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author : gaofee
 * @date : 16:00 2022/6/7
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootApplication
@EnableEurekaClient

public class LogApp {

    public static void main(String[] args) {
        SpringApplication.run(LogApp.class, args);
    }
}
