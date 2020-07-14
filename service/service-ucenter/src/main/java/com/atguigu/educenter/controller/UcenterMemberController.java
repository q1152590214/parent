package com.atguigu.educenter.controller;


import com.atguigu.JwtUtilt;
import com.atguigu.Result.Result;
import com.atguigu.Vo.UcenterOrderVo;
import com.atguigu.Vo.UcenterVo;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.implementation.bind.annotation.Pipe;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import sun.awt.SunHints;
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
@RequestMapping("/educenter/member/")
@CrossOrigin
@Api(description = "用户模块")
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

    @ApiOperation("手机号注册")
    @PostMapping("register")
    public Result registerUser(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return Result.OK();
    }


    @ApiOperation("从head里获取JwtToken 在获取主体信息")
    @GetMapping("getMembberInfo")
    public Result getMembberInfo(HttpServletRequest request){
        String memberIdByJwtToken = JwtUtilt.getMemberIdByJwtToken(request);
        UcenterMember userInfo = ucenterMemberService.getById(memberIdByJwtToken);
        return Result.OK().data("userInfo",userInfo);
    }


    /**
     *
     * @param id
     * @return
     */
    @ApiOperation("根据ID查询用户，返回评论需要用户信息")
    @GetMapping("getMembberInfo/{id}")
    public UcenterVo getTokenById(@PathVariable("id") String id){
        UcenterMember byId = ucenterMemberService.getById(id);
        UcenterVo ucenterVo = new UcenterVo();
        BeanUtils.copyProperties(byId,ucenterVo);
        return ucenterVo;
    }


    @ApiOperation("根据用户查询,返回订单需要的用户信息")
    @GetMapping("getUcenterInfoOrder/{id}")
    public UcenterOrderVo getUcenterInfoOrder(@PathVariable("id")String id){
        UcenterOrderVo ucenterOrderVo = new UcenterOrderVo();
        UcenterMember byId = ucenterMemberService.getById(id);
        BeanUtils.copyProperties(byId,ucenterOrderVo);
        return ucenterOrderVo;
    }

    @GetMapping("countRegister/{day}")
    public Result countRegister(@PathVariable("day")String day){
        Integer count = ucenterMemberService.countRegisterDay(day);
        return Result.OK().data("countRegister",count);
    }

}

