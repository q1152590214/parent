package com.atguigu.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.Result.Result;
import com.atguigu.Utils.ConstanVodUtils;
import com.atguigu.Utils.InitObject;
import com.atguigu.exception.MyExcaption;
import com.atguigu.service.VodService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class VodServiceImpl implements VodService {


    /**
     * 文件上传到阿里云，放回授权ID
     * @param file
     * @return
     */
    @Override
    public String uploadVideoAly(MultipartFile file) {

        String videoId = null;
        try {
            String title ;
            String fileName =file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            title = fileName.substring(0,fileName.lastIndexOf('.'));
            UploadStreamRequest request = new UploadStreamRequest(ConstanVodUtils.ACCESS_KEY_ID, ConstanVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                videoId = response.getVideoId();
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
                videoId = response.getVideoId();
            }

        }catch (IOException e){
            throw  new MyExcaption(20001,"文件流异常");
        }

        return videoId;
    }

    @Override
    public void deleteRemoveAlyVod(List videolist) {
        try {
            int i = 10/0;
         String id = StringUtils.join(videolist.toArray(),",");
            DefaultAcsClient client = InitObject.initVodClient(ConstanVodUtils.ACCESS_KEY_ID, ConstanVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
        }catch (ClientException e) {
            throw  new MyExcaption(20001,e.getMessage());
        }
    }


}
