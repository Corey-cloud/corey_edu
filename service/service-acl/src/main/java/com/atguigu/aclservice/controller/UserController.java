package com.atguigu.aclservice.controller;


import com.atguigu.aclservice.model.entity.User;
import com.atguigu.aclservice.model.vo.UserVo;
import com.atguigu.aclservice.service.RoleService;
import com.atguigu.aclservice.service.UserService;
import com.atguigu.commonutils.MD5;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.RSAUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/user")
//@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取公钥")
    @GetMapping("/getPublicKey")
    public R getKey(HttpServletRequest request) {
        String publicKey = RSAUtils.generateBase64PublicKey();
        System.out.println(publicKey);
        return R.ok().data("publicKey", publicKey);
    }

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Integer page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Integer limit,

            @ApiParam(name = "userQuery", value = "查询对象", required = false)
                    User userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username", userQueryVo.getUsername());
        }
        IPage<User> pageModel = userService.page(pageParam, wrapper);
        int count = userService.count(wrapper);
        return R.ok().data("items", pageModel.getRecords()).data("total", count);
    }

    @ApiOperation(value = "根据id获取用户信息")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable String id) {
        User user = userService.getById(id);
        return R.ok().data("data", user);
    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public R save(@RequestBody UserVo userVo) {

        // 将从前端获取到的用户加密信息进行解密
        userVo.setUsername(RSAUtils.decryptBase64(userVo.getUsername()));
        userVo.setPass(RSAUtils.decryptBase64(userVo.getPass()));
        userVo.setCheckPass(RSAUtils.decryptBase64(userVo.getCheckPass()));

        // 后端校验
        String regex1 = "[A-Za-z0-9]{5,10}";
        String regex2 = "[A-Za-z0-9]{2,8}";
        if (!userVo.getUsername().matches(regex1)) {
            throw new GuliException(20001, "用户名不得小于5个或大于10个字符!");
        }
        if (!userVo.getNickName().matches(regex2)) {
            throw new GuliException(20001, "昵称不得小于2个或大于8个字符!");
        }
        if (!userVo.getPass().matches("\\w{6,16}")) {
            throw new GuliException(20001, "密码不能小于6个或大于16个字符");
        }
        if (!userVo.getPass().equals(userVo.getCheckPass())) {
            throw new GuliException(20001, "两次输入密码不一致！");
        }

        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        user.setPassword(MD5.encrypt(userVo.getPass()));

        try {
            userService.save(user);
        } catch (DuplicateKeyException e) {
            throw new GuliException(20001, "该用户名已存在");
        }

        return R.ok().message("添加用户成功");
    }

    @ApiOperation(value = "修改管理用户")
    @PutMapping("update")
    public R updateById(@RequestBody UserVo userVo) {

        // 将从前端获取到的用户加密信息进行解密
        userVo.setUsername(RSAUtils.decryptBase64(userVo.getUsername()));

        // 后端校验
        String regex1 = "[A-Za-z0-9]{5,10}";
        String regex2 = "[A-Za-z0-9]{2,8}";
        if (!userVo.getUsername().matches(regex1)) {
            throw new GuliException(20001, "用户名不得小于5个或大于10个字符!");
        }
        if (!userVo.getNickName().matches(regex2)) {
            throw new GuliException(20001, "昵称不得小于2个或大于8个字符!");
        }

        User user = new User();
        BeanUtils.copyProperties(userVo, user);

        try {
            userService.updateById(user);
        } catch (DuplicateKeyException e) {
            throw new GuliException(20001, "该用户名已存在");
        }

        return R.ok().message("修改用户成功");
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        userService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public R batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return R.ok();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public R toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return R.ok().data(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public R doAssign(@RequestParam String userId, @RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId, roleId);
        return R.ok();
    }
}

