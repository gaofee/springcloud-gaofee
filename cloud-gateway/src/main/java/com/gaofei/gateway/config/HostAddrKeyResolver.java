package com.gaofei.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author : gaofee
 * @date : 15:41 2021/6/7
 * @码云地址 : https://gitee.com/itgaofee
 *
 * 指定按照ip地址来限流
 */
@Component("addressKey")
public class HostAddrKeyResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        String hostAddress = exchange.getRequest().getRemoteAddress().getAddress().getHostName();
        System.out.println(hostAddress+"==================");
        //根据地址限流
        return Mono.just(hostAddress);
    }
}
