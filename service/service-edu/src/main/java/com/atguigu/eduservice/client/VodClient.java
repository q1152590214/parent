package com.atguigu.eduservice.client;

import com.atguigu.Result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Administrator
 */
@Component
@FeignClient(value = "service-vod" ,fallback = VodClientDegradFeignClient.class)
public interface VodClient {


    @DeleteMapping("eudvod/video/removeAlyVideo/{id}")
    public Result deleteVideo(@PathVariable("id") String id);

    @DeleteMapping("eudvod/video/delete-batch")
    public Result deleteBatch(@RequestParam("videolist") List<String> videolist);

}
