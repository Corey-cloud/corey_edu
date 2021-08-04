package com.atguigu.ucenterservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.commonutils.vo.UcenterMemberVo;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author corey
 * @since 2021-07-28
 */
@Api(description="用户登录注册")
@RestController
@CrossOrigin //跨域
@RequestMapping("/ucenterservice/apimember")
public class MemberApiController {

    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        System.out.println("token:"+token);
        return R.ok().data("token", token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("auth/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            System.out.println("memberId: "+ memberId);
            UcenterMember loginInfoVo = memberService.getLoginInfo(memberId);
            System.out.println("loginInfo: " + loginInfoVo);
            return R.ok().data("item", loginInfoVo);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"error");
        }
    }

    //根据用户id获取用户信息
    @PostMapping("getInfoUc/{id}")
    public UcenterMemberVo getInfo(@PathVariable String id) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(id);
        UcenterMemberVo memeber = new UcenterMemberVo();
        BeanUtils.copyProperties(ucenterMember,memeber);
        return memeber;
    }
}

