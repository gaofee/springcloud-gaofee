package com.gaofei.auth.controller;

import com.gaofei.auth.domain.User;
import com.gaofei.auth.service.LoginServcie;
import com.gaofei.auth.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : gaofee
 * @date : 10:51 2022/5/26
 * @码云地址 : https://feege.gitee.io
 */
@RestController
public class LoginController {


    @Autowired
    private LoginServcie loginServcie;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        return loginServcie.login(user);
    }

    @RequestMapping("list")
    public String list(){
        return "hello";
    }

}
