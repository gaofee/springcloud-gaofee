package com.gaofei.jpa.dao;

import com.gaofei.jpa.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author : gaofee
 * @date : 11:06 2022/5/24
 * @码云地址 : https://feege.gitee.io
 * 使用jpa的方式来操作用户表
 */
public interface UserRepository extends JpaRepository<User,Integer> {

    List<User> findUserByNameIsLike(String name);

    List<User> findUserByNameIsLikeAndIdEquals(String name,Integer id);

    Page<User> findUserByNameIsLike(String name, Pageable page);


    Page<User> findUserByBirthdayBetween(Date  s, Date e, Pageable page);

}
