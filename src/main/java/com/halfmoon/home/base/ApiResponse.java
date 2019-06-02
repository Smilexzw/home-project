package com.halfmoon.home.base;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Api格式封装
 * @author: xuzhangwang
 */
@Getter
@Setter
public class ApiResponse {

    private int code;
    private String message;
    private Object data;
    private boolean more;

    public ApiResponse() {
        this.code = Status.SUCCESS.getCode();
        this.message = Status.SUCCESS.getStandardMessage();
    }

    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }



    public static ApiResponse ofMessage(int code, String message) {
        return new ApiResponse(code, message, null);
    }

    public static ApiResponse ofSuccess(Object data) {
        return new ApiResponse(Status.SUCCESS.getCode(), Status.SUCCESS.getStandardMessage(), data);
    }


    public static ApiResponse ofStatus(Status status) {
        return new ApiResponse(status.getCode(), status.getStandardMessage(), null);
    }


    public enum Status {
        /**
         * 成功返回状态码
         */
        SUCCESS(200, "OK"),

        /**
         * 失败返回的状态码
         */
        BAD_REQUEST(400, "Bad Request"),

        NOT_FOUNT(404, "Not Found"),
        /**
         * 服务出现错误
         */
        INTERNAL_SERVER_ERROR(500, "Unknown Internal Error"),

        NOT_VALID_PARAM(40005, "Not valid Params"),

        NOT_SUPPORTED_OPERATION(40006, "Operation not supported"),

        NOT_LOGIN(50000, "Not Login");

        private int code;
        private String standardMessage;
        Status(int code, String standardMessage) {
            this.code = code;
            this.standardMessage = standardMessage;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getStandardMessage() {
            return standardMessage;
        }

        public void setStandardMessage(String standardMessage) {
            this.standardMessage = standardMessage;
        }
    }

}
