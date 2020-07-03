package com.atguigu.eduservice.controller;


import com.atguigu.Result.Result;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-22
 */
@RestController
@RequestMapping("/eduservice/video")
@Api(description = "小节模块")
public class EduVideoController {

    @Resource
    EduVideoService eduVideoService;

    @Resource
    VodClient vodClient;
    /**
     * 添加小节
     * @param eduVideo
     * @return
     */
    @ApiOperation(value = "添加小节")
    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return Result.OK();
    }


    /**
     * 删除小节,后面需要完善,需要删除视频
     * @param id
     * @return
     */
    @ApiOperation("删除小节")
    @DeleteMapping("{id}")
    public Result deleteVideo (@PathVariable("id")String id){
        EduVideo eduVideo = eduVideoService.getById(id);
        String VideoId = new String(eduVideo.getVideoSourceId());
        if(!StringUtils.isEmpty(VideoId)) {
            vodClient.deleteVideo(VideoId);
        }
        eduVideoService.removeById(id);
        return Result.OK();
    }


    @ApiOperation("获取根据ID获取小节")
    @GetMapping("getVideo/{videoId}")
    public Result getVideo (@PathVariable("videoId") String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return Result.OK().data("Video",eduVideo);
    }



    @ApiOperation(value = "修改小节")
    @PostMapping("/updateVideo")
    public Result updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return Result.OK();
    }


}

