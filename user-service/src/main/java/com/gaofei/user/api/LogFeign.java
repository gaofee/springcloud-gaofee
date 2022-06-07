package com.gaofei.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : gaofee
 * @date : 16:21 2022/6/7
 * @码云地址 : https://feege.gitee.io
 */
@FeignClient(value = "log-service")
@Component
public interface LogFeign {
    @RequestMapping("/log/save")
    public String save(@RequestParam("uuid") String uuid, @RequestParam("msg")String msg);
}
