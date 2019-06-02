package com.halfmoon.home.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by 瓦力.
 */
@Data
@Entity
@Table(name = "house_tag")
public class HouseTag {
    @Id
    private Long id;

    @Column(name = "house_id")
    private Long houseId;

    private String name;

}
