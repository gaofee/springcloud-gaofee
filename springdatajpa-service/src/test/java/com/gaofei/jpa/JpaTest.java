package com.gaofei.jpa;

import com.gaofei.jpa.dao.UserRepository;
import com.gaofei.jpa.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void search(){
        User user = new User();

//        List<User> all = userRepository.findAll();
        System.out.println(userRepository.findById(1));
        System.out.println(userRepository.findUserByNameIsLike("李%"));
        System.out.println(userRepository.findUserByNameIsLikeAndIdEquals("李%",2));


    }

    @Test
    public void save(){
        User user = new User();
//        user.setId(1);
        user.setBirthday(new Date());
        user.setName("lisi");
        userRepository.save(user);
    }
}
