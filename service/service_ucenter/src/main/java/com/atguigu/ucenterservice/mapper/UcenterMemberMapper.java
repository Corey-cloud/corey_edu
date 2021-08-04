package com.atguigu.ucenterservice.mapper;

import com.atguigu.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author corey
 * @since 2021-07-29
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer selectRegisterCount(String day);
}
