package com.halfmoon.home.repository;

import com.halfmoon.home.entity.House;
import com.halfmoon.home.entity.HouseTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author: xuzhangwang
 */
public interface HouseTagRepository extends CrudRepository<HouseTag, Long> {

    /**
     * 根据房源id进行查询的房源的Tag
     * @param houseIds
     * @return
     */
    public List<HouseTag> findAllByHouseIdIn(List<Long> houseIds);

    /**
     * 根据房源id查询该房源的标签
     * @param houseId
     * @return
     */
    List<HouseTag> findAllByHouseIdIn(Long houseId);

}
