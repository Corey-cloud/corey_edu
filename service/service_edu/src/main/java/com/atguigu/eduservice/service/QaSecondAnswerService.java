package com.atguigu.eduservice.service;

import com.atguigu.eduservice.model.entity.QaSecondAnswer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Created by corey on 2022/3/31
 **/

public interface QaSecondAnswerService extends IService<QaSecondAnswer> {

    List<QaSecondAnswer> get2AnswerList(String answerId);

    /**
     * 获取评论数
     * @param questionId
     * @return
     */
    int queryCommentCount(Integer questionId);
}
