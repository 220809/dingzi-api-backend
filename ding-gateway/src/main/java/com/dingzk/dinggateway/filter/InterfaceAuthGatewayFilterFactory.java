package com.dingzk.dinggateway.filter;

import cn.hutool.core.util.StrUtil;
import com.dingzk.dingapicommon.rpc.UserRpcService;
import com.dingzk.dinginterfacesdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InterfaceAuthGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @DubboReference
    private UserRpcService userRpcService;

    private static final long FIVE_MINUTE_EXPIRE = 5 * 60 * 1000L;

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> {
            System.out.println("InterfaceAuth....");
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            String accessKey = headers.getFirst("accessKey");
            // 获取用户 secretKey
            String secretKey = userRpcService.getUserSecretKeyByAccessKey(accessKey);
            String timestamp = headers.getFirst("timestamp");
            if (timestamp == null) {
                throw new RuntimeException("timestamp无效");
            }
            // 校验 timestamp
            long timestampValue = Long.parseLong(timestamp);
            if (System.currentTimeMillis() - timestampValue >= FIVE_MINUTE_EXPIRE) {
                throw new RuntimeException("timestamp已过期");
            }
            String nonce = headers.getFirst("nonce");
            // 校验随机值是否被使用
            // 校验 signature
            String sign = headers.getFirst("sign");
            String body = headers.getFirst("body");
            String checkedSign = SignUtils.generateSign(nonce, timestampValue, body, secretKey);
            if (StrUtil.isBlank(sign) || !SignUtils.verify(sign, checkedSign)) {
                throw new RuntimeException("非法签名");
            }
            return chain.filter(exchange);
        };
    }
}
