package com.atguigu.educrm.controller;


import com.atguigu.Result.Result;
import com.atguigu.educrm.entity.CrmBanner;
import com.atguigu.educrm.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-03
 */
@RestController
@RequestMapping("/educms/banneradmin/")
@CrossOrigin
@Api(description = "首页模块")
public class CrmBannerController {

    @Resource
    CrmBannerService crmBannerService;

    @ApiOperation(value = "首页列表分页")
    @GetMapping("pageBanner/{page}/{limit}")
    public Result pageBanner(@PathVariable("page") long page, @PathVariable("limit") long limit){
        Page<CrmBanner> crmBannerPage = new Page<>(page,limit);
        crmBannerService.page(crmBannerPage,null);
        return Result.OK().data("items",crmBannerPage.getRecords()).data("total",crmBannerPage.getTotal());
    }

    @PostMapping("addBanner")
    public Result  saveBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return Result.OK();
    }

    @PostMapping("update")
    public Result updateBanner(@RequestBody CrmBanner crmBanner){

        crmBannerService.updateById(crmBanner);
        return Result.OK();
    }


    @DeleteMapping("remove/{id}")
    public Result  deleteBanner(@PathVariable("id") String id){
        crmBannerService.removeById(id);
        return Result.OK();
    }

    @GetMapping("get/{id}")
    public Result getBanner(@PathVariable("id") String id){
        CrmBanner byId = crmBannerService.getById(id);
        return Result.OK().data("item",byId);
    }

}

