package com.leeyen.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/get/spring/boot/hello/message")
    public String getMessage(){
        return "hello springboot";
    }

    @RequestMapping("/hello/thymeleaf")
    public String helloTthymeleaf() {
        return "hello-thymeleaf";
    }
}
