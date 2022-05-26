import com.gaofei.auth.AuthApp;
import com.gaofei.auth.domain.User;
import com.gaofei.auth.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.util.List;

/**
 * @author : gaofee
 * @date : 11:56 2022/5/26
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootTest(classes = AuthApp.class)
@RunWith(SpringRunner.class)
public class TestDemo {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testPasswordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePassword = bCryptPasswordEncoder.encode("1234");
        System.out.println(encodePassword);

        boolean matches = bCryptPasswordEncoder.matches("1234", "$2a$10$kb.0CWCN7YTwtKoMeC8AKeT4kzvmz1CKuq1Kje0cLvSSTdpSvV1bu");
        System.out.println(matches);
    }

    @Test
    public void testmb(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }
}
