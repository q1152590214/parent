package com.atguigu.eduservice.controller.front;

import com.atguigu.Result.Result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.management.Descriptor;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
@Api(description = "前台讲师模块")
public class TeacherFronController {

    @Resource
    private EduTeacherService eduTeacherService;

    @Resource
    EduCourseService eduCourseService;


    @ApiOperation(value = "前端讲师分页")
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@PathVariable("page") long page,@PathVariable("limit") long limit){
        Page<EduTeacher> eduTeacherPage = new Page<>(page,limit);
        Map<String,Object> eduTeacherServiceTeacherFrontList = eduTeacherService.getTeacherFrontList(eduTeacherPage);
        return Result.OK().data(eduTeacherServiceTeacherFrontList);
    }

    @ApiOperation(value = "讲师详情")
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public Result  getTeacherFrontInfo(@PathVariable("teacherId") String teacherId){
        EduTeacher TeacherById = eduTeacherService.getById(teacherId);
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.eq("teacher_id",TeacherById.getId());
        List<EduCourse> list = eduCourseService.list(eduCourseQueryWrapper);

        return Result.OK().data("teacher",TeacherById).data("courseList",list);
    }

}
