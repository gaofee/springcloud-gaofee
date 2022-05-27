package com.gaofei.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gaofei.auth.domain.LoginUser;
import com.gaofei.auth.domain.User;
import com.gaofei.auth.mapper.MenuMapper;
import com.gaofei.auth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author : gaofee
 * @date : 15:56 2022/5/26
 * @码云地址 : https://feege.gitee.io
 * loadUserByUsername方法默认是从内存中查询的用户
 * 重写springsecurity中的loadUserByUsername方法,
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,username);
        //从数据库查询出来的用户
        User user = userMapper.selectOne(wrapper);
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        // 根据用户查询权限信息 添加到LoginUser中
        List<String> permissions =  menuMapper.selectPermsByUserId(user.getId());
//        List<String> permissions = new ArrayList<>(Arrays.asList("sys:user:list","sys:user:del"));
        //封装成UserDetails对象返回
        return new LoginUser(user,permissions);
    }
}
