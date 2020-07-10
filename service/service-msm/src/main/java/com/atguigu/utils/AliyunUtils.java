package com.atguigu.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AliyunUtils implements InitializingBean {


    @Value(value = "${aliyun.vod.file.keyid}")
    private  String Key;

    @Value(value = "${aliyun.vod.file.keysecrer}")
    private  String Secret;


    public static  String ALIYUN_KEY;
    public static  String ALIYUN_SECRET;





    @Override
    public void afterPropertiesSet() throws Exception {
            ALIYUN_KEY = Key;
            ALIYUN_SECRET = Secret;
    }
}
