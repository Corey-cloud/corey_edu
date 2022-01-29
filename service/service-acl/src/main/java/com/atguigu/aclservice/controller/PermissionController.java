package com.atguigu.aclservice.controller;


import com.atguigu.aclservice.model.entity.Permission;
import com.atguigu.aclservice.service.PermissionService;
import com.atguigu.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/permissions")
    public R indexAllPermission() {
        List<Permission> permissionsList = permissionService.queryAllMenu();
        return R.ok().data("permissionsList", permissionsList);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("/permissions")
    public R save(@RequestBody Permission permission) {
        boolean flag = permissionService.save(permission);
        if (!flag) {
            return R.error().message("添加失败");
        }
        return R.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("/permissions")
    public R updateById(@RequestBody Permission permission) {
        boolean flag = permissionService.updateById(permission);
        if (!flag) {
            return R.error().message("修改失败");
        }
        return R.ok();
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("/permissions/{id}")
    public R remove(@PathVariable String id) {
        permissionService.removeChildById(id);
        return R.ok();
    }
}

