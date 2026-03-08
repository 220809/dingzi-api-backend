package com.dingzk.dinginterfacesdk.config;

import com.dingzk.dinginterfacesdk.client.ApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "ding-api")
@ComponentScan
@Configuration
@Data
public class DingApiConfiguration {

    private String accessKey;
    private String secretKey;

    @Bean
    public ApiClient testClient() {
        return new ApiClient(accessKey, secretKey);
    }
}
