package com.halfmoon.home.base;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Datatables结构返回
 * @author: xuzhangwang
 */
@Getter
@Setter
public class ApiDataTableResponse extends ApiResponse{

    private int draw;

    private long recordTotal;

    private long recordsFiltered;

    public ApiDataTableResponse(ApiResponse.Status status) {
        this(status.getCode(), status.getStandardMessage(), null);
    }

    public ApiDataTableResponse(int code, String message, Object data) {
        super(code, message, data);
    }


}
