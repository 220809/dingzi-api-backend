package com.dingzk.dingapi.aop;

import com.dingzk.dingapi.annotation.Authority;
import com.dingzk.dingapi.common.ErrorCode;
import com.dingzk.dingapi.exception.BusinessException;
import com.dingzk.dingapi.model.entity.User;
import com.dingzk.dingapi.service.UserService;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    @Around("@annotation(authority)")
    public Object authIntercept(ProceedingJoinPoint joinPoint, Authority authority) throws Throwable {
        User loginUser = userService.getLoginUser();
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        if (authority.role() != 0) {
            if (authority.role() != loginUser.getUserRole()) {
                throw new BusinessException(ErrorCode.NO_AUTH);
            }
        }
        return joinPoint.proceed();
    }
}