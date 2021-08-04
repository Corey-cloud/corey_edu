package com.atguigu.staservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by corey on 2021/8/1
 **/

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @GetMapping(value = "/ucenterservice/ucenter-member/countregister/{day}")
    public R registerCount(@PathVariable String day);
}
