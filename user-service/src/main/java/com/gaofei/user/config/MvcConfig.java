package com.gaofei.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : gaofee
 * @date : 11:43 2022/4/21
 * @码云地址 : https://feege.gitee.io
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")  //相当于根据项目访问图片的路径
                .addResourceLocations("file:D:/upload/");//对应的物理路径
    }
}
