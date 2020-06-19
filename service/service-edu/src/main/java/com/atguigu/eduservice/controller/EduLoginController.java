package com.atguigu.eduservice.controller;

import com.atguigu.Result.Result;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user/")
@CrossOrigin
@Api(description ="登录模块" )
public class EduLoginController {

    @PostMapping("login")
    public Result login(){

        return Result.OK().data("token","admin");
    }


    @GetMapping("info")
    public Result info(){

        return Result.OK().data("roles","[name]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80");
    }

}
