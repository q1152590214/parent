package com.atguigu.eduservice.controller;


import com.atguigu.JwtUtilt;
import com.atguigu.Result.Result;
import com.atguigu.Vo.UcenterVo;
import com.atguigu.eduservice.client.MemberClient;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.service.EduCommentService;
import com.atguigu.exception.MyExcaption;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-10
 */
@RestController
@RequestMapping("/eduservice/comment")
@Api(description = "评论模块")
public class EduCommentController {


    @Resource
    EduCommentService eduCommentService;

    @Resource
    MemberClient memberClient;

    @ApiOperation(value = "根据课程ID查询评论列表")
    @PostMapping("getPageCommentList/{page}/{limit}")
    public Result getPageCommentList(@PathVariable("page")long page, @PathVariable("limit")long limit,
                                     @RequestParam(value = "CourseId",required =  true) String CourseId){
        Page<EduComment> eduCommentPage = new Page<>(page,limit);

       Map<String,Object> map =   eduCommentService.getPageCommentList(eduCommentPage,CourseId);
        return Result.OK().data(map);
    }


    @PostMapping("AddComment")
    @ApiOperation(value = "添加评论")
    public Result AddComment(@RequestBody EduComment eduComment, HttpServletRequest request){
        String memberIdByJwtToken = JwtUtilt.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberIdByJwtToken)){
            throw  new MyExcaption(28004,"请登录");
        }
        System.out.println(memberIdByJwtToken);
        eduComment.setMemberId(memberIdByJwtToken);

        UcenterVo tokenById = memberClient.getTokenById(memberIdByJwtToken);
        BeanUtils.copyProperties(tokenById,eduComment);
        boolean save = eduCommentService.save(eduComment);
        if(save){ return Result.OK();}
        else {return Result.ERROR();}

    }
}

