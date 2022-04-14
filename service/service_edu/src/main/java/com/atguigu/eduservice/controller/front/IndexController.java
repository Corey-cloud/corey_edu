package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.model.entity.EduCourse;
import com.atguigu.eduservice.model.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by corey on 2021/7/27
 **/
@RestController
@RequestMapping("/edu/index")
//@CrossOrigin
public class IndexController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //查询前8条热门课程，查询前4条名师
    @GetMapping
    public R index() {

        //查询前12条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 12");
        List<EduCourse> courseList = courseService.list(wrapper);

        //首页排行榜
        QueryWrapper<EduCourse> rankWrapper = new QueryWrapper<>();
        rankWrapper.orderByDesc("view_count").last("limit 10");
        List<EduCourse> rankingList = courseService.list(rankWrapper);
        rankingList.get(0).setShowWord(false);

        //查询前4条名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);
        return R.ok().data("courseList",courseList).data("teacherList",teacherList).data("rankingList",rankingList);
    }
}
