package com.halfmoon.home.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author: xuzhangwang
 */
@Entity
@Data
public class User {

    @Id
    private Long id;

    private String name;

    private String password;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private int status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    private String avatar;
}
