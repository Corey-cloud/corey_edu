package com.atguigu.aclservice.controller;


import com.atguigu.aclservice.model.entity.Permission;
import com.atguigu.aclservice.model.entity.Role;
import com.atguigu.aclservice.model.vo.RoleVo;
import com.atguigu.aclservice.service.PermissionService;
import com.atguigu.aclservice.service.RoleService;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl")
//@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("/roles")
    public R index(
            @ApiParam(name = "page", value = "当前页", required = true)
            @RequestParam Integer page,

            @ApiParam(name = "limit", value = "页大小", required = true)
            @RequestParam Integer limit,

            @ApiParam(name = "roleQueryVo", value = "角色查询对象", required = false)
            RoleVo roleQueryVo) {
        System.out.println(roleQueryVo);
        // 分页构造器
        Page<Role> pageParam = new Page<>(page, limit);

        // sql条件构造器
        QueryWrapper<Role> wrapper = new QueryWrapper<>();

        // 当查询条件不为空
        if(!StringUtils.isEmpty(roleQueryVo.getRoleName())) {
            wrapper.like("role_name",roleQueryVo.getRoleName());
        }
        if(!StringUtils.isEmpty(roleQueryVo.getRoleCode())) {
            wrapper.like("role_code",roleQueryVo.getRoleCode());
        }

        // 分页查询
        roleService.page(pageParam,wrapper);
        return R.ok().data("rolesInfoList", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "根据id获取")
    @GetMapping("/roles/{id}")
    public R get(@PathVariable String id) {
        Role role = roleService.getById(id);
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(role, roleVo);
        return R.ok().data("roleInfo", roleVo);
    }

    @ApiOperation(value = "新增")
    @PostMapping("/roles")
    public R save(@RequestBody Role role) {

        // 数据规则校验
        String regex = "\\w{6,6}";
        int length = role.getRoleName().length();
        if (length < 2 || length > 8) {
            return R.error().message("很遗憾！服务器校验不通过！" +
                    "（角色名称不得小于2个字符或大于8个字符）");
        }
        if (!role.getRoleCode().matches(regex)) {
            return R.error().message("很遗憾！服务器检验不通过！" +
                    "（角色编码必须为6个字符）");
        }

        Role role_name = roleService.getOne(new QueryWrapper<Role>().eq("role_name", role.getRoleName()));
        if (role_name != null) {
            return R.error().message("该角色名称已存在");
        }

        boolean flag = roleService.save(role);
        if (!flag) {
            return R.error().message("添加失败");
        }
        return R.ok().message("添加成功");
    }

    @ApiOperation(value = "修改")
    @PutMapping("/roles")
    public R updateById(@RequestBody Role role) {

        // 规则校验
        String regex = "\\w{6,6}";
        int length = role.getRoleName().length();
        if (length < 2 || length > 8) {
            return R.error().message("很遗憾！服务器校验不通过！" +
                    "（角色名称不得小于2个字符或大于8个字符）");
        }
        if (!role.getRoleCode().matches(regex)) {
            return R.error().message("很遗憾！服务器检验不通过！" +
                    "（角色编码必须为6个字符）");
        }

        // 判断是否存在相同的角色名和角色编码
        Role role1 = roleService.getById(role.getId());
        Role role2 = roleService.getOne(new QueryWrapper<Role>().eq("role_name", role.getRoleName()));
        Role role3 = roleService.getOne(new QueryWrapper<Role>().eq("role_code", role.getRoleCode()));
        if (role2 != null && !role2.getRoleName().equals(role1.getRoleName())) {
            return R.error().message("该角色名称已存在");
        }
        if (role3 != null && !role3.getRoleCode().equals(role1.getRoleCode())) {
            return R.error().message("该角色编码已存在");
        }

        boolean flag = roleService.updateById(role);
        if (!flag) {
            return R.error().message("修改失败");
        }

        return R.ok().message("修改成功");
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/roles/{id}")
    public R remove(@PathVariable String id) {
        boolean flag = roleService.removeById(id);
        if (!flag) {
            return R.error().message("删除失败");
        }
        return R.ok().message("删除成功");
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("/roles/batchRemove")
    public R batchRemove(@RequestBody List<String> idList) {
        boolean flag = roleService.removeByIds(idList);
        if (!flag) {
            return R.error().message("批量删除失败");
        }
        return R.ok().message("批量删除成功");
    }

    @ApiOperation(value = "根据角色id获取菜单")
    @GetMapping("roles/getAssign/{roleId}")
    public R toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return R.ok().data("permissionList", list);
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("roles/doAssign")
    public R doAssign(String roleId,String[] permissionId) {
        boolean flag = permissionService.saveRolePermissionRealtionShip(roleId, permissionId);
        if (!flag) {
            return R.error().message("保存失败");
        }
        return R.ok();
    }
}

