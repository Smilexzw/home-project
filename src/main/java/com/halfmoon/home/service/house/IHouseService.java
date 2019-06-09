package com.halfmoon.home.service.house;

import com.halfmoon.home.service.ServiceMultiResult;
import com.halfmoon.home.service.ServiceResult;
import com.halfmoon.home.web.dto.HouseDTO;
import com.halfmoon.home.web.dto.HouseDetailDTO;
import com.halfmoon.home.web.form.DatatableSearch;
import com.halfmoon.home.web.form.RentSearch;

/**
 * 房屋管理服务接口
 * @author: xuzhangwang
 */
public interface IHouseService {

    /**
     * 实现查询接口
     * @param searchBody
     * @return
     */
    ServiceMultiResult<HouseDTO> adminQuery(DatatableSearch searchBody);

    /**
     * 修改房源状态(类似于发布，下架，删除，已出租等等状态的信息)
     * @param id
     * @param value
     * @return
     */
    ServiceResult updateStatus(Long id, int value);

    /**
     * 在用户界面查询已出租的房源信息
     * @param rentSearch
     * @return
     */
    ServiceMultiResult<HouseDTO> query(RentSearch rentSearch);


    /**
     * 根据房源id进行房源的查找
     * @param houseId
     * @return
     */
    ServiceResult<HouseDTO> rentHouseDetail(Long houseId);
}
