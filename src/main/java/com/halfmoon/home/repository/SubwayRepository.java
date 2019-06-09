package com.halfmoon.home.repository;

import com.halfmoon.home.entity.Subway;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author: xuzhangwang
 */
public interface SubwayRepository extends CrudRepository<Subway, Long> {

    /**
     * 根据城市的英文缩写获取线路
     * @param cityEnName
     * @return
     */
    List<Subway> findByCityEnName(String cityEnName);

}
