package com.halfmoon.home.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: xuzhangwang
 */
@Entity
@Data
@Table(name = "subway_station")
public class SubwayStation {

    @Id
    private Long id;

    @Column(name = "subway_id")
    private Long subwayId;

    private String name;

}
