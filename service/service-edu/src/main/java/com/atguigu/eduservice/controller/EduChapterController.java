package com.atguigu.eduservice.controller;


import com.atguigu.Result.Result;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chaptervo.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
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

    @Resource
    EduVideoService eduVideoService;

    @ApiOperation(value = "根据课程ID查询下面的章节和小节")
    @GetMapping("/getChapterVideo/{courseId}")
    public Result getChapterVideo(@PathVariable(value = "courseId") String courseId){

        List<ChapterVo> list =  eduChapterService.getChapterVideoByCouresId(courseId);
        return Result.OK().data("allChapterVideo",list);
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("/addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return Result.OK();

    }

    @ApiOperation(value = "根据章节ID查询章节")
    @GetMapping("getChapterInfo/{chapterId}")
    public Result getCahpterById(@PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return Result.OK().data("chapter",eduChapter);

    }



    @ApiOperation(value = "更改章节信息")
    @PostMapping("updateChapter")
    public Result updateChapterById(@RequestBody EduChapter eduChapter){
        boolean b = eduChapterService.updateById(eduChapter);
        if (!b){
            return Result.ERROR();
        }
        return  Result.OK();
    }


    @ApiOperation(value = "删除章节信息")
    @DeleteMapping("/{chapterId}")
    public Result deleteChapter(@PathVariable String chapterId){



       boolean delete =   eduChapterService.deleteChapter(chapterId);
       if(delete){
           return Result.OK();
       }else{
           return Result.ERROR();
       }



    }

}

