package com.atguigu.educrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.atguigu")
@MapperScan("com.atguigu.educrm.mapper")
public class eduServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(eduServiceMain.class,args);
    }
}
