package com.atguigu.oss.services.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.services.OssService;
import com.atguigu.oss.utils.ConstanPropertiesUtils;
import io.swagger.annotations.Api;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service

public class OssServiceImpl implements OssService  {



    @Override
    public String uploadOssFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint =ConstanPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId =ConstanPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret =ConstanPropertiesUtils.ACCESS_KEY_SECRET;
        String buckeyName =ConstanPropertiesUtils.BUCKET_NAME;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            //获取文件的名称
            StringBuilder fileName = new StringBuilder(file.getOriginalFilename());
            //qwd3-1233-wqe43-qnf4
            String uuid = UUID.randomUUID().toString().replaceAll("-", ""); //替换string里某个字符为XXX
            fileName.insert(0,uuid);
            String dateTime = new DateTime().toString("yyyy/MM/dd");
            //第一个参数为Byckey 的名称
            //第二个参数 上传到OSS文件的路径和名称
            //第三个参数为 上传的文件的输入流
            fileName.insert(0,dateTime+"/");
            ossClient.putObject(buckeyName, fileName.toString(), inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //上传文件的路径放回出去,拼接
            //URL规则
            //https://edu-parent-5227.oss-cn-beijing.aliyuncs.com/QQ%E5%9B%BE%E7%89%8720191125161104.jpg
            String  url = "https://"+buckeyName+"."+endpoint+"/"+fileName;
            return url;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
