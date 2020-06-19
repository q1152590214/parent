package com.atguigu.oss.services;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String uploadOssFileAvatar(MultipartFile file);
}
