package com.atguigu.eduservice.controller.admin;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.model.entity.EduTeacher;
import com.atguigu.eduservice.model.vo.EduTeacherVo;
import com.atguigu.eduservice.model.vo.TeacherQueryVo;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/edu/admin/teachers")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    // 获取讲师分页列表
    @ApiOperation(value = "获取讲师分页列表")
    @GetMapping
    public R list(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @RequestParam Integer page,
            
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @RequestParam Integer limit,
            
            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
            TeacherQueryVo teacherQueryVo) {
        
        // 创建Page对象
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        
        // sql条件构造器
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //获取查询条件
        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String begin = teacherQueryVo.getBegin();
        String end = teacherQueryVo.getEnd();

        // 当查询条件不为空
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (null != level && !"".equals(Integer.toString(level))) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        // 按创建时间降序获取讲师分页列表
        eduTeacherService.page(pageParam, wrapper.orderByDesc("gmt_create"));

        return R.ok().data("teacherList", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    //添加讲师
    @ApiOperation(value = "添加讲师")
    @PostMapping
    public R addTeacher(
            @ApiParam(name = "eduTeacherVo", value = "讲师对象", required = true)
            @RequestBody EduTeacherVo eduTeacherVo) {
        EduTeacher eduTeacher = new EduTeacher();
        BeanUtils.copyProperties(eduTeacherVo, eduTeacher);
        boolean flag = eduTeacherService.save(eduTeacher);
        return flag ? R.ok() : R.error();
    }

    // 逻辑删除讲师
    @ApiOperation(value = "根据ID逻辑删除讲师")
    @DeleteMapping("/{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        return flag ? R.ok() : R.error();
    }

    //根据ID修改讲师
    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping
    public R updateById(
            @ApiParam(name = "eduTeacherVo", value = "讲师对象", required = true)
            @RequestBody EduTeacherVo eduTeacherVo) {
        EduTeacher eduTeacher = new EduTeacher();
        BeanUtils.copyProperties(eduTeacherVo, eduTeacher);
        boolean flag = eduTeacherService.updateById(eduTeacher);
        return flag ? R.ok() : R.error();
    }

    //根据ID查询讲师
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        EduTeacherVo eduTeacherVo = new EduTeacherVo();
        BeanUtils.copyProperties(eduTeacher, eduTeacherVo);
        return R.ok().data("item", eduTeacherVo);
    }
}

