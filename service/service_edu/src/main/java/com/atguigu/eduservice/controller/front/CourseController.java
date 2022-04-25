package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.vo.CourseInfoVo;
import com.atguigu.eduservice.client.OrderClient;
import com.atguigu.eduservice.model.entity.EduCollect;
import com.atguigu.eduservice.model.entity.EduCourse;
import com.atguigu.eduservice.model.frontvo.CourseQueryVo;
import com.atguigu.eduservice.model.frontvo.CourseWebVo;
import com.atguigu.eduservice.model.vo.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCollectService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by corey on 2021/7/30
 **/

@Api(description = "前台课程信息")
@RestController
//@CrossOrigin
@RequestMapping("/edu/courses")
public class CourseController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private EduCollectService collectService;

    @ApiOperation(value = "分页课程列表")
    @PostMapping(value = "/{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseQueryVo courseQuery){
        Page<EduCourse> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.pageListWeb(pageParam, courseQuery);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "/{courseId}")
    public R getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId,
            HttpServletRequest request){

        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectInfoWebById(courseId);

        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);

        boolean token = JwtUtils.checkToken(request);
        boolean buyCourse = false;
        boolean isCollect = false;
        if (token) {
            //远程调用，判断课程是否被购买
            buyCourse = orderClient.isBuyCourse(JwtUtils.getMemberIdByJwtToken(request), courseId);

            // 判断用户是否收藏该课程
            EduCollect one = collectService.getOne(new QueryWrapper<EduCollect>().eq("member_id", JwtUtils.getMemberIdByJwtToken(request)).eq("course_id", courseId));
            if (one != null) {
                isCollect = true;
            }
        }

        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList).data("isBuyCourse", buyCourse).data("isCollect", isCollect);
    }

    //根据课程id查询课程信息
    @GetMapping("/getDto/{courseId}")
    public CourseInfoVo getCourseInfoDto(@PathVariable String courseId) {

        CourseInfoVo courseInfo = courseService.getCourseInfo(courseId);
        return courseInfo;
    }

    @PutMapping("/updateBuyCount/{courseId}")
    boolean updateBuyCount(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        EduCourse course = courseService.getById(courseId);
        eduCourse.setId(courseId).setBuyCount(course.getBuyCount()+1);
        return courseService.updateById(eduCourse);
    }

    // 搜索课程
    @GetMapping("/searchCourse")
    public R searchCourse(@RequestParam String searchStr) {
        QueryWrapper<EduCourse> qw = new QueryWrapper<>();
        qw.like("title", searchStr);
        List<EduCourse> list = courseService.list(qw);
        return R.ok().data("courseList", list);
    }

}
