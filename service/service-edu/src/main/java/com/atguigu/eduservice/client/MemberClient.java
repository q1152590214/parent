package com.atguigu.eduservice.client;


import com.atguigu.Vo.UcenterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-center")
@Component
public interface MemberClient {

    @GetMapping("/educenter/member/getMembberInfo/{id}")
    public UcenterVo getTokenById(@PathVariable("id") String id);

}
