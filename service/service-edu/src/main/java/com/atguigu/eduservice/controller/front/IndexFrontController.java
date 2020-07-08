package com.atguigu.eduservice.controller.front;

import com.atguigu.Result.Result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@Api(description = "首页模块")
public class IndexFrontController {


    @Resource
    EduTeacherService eduTeacherService;

    @Resource
    EduCourseService eduCourseService;


    @ApiOperation(value = "首页显示数据")
    @GetMapping("index")
    public Result indexFrontController(){

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("LIMIT 8");

        List<EduTeacher> eduTeacherList = eduTeacherService.list(wrapper);

        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.orderByDesc("id");
        eduCourseQueryWrapper.last("LIMIT 4");
        List<EduCourse> eduCourseList = eduCourseService.list(eduCourseQueryWrapper);
        return Result.OK().data("eduList",eduCourseList).data("techerList",eduTeacherList);
    }
}
