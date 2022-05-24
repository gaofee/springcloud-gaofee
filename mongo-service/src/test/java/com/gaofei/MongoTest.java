package com.gaofei;

import cn.hutool.core.util.DesensitizedUtil;
import com.gaofei.dao.UserRes;
import com.gaofei.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author : gaofee
 * @date : 15:55 2022/5/23
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoTest {
    @Autowired
    UserRes userRes;

    @Autowired
    MongoTemplate mongoTemplate;
    @Test
    public void testSave(){
        String name = "高飞";
        char[] chars = name.toCharArray();
        System.out.println(Arrays.toString(chars));
        name=chars[0]+"**";
        System.out.println(name);
        String phone = "13938087654";//139****7654
        System.out.println(DesensitizedUtil.mobilePhone(phone));

        /*User user = new User();
        user.setId(1);
        user.setBirthday(new Date());
        user.setName("zhangsan");
//        userRes.save(user);
//        mongoTemplate.save(user);
        *//*List<User> zhangsan = userRes.findUserByNameIsLikeAndIdEquals("zhang", 1);
        System.out.println(zhangsan);*//*

        Query query = new Query();
//        query.addCriteria(Criteria.where("name").is("zhangsan"));
        query.addCriteria(Criteria.where("id").is(1));

        List<User> users = mongoTemplate.find(query, User.class);
        System.out.println(users);*/
    }
}
