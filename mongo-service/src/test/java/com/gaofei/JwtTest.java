package com.gaofei;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.jwt.JWT;
import com.gaofei.dao.UserRes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author : gaofee
 * @date : 15:55 2022/5/23
 * @码云地址 : https://feege.gitee.io
 */

public class JwtTest {



    public static void main(String[] args) throws ParseException {
        //2022-06-13 10:47:57
        DateTime now = DateTime.of(DateUtil.current()+1000*60*5);
        System.out.println(now);

        // 密钥
        byte[] key = "gaofei".getBytes();

        String token = JWT.create()
                .setPayload("sub", "1234567890")
                .setPayload("name", "looly")
                .setPayload("admin", true)
                .setKey(key).setExpiresAt(now)
                .sign();

//        String string = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Imxvb2x5IiwiYWRtaW4iOnRydWUsImV4cCI6MTY1NTA4ODQ3N30.kDjZqOanwUUR30Qm5Un321YZdEAIPjNdXNxcYwm6FqE";
        System.out.println(token);
        boolean verify = JWT.of(token).setKey("gaofei".getBytes()).validate(0);
        System.out.println(verify);
    }
}
