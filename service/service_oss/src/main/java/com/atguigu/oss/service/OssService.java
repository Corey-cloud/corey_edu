package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by corey on 2021/7/22
 **/

public interface OssService {

    /**
     * 文件上传至阿里云
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
