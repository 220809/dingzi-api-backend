package com.dingzk.dingapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.dingzk.dingapi.mapper"})
public class DingApiBdApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingApiBdApplication.class, args);
    }

}