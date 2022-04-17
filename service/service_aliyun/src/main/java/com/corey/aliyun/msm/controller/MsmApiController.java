package com.corey.aliyun.msm.controller;

import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.corey.aliyun.msm.service.MsmService;
import com.corey.aliyun.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by corey on 2021/7/28
 **/

@RestController
//@CrossOrigin //跨域
@RequestMapping("/aliyun")
public class MsmApiController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping(value = "/send/{phone}")
    public R code(@PathVariable String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) return R.ok().data("这是redis中的验证码",code);

        code = RandomUtil.getSixBitRandom();
        System.out.println("验证码："+code);
        boolean isSend = false;
        try {
            isSend = msmService.send(phone, code);
        } catch (Exception e) {
            return R.error().message(e.getMessage());
        }

       if(isSend) {
           System.out.println("短信发送成功");
           redisTemplate.opsForValue().set(phone, code,15, TimeUnit.MINUTES);
           return R.ok();
       } else {
           return R.error().message("发送短信失败");
       }
    }
}
