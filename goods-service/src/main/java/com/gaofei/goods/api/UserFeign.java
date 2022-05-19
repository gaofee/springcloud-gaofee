package com.gaofei.goods.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : gaofee
 * @date : 16:10 2022/5/19
 * @码云地址 : https://feege.gitee.io
 */
@FeignClient(value = "user-service",fallback = UserCallback.class)
@Component
public interface UserFeign {

    @RequestMapping("/list")
    public String list();
}
