package com.gaofei.log;

import com.gaofei.log.domain.SysLog;
import com.gaofei.log.repository.LogRep;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author : gaofee
 * @date : 16:06 2022/6/7
 * @码云地址 : https://feege.gitee.io
 */
@SpringBootTest(classes = LogApp.class)
@RunWith(SpringRunner.class)
public class LogTest {

    @Resource
    LogRep logRep;

    @Test
    public void t1(){
       /* SysLog log = new SysLog();
        log.setId(1);
        log.setCreateTime(new Date());
        log.setMsg("空指针异常");
        logRep.save(log);*/
    }
}
