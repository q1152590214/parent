package com.atguigu.educenter.controller;


import com.atguigu.Result.Result;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(description = "登录模块")
public class UcenterMemberController {


    @Resource
    UcenterMemberService ucenterMemberService;


    /**
     * 根据手机号和密码进行登录验证
     * @param ucenterMember
     * @return
     */
    @PostMapping("login")
    @ApiOperation(value = "登录")
    public Result loginUser(@RequestBody UcenterMember ucenterMember){
        String  token = ucenterMemberService.login(ucenterMember);
        return Result.OK().data("token",token);
    }

    @PostMapping("register")
    public Result registerUser(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return Result.OK();
    }
}

