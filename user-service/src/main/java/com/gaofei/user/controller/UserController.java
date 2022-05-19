package com.gaofei.user.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : gaofee
 * @date : 15:59 2022/5/19
 * @码云地址 : https://feege.gitee.io
 */
@RestController
@RequestMapping("user")
public class UserController {


    @RequestMapping("/list")
    public String list(){
        return "这是user服务的list接口!!******";
    }

}
