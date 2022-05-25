package com.gaofei.jpa.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author : gaofee
 * @date : 10:25 2022/5/25
 * @码云地址 : https://feege.gitee.io
 */
@Data
@Entity
@Table(name = "t_orders")
public class Orders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    private Integer id;

    //order_details
    @Column(name = "order_details")
    private String orderDetails;//订单详情

    @Column(name = "create_time")
    private Date createTime;


    @Transient //忽略这个字段和数据库表的映射
    private Date beginTime;


    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "uid")
    private User users;


    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", orderDetails='" + orderDetails + '\'' +
                ", createTime=" + createTime +
                ", beginTime=" + beginTime +
                '}';
    }
}
