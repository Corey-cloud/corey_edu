package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.model.entity.EduCourse;
import com.atguigu.eduservice.model.entity.EduCourseDescription;
import com.atguigu.eduservice.model.entity.EduTeacher;
import com.atguigu.eduservice.model.frontvo.CourseQueryVo;
import com.atguigu.eduservice.model.frontvo.CourseWebVo;
import com.atguigu.eduservice.model.vo.CourseInfoVo;
import com.atguigu.eduservice.model.vo.CoursePublishVo;
import com.atguigu.eduservice.model.vo.CourseQuery;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.*;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduTeacherService teacherService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        //保存课程基本信息
        EduCourse course = new EduCourse();
        course.setStatus(EduCourse.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfoVo, course);
        boolean resultCourseInfo = this.save(course);
        if(!resultCourseInfo){
            throw new GuliException(20001, "课程信息保存失败");
        }
        //保存课程详情信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(course.getId());
        boolean resultDescription = courseDescriptionService.save(courseDescription);
        if(!resultDescription){
            throw new GuliException(20001, "课程详情信息保存失败");
        }
        return course.getId();
    }

    @Override
    public CourseInfoVo getCourseInfoFormById(String id) {
        EduCourse course = this.getById(id);
        if(course == null){
            throw new GuliException(20001, "数据不存在");
        }
        CourseInfoVo courseInfoForm = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoForm);
        EduCourseDescription courseDescription = courseDescriptionService.getById(id);
        if(course != null){
            courseInfoForm.setDescription(courseDescription.getDescription());
        }
        return courseInfoForm;
    }

    @Override
    public void updateCourseInfoById(CourseInfoVo courseInfoVo) {
        //保存课程基本信息
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, course);
        boolean resultCourseInfo = this.updateById(course);
        if(!resultCourseInfo){
            throw new GuliException(20001, "课程信息保存失败");
        }
        //保存课程详情信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(course.getId());
        boolean resultDescription =
                courseDescriptionService.updateById(courseDescription);
        if(!resultDescription){
            throw new GuliException(20001, "课程详情信息保存失败");
        }
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.getCoursePublishVoById(id);
    }

    @Override
    public boolean publishCourseById(String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus(EduCourse.COURSE_NORMAL);
        Integer count = baseMapper.updateById(course);
        return null != count && count > 0;
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if (courseQuery == null) {
            baseMapper.selectPage(pageParam,queryWrapper);
            return;
        }
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public boolean removeCourseById(String id) {
        //根据id删除所有视频
        videoService.removeByCourseId(id);
        //根据id删除所有章节
        chapterService.removeByCourseId(id);
        //根据id删除课程简介
        courseDescriptionService.removeByCourseId(id);
        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }

    /**
     * 根据讲师id查询当前讲师的课程列表
     * @param teacherId
     * @return
     */
    @Override
    public List<EduCourse> selectByTeacherId(String teacherId) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<EduCourse>();
        queryWrapper.eq("teacher_id", teacherId);
        //按照最后更新时间倒序排列
        queryWrapper.orderByDesc("gmt_modified");
        List<EduCourse> courses = baseMapper.selectList(queryWrapper);
        return courses;
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryVo courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            queryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam, queryWrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo selectInfoWebById(String id) {
        this.updatePageViewCount(id);
        return baseMapper.selectInfoWebById(id);
    }

    @Override
    public void updatePageViewCount(String id) {
        EduCourse course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }

    @Override
    public com.atguigu.commonutils.vo.CourseInfoVo getCourseInfo(String courseId) {
        EduCourse course = this.getById(courseId);
        EduTeacher teacher = teacherService.getById(course.getTeacherId());
        com.atguigu.commonutils.vo.CourseInfoVo courseInfoVo = new com.atguigu.commonutils.vo.CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);
        courseInfoVo.setTeacherName(teacher.getName());
        return courseInfoVo;
    }

}
