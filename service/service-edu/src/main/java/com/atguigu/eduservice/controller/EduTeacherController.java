package com.atguigu.eduservice.controller;


import com.atguigu.Result.Result;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-12
 */
@RestController
@RequestMapping("/eduservice/teacher")
@Api(description = "讲师列表")
public class EduTeacherController {

    @Resource
    private EduTeacherService eduTeacherService;


    @GetMapping("finall")
    @ApiOperation(value = "查询所有讲师")
    public Result findAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        if(list==null||list.size()==0){
            return Result.ERROR();
        }else  return Result.OK().data("items",list);

    }

    @PostMapping("{id}")
    @ApiOperation(value = "根据ID删除讲师")
    public Result removeTeacher(@ApiParam(name = "id",
            value = "讲师ID",required = true) @PathVariable("id") int id){
        if( eduTeacherService.removeById(id)) return Result.OK();
        else  return Result.ERROR();
    }

    @GetMapping("pageTeacher/{current}/{limit}")
    @ApiOperation(value = "分页查询讲师列表")
    public Result pathListTeacher(@ApiParam(name = "current" ,value = "单前页数", required = true)
                                      @PathVariable("current") int current,
                                  @ApiParam(name = "limit" ,value = "每页显示行数", required = true)
                                  @PathVariable("limit") int limit){
        Page<EduTeacher> objectPage = new Page<>(current, limit);
            eduTeacherService.page(objectPage,null);
        long sumcount = objectPage.getTotal();

        return Result.OK().data("total",sumcount).data("rows",objectPage);
    }


}

