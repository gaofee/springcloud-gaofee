package com.gaofei.goods.controller;

import com.gaofei.goods.api.AuthFeign;
import com.gaofei.goods.api.UserFeign;
import com.gaofei.goods.domain.User;
import com.gaofei.goods.mapper.GoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : gaofee
 * @date : 16:02 2022/5/19
 * @码云地址 : https://feege.gitee.io
 */

@RestController
@Slf4j
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private AuthFeign authFeign;

    @Autowired
    private GoodsMapper goodsMapper;

    @RequestMapping("list")
    @Transactional
    public String list(){
        //在springcloud中不建议这么做
//        /*String s = HttpUtil.get("http://localhost:8081/list");
//        log.info("这是从user服务拿来的数据:{}",s);*/
        //远程调用,发送一次请求到远程auth服务
//        String s = authFeign.list();

        User user = new User();
        user.setName("goods");
        goodsMapper.insert(user);
        //模拟goods服务出异常了
//        int i = 1/0;
        return "goods接口的数据";
    }
}
