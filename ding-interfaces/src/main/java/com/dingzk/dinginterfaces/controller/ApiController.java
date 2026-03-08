package com.dingzk.dinginterfaces.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ApiController {

    @GetMapping("/randInt")
    public int getRandomInteger(@RequestParam Long rangeMax) {
        rangeMax = rangeMax == null ? 100 : rangeMax;
        return (int) (Math.random() * rangeMax);
    }
}
