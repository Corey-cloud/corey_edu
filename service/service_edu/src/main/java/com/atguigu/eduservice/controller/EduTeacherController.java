package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-20
 */

@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
//@CrossOrigin    //解决跨域
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //1 查询讲师表所有数据
    //rest风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping
    public R list() {
        return R.ok().data("items", eduTeacherService.list(null));
    }

    //2 逻辑删除讲师的方法
    @ApiOperation(value = "根据ID逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        return flag ? R.ok() : R.error();
    }

    //3 分页查询讲师的方法
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        //创建Page对象
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageParam对象里面
        eduTeacherService.page(pageParam, null);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    //4 条件查询带分页的方法
    @ApiOperation(value = "带条件查询的分页讲师列表")
    @PostMapping("pageTeacherCondition/{page}/{limit}")
    public R pageTeacherCondition(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
            @RequestBody(required = false)
            TeacherQuery teacherQuery) {
        //创建Page对象
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        eduTeacherService.pageQuery(pageParam, teacherQuery);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    //添加讲师的方法
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(
            @ApiParam(name = "eduTeacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        return save ? R.ok() : R.error();
    }

    //根据ID查询讲师的方法
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("item", eduTeacher);
    }

    //根据ID修改讲师的方法
    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "eduTeacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher eduTeacher) {
        eduTeacher.setId(id);
        boolean update = eduTeacherService.updateById(eduTeacher);
        return update ? R.ok() : R.error();
    }
}

