package com.atguigu.eduservice.client;

import com.atguigu.Result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodClientDegradFeignClient implements  VodClient {

    @Override
    public Result deleteVideo(String id) {
        return Result.ERROR().massage("网络错误，请稍后");
    }

    @Override
    public Result deleteBatch(List<String> videolist) {
        return Result.ERROR().massage("网络错误，请稍后");
    }
}
