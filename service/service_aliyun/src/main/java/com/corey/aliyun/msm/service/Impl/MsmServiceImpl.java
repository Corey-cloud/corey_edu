package com.corey.aliyun.msm.service.Impl;

import com.corey.aliyun.msm.service.MsmService;
import com.corey.aliyun.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by corey on 2021/7/28
 **/
@Service
public class MsmServiceImpl implements MsmService {
    // msm-api:
    // host: https://gyytz.market.alicloudapi.com
    // path: /sms/smsSend
    // method: POST
    // appcode: 4bd07cd367ce40d2bba98a52664ae896
    // smsSignId: 2e65b1bb3d054466b82f0c9d125465e2
    // templateId: 908e94ccf08b4476ba6c876d13f084ad
    // messageTemplateId: 63698e3463bd490dbc3edc46a20c55f5
    // expireMin: 10
    // cd: 60

    /**
     * 发送短信
     * @param phone
     * @param code
     */
    @Override
    public boolean send(String phone, String code) throws Exception {
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "4bd07cd367ce40d2bba98a52664ae896";
        String smsSignId = "2e65b1bb3d054466b82f0c9d125465e2";
        String templateId = "908e94ccf08b4476ba6c876d13f084ad";
        Map<String, String> headers = new HashMap<>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> queries = new HashMap<>();
        queries.put("mobile", phone);
        queries.put("param", "**code**:" + code + ",**minute**:" + 15);
        queries.put("smsSignId", smsSignId);
        queries.put("templateId", templateId);
        Map<String, String> body = new HashMap<>();

        HttpResponse response = HttpUtils.doPost(host, path, method, headers, queries, body);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode != 200) {
            String reasonPhrase = statusLine.getReasonPhrase();
            System.out.println(reasonPhrase);
            return false;
        }
        return true;
    }
}
