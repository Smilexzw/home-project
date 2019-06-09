package com.halfmoon.home.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author: xuzhangwang
 */
@Entity
@Data
@Table(name = "house")
public class House {

    @Id
    private Long id;

    private String title;

    /**
     * 所属管理员
     */
    @Column(name = "admin_id")
    private Long adminId;

    private int price;

    private int area;


    /**
     * 卧室数量
     */
    private int room;

    /**
     * 客厅数量
     */
    private int parlour;

    private int bathroom;

    private int floor;

    @Column(name = "total_floor")
    private int totalFloor;

    @Column(name = "watch_times")
    private int watchTimes;

    /**
     * 建立年限
     */
    @Column(name = "build_year")
    private int buildYear;

    private int status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 上次更新时间
     */
    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @Column(name = "city_en_name")
    private String cityEnName;

    @Column(name = "region_en_name")
    private String regionEnName;

    /**
     * 所属街道
     */
    private String street;

    private String district;

    /**
     * 房子的朝向
     */
    private int direction;

    private String cover;

    /**
     * 房子距离地铁的距离
     */
    private int distanceToSubway;
}
