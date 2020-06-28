package com.atguigu.eduservice.controller;


import com.atguigu.Result.Result;
import com.atguigu.eduservice.entity.vo.ConresInfoVo;
import com.atguigu.eduservice.service.EduCourseCollectService;
import com.atguigu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-22
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
@Api(description = "课程模块")
public class EduCourseController {

    @Resource
    private EduCourseService eduCourseService;


    @PostMapping("/addCourseInfo")
    @ApiOperation(value = "添加课程信息")
    public Result SaveCourse(@RequestBody ConresInfoVo conresInfoVo){
        String cid=eduCourseService.saveConrseInfo(conresInfoVo);
        return Result.OK().data("courseId",cid);
    }


    @ApiOperation(value = "根据课程ID查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable("courseId") String courseId){
        ConresInfoVo conresInfoVo = eduCourseService.getCourseInfo(courseId);
        return Result.OK().data("conresInfoVo",conresInfoVo);
    }

    @ApiOperation(value = "根据课程ID修改课程的基本信息")
    @PostMapping
    public Result updataCourseInfo(@RequestBody ConresInfoVo conresInfoVo){

        eduCourseService.updataCourseInfo(conresInfoVo);
        return Result.OK();

    }
}

