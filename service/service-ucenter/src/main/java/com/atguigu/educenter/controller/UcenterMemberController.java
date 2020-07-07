package com.atguigu.educenter.controller;


import com.atguigu.Result.Result;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-07
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {


    @Resource
    UcenterMemberService ucenterMemberService;


    @GetMapping("login")
    public Result loginUser(@RequestBody UcenterMember ucenterMember){

        String  token =    ucenterMemberService.login(ucenterMember);


        return Result.OK().data("token",token);
    }
}

