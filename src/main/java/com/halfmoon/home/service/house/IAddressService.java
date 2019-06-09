package com.halfmoon.home.service.house;

import com.halfmoon.home.entity.Subway;
import com.halfmoon.home.entity.SupportAddress;
import com.halfmoon.home.service.ServiceMultiResult;
import com.halfmoon.home.service.ServiceResult;
import com.halfmoon.home.web.dto.SubwayDTO;
import com.halfmoon.home.web.dto.SubwayStationDTO;
import com.halfmoon.home.web.dto.SupportAddressDTO;

import java.util.List;
import java.util.Map;


/**
 * 地址服务接口
 * @author: xuzhangwang
 */
public interface IAddressService {

    /**
     * 返回所有城市列表
     * @return
     */
    ServiceMultiResult<SupportAddressDTO> findAllCities();

    /**
     * 根据英文间写获取具体区域的信息
     */
    Map<SupportAddress.Level, SupportAddressDTO> findCityAndRegion(String cityEnName, String regionEnName);

    /**
     * 根据城市英文获取该城市所有支持的区域信息。
     * @param cityName
     * @return
     */
    public ServiceMultiResult<SupportAddressDTO> findAllRegionsByCityName(String cityName);

    /**
     * 获取该城市所有的地铁线路
     * @param cityEnName
     * @return
     */
    List<SubwayDTO> findAllSubwayByCityEnName(String cityEnName);


    /**
     * 根据地铁线路获取地铁站点
     * @param subwayId
     * @return
     */
    List<SubwayStationDTO> findSubwayStationBySubway(Long subwayId);

    /**
     * 根据城市缩写获取城市的具体信息
     * @param cityEnName
     * @return
     */
    ServiceResult<SupportAddressDTO> findCity(String cityEnName);

}
