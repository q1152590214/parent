package com.atguigu.statistics_daily.controller;


import com.atguigu.Result.Result;
import com.atguigu.statistics_daily.client.UcenterClient;
import com.atguigu.statistics_daily.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-14
 */
@RestController
@RequestMapping("/statistics/sta/")
@Api(description = "统计模块")
public class StatisticsDailyController {


    @Resource
    private StatisticsDailyService dailyService;


    @ApiOperation(value = "根据日期查询相应的记录")
    @PostMapping("registerCount/{day}")
    public Result registerCount(@PathVariable("day")String day){
        dailyService.registerCount(day);
        return  Result.OK();
    }

    @GetMapping("ShowData/{type}/{begin}/{edn}")
    public Result getShowData(@PathVariable("type") String type,@PathVariable("begin") String begin,@PathVariable("edn") String edn){
       Map<String,Object>  map = dailyService.getShowData(type,begin,edn);
       return  Result.OK().data(map);
    }




}

