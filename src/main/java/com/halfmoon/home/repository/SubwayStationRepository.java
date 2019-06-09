package com.halfmoon.home.repository;

import com.halfmoon.home.entity.SubwayStation;
import com.halfmoon.home.service.ServiceMultiResult;
import com.halfmoon.home.web.dto.SubwayStationDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author: xuzhangwang
 */
public interface SubwayStationRepository extends CrudRepository<SubwayStation, Long> {

    /**
     * 根据铁路路线查找站点
     * @param subwayId
     * @return
     */
    public List<SubwayStation> findAllBySubwayId(Long subwayId);

}
