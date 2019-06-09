package com.halfmoon.home.base;

import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 房源排序类
 * @author: xuzhangwang
 */
public class HouseSort {

    /**
     * 默认排序为lastUpdateTime
     * 对应的有创建时间，面积，租金，距离地铁
     * 对应的有从小到大，从大大小
     */
    private static final String DEFAULT_SORT_KEY = "lastUpdateTime";

    private static final String DISTANCETOSUBWAY = "distanceToSubway";

    private static final Set<String> SORT_KEYS = new HashSet<>();

    static {
        SORT_KEYS.add(DEFAULT_SORT_KEY);
        SORT_KEYS.add("createTime");
        SORT_KEYS.add("price");
        SORT_KEYS.add("area");
        SORT_KEYS.add(DISTANCETOSUBWAY);
    }

    public static String getSortKey(String key) {
        if (!SORT_KEYS.contains(key)) {
            key = DEFAULT_SORT_KEY;
        }
        return key;
    }

    /**
     *
     * @param key 按照什么进行排序
     * @param directionKey  排序方式，desc还是asc
     * @return
     */
    public static Sort genterateSort(String key, String directionKey) {
        if (!SORT_KEYS.contains(key)) {
            key =DEFAULT_SORT_KEY;
        }

        Sort.Direction direction = Sort.Direction.fromStringOrNull(directionKey);
        if (direction == null) {
            direction = Sort.Direction.DESC;
        }
        //  Sort sort = new Sort(Sort.Direction.ASC, "price");
        return new Sort(direction, key);
    }
}
