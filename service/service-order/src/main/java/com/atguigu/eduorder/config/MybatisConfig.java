package com.atguigu.eduorder.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.atguigu.eduorder.mapper")
@Configuration
public class MybatisConfig {
}
