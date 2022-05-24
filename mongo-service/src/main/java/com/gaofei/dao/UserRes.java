package com.gaofei.dao;

import com.gaofei.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author : gaofee
 * @date : 16:00 2022/5/23
 * @码云地址 : https://feege.gitee.io
 */
public interface UserRes extends MongoRepository<User,Integer> {
    List<User> findUserByNameIsLikeAndIdEquals(String name, Integer id);
}
