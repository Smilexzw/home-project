package com.halfmoon.home.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: xuzhangwang
 */
@Entity
@Data
@Table(name = "support_address")
public class SupportAddress {

    @Id
    private Long id;

    /**
     * 上一级行政单位
     */
    @Column(name = "belong_to")
    private String belongTo;

    @Column(name = "en_name")
    private String enName;

    @Column(name = "cn_name")
    private String cnName;

    /**
     * 行政级别
     */
    private String level;

    /**
     *
     */
    @Column(name = "baidu_map_lng")
    private double baiduMapLongitude;

    @Column(name = "baidu_map_lat")
    private double baiduMapLatitude;

    public enum Level {
        /**
         * 城市信息
         */
        CITY("city"),
        REGION("region");
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
        
        Level(String value) {
            this.value= value;    
        }
        
        public static Level of(String value) {
            for (Level level : Level.values()) {
                if (level.getValue().equals(value)) {
                    return level;
                }
            }
            throw new IllegalArgumentException();
        }
    }
}
