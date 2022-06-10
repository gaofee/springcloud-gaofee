package com.gaofei.auth.controller;

import com.gaofei.auth.service.LoginServcie;
import com.gaofei.common.security.domain.User;
import com.gaofei.common.security.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/user")
public class LoginController {


    @Autowired
    private LoginServcie loginServcie;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return loginServcie.login(user);
    }

    @RequestMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:list')") //声明所需的权限
    public String list(){
        return "list";
    }
    @RequestMapping("/del")
    @PreAuthorize("hasAuthority('sys:user:del')")
    public String del(){
        return "del";
    }

}
