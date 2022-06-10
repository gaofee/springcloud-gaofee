package com.gaofei.log;

import com.gaofei.log.domain.SysLog;
import com.gaofei.log.repository.LogRep;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author : gaofee
 * @date : 16:09 2022/6/7
 * @码云地址 : https://feege.gitee.io
 */
@RestController
@RequestMapping("log")
public class LogController {

    @Autowired
    private LogRep logRep;

    /*
       保存日志接口
     */
    @RequestMapping("save")
    public String save(@RequestParam("uuid") String uuid, @RequestParam("msg")String msg){
        SysLog sysLog = new SysLog();
        sysLog.setMsg(msg);
        sysLog.setId(uuid);
        sysLog.setCreateTime(new Date());
        logRep.save(sysLog);
        return "success!";
    }


    /*
       查询日志接口
     */
    @RequestMapping("list")
    public List<SysLog> list(String msg){

        List<SysLog> sysLogByMsgLike = logRep.findSysLogByMsgLike(msg);

        return sysLogByMsgLike;
    }

    @RequestMapping("/list1")
    @PreAuthorize("hasAuthority('sys:user:list')") //声明所需的权限
    public String list1(){
        return "list";
    }
    @RequestMapping("/del")
    @PreAuthorize("hasAuthority('sys:user:del')")
    public String del(){
        return "del";
    }
}
