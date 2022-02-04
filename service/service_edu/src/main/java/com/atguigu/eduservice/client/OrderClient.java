package com.atguigu.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by corey on 2021/8/1
 **/

@Component
@FeignClient(value = "service-order", fallback = OrderFile.class)
public interface OrderClient {
    @GetMapping("/order/isBuyCourse/{memberId}/{courseId}")
    boolean isBuyCourse(@PathVariable String memberId,
                               @PathVariable String courseId);
}
