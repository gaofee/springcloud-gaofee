package com.gaofei.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : gaofee
 * @date : 15:56 2022/5/23
 * @码云地址 : https://feege.gitee.io
 */
@Data
public class User implements Serializable {


    private Integer id;
    private String name;
    private Date birthday;
}
