package com.halfmoon.home.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 照片信息
 * @author: xuzhangwang
 */
@Data
@Entity
@Table(name = "house_picture")
public class HousePicture {

    @Id
    private Long id;

    @Column(name = "house_id")
    private Long houseId;

    private String path;

    @Column(name = "cdn_prefix")
    private String cdnPrefix;

    private int width;

    private int height;

    private String location;
}
