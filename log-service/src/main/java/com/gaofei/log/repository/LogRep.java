package com.gaofei.log.repository;

import com.gaofei.log.domain.SysLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * @author : gaofee
 * @date : 16:02 2022/6/7
 * @码云地址 : https://feege.gitee.io
 */
public interface LogRep extends MongoRepository<SysLog,String> {

    //根据日志的信息模糊查询
    List<SysLog> findSysLogByMsgLike(String msg);

    //根据根据日志的创建时间区间来查询
    List<SysLog> findSysLogByCreateTimeBetween(Date startTime, Date endTime);
}
