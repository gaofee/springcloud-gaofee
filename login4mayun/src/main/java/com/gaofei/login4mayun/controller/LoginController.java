package com.gaofei.login4mayun.controller;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/oauth")
public class LoginController {



    @RequestMapping("/render")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }


    @RequestMapping("/callback")
    public Object login(AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest();
        return authRequest.login(callback);
    }

    private AuthRequest getAuthRequest() {
        AuthRequest authRequest = new AuthGiteeRequest(AuthConfig.builder()
                .clientId("7fb8c7c5f6061a82c2c43f300657185a20a3b49faad7e39cfa1403505628e3cb")
                .clientSecret("6ca6b97d81855f86b04d87eccf2045457ee3d784c389f7169e7eb56c58bc2750")
                .redirectUri("http://127.0.0.1:8088/oauth/callback")
                .build());
        return authRequest;
    }
}
