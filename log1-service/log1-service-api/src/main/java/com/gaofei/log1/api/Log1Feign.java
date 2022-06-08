package com.gaofei.log1.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : gaofee
 * @date : 11:02 2022/6/8
 * @码云地址 : https://feege.gitee.io
 */
@FeignClient(value = "log1-service")
@Component
public interface Log1Feign {
    @RequestMapping("/log1/save")
    public String save(@RequestParam("uuid") String uuid, @RequestParam("msg")String msg);
}
