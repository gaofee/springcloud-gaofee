package com.gaofei.auth.service;

import com.gaofei.auth.domain.User;
import com.gaofei.auth.utils.ResponseResult;

/**
 * @author : gaofee
 * @date : 16:41 2022/5/26
 * @码云地址 : https://feege.gitee.io
 */
public interface LoginServcie {
    ResponseResult login(User user);
}
