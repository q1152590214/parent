package com.atguigu.test;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import org.junit.Test;

import java.util.List;

import static com.atguigu.test.test.initVodClient;

public class testVod {

    public static void main(String[] args) throws ClientException {
        DefaultAcsClient client = initVodClient("LTAI4G7TCBoWWLUTfwKxKt4E","APZrJCUi4uHmi2jIFyqjFcpc44eTFb");
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        GetVideoPlayAuthResponse getVideoPlayAuthResponse = new GetVideoPlayAuthResponse();
        try {
            response=getPlayInfo(client);
            getVideoPlayAuthResponse = getVideoPlayAuth(client);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
            System.out.println("视频凭证："+ getVideoPlayAuthResponse.getPlayAuth());

        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.print("RequestId = " + response.getRequestId() + "\n");

    }

    @Test
    public void Test() throws Exception {
        DefaultAcsClient client = initVodClient("LTAI4G7TCBoWWLUTfwKxKt4E","APZrJCUi4uHmi2jIFyqjFcpc44eTFb");
        GetVideoPlayAuthResponse videoPlayAuth = getVideoPlayAuth(client);
        System.out.println("凭证："+videoPlayAuth.getPlayAuth()+"\n");
        System.out.println("名称："+videoPlayAuth.getVideoMeta().getTitle());
        System.out.println("id:"+videoPlayAuth.getRequestId());
    }


    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("1d4137efc2944c8e86736266d9be0b79");
        return client.getAcsResponse(request);
    }



    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("1d4137efc2944c8e86736266d9be0b79");
        return client.getAcsResponse(request);
    }



    @Test
    public void  test1(){
        String accessKeyId = "LTAI4G7TCBoWWLUTfwKxKt4E";
        String accessKeySecret = "APZrJCUi4uHmi2jIFyqjFcpc44eTFb";
        String title = "测试";
        //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        //文件名必须包含扩展名
        String fileName = "D:\\bilibili缓存\\孕 气 不 会 差\\1.先上DJ ！ 先上DJ ！(Av93091311,P1).mp4";
        //本地文件上传
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为1M字节 */
        request.setPartSize(1 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
    /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
        request.setEnableCheckpoint(false);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    public static DeleteVideoResponse deleteVideo(DefaultAcsClient client) throws Exception {
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds("1118a74715f24fa6ab6207b5f27e6e2a");
        return client.getAcsResponse(request);
    }
    /*请求示例*/
    @Test
    public  void test() throws ClientException {
        DefaultAcsClient client = initVodClient("LTAI4G7TCBoWWLUTfwKxKt4E", "APZrJCUi4uHmi2jIFyqjFcpc44eTFb");
        try {

            DeleteVideoResponse response = new DeleteVideoResponse();
            response = deleteVideo(client);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }

    }


}
