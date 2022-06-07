package com.gaofei;

import com.gaofei.user.UserApp;
import com.gaofei.user.domain.User;
import com.gaofei.user.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : gaofee
 * @date : 16:27 2022/6/5
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootTest(classes = UserApp.class)
@RunWith(SpringRunner.class)
public class MbpTest {

    @Resource
    UserMapper userMapper;

    @Test
     //把此方法交给事务来管理
    public void t(){
        User user = new User();
        user.setName("宏碁");
        userMapper.insert(user);


    }

}
