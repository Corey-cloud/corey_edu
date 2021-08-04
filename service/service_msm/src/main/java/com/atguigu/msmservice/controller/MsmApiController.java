package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by corey on 2021/7/28
 **/

@RestController
@CrossOrigin //跨域
@RequestMapping("/edumsm")
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
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
        return R.ok().data("这是模拟用阿里的api发送的验证码：",code);
//        boolean isSend = msmService.send(phone, "SMS_220637734", param);
//        if(isSend) {
//            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
//            return R.ok();
//        } else {
//            return R.error().message("发送短信失败");
//        }
    }
}
