package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.mapper.QaQuestionMapper;
import com.atguigu.eduservice.model.entity.QaQuestion;
import com.atguigu.eduservice.model.frontvo.QaUpdateVo;
import com.atguigu.eduservice.model.frontvo.QuestionQueryVo;
import com.atguigu.eduservice.service.QaQuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by corey on 2022/3/31
 **/

@Service
public class QaQuestionServiceImpl extends ServiceImpl<QaQuestionMapper, QaQuestion> implements QaQuestionService {

    @Override
    public Map<String, Object> getQuestionList(Integer page, Integer limit, QuestionQueryVo questionQueryVo) {
        Page<QaQuestion> pageParam = new Page<>(page, limit);
        QueryWrapper<QaQuestion> qw = new QueryWrapper<>();
        if (null != questionQueryVo) {
            System.out.println(questionQueryVo);
            switch (questionQueryVo.getQt()) {
                case "1":
                    qw.orderByDesc("gmt_modified");
                case "2":
                    qw.orderByDesc("qa_view");
                    break;
                case "3":
                    qw.eq("qa_comments", 0);
                default:
                    qw.orderByDesc("gmt_create");
                    break;
            }
        } else {
            qw.orderByDesc("gmt_create");
        }
        qw.eq("status",1);
        qw.eq("enable",1);
        baseMapper.selectPage(pageParam, qw);

        List<QaQuestion> records = pageParam.getRecords();
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
    public Map<String, Object> getQListBack(Integer page, Integer limit, QuestionQueryVo questionQueryVo) {
        Page<QaQuestion> pageParam = new Page<>(page, limit);
        QueryWrapper<QaQuestion> qw = new QueryWrapper<>();
        if (null != questionQueryVo) {
            if (null != questionQueryVo.getStatus()) {
                qw.eq("status", questionQueryVo.getStatus());
            }
            if (null != questionQueryVo.getEnable()) {
                qw.eq("enable", questionQueryVo.getEnable());
            }
            if (!StringUtils.isEmpty(questionQueryVo.getQt())) {
                switch (questionQueryVo.getQt()) {
                    case "1":
                        qw.orderByDesc("gmt_modified");
                    case "2":
                        qw.orderByDesc("qa_view");
                    case "3":
                        qw.eq("qa_comments", 0);
                    default:
                        qw.orderByDesc("gmt_create");
                        break;
                }
            }
        }
        baseMapper.selectPage(pageParam, qw);

        List<QaQuestion> records = pageParam.getRecords();
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
    public void updateEnable(QaUpdateVo qaUpdateVo) {
        QaQuestion qaQuestion = selectQtById(qaUpdateVo.getId());
        qaQuestion.setStatus(1);
        qaQuestion.setEnable(qaUpdateVo.getEnable());
        qaQuestion.setRemark(qaUpdateVo.getRemark());
        baseMapper.updateById(qaQuestion);
    }

    @Override
    public QaQuestion queryQaAnswer(String id) {
        return baseMapper.selectQuestion(id);
    }

    //根据id 查询问答的帖子
    public QaQuestion selectQtById(String id){
        return baseMapper.selectById(id);
    }

}
