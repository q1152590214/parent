package com.atguigu.eduorder.Utils;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayMobilePublicMultiMediaClient;
import com.alipay.api.DefaultAlipayClient;
import com.atguigu.eduorder.entity.AliPayConfig;

public class AliPayUtil {

    public static AlipayClient getAlipayClient(){
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.URL,AliPayConfig.APPID,AliPayConfig.RSA_PRIVATE_KEY,AliPayConfig.FORMAT,AliPayConfig.CHARSET,AliPayConfig.ALIPAY_PUBLIC_KEY,AliPayConfig.SIGNTYPE);
        return alipayClient;
    }
}
