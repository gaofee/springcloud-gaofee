package com.gaofei.gateway.filter;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author : gaofee
 * @date : 11:17 2022/5/20
 * @码云地址 : https://feege.gitee.io
 */
//@Component
public class LoginFilter implements GlobalFilter, Ordered {
    @Resource
    RedisTemplate redisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpResponse response = exchange.getResponse();
        System.out.println("执行了过滤器的代码!!");
        //1.首先对登录请求进行放行
        String path = exchange.getRequest().getURI().getPath();
        if(path.contains("/login")){
            return chain.filter(exchange);
        }
        if(path.contains("/captcha")){
            return chain.filter(exchange);
        }
        String redisToken = (String) redisTemplate.opsForValue().get("tokenId");
        //2.从请求头中获取token
        String token = exchange.getRequest().getHeaders().getFirst("token");
        //3.检查token是否合法jwt
        if(redisToken==null||StringUtils.isBlank(token) || !JWT.of(token).setKey("gaofei".getBytes()).validate(0)){
            //不合法
            HashMap<String, Object> params = new HashMap<>();
            params.put("code",401);
            params.put("msg", "登录过期或者token不合法");
            response.getHeaders().add("Content-Type","application/json;charset=UTF-8");
            //设置未认证的状态码401
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //进行数据转换
            DataBuffer wrap = response.bufferFactory().wrap(JSONUtil.toJsonStr(params).getBytes());
            return response.writeWith(Flux.just(wrap));
        }
        //3.1如果redisToken和请求头中token不一致-->token被篡改

        //4.合法就放行,
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
