package com.atguigu.aclservice.service;

import com.atguigu.aclservice.model.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface RoleService extends IService<Role> {

    //根据用户id获取角色数据
    Map<String, Object> findRoleByUserId(String userId);

    //根据用户分配角色
    boolean saveUserRoleRealtionShip(String userId, String[] roleId);

    List<Role> selectRoleByUserId(String id);
}
