package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by corey on 2021/7/26
 **/
@Component
@FeignClient(name = "service-aliyun", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    @DeleteMapping("/aliyun/vod/videos/{videoId}")
    R removeVideo(@PathVariable("videoId") String videoId);

    @DeleteMapping("/aliyun/vod/videos/delete-batch")
    R removeVideoList(@RequestParam("videoIdList") List<String> videoIdList);
}
