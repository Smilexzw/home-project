package com.halfmoon.home.repository;

import com.halfmoon.home.entity.HouseDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author: xuzhangwang
 */
public interface HouseDetailRepository extends CrudRepository<HouseDetail, Long> {

    List<HouseDetail> findAllByHouseIdIn(List<Long> houseIds);

    /**
     * 根据houseId查询房源详细信息
     */
    HouseDetail findByHouseId(Long houseId);
}
