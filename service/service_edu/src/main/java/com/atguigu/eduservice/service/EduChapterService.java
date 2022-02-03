package com.atguigu.eduservice.service;

import com.atguigu.eduservice.model.entity.EduChapter;
import com.atguigu.eduservice.model.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface EduChapterService extends IService<EduChapter> {

    // 根据课程id获取嵌套章节列表
    List<ChapterVo> nestedList(String courseId);

    boolean removeChapterById(String id);

    boolean removeByCourseId(String courseId);
}
