package com.gaofei.goods.controller;

import com.gaofei.goods.api.UserFeign;
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

    /*@Autowired
    private AuthFeign authFeign;

    @Autowired
    private GoodsMapper goodsMapper;*/

    @RequestMapping("list")
    public String list(){

        return "goods接口的数据:"+userFeign.list();
    }
}
