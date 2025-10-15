package com.dingzk.dingapi.exception;

import com.dingzk.dingapi.common.BaseResponse;
import com.dingzk.dingapi.common.ErrorCode;
import com.dingzk.dingapi.common.ResUtils;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = {BusinessException.class})
    public BaseResponse<?> handleBusinessException(BusinessException e) {
        log.error("BusinessException: {}", e.getMessage());
        return ResUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(exception = {RuntimeException.class})
    public BaseResponse<?> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException: {}", e.getMessage());
        return ResUtils.error(ErrorCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public BaseResponse<?> handleValidationException(MethodArgumentNotValidException e) {
        FieldError fieldError = (FieldError) e.getBindingResult().getAllErrors().get(0);
        String message = String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage());
        log.error("Validation Error: {}", message);
        return ResUtils.error(ErrorCode.BAD_PARAM, message);
    }
}