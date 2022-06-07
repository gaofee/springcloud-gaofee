package com.gaofei.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : gaofee
 * @date : 14:59 2022/6/6
 * @码云地址 : https://feege.gitee.io
 */
@FeignClient(value = "goods-service")
@Component
public interface GoodsFeign {
    @RequestMapping("/goods/list")
    public String list();
}
