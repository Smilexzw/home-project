package com.halfmoon.home.service.search;

/**
 * 房源搜索接口，从ES中获取
 * @author: xuzhangwang
 */
public interface ISearchSerivce {

    /**
     * 添加目标索引接口
     * @param houseId
     */
    boolean index(Long houseId);

    /**
     * 移除房源索引接口
     * @param houseId
     */
    void remove(Long houseId);

}
