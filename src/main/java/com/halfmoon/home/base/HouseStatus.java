package com.halfmoon.home.base;

/**
 * 房源状态信息
 *
 * @author: xuzhangwang
 */
public enum HouseStatus {

    /**
     * 0未审核
     * 1审核通过
     * 2已出租
     * 3逻辑删除
     */
    NOT_AUDITED(0),
    PASSES(1),
    RENTED(2),
    DELETED(3);

    private int value;

    HouseStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
