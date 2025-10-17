package com.dingzk.dingapi.aop;

import com.dingzk.dingapi.annotation.Sensitive;
import com.dingzk.dingapi.utils.SensitiveUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 全局请求响应日志
 * @author dingzi
 * @since 2025-10-15 20:40
 */
@Aspect
@Component
@Slf4j
public class LogInterceptor {

    /**
     * 执行拦截
     */
    @Around("execution(* com.dingzk.dingapi.controller.*.*(..))")
    public Object doInterceptor(ProceedingJoinPoint point) throws Throwable {
        // 计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 获取请求路径
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 生成请求唯一 id
        String requestId = UUID.randomUUID().toString();
        String url = httpServletRequest.getRequestURI();
        // 获取请求参数
        String reqParam = desensitizeReqParams(point.getArgs());
        // 输出请求日志
        log.info("request start, id: {}, path: {}, ip: {}, params: {}", requestId, url,
                httpServletRequest.getRemoteHost(), reqParam);
        // 执行原方法
        Object result = point.proceed();
        // 输出响应日志
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        log.info("request end, id: {}, cost: {}ms", requestId, totalTimeMillis);
        return result;
    }

    private String desensitizeReqParams(Object[] args) throws IllegalAccessException {
        List<String> reqParams = new ArrayList<>();
        for (Object arg : args) {
            for (Field field : arg.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                // 跳过 serialVersionUID
                if (Objects.equals("serialVersionUID", field.getName())) {
                    continue;
                }
                if (field.isAnnotationPresent(Sensitive.class)) {
                    String value = ((String) field.get(arg));
                    Sensitive annotation = field.getAnnotation(Sensitive.class);
                    reqParams.add(String.format("%s: %s", field.getName(), SensitiveUtils.desensitize(value, annotation.type())));
                    continue;
                }
                reqParams.add(String.format("%s: %s", field.getName(), field.get(arg)));
            }
        }
        return StringUtils.join(reqParams, ", ");
    }
}