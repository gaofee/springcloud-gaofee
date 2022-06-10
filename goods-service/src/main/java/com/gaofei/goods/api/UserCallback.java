package com.gaofei.goods.api;

import org.springframework.stereotype.Component;

/**
 * @author : gaofee
 * @date : 16:21 2022/5/19
 * @码云地址 : https://feege.gitee.io
 */
//@Component
public class UserCallback implements UserFeign{
    @Override
    public String list() {
        //熔断降级后的逻辑
        return "用户服务开小差了.请联系管理员!!!";
    }
}
