package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.mapper.QaAnswerMapper;
import com.atguigu.eduservice.model.entity.QaAnswer;
import com.atguigu.eduservice.model.entity.QaQuestion;
import com.atguigu.eduservice.model.entity.QaSecondAnswer;
import com.atguigu.eduservice.service.QaAnswerService;
import com.atguigu.eduservice.service.QaQuestionService;
import com.atguigu.eduservice.service.QaSecondAnswerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by corey on 2022/3/31
 **/

@Service
public class QaAnswerServiceImpl extends ServiceImpl<QaAnswerMapper, QaAnswer> implements QaAnswerService {

    @Autowired
    private QaSecondAnswerService secondAnswerService;

    @Autowired
    private QaQuestionService questionService;

    @Override
    public Map<String, Object> getAnswerAnd2Answer(Integer page, Integer limit, Integer questionId) {
        Page<QaAnswer> pageParam = new Page<>(page, limit);
        QueryWrapper<QaAnswer> qw = new QueryWrapper<>();
        qw.eq("question_id",questionId);
        qw.orderByDesc("gmt_create");
        baseMapper.selectPage(pageParam, qw);
        for (QaAnswer record : pageParam.getRecords()) {
            List<QaSecondAnswer> answer2List = secondAnswerService.get2AnswerList(record.getId());
            record.setAnswer2List(answer2List);
        }

        List<QaAnswer> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long size = pageParam.getSize();
        long pages = pageParam.getPages();
        long total = pageParam.getTotal();
        boolean hasPrevious = pageParam.hasPrevious();
        boolean hasNext = pageParam.hasNext();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("size", size);
        map.put("pages", pages);
        map.put("total", total);
        map.put("hasPrevious", hasPrevious);
        map.put("hasNext", hasNext);
        return map;
    }

    @Override
    public void reply(QaAnswer answer) {
        QaQuestion qaQuestion = queryQtAnswer(answer.getQuestionId());
        int count1 = queryCommentCount(answer.getQuestionId());
        int count2 = secondAnswerService.queryCommentCount(answer.getQuestionId());
        qaQuestion.setQaComments(count1+count2);
        questionService.updateById(qaQuestion);
        baseMapper.insert(answer);
    }

    /**
     * 获取Question信息
     * @param id
     * @return
     */
    public QaQuestion queryQtAnswer(Integer id) {
        return questionService.getById(id);
    }

    /**
     * 获取评论个数
     * @param id
     * @return
     */
    public int queryCommentCount(Integer id) {
        return baseMapper.selectCount(new QueryWrapper<QaAnswer>().eq("question_id",id));
    }

    /**
     * 根据问题id获取回答列表
     * @param questionId
     * @param pageParam
     * @return
     */
    private Page selectAnswerByQuestionId(Integer questionId, Page pageParam) {
        QueryWrapper<QaAnswer> qw = new QueryWrapper<>();
        qw.eq("question_id",questionId);
        qw.orderByDesc("gmt_create");
        return (Page) baseMapper.selectPage(pageParam, qw);
    }
}
