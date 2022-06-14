package com.gaofei.user.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.Console;
import cn.hutool.jwt.JWT;
import com.gaofei.log1.api.Log1Feign;
import com.gaofei.user.api.LogFeign;
import com.gaofei.user.domain.User;
import com.gaofei.user.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * @author : gaofee
 * @date : 15:59 2022/5/19
 * @码云地址 : https://feege.gitee.io
 */
@RestController
@RequestMapping("user")
@Api("APP用户操作相关的接口,用户controller")
public class UserController {

    @Resource
    RedisTemplate redisTemplate;

    @RequestMapping("/login")
    @ApiOperation(value="用户登录",notes="账户和密码进行校验")
    @ApiImplicitParam(name = "username",value = "登录账户",required = true,dataType = "String")
    public Object login(String userName,String password,String code){
        String dbUsername = "admin";
        String dbPassword = "1234";
        //比对验证码
        if(code ==null || code.equals("")){
            return "codeerror";
        }

        //验证码不为null的情况开始比对验证码
        //从redis中获取生成的时候的验证码,和前端传过来的码做比对
        String redisCode = (String)redisTemplate.opsForValue().get("capCode");
        if(redisCode==null||redisCode.equals("")){
            return "codeexpire";
        }
        //把验证码都转成小写在做比对
        if(redisCode.toLowerCase().equals(code.toLowerCase())){
            //拿着username和password去数据库查询
            if(userName.equals(dbUsername)&& password.equals(dbPassword)){
                //登录成功
                //如果能查询出来,说明有对应用户
                //生成jwt  token
                String token = JWT.create()
                        .setPayload("username", "gaofei")
                        .setPayload("role", "admin")
                        .setKey("gaofei".getBytes())
                        .sign();

                redisTemplate.opsForValue().set("tokenId",token,1,TimeUnit.DAYS);
                //返回给前端
                return token;
        }

        }

        return "error";
    }

    @Resource
    UserMapper userMapper;

    /*@Resource
    GoodsFeign goodsFeign;*/
    @RequestMapping("/list")
    @ApiOperation(value="用户列表",notes="pageNum当前页,pageSize每页显示大小")
    @Transactional  //本地事务,只针对一个数据库有效,不能够同时控制多个数据库的事务
    public String list(){
        /*User user = new User();
        user.setName("users");
        userMapper.insert(user);
        //1库保存
        //2库保存
        String list = goodsFeign.list();
        int i = 1/0;*/

        System.out.println("user-service11111111111111");


        return "这是user服务的list接口!!******";
    }


    @Resource
    private LogFeign logFeign;

    @Resource
    private Log1Feign log1Feign;

    @RequestMapping("aaaa")
    public  String aaaa(){
        log1Feign.save(UUID.randomUUID().toString().replace("-", ""), "参数异常");
        return "success";
    }


    @RequestMapping("captcha")
    public  Object captcha(){
        try {
            //定义图形验证码的长和宽
            LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

            //图形验证码写出，可以写出到文件，也可以写出到流
            lineCaptcha.write("d:/upload/line.png");
            //输出code
            Console.log(lineCaptcha.getCode());


//            int i = 1/0;
            //把生成的验证码存入redis,过期时间是1分钟
            redisTemplate.opsForValue().set("capCode",lineCaptcha.getCode(), 60, TimeUnit.SECONDS);
            //验证图形验证码的有效性，返回boolean值
//        lineCaptcha.verify("1234");


        /*//重新生成验证码
        lineCaptcha.createCode();
        lineCaptcha.write("d:/line.png");
        //新的验证码
        Console.log(lineCaptcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        lineCaptcha.verify("1234");*/
        } catch (Exception e) {


            //远程调用日志服务,保存日志
            logFeign.save(UUID.randomUUID().toString().replace("-", ""), e.getMessage()+"");
        }
        return "http://192.168.21.1:8081/upload/line.png?time="+new Date().getTime();
    }

}
