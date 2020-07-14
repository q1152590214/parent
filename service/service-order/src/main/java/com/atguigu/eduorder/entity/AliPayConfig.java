package com.atguigu.eduorder.entity;

import lombok.Data;

@Data
public class AliPayConfig {

    // 商户appid
    public static String APPID = "2016102800774544";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCc4SGX2SW94R6GiH3IoF30WRzfZvhvUvkUgaYgDF1sTjPu91zFnDApS5JKda/3Ir4Rjf/KTHXNAXc3nZkgzXkBlswKYMGe/qKmwKalo/mkvEIibOEi3t0DddxK8nnDJSAVLsQuTfqR5Xx5GPxhoTIv5xh+NWqDoRPYwv81vRciGMYd1Skm9F8OSqQVHIRzjjyEzzesTFbnJ8X8kHLbPD5Hz8jX0rtHKVvAHznWbcXyAwQRhIMzNrFs7PjVQ5ZCCZfLnnJTgWyQoZ+IajOF5BLNeKeZPiufhI3PhwJccWWOVbi0F92JOHACDT+lU8rd7hOhW14tkHTSv0zz44d/Q6YTAgMBAAECggEAEkw/+tIdENSdFtv8r9Xkn4d1UqlT+MgYvUeR9NL8YpGMhSbc7z+UbnyeB4lKiOzIdou1dgcNBmhRW29KWQUeRA0up1c5vi29Eu4NgexqRciffhxs9gBAjxLEKwm1IgnGgDIAN9Zoj3PQ3N8JMOn4TJYLyUy5q7ix8+pqkf4IILXMQM8g5AMLm+s3sRjaZeUgsO4BJOcNV9zKXp7e+AxFRboxauF+yxy8zQgTggquMLk7pwvy9VcDLPJIzaxt15ImoS+tj40MF9fpMW2r0tW3gCFmbiRl2iKl6vC7AVMMqVwLmPQA/hP2+tt3YKR0FxXB602CQMvtgEM1RfT6HhTGQQKBgQDObM6EvgpRUcdE3H7kwxtIMwMfmsyFlHIRPa71rs13RDvxShAyAqcOCA06v6ndhsvXoPQJ2avNmm3auirnpbp/p1U5hJvxHyQk2KG3r61soE0fAiQ0Wp05MpwGsUDXAkLzPyJySqAVDrsU6ZC4T+Wawoy0tXNxkqLnDMApvrj0CwKBgQDCjjd7LbSdpT5Cz5JjUIx0UU4Z2LFxLNx65OJfmrVUrZefHr4PNWMX8Pl2pToN0GAnNLJu242NSN6SyZtPf4HtB0XqMuDOM5mTjwArcWxKFUt27DTCIroMwasnANsoL+JhFiCfjbv0G4+22nHeDrU/oiRYWGzpHyH5FlnizqUTGQKBgQCgRmEvNfMC4tEqyV0JN+gF7f29205zZSgP9OnUvm2v7d3w4gWjHuYggeXruIcmhpPdD46L22OG1yH2k1fe1pHwrbcBWtJNrFU++qjN22x3DBv1OI9tyvGKSJ5Wn+ftSUc8YMfZH9Idp3tgqF0Cjp6CxA3F/aDuDOHJLeBCSWCHswKBgQC+5Gmdi8/u6gZ36VC573uQWYInLA2WfSzrzA4tPg/zQMxHDRPwujI7eG5csmuOyEi/zx3QVc3CtZ/lKLkhvHsPxZYc38ThrZFfTe0dsxFMcy5zRfnSDd68y32SLG3cNXeKSPrDcn7bWfudryIQfh/qir3cjQ53gduEmiChwJ4JaQKBgBpHl5HP5xi8Cjyon9+T/xxzMellME1rm/LGHDHuMJXmFY/2KgL37/Xjj6G6M6t/njD59Ut9AjtCc6CB+4gSGwj3aXisKgBXGG0boCMb6lIdrKtiaCz10dUFpwOgWSJp8RtbGneuo8YbBM2RYuv5HI1qPujhH4hhyWJB48cFB6fK";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://chx5227.natapp1.cc/eduorder/order/queryPayStatus";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://chx5227.natapp1.cc/eduorder/order/queryPayStatus";
    // 请求网关地址
    public static String URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY =  "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhqrSiM+F7c1x5hBPVtifZPq2pjaK/0oroC8fa/5l7JAVo3pB82LU8wPLyFFCwL4XG7NAaksE9sUAVGWudVMsG1xI0xdzChe6+zSrk0oP2mdlJ3p7BRZIXjiKNMRheSJG21jTbw7rMnGbeB1mDzj2wD8K4vvwKkopbQuRQMBJEbR3Kcrl3MXZQVIvp9C3kaR1ATsK5XgNnrc0fB12R1wbxwm5p31Sspym+FPzaHFRg4Kn6dm4xLKMo9sxK8zE+AyFssTiR8oN/+d7Yzq+g+v7FY+TGYNe1BYpAKEQI9iR71t6IQm0F9W4DsnQRaNuLX+PUNo8DfnIHJvs46wK84P4MwIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
