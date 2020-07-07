package com.atguigu.educrm.controller;


import com.atguigu.Result.Result;
import com.atguigu.educrm.entity.CrmBanner;
import com.atguigu.educrm.service.CrmBannerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-03
 */
@RestController
@RequestMapping("/educms/bannerfron")
@CrossOrigin
public class CrmBannerFrontController {

    @Resource
    CrmBannerService crmBannerService;

    @GetMapping("getAllBranner")
    public Result getAllBranner(){
        List<CrmBanner> list = crmBannerService.selectAllBanner();
        return Result.OK().data("list",list);
    }

}

