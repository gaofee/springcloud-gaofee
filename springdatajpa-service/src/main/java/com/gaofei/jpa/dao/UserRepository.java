package com.gaofei.jpa.dao;

import com.gaofei.jpa.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author : gaofee
 * @date : 11:06 2022/5/24
 * @码云地址 : https://feege.gitee.io
 * 使用jpa的方式来操作用户表                                               实现复杂查询的接口
 */
public interface UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {

    List<User> findUserByNameIsLike(String name);

    List<User> findUserByNameIsLikeAndIdEquals(String name,Integer id);

    Page<User> findUserByNameIsLike(String name, Pageable page);


    Page<User> findUserByBirthdayBetween(Date  s, Date e, Pageable page);

    //使用自己写的sql来查询
    @Query(nativeQuery=true,value="select * from t_user where name = ?1 and id = ?2")
    List<User> list(String name,Integer id );

    //使用hibernate 提供的jpql来查询  类似于面向对象的查询方式
//    @Query(value="from User u where u.name = ?1 and u.id =?2 ")
    @Query(value="from User u where u.name = :nm and u.id =:idd ")
    List<User> list1(@Param("nm") String name, @Param("idd") Integer id );

    //:#{#对象名称.对象属性}
    @Transactional
    @Query(nativeQuery=true,value="insert into t_user (name,birthday) values (:#{#user.name},:#{#user.birthday})")
    @Modifying  //要对数据进行修改或者添加操作
    void add(User user);

}
