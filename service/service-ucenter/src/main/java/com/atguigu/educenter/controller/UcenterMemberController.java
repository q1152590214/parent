package com.atguigu.educenter.controller;


import com.atguigu.JwtUtilt;
import com.atguigu.Result.Result;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.tree.VoidDescriptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

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
    public Result loginUser(@RequestBody UcenterMember ucenterMember,   HttpServletRequest request){
        String  token = ucenterMemberService.login(ucenterMember);
        request.setAttribute("token",token);
        return Result.OK().data("token",token);
    }

    @PostMapping("register")
    public Result registerUser(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return Result.OK();
    }

    @GetMapping("getMembberInfo")
    public Result getMembberInfo(HttpServletRequest request){

        String memberIdByJwtToken = JwtUtilt.getMemberIdByJwtToken(request);
        UcenterMember userInfo = ucenterMemberService.getById(memberIdByJwtToken);
        return Result.OK().data("userInfo",userInfo);
    }


}

