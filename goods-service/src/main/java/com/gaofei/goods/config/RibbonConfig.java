package com.gaofei.goods.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : gaofee
 * @date : 10:38 2022/5/20
 * @码云地址 : https://feege.gitee.io
 */
//是一个配置类
@Configuration
public class RibbonConfig {

    /*
    声明负载均衡策略为随机
     */
    @Bean
    public IRule rule(){
       return new RandomRule();
    }
}
