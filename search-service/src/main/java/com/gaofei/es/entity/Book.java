package com.gaofei.es.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Data
public class Book implements Serializable {
    private Integer id;
    private String name;
}
