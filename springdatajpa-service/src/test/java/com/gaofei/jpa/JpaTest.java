package com.gaofei.jpa;

import com.gaofei.jpa.dao.UserRepository;
import com.gaofei.jpa.domain.Orders;
import com.gaofei.jpa.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author : gaofee
 * @date : 11:21 2022/5/24
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JpaTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void list(){
        Specification specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                User user = new User();
                user.setName("lisi");
                user.setId(1);
                //保存查询条件
                List<Predicate> predicates = new ArrayList<>();
                //模糊查询
                if(!StringUtils.isEmpty(user.getName())){
                    Predicate username = cb.like(root.get("name"), "%".concat(user.getName()).concat("%"));
                    predicates.add(username);
                }
                //等于
                if(!StringUtils.isEmpty(user.getId())){
                    Predicate password = cb.equal(root.get("id"), user.getId());
                    predicates.add(password);
                }
               /* //大于等于
                if(userEntity.getStartDate()!=null){
                    Predicate predicate = cb.greaterThanOrEqualTo(root.get("created"), userEntity.getStartDate());
                    predicates.add(predicate);
                }
                //小于等于
                if(userEntity.getEndDate()!=null){
                    Predicate predicate = cb.lessThanOrEqualTo(root.get("created"), userEntity.getEndDate());
                    predicates.add(predicate);
                }*/
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Pageable pageable = PageRequest.of(0,2, Sort.Direction.DESC,"id");
        userRepository.findAll(specification,pageable);



    }



    @Test
    public void search(){
        User user = new User();
        user.setName("李");
        PageRequest of = PageRequest.of(1, 2);
        Page<User> userByNameIsLike = userRepository.findUserByNameIsLike("李%", of);
        System.out.println(userByNameIsLike.getContent());

//        List<User> all = userRepository.findAll();
//        System.out.println(userRepository.findById(1));
//        System.out.println(userRepository.findUserByNameIsLike("李%"));
//        System.out.println(userRepository.findUserByNameIsLikeAndIdEquals("李%",2));

        //构造条件
//        Example<User> example = Example.of(user, ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith()));
        Example<User> example = Example.of(user, ExampleMatcher.matching().withMatcher("name", ma->ma.startsWith()));
////        分页                            当前页-1,每页显示多少条
//        PageRequest of = PageRequest.of(0, 2);
//        Page<User> all = userRepository.findAll(example, of);
//        System.out.println(all.getContent());
    }

    @Test
    public void save(){
        User user = new User();
        user.setBirthday(new Date());
        user.setName("wangwu1122");


        Orders o1 = new Orders();
        Orders o2 = new Orders();
        o1.setOrderDetails("订单1");
        o2.setOrderDetails("订单2");

        o1.setUsers(user);
        o2.setUsers(user);

        List<Orders> orders = Arrays.asList(o1, o2);

        user.setOrdersList(orders);

        userRepository.save(user);
    }
}
