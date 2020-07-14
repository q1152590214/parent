package com.atguigu.eduorder.client;

import com.atguigu.Vo.CourseTeacherOrderVo;
import com.atguigu.Vo.UcenterOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Component
@FeignClient(value = "service-center")
public interface UcenterClient {

    @GetMapping("/eduservice/coursefront/getUcenterInfoOrder/{id}")
    public UcenterOrderVo getUcenterInfoOrder(@PathVariable("id")String id);
}
