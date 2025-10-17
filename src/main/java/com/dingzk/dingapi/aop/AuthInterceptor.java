package com.dingzk.dingapi.aop;

import com.dingzk.dingapi.annotation.Authority;
import com.dingzk.dingapi.common.ErrorCode;
import com.dingzk.dingapi.exception.BusinessException;
import com.dingzk.dingapi.model.entity.User;
import com.dingzk.dingapi.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;

    private void authIntercept(Authority authority) {
        User loginUser = userService.getLoginUser();
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        if (authority.role() != 0) {
            if (authority.role() != loginUser.getUserRole()) {
                throw new BusinessException(ErrorCode.NO_AUTH);
            }
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod hm) {
            if (hm.getMethod().isAnnotationPresent(Authority.class)) {
                authIntercept(hm.getMethod().getAnnotation(Authority.class));
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}