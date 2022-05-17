package com.atguigu.ucenterservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.MemberVo;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author corey
 * @since 2021-07-29
 */
@RestController
@RequestMapping("/ucenter")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @GetMapping(value = "/member/countregister/{day}")
    public R registerCount(@PathVariable String day){
        Integer count = memberService.countRegisterByDay(day);
        if (count != null) {
            return R.ok().data("countRegister", count);
        } else {
            return R.error();
        }
    }

    @GetMapping("/admin/member")
    public R pageList(@RequestParam Integer page,
                      @RequestParam Integer limit,
                      MemberVo member) {
        Page<UcenterMember> pageParam = new Page<>(page, limit);
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        if (member != null) {
            if (!StringUtils.isEmpty(member.getNickname())) {
                wrapper.like("nickname", member.getNickname());
            }
            if (!StringUtils.isEmpty(member.getMobile())) {
                wrapper.like("mobile", member.getMobile());
            }
            if (!StringUtils.isEmpty(member.getBegin())) {
                wrapper.ge("gmt_create", member.getBegin());
            }
            if (!StringUtils.isEmpty(member.getEnd())) {
                wrapper.le("gmt_create", member.getEnd());
            }
            if (!StringUtils.isEmpty(member.getIsDisabled())) {
                wrapper.eq("is_disabled", member.getIsDisabled());
            }
        }
        IPage<UcenterMember> pageModel = memberService.page(pageParam, wrapper.orderByDesc("gmt_create"));
        List<UcenterMember> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return R.ok().data("records", records).data("total", total);
    }

    @GetMapping("/admin/member/{id}")
    public R getById(@PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        return R.ok().data("member", member);
    }

    @PutMapping("/admin/member")
    public R update(@RequestBody UcenterMember member) {
        boolean update = memberService.updateById(member);
        if (update) {
            return R.ok();
        }
        return R.error();
    }

    @DeleteMapping("/admin/member")
    public R deleteById(@RequestParam String id) {
        boolean delete = memberService.removeById(id);
        if (delete) {
            return R.ok();
        }
        return R.error();
    }
}

