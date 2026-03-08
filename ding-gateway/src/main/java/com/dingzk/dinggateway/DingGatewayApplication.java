package com.dingzk.dinggateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.dingzk.dingapi.demo.dubbo", "com.dingzk.dinggateway"})
@EnableDubbo
public class DingGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingGatewayApplication.class, args);
    }

}
