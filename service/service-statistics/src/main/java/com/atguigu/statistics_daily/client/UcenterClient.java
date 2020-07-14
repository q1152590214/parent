package com.atguigu.statistics_daily.client;


import com.atguigu.Result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-center")
public interface UcenterClient {

    @GetMapping("/educenter/member/countRegister/{day}")
    public Result countRegister(@PathVariable("day")String day);
}
