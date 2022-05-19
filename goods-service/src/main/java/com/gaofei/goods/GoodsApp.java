package com.gaofei.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author : gaofee
 * @date : 15:55 2022/5/19
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootApplication
//声明他是eureka的客户端,注册进eureka
@EnableEurekaClient
//开启远程调用
@EnableFeignClients
//开启熔断
@EnableHystrix
public class GoodsApp {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApp.class, args);
    }
}
