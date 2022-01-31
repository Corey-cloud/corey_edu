package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.model.entity.EduCourse;
import com.atguigu.eduservice.model.frontvo.CourseWebVo;
import com.atguigu.eduservice.model.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePublishVoById(String id);

    CourseWebVo selectInfoWebById(String courseId);
}
