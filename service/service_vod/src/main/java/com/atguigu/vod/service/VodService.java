package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by corey on 2021/7/25
 **/

public interface VodService {

    String uploadVideo(MultipartFile file);

    void removeVideo(String videoId);

    void removeVideoList(List<String> videoIdList);
}
