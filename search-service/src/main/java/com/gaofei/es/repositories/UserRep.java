package com.gaofei.es.repositories;

import com.gaofei.es.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author : gaofee
 * @date : 10:29 2022/5/31
 * @码云地址 : https://feege.gitee.io
 */
public interface UserRep extends ElasticsearchRepository<User,Integer> {


}
