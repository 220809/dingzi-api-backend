package com.dingzk.dingapi.exception;

import com.dingzk.dingapi.common.ErrorCode;
import lombok.Getter;

import java.io.Serial;

@Getter
public class BusinessException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 5956768683135396526L;

    private final int code;


    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage());
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
}