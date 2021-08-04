package com.atguigu.cmsservice.controller;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by corey on 2021/7/27
 **/

@Api(description = "网站首页Banner列表")
@CrossOrigin //跨域
@RestController
@RequestMapping("/educms/banner")
public class BannerApiController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    public R index() {
        List<CrmBanner> list = bannerService.selectIndexList();
        return R.ok().data("bannerList", list);
    }
}
