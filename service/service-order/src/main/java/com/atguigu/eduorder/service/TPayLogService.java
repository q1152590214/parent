package com.atguigu.eduorder.service;

import com.alipay.api.AlipayApiException;
import com.atguigu.eduorder.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-11
 */
public interface TPayLogService extends IService<TPayLog> {


    Map<String, String> queryPayOrder(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, AlipayApiException;

    void updataOrderStatus(Map<String, String> map);
}
