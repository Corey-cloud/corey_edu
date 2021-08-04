package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    @Override
    public boolean removeByCourseId(String courseId) {
        QueryWrapper<EduCourseDescription> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", courseId);
        Integer count = baseMapper.delete(queryWrapper);
        return null != count && count > 0;
    }
}
