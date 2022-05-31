package com.gaofei.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author : gaofee
 * @date : 10:24 2022/5/31
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.gaofei.es.repositories")
public class SearchApp {
    public static void main(String[] args) {
        SpringApplication.run(SearchApp.class, args);
    }
}
