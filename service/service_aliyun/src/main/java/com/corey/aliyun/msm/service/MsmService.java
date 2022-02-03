package com.corey.aliyun.msm.service;

import java.util.Map;

/**
 * Created by corey on 2021/7/28
 **/

public interface MsmService {
    boolean send(String PhoneNumbers, String templateCode, Map<String,Object> param);
}
