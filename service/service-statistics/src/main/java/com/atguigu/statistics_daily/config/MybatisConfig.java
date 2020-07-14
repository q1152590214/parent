package com.atguigu.statistics_daily.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.atguigu.statistics_daily.mapper")
public class MybatisConfig {
}
