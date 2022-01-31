package com.atguigu.aclservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.aclservice.service.IndexService;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.RSAUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/index")
//@CrossOrigin
public class IndexController {

    @Autowired
    private IndexService indexService;

    @ApiOperation(value = "获取公钥")
    @GetMapping("/publicKey")
    public R getKey() {
        System.out.println("------获取公钥------");
        String publicKey = RSAUtils.generateBase64PublicKey();
        System.out.println(publicKey);
        return R.ok().data("publicKey", publicKey);
    }

    /**
     * 根据token获取用户信息
     */
    @GetMapping("info")
    public R info(){
        //获取用户信息
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return R.ok().data(userInfo);
    }

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("menu")
    public R getMenu(){
        //获取菜单
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return R.ok().data("permissionList", permissionList);
    }

    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }

}
