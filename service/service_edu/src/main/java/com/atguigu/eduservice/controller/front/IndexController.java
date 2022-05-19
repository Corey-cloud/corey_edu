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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        // 分别查询最近七天发布的课程
        QueryWrapper<EduCourse> qw = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        List<EduCourse> list = courseService.list(qw);
        List<List<EduCourse>> res = new ArrayList<>();
        ArrayList<EduCourse> eduCourses1 = new ArrayList<>();
        ArrayList<EduCourse> eduCourses2 = new ArrayList<>();
        ArrayList<EduCourse> eduCourses3 = new ArrayList<>();
        ArrayList<EduCourse> eduCourses4 = new ArrayList<>();
        ArrayList<EduCourse> eduCourses5 = new ArrayList<>();
        ArrayList<EduCourse> eduCourses6 = new ArrayList<>();
        ArrayList<EduCourse> eduCourses7 = new ArrayList<>();
        res.add(0, eduCourses1);
        res.add(1, eduCourses2);
        res.add(2, eduCourses3);
        res.add(3, eduCourses4);
        res.add(4, eduCourses5);
        res.add(5, eduCourses6);
        res.add(6, eduCourses7);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        for (EduCourse course : list) {
            long t = Long.parseLong(formatter.format(new Date())) - Long.parseLong(formatter.format(course.getGmtCreate()));
            switch ((int) t) {
                case 0:
                    eduCourses1.add(course);
                    break;
                case 1:
                    eduCourses2.add(course);
                    break;
                case 2:
                    eduCourses3.add(course);
                    break;
                case 3:
                    eduCourses4.add(course);
                    break;
                case 4:
                    eduCourses5.add(course);
                    break;
                case 5:
                    eduCourses6.add(course);
                    break;
                case 6:
                    eduCourses7.add(course);
                    break;
            }
        }

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
        return R.ok().data("courseList",courseList).data("teacherList",teacherList).data("rankingList",rankingList).data("updateItems", res);
    }
}
