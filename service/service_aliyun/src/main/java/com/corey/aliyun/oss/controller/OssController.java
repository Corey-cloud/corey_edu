package com.corey.aliyun.oss.controller;

import com.atguigu.commonutils.R;
import com.corey.aliyun.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by corey on 2021/7/22
 **/

@Api(description="阿里云文件管理")
@RestController
@RequestMapping("/aliyun/oss/fileoss")
//@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    // 上传资源到阿里云oss的方法
    @ApiOperation(value = "文件上传")
    @PostMapping
    public R updateOssFile(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
        String url = ossService.upload(file);
        return R.ok().message("文件上传成功").data("url", url);
    }
}
