package com.gaofei.user.controller;

import cn.hutool.jwt.JWT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : gaofee
 * @date : 15:59 2022/5/19
 * @码云地址 : https://feege.gitee.io
 */
@RestController
@RequestMapping("user")
@Api("APP用户操作相关的接口,用户controller")
public class UserController {

    @RequestMapping("/login")
    @ApiOperation(value="用户登录",notes="账户和密码进行校验")
    @ApiImplicitParam(name = "username",value = "登录账户",required = true,dataType = "String")
    public Object login(String username,String password){
        String dbUsername = "admin";
        String dbPassword = "admin";
        //拿着username和password去数据库查询
        if(username.equals(dbUsername)&& password.equals(dbPassword)){
            //登录成功
            //如果能查询出来,说明有对应用户
            //生成jwt  token
            String token = JWT.create()
                    .setPayload("username", "gaofei")
                    .setPayload("role", "admin")
                    .setKey("gaofei".getBytes())
                    .sign();
            //返回给前端
            return token;
        }

        return "error";
    }

    @PostMapping("/list")
    @ApiOperation(value="用户列表",notes="pageNum当前页,pageSize每页显示大小")
    public String list(){
        System.out.println("用户2服务");
        return "这是user服务的list接口!!******";
    }

}
