package com.corey.aliyun.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.corey.aliyun.utils.AliyunVodSDKUtil;
import com.corey.aliyun.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by corey on 2021/7/25
 **/
@Api(description="阿里云视频点播微服务")
@RestController
//@CrossOrigin
@RequestMapping("/aliyun/vod/videos")
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("upload")
    public R uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return R.ok().message("视频上传成功").data("videoId", videoId);
    }

    // 根据id删除云端视频资源
    @DeleteMapping("{videoId}")
    public R removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
                         @PathVariable String videoId) {
        vodService.removeVideo(videoId);
        return R.ok().message("视频删除成功");
    }

    /**
     * 批量删除视频
     * @param videoIdList
     * @return
     */
    @DeleteMapping("delete-batch")
    public R removeVideoList(
            @ApiParam(name = "videoIdList", value = "云端视频id", required = true)
            @RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeVideoList(videoIdList);
        return R.ok().message("批量删除视频成功");
    }

    // 获取播放凭证
    @GetMapping("get-play-auth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {
        //获取阿里云存储相关常量
        String accessKeyId = "LTAI5tRxycDSE7PqAM7U2uXh";
        String accessKeySecret = "mppCivtML5Y9EyFxJaeuX8rblFQtGS";
        //初始化
        DefaultAcsClient client = AliyunVodSDKUtil.initVodClient(accessKeyId, accessKeySecret);
        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        // if the video does not exist
        if (response == null) {
            return R.error().message("The video does not exist");
        }
        //得到播放凭证
        String playAuth = response.getPlayAuth();
        System.out.println("播放凭证："+playAuth);
        //返回结果
        return R.ok().message("获取播放凭证成功").data("playAuth", playAuth);
    }

}
