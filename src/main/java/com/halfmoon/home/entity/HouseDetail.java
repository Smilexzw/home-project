package com.halfmoon.home.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 房子的详细的信息
 * @author: xuzhangwang
 */
@Entity
@Data
@Table(name = "house_detail")
public class HouseDetail {

    @Id
    private Long id;

    @Column(name = "house_id")
    private Long houseId;

    private String description;

    /**
     * 户型介绍
     */
    @Column(name = "layout_desc")
    private String layoutDesc;

    private String traffic;

    /**
     * 周边配套
     */
    @Column(name = "round_service")
    private String roundService;

    /**
     * 租赁方式
     */
    @Column(name = "rent_way")
    private int rentWay;

    /**
     * 详细地址
     */
    @Column(name = "address")
    private String detailAddress;

    /**
     * 附近地铁线id
     */
    @Column(name = "subway_line_id")
    private Long subwayLineId;

    /**
     * 地铁站id
     */
    @Column(name = "subway_station_id")
    private Long subwayStationId;

    /**
     * 附近地铁线名称
     */
    @Column(name = "subway_line_name")
    private String subwayLineName;

    /**
     * 地铁站名
     */
    @Column(name = "subway_station_name")
    private String subwayStationName;
}
