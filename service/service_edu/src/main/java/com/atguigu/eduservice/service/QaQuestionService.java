package com.atguigu.eduservice.service;

import com.atguigu.eduservice.model.entity.QaQuestion;
import com.atguigu.eduservice.model.frontvo.QaUpdateVo;
import com.atguigu.eduservice.model.frontvo.QuestionQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * Created by corey on 2022/3/31
 **/

public interface QaQuestionService extends IService<QaQuestion> {

    /**
     * 获取问答分页集合
     * @param page
     * @param limit
     * @return
     */
    Map<String, Object> getQuestionList(Integer page, Integer limit, QuestionQueryVo questionQueryVo);


    /**
     * 获取后台问答帖子列表
     * @param page
     * @param limit
     * @param questionQueryVo
     * @return
     */
    Map<String, Object> getQListBack(Integer page, Integer limit, QuestionQueryVo questionQueryVo);

    /**
     * 修改问答的发布状态
     * @param qaUpdateVo
     */
    void updateEnable(QaUpdateVo qaUpdateVo);

    /**
     * 获取问答回复信息
     * @param id
     */
    QaQuestion queryQaAnswer(String id);
}
