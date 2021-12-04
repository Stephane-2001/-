package com.leeyen.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//将当前类标记为springboot引用
@SpringBootApplication
//扫描mapper所在的包
@MapperScan("com.leeyen.springboot.mapper")
public class SpringBootMainType {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMainType.class, args);
    }
}
