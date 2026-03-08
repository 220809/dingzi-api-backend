package com.dingzk.dingapi;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.dingzk.dingapi.mapper"})
@EnableDubbo
public class DingApiBdApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingApiBdApplication.class, args);
    }

}