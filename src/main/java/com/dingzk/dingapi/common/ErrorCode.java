package com.dingzk.dingapi.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    BAD_PARAM(40000, "请求参数错误"),
    NOT_LOGIN(40100, "未登录"),
    NO_AUTH(40101, "无权限"),
    FORBIDDEN(40300, "禁止访问"),
    NOT_FOUND(40400, "数据不存在"),
    SYSTEM_ERROR(50000, "系统错误");

    private final int code;
    private final String message;
}