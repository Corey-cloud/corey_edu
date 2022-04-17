package com.atguigu.ucenterservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.commonutils.vo.UcenterMemberVo;
import com.atguigu.ucenterservice.entity.vo.UpdatePasswordVo;
import com.atguigu.ucenterservice.entity.vo.UserVo;
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
//@CrossOrigin //跨域
@RequestMapping("/ucenter/member")
public class MemberApiController {

    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        try {
            String token = memberService.login(loginVo);
            System.out.println("token:"+token);
            return R.ok().data("token", token);
        } catch(GuliException e) {
            return R.error().message(e.getMsg());
        }
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo) {
        try {
            memberService.register(registerVo);
        } catch (GuliException e) {
            return R.error().message(e.getMsg());
        }
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
            return R.ok().data("userInfo", loginInfoVo);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"获取登录信息失败");
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

    //根据用户id获取
    @GetMapping("/{id}")
    public R getById(@PathVariable String id) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(id);
        UserVo user = new UserVo();
        BeanUtils.copyProperties(ucenterMember, user);
        return R.ok().data("user", user);
    }

    //根据用户id修改
    @PutMapping
    public R updateById(@RequestBody UserVo user) {
        if (user.getNickname().length() < 2 || user.getNickname().length() > 8) {
            return R.error().message("昵称必须为2-8个字符");
        }
        if (user.getSign().length() > 50) {
            return R.error().message("个性签名不得超过50个字符");
        }
        UcenterMember member = new UcenterMember();
        BeanUtils.copyProperties(user, member);
        //根据用户id获取用户信息
        boolean flag = memberService.updateById(member);
        if (flag) {
            return R.ok();
        }
        return R.error();
    }

    // 修改密码
    @PutMapping("/updatePassword")
    public R updatePassword(
            @RequestBody UpdatePasswordVo vo) {
        if (vo != null) {
            if (!vo.getPass().equals(vo.getCheckPass())) {
                return R.error().message("两次输入的密码不一致");
            }
            UcenterMember member = new UcenterMember();
            member.setId(vo.getId());
            member.setPassword(MD5.encrypt(vo.getCheckPass()));
            boolean update = memberService.updateById(member);
            if (update) {
                return R.ok();
            } else {
                return R.error();
            }
        }
        return R.error().message("传入的对象为空！NullPointer");
    }
}

