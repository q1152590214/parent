package com.atguigu.oss.controller;

import com.atguigu.Result.Result;
import com.atguigu.oss.services.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
@Api(description = "文件上传模块")
public class OssController {

    @Resource
    OssService ossService;


    @PostMapping
    @ApiOperation(value = "文件上传")
    public Result uploadOssFile(MultipartFile file){
            //上传文件
        String url = ossService.uploadOssFileAvatar(file);
        return Result.OK().data("url",url);
    }
}
