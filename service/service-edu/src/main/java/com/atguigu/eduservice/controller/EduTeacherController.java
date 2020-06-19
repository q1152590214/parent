package com.atguigu.eduservice.controller;


import com.atguigu.Result.Result;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
@CrossOrigin
public class EduTeacherController {

    @Resource
    private EduTeacherService eduTeacherService;


    /**
     * 查询所有讲师
     * @return
     */
    @GetMapping("/finall")
    @ApiOperation(value = "查询所有讲师")
    public Result findAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        if(list==null||list.size()==0){
            return Result.ERROR();
        }else  return Result.OK().data("items",list);

    }

    /**
     * 根据ID删除讲师，逻辑删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除讲师,逻辑删除")
    public Result removeTeacher(@ApiParam(name = "id",
            value = "讲师ID",required = true) @PathVariable("id") String id){
        if( eduTeacherService.removeById(id)) return Result.OK();
        else  return Result.ERROR();
    }

    /**
     * 根据查询讲师，分页显示
     * @param current
     * @param limit
     * @return
     */
    @GetMapping("/pageTeacher/{current}/{limit}")
    @ApiOperation(value = "分页查询讲师列表")
    public Result pathListTeacher(@ApiParam(name = "current" ,value = "单前页数", required = true)
                                      @PathVariable("current") int current,
                                  @ApiParam(name = "limit" ,value = "每页显示行数", required = true)
                                  @PathVariable("limit") int limit){
        Page<EduTeacher> objectPage = new Page<>(current, limit);
            eduTeacherService.page(objectPage,null);
        long sumcount = objectPage.getTotal();
        List<EduTeacher> list  = objectPage.getRecords();

        return Result.OK().data("total",sumcount).data("rows",list);
    }
    /**
     * 根据条件查询讲师，分页显示
     * @param current
     * @param limit
     * @param teacherQuery
     * @return
     */
    @ApiOperation(value = "按条件查询讲师，分页显示")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public Result pageTeacherCondition(@ApiParam(name = "current" ,value = "单前页数", required = true)
                                       @PathVariable("current") int current,
                                       @ApiParam(name = "limit" ,value = "每页显示行数", required = true)
                                       @PathVariable("limit") int limit, @RequestBody(required = false) TeacherQuery teacherQuery){

        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        eduTeacherService.pageQuery(eduTeacherPage,teacherQuery);
        long total = eduTeacherPage.getTotal();
        List<EduTeacher> records = eduTeacherPage.getRecords();;
        return Result.OK().data("rows",records).data("total",total);
    }


    /**
     * 添加讲师
     * @param eduTeacher
     * @return
     */
    @PostMapping("/addteacher")
    @ApiOperation(value = "添加讲师")
    public Result AddTeacher(@RequestBody EduTeacher eduTeacher){ //@Requestbody 要做post提交试用
        boolean save = eduTeacherService.save(eduTeacher);
        if(save) return Result.OK();
        else return Result.ERROR();
    }


    /**
     * 根据讲师ID查询讲师
     * @param id
     * @return
     */
   @ApiOperation("根据ID查询讲师")
   @GetMapping("/getTeacher/{id}")
    public Result getTeacherById(@PathVariable("id") String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return Result.OK().data("teacher",teacher);
    }


    /**
     * 修改讲师信息
     * @param eduTeacher
     * @return
     */
    @ApiOperation("根据讲师ID修改讲师信息")
    @PostMapping("/updataTeacher")
    public Result updataTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        return Result.OK();
    }

// 获取全局数据绑定
//    @GetMapping("123456")
//    public Result result(Model model){
//        Map<String, Object> stringObjectMap = model.asMap();
//        System.out.println(stringObjectMap.get("旺仔"));
//        Map<String,Object> map = (Map<String, Object>) stringObjectMap.get("mp");
//        return Result.OK().data("旺仔",map);
//    }
}

