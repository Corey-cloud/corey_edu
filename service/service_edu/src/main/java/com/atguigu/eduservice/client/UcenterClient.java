package com.atguigu.eduservice.client;

import com.atguigu.commonutils.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by corey on 2021/7/31
 **/

@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    //根据用户id获取用户信息
    @PostMapping("/ucenterservice/apimember/getInfoUc/{id}")
    public UcenterMemberVo getInfo(@PathVariable String id);
}

