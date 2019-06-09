package com.halfmoon.home.service.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ES的索引结构模板
 * 为了解决mysql进行房源信息以及房源信息等多表查询的麻烦的问题
 * @author: xuzhangwang
 */

@Data
public class HouseIndexTemplate {

    /**
     * 房源Id
     */
    private Long houseId;

    /**
     * 房源标题
     */
    private String title;

    /**
     * 房源价格
     */
    private int price;

    /**
     * 房源面积
     */
    private int area;

    /**
     * 房源创建时间
     */
    private Date createTime;

    /**
     * 上次房源更新时间
     */
    private Date lastUpdateTime;

    /**
     * 房间朝向
     */
    private int direction;

    /**
     * 距地铁距离 默认-1 附近无地铁
     */
    private int distanceToSubway;

    /**
     * 附近地铁线路名称
     */
    private String subwayLineName;

    /**
     * 地铁站名
     */
    private String subwayStationName;

    /**
     * 街道
     */
    private String street;

    /**
     * 所在小区
     */
    private String district;

    /**
     * 详细描述
     */
    private String description;

    /**
     * 交通出行
     */
    private String traffic;

    /**
     * 出租方式
     */
    private int rentWay;

    /**
     * 房源标签
     */
    private List<String> tags;


}
