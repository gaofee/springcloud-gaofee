package com.gaofei.goods.api;

import com.gaofei.goods.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : gaofee
 * @date : 14:04 2022/5/30
 * @码云地址 : https://feege.gitee.io
 */
@FeignClient(value = "auth-service",configuration = {FeignConfig.class})
@Component
public interface AuthFeign {

    @RequestMapping("/user/list")
    public String list();
}
