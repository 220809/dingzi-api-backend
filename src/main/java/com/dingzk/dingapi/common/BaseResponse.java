package com.dingzk.dingapi.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 3111358233482166974L;

    private int code;
    private T data;
    private String message;
}