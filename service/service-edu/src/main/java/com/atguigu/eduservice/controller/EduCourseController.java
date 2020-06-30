package com.atguigu.eduservice.controller;


import com.atguigu.Result.Result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.ConresInfoVo;
import com.atguigu.eduservice.entity.vo.CouresPublishVo;
import com.atguigu.eduservice.service.EduCourseCollectService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    @PostMapping("/updataCourseInfo")
    public Result updataCourseInfo(@RequestBody ConresInfoVo conresInfoVo){
        eduCourseService.updataCourseInfo(conresInfoVo);
        return Result.OK();

    }


    @ApiOperation(value = "根据课程ID查询课程确认信息")
    @GetMapping("getPublishCourseInfo/{courseId}")
    public Result getPublishCourseInfo(@PathVariable("courseId") String courseId){
        CouresPublishVo couresPublishVo= eduCourseService.getPublishCourseInfo(courseId);
        return  Result.OK().data("publishCourse",couresPublishVo);
    }

    @ApiOperation(value = "更新课程的发布状态")
    @PostMapping("publishCourse/{courseId}")
    public Result publishCourse(@PathVariable("courseId") String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        boolean b = eduCourseService.updateById(eduCourse);
        if(b){
            return Result.OK();
        }else{
            return Result.ERROR();
        }
    }


    @ApiOperation(value = "条件查询课程列表，分页显示")
    @PostMapping("pageCourse/{current}/{limit}")
    public Result pageCourse(@PathVariable("current") int  current ,
                             @PathVariable("limit") int limit,
                             @RequestParam(value = "courseName",required = false) @ApiParam(value = "courseName",name = "课程名称",required = false) String courseName,
                             @RequestParam(value = "type",required = false) @ApiParam(value = "type",name = "课程发布状态",required = false)String type){
        Page<EduCourse> eduCoursePage = new Page<>(current,limit);
        eduCourseService.pageQuery(eduCoursePage,courseName,type);
        List<EduCourse> eduPageCourseList = eduCoursePage.getRecords();
        long total = eduCoursePage.getTotal();
        return Result.OK().data("list",eduPageCourseList).data("total",total);
    }


    @DeleteMapping("/{courseId}")
    public Result deleteCourse(@PathVariable String courseId){

        eduCourseService.deleteCourse(courseId);

        return Result.OK();
    }
}

