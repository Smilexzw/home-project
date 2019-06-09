package com.halfmoon.home.web.dto;

import lombok.Data;

/**
 * 向前端返回的
 * @author: xuzhangwang
 */
@Data
public class SubwayStationDTO {

    private Long id;

    private Long subwayId;

    private String name;

}
