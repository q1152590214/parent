package com.atguigu.eduservice.controller;


import com.atguigu.Result.Result;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-19
 */
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
@Api(description = "分类模块")
public class EduSubjectController {


    @Resource
    EduSubjectService eduSubjectService;


    @ApiOperation(value = "上传excel文件，进行分类添加")
    @PostMapping("/addSubject")
    public Result addSubject(MultipartFile file){

          eduSubjectService.saveSubject(file,eduSubjectService);
        return Result.OK();
    }


    @GetMapping("/getAllSubject")
    @ApiOperation(value = "查询一级分类和下面的二级分类")
    public Result  GetAllSubject(){
        List<OneSubject> list = eduSubjectService.getAllOneSubject();
        return Result.OK().data("list",list);
    }
}

