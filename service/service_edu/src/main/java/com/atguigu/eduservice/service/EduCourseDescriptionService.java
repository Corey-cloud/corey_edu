package com.atguigu.eduservice.service;

import com.atguigu.eduservice.model.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    boolean removeByCourseId(String courseId);
}
