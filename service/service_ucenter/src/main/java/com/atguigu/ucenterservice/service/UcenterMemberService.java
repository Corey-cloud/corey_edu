package com.atguigu.ucenterservice.service;

import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author corey
 * @since 2021-07-29
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    UcenterMember getLoginInfo(String memberId);

    Integer countRegisterByDay(String day);
}
