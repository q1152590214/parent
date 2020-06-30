package com.atguigu.controller;

import com.atguigu.Result.Result;
import com.atguigu.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.ws.rs.POST;

@RestController
@RequestMapping("eudvod/video")
@CrossOrigin
@Api(description = "视频上传模块")
public class VodController {

    @Resource
    VodService vodService;


    @ApiOperation(value = "视频上传")
    @PostMapping("/uploadAlyVideo")
    public Result uploadAlyVideo(MultipartFile file){
        String videoId = vodService.uploadVideoAly(file);
        return Result.OK().data("videoId",videoId);
    }
}
