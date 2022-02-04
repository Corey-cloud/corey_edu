package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.model.entity.EduCourse;
import com.atguigu.eduservice.model.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by corey on 2021/7/30
 **/

@Api(description = "前台讲师信息")
@RestController
//@CrossOrigin
@RequestMapping(value = "/edu/teachers")
public class TeacherController {

    @Autowired
    EduTeacherService teacherService;

    @Autowired
    EduCourseService courseService;

    @ApiOperation(value = "获取讲师分页列表")
    @GetMapping(value = "{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){
        Page<EduTeacher> pageParam = new Page<EduTeacher>(page, limit);
        Map<String, Object> map = teacherService.pageListWeb(pageParam);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("/{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        //查询讲师信息
        EduTeacher teacher = teacherService.getById(id);
        //根据讲师id查询这个讲师的课程列表
        List<EduCourse> courseList = courseService.selectByTeacherId(id);
        return R.ok().data("teacher", teacher).data("courseList", courseList);
    }

}
