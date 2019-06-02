package com.halfmoon.home.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: xuzhangwang
 */
@Data
@Table(name = "subway")
@Entity
public class Subway {

    @Id
    private long id;

    private String name;

    @Column(name = "city_en_name")
    private String cityEnName;

}
