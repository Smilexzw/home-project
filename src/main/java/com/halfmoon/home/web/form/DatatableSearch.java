package com.halfmoon.home.web.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author: xuzhangwang
 */
@Getter
@Setter
public class DatatableSearch {

    /**
     * Datatable要求回显的字段
     */
    private int draw;

    /**
     * datatables
     * 规定的分页字段
     */
    private int start;
    private int length;

    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTimeMin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTimeMax;

    private String city;
    private String title;
    private String direction;
    private String orderBy;
}
