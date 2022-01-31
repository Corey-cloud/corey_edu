package com.atguigu.eduservice.service;

import com.atguigu.eduservice.model.entity.EduVideo;
import com.atguigu.eduservice.model.vo.VideoInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean getCountByChapterId(String chapterId);

    void saveVideoInfo(VideoInfoVo videoInfoForm);

    VideoInfoVo getVideoInfoFormById(String id);

    void updateVideoInfoById(VideoInfoVo videoInfoForm);

    boolean removeVideoById(String id);

    boolean removeByCourseId(String courseId);
}
