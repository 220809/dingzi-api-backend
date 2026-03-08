package com.dingzk.dingapi.demo.dubbo;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class Consumer implements CommandLineRunner {
    @DubboReference
    private DubboDemoService dubboDemoService;

    @Override
    public void run(String... args) throws Exception {
        String result = dubboDemoService.sayHello("world");
        System.out.println("Receive result ======> " + result);
    }
}