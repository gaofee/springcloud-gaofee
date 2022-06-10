package com.gaofei.goods.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : gaofee
 * @date : 10:43 2022/5/28
 * @码云地址 : https://feege.gitee.io
 */
//@Configuration
public class FeignConfig implements RequestInterceptor {


    @Override
    public void apply(RequestTemplate requestTemplate) {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(),true);
        // 获取当前请求Spring信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取请求体
        HttpServletRequest request = attributes.getRequest();
        // 获取Header、或参数等
        String token = request.getHeader("token");
        // 注入Feign请求体
        if(token==null){
            throw new RuntimeException("token为空,请先登录!!");
        }
        requestTemplate.header("token", token );
    }

}
