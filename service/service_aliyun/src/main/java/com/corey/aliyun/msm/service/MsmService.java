package com.corey.aliyun.msm.service;

/**
 * Created by corey on 2021/7/28
 **/

public interface MsmService {
    boolean send(String phone, String code) throws Exception;
}
