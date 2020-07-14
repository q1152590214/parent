package com.atguigu.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(value = "service-order")
public interface OrderClient {

    @PostMapping("isBuyCourse/{courseId}/{menberId}")
    public boolean isBuyCourse(@PathVariable("courseId")String courseId , @PathVariable("menberId")String menberId);

}
