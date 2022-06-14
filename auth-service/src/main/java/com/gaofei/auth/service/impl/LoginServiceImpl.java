package com.gaofei.auth.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import com.gaofei.auth.domain.LoginUser;
import com.gaofei.auth.domain.User;
import com.gaofei.auth.service.LoginServcie;
import com.gaofei.auth.utils.JwtUtil;
import com.gaofei.auth.utils.RedisCache;
import com.gaofei.auth.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author : gaofee
 * @date : 16:41 2022/5/26
 * @码云地址 : https://feege.gitee.io
 */
@Service
public class LoginServiceImpl implements LoginServcie {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //      调用ProviderManager的方法进行认证 如果认证通过生成jwt
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        //这行代码走完,代表已经走完后面的过滤器的处理了
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
//        String jwt = JwtUtil.createJWT(userId);
        //使用hutool工具类生成token
        String jwt = JWT.create()
                .setPayload("userId", userId)
                .setKey("gaofei".getBytes()).setExpiresAt(DateTime.of(DateUtil.current()+1000*60*30))//token30分钟有效
                .sign();
        //​		把用户信息存入redis中
        redisCache.setCacheObject("login:"+userId,loginUser);
        //把token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return new ResponseResult(200,"登陆成功",map);
    }
}
