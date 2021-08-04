package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by corey on 2021/7/22
 **/

@Api(description="阿里云文件管理")
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
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
