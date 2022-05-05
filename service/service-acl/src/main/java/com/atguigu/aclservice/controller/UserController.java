package com.atguigu.aclservice.controller;


import com.atguigu.aclservice.model.dto.UserDto;
import com.atguigu.aclservice.model.entity.User;
import com.atguigu.aclservice.model.vo.UserVo;
import com.atguigu.aclservice.service.RoleService;
import com.atguigu.aclservice.service.UserService;
import com.atguigu.commonutils.MD5;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.RSAUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author corey
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取用户分页列表")
    @GetMapping
    public R list(
            @ApiParam(name = "page", value = "当前页", required = true)
            @RequestParam Integer page,

            @ApiParam(name = "limit", value = "页大小", required = true)
            @RequestParam Integer limit,

            @ApiParam(name = "userQuery", value = "查询对象", required = false)
            UserVo userQueryVo) {

        // 分页构造器
        Page<User> pageParam = new Page<>(page, limit);

        // sql条件构造器
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        // 当查询条件不为空
        if (!StringUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username", userQueryVo.getUsername());
        }
        if (!StringUtils.isEmpty(userQueryVo.getNickName())) {
            wrapper.like("nick_name", userQueryVo.getNickName());
        }

        // 分页查询用户信息
        IPage<User> pageModel = userService.page(pageParam, wrapper);
        List<User> userRecords = pageModel.getRecords();

        // 隐藏表结构，转成VO
        List<UserVo> userInfoList = new ArrayList<>();
        for (User user : userRecords) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            userInfoList.add(userVo);
        }

        return R.ok().data("userInfoList", userInfoList).data("total", pageModel.getTotal());
    }

    @ApiOperation(value = "根据id获取")
    @GetMapping("/{id}")
    public R getById(@PathVariable String id) {
        User user = userService.getById(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return R.ok().data("userInfo", userVo);
    }

    @ApiOperation(value = "新增")
    @PostMapping
    public R save(@RequestBody UserDto userDto) {

        // 信息解密
        userDto.setUsername(RSAUtils.decryptBase64(userDto.getUsername()));
        userDto.setPass(RSAUtils.decryptBase64(userDto.getPass()));
        userDto.setCheckPass(RSAUtils.decryptBase64(userDto.getCheckPass()));

        // 后端校验
        String regex1 = "[A-Za-z0-9]{2,10}";
        if (!userDto.getUsername().matches(regex1)) {
            return R.error().message("用户名不得小于2个或大于10个字符");
        }
        int length = userDto.getNickName().length();
        if (length < 2 || length > 8) {
            return R.error().message("昵称不得小于2个或大于8个字符");
        }
        if (!userDto.getPass().matches("\\w{6,16}")) {
            return R.error().message("密码不能小于6个或大于16个字符");
        }
        if (!userDto.getPass().equals(userDto.getCheckPass())) {
            return R.error().message("两次输入密码不一致");
        }

        // 属性值拷贝
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        // 密码进行MD5加密
        user.setPassword(MD5.encrypt(userDto.getPass()));

        // 判断用户名是否存在
        User one = userService.selectByUsername(user.getUsername());
        if (one != null) {
            return R.error().message("该用户名已存在");
        }

        boolean flag = userService.save(user);
        if (!flag) {
            return R.error().message("添加失败");
        }
        return R.ok().message("添加成功");
    }

    @ApiOperation(value = "修改")
    @PutMapping
    public R updateById(@RequestBody UserDto userDto) {

        // 将从前端获取到的用户加密信息进行解密
        userDto.setUsername(RSAUtils.decryptBase64(userDto.getUsername()));

        // 后端校验
        String regex1 = "[A-Za-z0-9]{2,10}";
        if (!userDto.getUsername().matches(regex1)) {
            return R.error().message("用户名不得小于2个或大于10个字符");
        }
        int length = userDto.getNickName().length();
        if (length < 2 || length > 8) {
            return R.error().message("昵称不得小于2个或大于8个字符");
        }

        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        // 判断用户名是否存在
        User user1 = userService.getById(user.getId());
        User user2 = userService.selectByUsername(user.getUsername());
        if (user2 != null && !user1.getUsername().equals(user2.getUsername())) {
            return R.error().message("该用户名已存在");
        }

        boolean flag = userService.updateById(user);
        if (!flag) {
            return R.error().message("修改失败");
        }

        return R.ok().message("修改成功");
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable String id) {
        boolean flag = userService.removeById(id);
        if (!flag) {
            return R.error().message("删除失败");
        }
        return R.ok().message("删除成功");
    }

    @ApiOperation(value = "根据id列表批量删除")
    @DeleteMapping("/batchRemove")
    public R batchRemove(@RequestBody List<String> idList) {
        boolean flag = userService.removeByIds(idList);
        if (!flag) {
            return R.error().message("批量删除失败");
        }
        return R.ok().message("批量删除成功");
    }

    @ApiOperation(value = "根据用户id获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public R toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return R.ok().data(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public R doAssign(@RequestParam String userId, @RequestParam String[] roleId) {
        boolean flag = roleService.saveUserRoleRealtionShip(userId, roleId);
        if (!flag) {
            return R.error().message("保存失败");
        }
        return R.ok();
    }
}

