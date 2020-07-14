package com.atguigu.eduorder.controller;


import com.alipay.api.AlipayApiException;
import com.atguigu.JwtUtilt;
import com.atguigu.Result.Result;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.entity.TPayLog;
import com.atguigu.eduorder.service.TOrderService;
import com.atguigu.eduorder.service.TPayLogService;
import com.atguigu.exception.MyExcaption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.web.bind.annotation.*;
import sun.awt.SunHints;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-11
 */
@RestController
@RequestMapping("/eduorder/order/")
@CrossOrigin
@Api(description = "订单模块")
public class TOrderController {

    @Resource
    TOrderService orderService;

    @Resource
    TPayLogService tPayLogService ;


    @ApiOperation(value = "生产订单编号")
    @PostMapping("createOrder/{courseId}")
    public Result saveOrder(@PathVariable("courseId") String courseId, HttpServletRequest request){
        String orderNo  =  orderService.createOrder(courseId, JwtUtilt.getMemberIdByJwtToken(request));
        return Result.OK().data("orderId",orderNo);
    }



    @ApiOperation(value = "订单编号查询订单")
    @GetMapping("gerOrderInfo/{orderOn}")
    public Result gerOrderInfo(@PathVariable("orderOn") String id){
        QueryWrapper<TOrder> tOrderQueryWrapper = new QueryWrapper<>();
        tOrderQueryWrapper.eq("order_on",id);
        TOrder one = orderService.getOne(tOrderQueryWrapper);
        return Result.OK().data("item",one);
    }

    @ApiOperation("异步支付宝回调方法")
    @PostMapping("queryPayStatus")
    public Result queryPayOrder(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, AlipayApiException {
        Map<String,String> map = tPayLogService.queryPayOrder(request,response);
        System.out.println("###########################################\n"+map);
        if(map.keySet().size()<1){
            return Result.ERROR().massage("支付失败");
        }else if (map.get("verify_result").equals("true")){
            tPayLogService.updataOrderStatus(map);
            return Result.OK();
        }
        return Result.OK().code(25000).massage("支付中");
    }

    @ApiOperation(value = "查询购买了什么课程")
    @PostMapping("isBuyCourse/{courseId}/{menberId}")
    public boolean isBuyCourse(@PathVariable("courseId")String courseId ,@PathVariable("menberId")String menberId){
        QueryWrapper<TOrder> tOrderQueryWrapper = new QueryWrapper<>();
        tOrderQueryWrapper.eq("course_id",courseId);
        tOrderQueryWrapper.eq("member_id",menberId);
        tOrderQueryWrapper.eq("status",1);
        int count = orderService.count(tOrderQueryWrapper);
        if(count>0){
            return true;
        }
        return false;
    }


}

