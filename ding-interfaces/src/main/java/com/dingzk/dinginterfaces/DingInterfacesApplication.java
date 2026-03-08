package com.dingzk.dinginterfaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DingInterfacesApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingInterfacesApplication.class, args);
    }

}
