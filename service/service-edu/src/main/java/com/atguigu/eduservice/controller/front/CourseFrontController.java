package com.atguigu.eduservice.controller.front;

import com.atguigu.Result.Result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chaptervo.ChapterVo;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CouresPublishVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront/")
@Api(description = "前段课程模块")
public class CourseFrontController {

    @Resource
    EduCourseService eduCourseService;
    
    @Resource
    EduChapterService eduChapterService;

    @ApiOperation("前台课程信息")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public Result getFrontCourseList(@PathVariable("page") long page, @PathVariable("limit") long limit,
                                     @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> eduCoursePage = new Page<>();
      Map<String,Object> map =  eduCourseService.getFrontCourseList(eduCoursePage,courseFrontVo);
        return Result.OK().data(map);
    }

    @ApiOperation("前台课程详情")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public Result  getFrontCourseInfo(@PathVariable("courseId")String courseId){
        CourseWebVo courseWebVo =     eduCourseService.getBaseCourseInfo(courseId);
        List<ChapterVo> chapterVideoByCouresId = eduChapterService.getChapterVideoByCouresId(courseId);
        return Result.OK().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoByCouresId);
    }


}
