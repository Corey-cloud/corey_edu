package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.mapper.QaSecondAnswerMapper;
import com.atguigu.eduservice.model.entity.QaSecondAnswer;
import com.atguigu.eduservice.service.QaSecondAnswerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by corey on 2022/3/31
 **/

@Service
public class QaSecondAnswerServiceImpl extends ServiceImpl<QaSecondAnswerMapper, QaSecondAnswer> implements QaSecondAnswerService {

    @Override
    public List<QaSecondAnswer> get2AnswerList(String answerId) {
        QueryWrapper<QaSecondAnswer> qw = new QueryWrapper<>();
        qw.eq("answer_id",answerId);
        return baseMapper.selectList(qw);
    }

    @Override
    public int queryCommentCount(String questionId) {
        return baseMapper.selectCount(new QueryWrapper<QaSecondAnswer>().eq("question_id",questionId));
    }
}
