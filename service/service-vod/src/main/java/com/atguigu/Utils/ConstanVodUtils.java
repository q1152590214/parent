package com.atguigu.Utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstanVodUtils implements InitializingBean {


    @Value(value = "${aliyun.vod.file.keyid}")
    private  String keyId;

    @Value(value = "${aliyun.vod.file.keysecrer}")
    private  String keySecrer;

    public static String ACCESS_KEY_SECRET;

    public static String ACCESS_KEY_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID=  keyId;
        ACCESS_KEY_SECRET = keySecrer;
        System.out.println(ACCESS_KEY_ID+"\n"+ACCESS_KEY_SECRET);
    }
}
