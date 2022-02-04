package com.atguigu.orderservice.client;

import com.atguigu.commonutils.vo.CourseInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by corey on 2021/8/1
 **/
@Component
@FeignClient("service-edu")
public interface EduClient {

    //根据课程id查询课程信息
    @GetMapping("/edu/courses/getDto/{courseId}")
    public CourseInfoVo getCourseInfoDto(@PathVariable String courseId);
}
