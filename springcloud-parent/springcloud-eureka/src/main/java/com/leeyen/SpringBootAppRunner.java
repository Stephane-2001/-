package com.leeyen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
// 启用eureka服务
@EnableEurekaServer
public class SpringBootAppRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAppRunner.class, args);
    }
}
