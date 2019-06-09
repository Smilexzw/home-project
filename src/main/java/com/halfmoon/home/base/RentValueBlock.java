package com.halfmoon.home.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 租房信息的区间常量数值定义
 * @author: xuzhangwang
 */
public class RentValueBlock {
    /**
     * 价格区间定义, 静态不变的变量需要初始化
     */
    public static final Map<String, RentValueBlock> PRICE_BLOCK;

    /**
     * 面积区间定义
     */
    public static final Map<String, RentValueBlock> AREA_BLOCK;

    /**
     * 无限区间定义
     */
    public static final RentValueBlock ALL = new RentValueBlock("*", -1, -1);

    static {
        PRICE_BLOCK = new HashMap<>();
        PRICE_BLOCK.put("*-1000", new RentValueBlock("*-1000", -1, 1000));
        PRICE_BLOCK.put("1000-3000", new RentValueBlock("1000-3000", 1000, 3000));
        PRICE_BLOCK.put("3000-*", new RentValueBlock("3000-*", 3000, -1));

        /**
         * 之前的作者在前端页面规定了，在max为负数则只返回min， min为负数的话就返回max
         */
        AREA_BLOCK = new HashMap<>();
        AREA_BLOCK.put("*-30", new RentValueBlock("*-30", -1, 30));
        AREA_BLOCK.put("30-50", new RentValueBlock("30-50", 30, 50));
        AREA_BLOCK.put("50-*", new RentValueBlock("50-*", 50, -1));
    }

    /**
     * 区间的key
     */
    private String key;
    /**
     * 区间最小值
     */
    private int min;
    /**
     * 区间最大值
     */
    private int max;

    public RentValueBlock() {}

    public RentValueBlock(String key, int min, int max) {
        this.key = key;
        this.min = min;
        this.max = max;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    /**
     * 根据价格进行区间匹配
     * @return
     */
    public static RentValueBlock matchPrice(String key) {
        RentValueBlock block = PRICE_BLOCK.get(key);
        if (block == null) {
            return ALL;
        }
        return block;
    }

    public static RentValueBlock matchArea(String key) {
        RentValueBlock block = AREA_BLOCK.get(key);
        if (block == null) {
            return ALL;
        }
        return block;
    }

    @Override
    public String toString() {
        return "RentValueBlock{" +
                "key='" + key + '\'' +
                ", min=" + min +
                ", max=" + max +
                '}';
    }
}
