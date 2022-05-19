package com.gaofei.goods.controller;

import cn.hutool.http.HttpUtil;
import com.gaofei.goods.api.UserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : gaofee
 * @date : 16:02 2022/5/19
 * @码云地址 : https://feege.gitee.io
 */

@RestController
@Slf4j
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private UserFeign userFeign;

    @RequestMapping("list")
    public String list(){
        //在springcloud中不建议这么做
        /*String s = HttpUtil.get("http://localhost:8081/list");
        log.info("这是从user服务拿来的数据:{}",s);*/
        String s = userFeign.list();

        return s;
    }
}
