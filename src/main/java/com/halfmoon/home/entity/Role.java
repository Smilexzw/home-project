package com.halfmoon.home.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: xuzhangwang
 */
@Entity
@Data
public class Role {
    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String name;
}
