package com.atguigu.eduorder.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.eduorder.Utils.AliPayUtil;
import com.atguigu.eduorder.Utils.OrderOnUtli;
import com.atguigu.eduorder.entity.AliPayConfig;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.entity.TPayLog;
import com.atguigu.eduorder.service.TOrderService;
import com.atguigu.eduorder.service.TPayLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Api(description = "支付宝模块")
@RequestMapping("eduorder/pay/")
public class AliPayController {

    @Resource
    TOrderService orderService;


    @GetMapping("createNative/{orderNo}")
    public void Alipay(@PathVariable("orderNo")String orderNo, HttpServletRequest httpRequest,
                       HttpServletResponse httpResponse) throws ServletException, IOException {


        String form = orderService.createAliPau(orderNo);
        httpResponse.setContentType( "text/html;charset="  + AliPayConfig.CHARSET);
        //直接将完整的表单html输出到页面
        httpResponse.getWriter().write(form);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }
}
