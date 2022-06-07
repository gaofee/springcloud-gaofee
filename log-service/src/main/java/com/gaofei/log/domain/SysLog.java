package com.gaofei.log.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : gaofee
 * @date : 16:05 2022/6/7
 * @码云地址 : https://feege.gitee.io
 */
@Data
public class SysLog implements Serializable {

    @MongoId(targetType = FieldType.STRING)
    @Field("_id")
    private String id;
    private String msg;
    private Date createTime;
}
