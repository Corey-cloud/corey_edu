package com.atguigu.demo;

import com.atguigu.eduservice.utils.HttpURLUtil;

/**
 *  查询IP归属地的接口
 *  太平洋IP地址获取IP信息
 *  http://whois.pconline.com.cn/ipJson.jsp
 */

public class TestParseIp {
    private static String url = "http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=";

    public static void main(String[] args) throws Exception {
        String ip = "0:0:0:0:0:0:0:1";
        url += ip;
        String s = HttpURLUtil.doGet(url);
        System.out.println(s);
    }
}
