package com.atguigu.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.Vo.CourseTeacherOrderVo;
import com.atguigu.Vo.UcenterOrderVo;
import com.atguigu.eduorder.Utils.AliPayUtil;
import com.atguigu.eduorder.Utils.OrderOnUtli;
import com.atguigu.eduorder.client.EduClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.AliPayConfig;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.entity.TPayLog;
import com.atguigu.eduorder.mapper.TOrderMapper;
import com.atguigu.eduorder.service.TOrderService;
import com.atguigu.eduorder.service.TPayLogService;
import com.atguigu.exception.MyExcaption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-11
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Resource
    EduClient eduClient;

    @Resource
    UcenterClient ucenterClient;

    @Resource
    TPayLogService tPayLogService;


    @Resource
    TOrderService orderService;


    @Override
    public String createOrder(String courseId, String memberIdByJwtToken) {

        CourseTeacherOrderVo courseTeacherInfoOrder = eduClient.getCourseTeacherInfoOrder(courseId);

        UcenterOrderVo ucenterInfoOrder = ucenterClient.getUcenterInfoOrder(memberIdByJwtToken);

        TOrder order = new TOrder();
        order.setOrderNo(OrderOnUtli.GetOrderOn());
        order.setTeacherName(courseTeacherInfoOrder.getTeacherName());
        order.setCourseCover(courseTeacherInfoOrder.getCover());
        order.setCourseId(courseTeacherInfoOrder.getId());
        order.setCourseTitle(courseTeacherInfoOrder.getTitle());
        order.setMemberId(ucenterInfoOrder.getId());
        order.setMobile(ucenterInfoOrder.getMobile());
        order.setNickname(ucenterInfoOrder.getNickname());
        order.setIsDeleted(false);
        order.setStatus(0);
        order.setPayType(2);
        int insert = baseMapper.insert(order);
        if(insert<0){
            throw  new MyExcaption(20001,"添加订单失败");
        }

        return order.getOrderNo();
    }

    /**
     * 支付宝支付
     * @param orderNo
     * @return
     */
    @Override
    public String createAliPau(String orderNo) {

        QueryWrapper<TOrder> tOrderQueryWrapper = new QueryWrapper<>();
        tOrderQueryWrapper.eq("order_no" ,orderNo);
        TOrder order = orderService.getOne(tOrderQueryWrapper);

        AlipayClient alipayClient  = AliPayUtil.getAlipayClient();
        AlipayTradePagePayRequest alipayRequest =  new  AlipayTradePagePayRequest(); //创建API对应的request
        //alipayRequest.setReturnUrl(AliPayConfig.return_url);
        alipayRequest.setNotifyUrl(AliPayConfig.notify_url); //在公共参数中设置回跳和通知地址
        //alipayRequest.putOtherTextParam("app_auth_token", "201611BB8xxxxxxxxxxxxxxxxxxxedcecde6");//如果 ISV 代商家接入电脑网站支付能力，则需要传入 app_auth_token，使用第三方应用授权；自研开发模式请忽略

        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("out_trade_no",OrderOnUtli.GetOrderOn());
        stringMap.put("product_code","FAST_INSTANT_TRADE_PAY");
        stringMap.put("total_amount",order.getTotalFee().toString());
        stringMap.put("subject",order.getCourseTitle());
        stringMap.put("body",order.getCourseTitle());
        stringMap.put("passback_params",order.getId());

        System.out.println("*******************************************8"+JSONObject.toJSONString(stringMap));
//        alipayRequest.setBizContent( "{"  +
//                "    \"out_trade_no\":\"20150320010101001\","  +
//                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\","  +
//                "    \"total_amount\":88.88,"  +
//                "    \"subject\":\"Iphone6 16G\","  +
//                "    \"body\":\"Iphone6 16G\","  +
//                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","  +
//                "    \"extend_params\":{"  +
//                "    \"sys_service_provider_id\":\"2088511833207846\""  +
//                "    }" +
//                "  }" ); //填充业务参数
        alipayRequest.setBizContent(JSONObject.toJSONString(stringMap));
        String form= null ;
        try  {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
            return form;
        }  catch  (AlipayApiException e) {
            throw  new MyExcaption(20001,"支付失败");
        }
    }
}
