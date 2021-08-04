package com.atguigu.vodtest;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.vod.utils.AliyunVodSDKUtil;
import org.junit.Test;

import java.util.List;

/**
 * Created by corey on 2021/7/25
 **/

public class VodSdkTest {
    String accessKeyId = "你的accessKeyId";
    String accessKeySecret = "你的accessKeySecret";

    /**
     * 获取视频播放凭证
     * @throws ClientException
     */

    @Test
    public void testGetVideoPlayAuth() throws ClientException {
        //初始化客户端、请求对象和相应对象
        // aliyun.oss.file.keyid=LTAI5tRxycDSE7PqAM7U2uXh
        // aliyun.oss.file.keysecret=mppCivtML5Y9EyFxJaeuX8rblFQtGS
        DefaultAcsClient client = AliyunVodSDKUtil.initVodClient("LTAI5tRxycDSE7PqAM7U2uXh", "mppCivtML5Y9EyFxJaeuX8rblFQtGS");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse getVideoPlayAuthResponse = new GetVideoPlayAuthResponse();
        GetVideoPlayAuthResponse response = getVideoPlayAuthResponse;
        try {
            //设置请求参数
            request.setVideoId("fdd9bc559ac647249721ff63d74dc0eb");
            //获取请求响应
            response = client.getAcsResponse(request);
            //输出请求结果
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");

        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    /**
     * 获取视频播放地址
     * @throws ClientException
     */
    @Test
    public void testGetPlayInfo() throws ClientException {
        //初始化客户端、请求对象和相应对象
        DefaultAcsClient client = AliyunVodSDKUtil.initVodClient("LTAI5tRxycDSE7PqAM7U2uXh", "mppCivtML5Y9EyFxJaeuX8rblFQtGS");
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            //设置请求参数
            //注意：这里只能获取非加密视频的播放地址
            request.setVideoId("fdd9bc559ac647249721ff63d74dc0eb");
            //获取请求响应
            response = client.getAcsResponse(request);
            //输出请求结果
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
