package com.atguigu.eduorder.client;

import com.atguigu.Vo.CourseTeacherOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-edu")
public interface EduClient {


    @GetMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public CourseTeacherOrderVo getCourseTeacherInfoOrder(@PathVariable("id") String id);
}
