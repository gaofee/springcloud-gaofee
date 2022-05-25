package com.gaofei.jpa.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author : gaofee
 * @date : 15:56 2022/5/23
 * @码云地址 : https://feege.gitee.io
 */
@Data
@Entity  //他是一个jpa的实体类
@Table( name = "t_user")//该实体类对应的数据库表
public class User implements Serializable {
    @Id
    /** 用于标注主键的生成策略，通过strategy 属性指定。**/
    /** JPA 自动选择一个最适合底层数据库的主键生成策略**/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @OneToMany(targetEntity = Orders.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "users")
    private List<Orders> ordersList;

}
