package com.gaofei;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : gaofee
 * @date : 11:47 2022/5/20
 * @码云地址 : https://feege.gitee.io
 */
public class JwtTest {
    public static void main(String[] args) {
        // 密钥
        byte[] key = "gaofei".getBytes();
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Imdhb2ZlaSIsInJvbGUiOiJhZG1pbiJ9.tY8O5QE9SgtUVh0waz6MUKMXGIqZG5g_gGKWMCQXego
        /*String token = JWT.create()
                .setPayload("username", "gaofei")
                .setPayload("role", "admin")
                .setKey(key)
                .sign();
        System.out.println(token);*/

//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Imdhb2ZlaSIsInJvbGUiOiJhZG1pbiJ9.tY8O5QE9SgtUVh0waz6MUKMXGIqZG5g_gGKWMCQXego";
        String token = null;
        //验证合法性
        JWT jwt = JWT.of(token);
        // JWT
        Object header = jwt.getHeader(JWTHeader.TYPE);
        System.out.println(header);
        // HS256
        Object header1 = jwt.getHeader(JWTHeader.ALGORITHM);
        System.out.println(header1);

        System.out.println(jwt.getPayload("username"));
        System.out.println(jwt.getPayload("role"));


        System.out.println(JWT.of(token).setKey("gaofei".getBytes()).verify());
    }
}
