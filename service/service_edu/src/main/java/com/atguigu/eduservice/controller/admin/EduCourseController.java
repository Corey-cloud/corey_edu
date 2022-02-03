package com.atguigu.eduservice.controller.admin;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.model.entity.EduCourse;
import com.atguigu.eduservice.model.vo.CourseInfoVo;
import com.atguigu.eduservice.model.vo.CoursePublishVo;
import com.atguigu.eduservice.model.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author corey
 * @since 2021-07-23
 */
@Api(description="课程管理")
//@CrossOrigin //跨域
@RestController
@RequestMapping("/edu/admin/courses")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "获取分页课程列表")
    @GetMapping
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @RequestParam Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @RequestParam Long limit,
            @ApiParam(name = "courseQuery", value = "课程查询对象", required = false)
                    CourseQuery courseQuery){
        // 构建Page对象
        Page<EduCourse> pageParam = new Page<>(page, limit);

        // 获取分页列表
        courseService.pageList(pageParam, courseQuery);

        return R.ok().data("courseInfoList", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "新增课程")
    @PostMapping
    public R addCourseInfo(
            @ApiParam(name = "CourseVo", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo) {
        String courseId = courseService.saveCourseInfo(courseInfoVo);
        if(!StringUtils.isEmpty(courseId)){
            return R.ok().data("courseId", courseId);
        }else{
            return R.error().message("保存失败");
        }
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        boolean flag = courseService.removeCourseById(id);
        return flag ? R.ok() : R.error();
    }

    // 根据id查询课程
    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        CourseInfoVo courseInfoForm = courseService.getCourseInfoFormById(id);
        return R.ok().data("item", courseInfoForm);
    }

    @ApiOperation(value = "更新课程")
    @PutMapping
    public R updateCourseInfoById(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoForm){
        courseService.updateCourseInfoById(courseInfoForm);
        return R.ok().data("courseId", courseInfoForm.getId());
    }

    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("course-publish-info/{id}")
    public R getCoursePublishVoById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        CoursePublishVo courseInfoForm = courseService.getCoursePublishVoById(id);
        return R.ok().data("item", courseInfoForm);
    }

    @ApiOperation(value = "根据id发布课程")
    @PutMapping("publish-course/{id}")
    public R publishCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        courseService.publishCourseById(id);
        return R.ok();
    }
}

