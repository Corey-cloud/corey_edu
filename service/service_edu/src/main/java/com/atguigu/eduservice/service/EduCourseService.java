package com.atguigu.eduservice.service;

import com.atguigu.eduservice.model.entity.EduCourse;
import com.atguigu.eduservice.model.frontvo.CourseQueryVo;
import com.atguigu.eduservice.model.frontvo.CourseWebVo;
import com.atguigu.eduservice.model.vo.CourseInfoVo;
import com.atguigu.eduservice.model.vo.CoursePublishVo;
import com.atguigu.eduservice.model.vo.CourseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 保存课程和课程详情信息
     * @param courseInfoVo
     * @return 新生成的课程id
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoFormById(String id);

    void updateCourseInfoById(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublishVoById(String id);

    boolean publishCourseById(String id);

    void pageList(Page<EduCourse> pageParam, CourseQuery courseQuery);

    boolean removeCourseById(String id);

    List<EduCourse> selectByTeacherId(String teacherId);

    Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryVo courseQuery);

    /**
     * 获取课程信息
     * @param id
     * @return
     */
    CourseWebVo selectInfoWebById(String id);

    /**
     * 更新课程浏览数
     * @param id
     */
    void updatePageViewCount(String id);

    com.atguigu.commonutils.vo.CourseInfoVo getCourseInfo(String courseId);
}
