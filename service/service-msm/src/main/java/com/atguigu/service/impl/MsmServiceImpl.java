package com.atguigu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.service.MsmService;
import com.atguigu.utils.AliyunUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(HashMap<String, Object> stringObjectHashMap, String phone) {

        if(StringUtils.isEmpty(phone)){ return false;}


        DefaultProfile profile =
                DefaultProfile.getProfile("default", AliyunUtils.ALIYUN_KEY, AliyunUtils.ALIYUN_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);


        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        //设置发送的手机号
        request.putQueryParameter("PhoneNumbers",phone);
        //阿里云签名名称
        request.putQueryParameter("SignName","云端科技线上教育系统");
        //模块Code
        request.putQueryParameter("TemplateCode","SMS_195480055");
        //验证码，以JSON的格式进行发送
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(stringObjectHashMap));
        try {
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            System.out.println(response.getData());
            return success;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return false;



    }
}
