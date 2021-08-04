package com.atguigu.eduservice.client;

import org.springframework.stereotype.Component;

/**
 * Created by corey on 2021/8/1
 **/
@Component
public class OrderFile implements OrderClient {

    @Override
    public boolean isBuyCourse(String memberId, String courseId) {
        return false;
    }
}
