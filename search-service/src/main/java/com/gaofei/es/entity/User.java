package com.gaofei.es.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * <p>
 *
 * </p>
 *
 * @author gaofei
 * @since 2022-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Document(indexName = "users",type = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * hrID
     */
    @Id
    private Integer id;

    /**
     * 姓名
     */
    @Field(analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",store = true,type = FieldType.Text)
    private String name;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 住宅电话
     */
    private String telephone;

    /**
     * 联系地址
     */
    @Field(analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",store = true,type = FieldType.Text)
    private String address;

    private Boolean enabled;

    /**
     * 用户名
     */
    @Field(analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",store = true,type = FieldType.Text)
    private String username;

    /**
     * 密码
     */

    private String password;

    private String userface;

    private String remark;

    private Integer sex;

    /**
     * 省
     */
    private Integer province;

    /**
     * 城市
     */
    private Integer city;

    /**
     * 区县
     */
    private Integer district;

    private LocalDate birthday;

    private String giteeid;




}
