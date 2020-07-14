package com.atguigu.educenter.utlis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
public class ConstanWxUtils implements InitializingBean {

    @Value(value = "${wx.open.appid}")
    private  String appId;

    @Value(value = "${wx.open.appsecret}")
    private  String appSecret;

    @Value(value = "${wx.open.redirecturl}")
    private  String redirectUrl;


    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_REDIRECT_URL = redirectUrl;
    }




}
