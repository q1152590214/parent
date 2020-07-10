package com.atguigu.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.Result.Result;
import com.atguigu.Utils.ConstanVodUtils;
import com.atguigu.Utils.InitObject;
import com.atguigu.exception.MyExcaption;
import com.atguigu.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import java.util.List;

@RestController
@RequestMapping("/eudvod/video/")
@CrossOrigin
@Api(description = "视频上传模块")
public class VodController {

    @Resource
    VodService vodService;


    @ApiOperation(value = "视频上传")
    @PostMapping("uploadAlyVideo")
    public Result uploadAlyVideo(MultipartFile file){
        String videoId = vodService.uploadVideoAly(file);
        return Result.OK().data("videoId",videoId);
    }

    @ApiOperation(value = "根据视频ID删除视频")
    @DeleteMapping("removeAlyVideo/{id}")
    public Result deleteVideo(@PathVariable("id") String id){
        try {
            System.out.println(id);
            DefaultAcsClient client = InitObject.initVodClient(ConstanVodUtils.ACCESS_KEY_ID, ConstanVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return Result.OK();
        }catch (ClientException e) {
            throw  new MyExcaption(20001,e.getMessage());
        }


    }
    @ApiOperation(value = "批量删除视频")
    @DeleteMapping("delete-batch")
    public Result deleteBatch(@RequestParam("videolist") List<String> videolist){
        vodService.deleteRemoveAlyVod(videolist);
        return Result.OK();
    }

    @ApiOperation(value = "获取播放凭证")
    @GetMapping("getPlayAuth/{id}")
    public  Result  getPlayAuth(@PathVariable("id")String id){
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstanVodUtils.ACCESS_KEY_ID, ConstanVodUtils.ACCESS_KEY_SECRET);

            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse videoPlayAuth = client.getAcsResponse(request);;
            //获取播放凭证
            String playAuth = videoPlayAuth.getPlayAuth();
            return Result.OK().data("playAuth",playAuth);
        }catch (Exception e){
            throw  new MyExcaption(20001,"获取凭证失败");
        }


    }
}
