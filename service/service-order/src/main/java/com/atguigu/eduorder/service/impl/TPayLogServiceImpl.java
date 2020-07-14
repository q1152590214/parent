package com.atguigu.eduorder.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.atguigu.eduorder.entity.AliPayConfig;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.entity.TPayLog;
import com.atguigu.eduorder.mapper.TPayLogMapper;
import com.atguigu.eduorder.service.TOrderService;
import com.atguigu.eduorder.service.TPayLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-11
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {


    @Resource
    TOrderService orderService;

    @Override
    public Map<String, String> queryPayOrder(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, AlipayApiException {
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verify_result = AlipaySignature.rsaCheckV1(params, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.CHARSET, "RSA2");
        params.put("out_trade_no",out_trade_no);
        params.put("trade_no",trade_no);
        params.put("trade_status",trade_status);
        params.put("verify_result",String.valueOf(verify_result));
        return params;



    }

    @Override
    public void updataOrderStatus(Map<String, String> map) {
        TOrder one = orderService.getById(map.get("passback_params"));
        if(one.getStatus().equals(1)){
            return;
        }
        one.setStatus(1);
        orderService.updateById(one);


        TPayLog tPayLog = new TPayLog();
        //订单号
        tPayLog.setOrderNo(one.getOrderNo());
        //交易金额
        tPayLog.setTotalFee(one.getTotalFee());
        //支付类型
        tPayLog.setPayType(2);
        //支付宝流水号
        tPayLog.setTransactionId(map.get("trade_no"));
        //交易完成时间
        tPayLog.setPayTime(new Date());
        //交易状态
        tPayLog.setTradeState(map.get("trade_status"));
        //详细Map
        tPayLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(tPayLog);
    }
}
