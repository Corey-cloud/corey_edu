package com.atguigu.eduservice.service;

import com.atguigu.eduservice.model.entity.QaAnswer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * Created by corey on 2022/3/31
 **/

public interface QaAnswerService extends IService<QaAnswer> {

    Map<String, Object> getAnswerAnd2Answer(Integer page, Integer limit, String questionId);

    /**
     * 回帖
     * @param answer
     */
    void reply(QaAnswer answer);
}
