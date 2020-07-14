package com.atguigu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.atguigu.educenter.mapper")
@ComponentScan(basePackages = {"com.atguigu"})
@EnableFeignClients
public class ServiceUcenterMain {

    public static void main(String[] args) {
        SpringApplication.run(ServiceUcenterMain.class,args);
    }
}
