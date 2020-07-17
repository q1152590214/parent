package com.atguigu.statistics_daily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.atguigu")
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class SatApplication {
    public static void main(String[] args) {
        SpringApplication.run(SatApplication.class,args);
    }
}
