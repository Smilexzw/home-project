package com.halfmoon.home.service;

import lombok.Getter;
import lombok.Setter;

/**
 * 服务接口通用结构
 * @author: xuzhangwang
 */
@Getter
@Setter
public class ServiceResult<T> {
    private boolean success;
    private String message;
    private T result;

    public ServiceResult(boolean success) {
        this.success = success;
    }

    public ServiceResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }


    public ServiceResult(boolean success, String message, T result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public static <T> ServiceResult<T> success() {
        return new ServiceResult<T>(true);
    }

    /**
     * 根据结果返回服务接口
     * @param result
     * @param <T>
     * @return
     */
    public static <T>ServiceResult<T> of(T result) {
        ServiceResult<T> serviceResult = new ServiceResult<T>(true);
        serviceResult.setResult(result);
        return serviceResult;
    }


    public static <T> ServiceResult<T> notFound() {
        return new ServiceResult<T>(false, Message.NOT_FOUND.getValue());
    }

    enum Message {
        /**
         * 没有找到对应的结果
         */
        NOT_FOUND("not found resources"),
        /**
         * 用户没有进行登入，没有权限
         */
        NOT_LOGIN("user not login!");
        private String value;
        Message(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
