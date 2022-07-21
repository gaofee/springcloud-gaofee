package com.gaofei.activiti;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.gaofei",exclude = SecurityAutoConfiguration.class)
@MapperScan("com.gaofei.activiti.mapper")
public class ActivitiApp {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApp.class, args);
    }
}
