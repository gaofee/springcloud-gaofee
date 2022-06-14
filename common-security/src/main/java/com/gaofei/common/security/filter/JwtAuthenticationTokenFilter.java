package com.gaofei.common.security.filter;

import cn.hutool.jwt.JWT;

import com.gaofei.common.security.domain.LoginUser;
import com.gaofei.common.security.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,  IOException {
        String requestURI = request.getRequestURI();
        if(requestURI.equals("/user/login")){
            //放行
            filterChain.doFilter(request, response);
            return;
        }
        //获取token
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }
        //校验合法性
        if(!JWT.of(token).setKey("gaofei".getBytes()).validate(0)){
            //放行
            filterChain.doFilter(request, response);
            return;
        }

        //解析token
        String userid;
        try {
//            Claims claims = JwtUtil.parseJWT(token);
//            userid = claims.getSubject();
            //使用hutool工具类解析token
            JWT jwt = JWT.of(token);
            userid= (String) jwt.getPayload("userId");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        //此处的LoginUser必须和auth认证服务的user是同一个对象,否则就会序列化失败
        LoginUser loginUser = redisCache.getCacheObject(redisKey);

        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录");
        }
        //存入SecurityContextHolder
        //获取权限信息封装到Authentication中
        Collection<? extends GrantedAuthority> authorities = loginUser.getAuthorities();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,authorities);
        //把用户相关的信息设置到security 上下文中,让security认为用户是登录状态
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行

        filterChain.doFilter(request, response);
    }
}
