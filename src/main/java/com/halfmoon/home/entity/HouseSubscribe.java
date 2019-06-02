package com.halfmoon.home.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 预约看房实体类
 * @author: xuzhangwang
 */
@Entity
@Data
@Table(name = "house_subscribe")
public class HouseSubscribe {

    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "admin_id")
    private Long adminId;

    /**
     * 预约状态 1-加入待看清单 2-已预约看房时间 3-看房完成
      */
    private int status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @Column(name = "order_time")
    private Date orderTime;

    private String telephone;

    /**
     * 踩坑 desc为MySQL保留字段 需要加转义 用户描述
     */
    @Column(name = "`desc`")
    private String desc;

}
