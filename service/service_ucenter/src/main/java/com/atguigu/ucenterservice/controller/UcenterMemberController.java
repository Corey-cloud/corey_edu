package com.atguigu.ucenterservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author corey
 * @since 2021-07-29
 */
@RestController
@RequestMapping("/ucenterservice/ucenter-member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @GetMapping(value = "countregister/{day}")
    public R registerCount(@PathVariable String day){
        Integer count = memberService.countRegisterByDay(day);
        if (count != null) {
            return R.ok().data("countRegister", count);
        } else {
            return R.error();
        }
    }
}

