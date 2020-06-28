package com.atguigu.eduservice.controller;


import com.atguigu.Result.Result;
import com.atguigu.eduservice.entity.chaptervo.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-23
 */
@RestController
@RequestMapping("/eduservice/chapter")
@Api(description = "章节模块")
@CrossOrigin
public class EduChapterController {


    @Resource
    EduChapterService eduChapterService;

    @ApiOperation(value = "根据课程ID查询下面的章节和小节")
    @GetMapping("/getChapterVideo/{courseId}")
    public Result getChapterVideo(@PathVariable(value = "courseId") String courseId){

        List<ChapterVo> list =  eduChapterService.getChapterVideoByCouresId(courseId);
        return Result.OK().data("allChapterVideo",list);
    }




}

