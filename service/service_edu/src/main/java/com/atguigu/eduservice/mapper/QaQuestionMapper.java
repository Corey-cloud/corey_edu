package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.model.entity.QaQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * Created by corey on 2022/3/31
 **/

public interface QaQuestionMapper extends BaseMapper<QaQuestion> {

    /**
     * 获取问答回复信息
     * @param id
     * @return
     */
    QaQuestion selectQuestion(String id);
}
