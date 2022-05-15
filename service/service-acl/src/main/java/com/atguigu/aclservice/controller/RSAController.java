package com.atguigu.aclservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.RSAUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by corey on 2022/5/15
 **/
@RestController
@RequestMapping("/rsa/acl")
public class RSAController {

    @ApiOperation(value = "获取公钥")
    @GetMapping("/getPublicKey")
    public R getKey() {
        System.out.println("------获取公钥------");
        String publicKey = RSAUtils.generateBase64PublicKey();
        System.out.println(publicKey);
        return R.ok().data("publicKey", publicKey);
    }
}
