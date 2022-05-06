package com.atguigu.eduservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.eduservice.mapper.QaAnswerMapper;
import com.atguigu.eduservice.model.entity.QaAnswer;
import com.atguigu.eduservice.model.entity.QaQuestion;
import com.atguigu.eduservice.model.entity.QaSecondAnswer;
import com.atguigu.eduservice.service.QaAnswerService;
import com.atguigu.eduservice.service.QaQuestionService;
import com.atguigu.eduservice.service.QaSecondAnswerService;
import com.atguigu.eduservice.utils.HttpURLUtil;
import com.atguigu.eduservice.utils.IP;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    public Map<String, Object> getAnswerAnd2Answer(Integer page, Integer limit, String questionId) {
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
    public void reply(QaAnswer answer, HttpServletRequest request) {
        // 获取请求主机IP地址
        String ip = IP.getIpAddress(request);
        System.out.println("ip:" + ip);

        // IP归属地查询
        String url = "http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=";
        url += ip;
        String data = HttpURLUtil.doGet(url);

        Map map = JSON.parseObject(data);

        String province = (String) map.get("pro");
        String city = (String) map.get("city");

        answer.setComeFrom(province+city);
        baseMapper.insert(answer);
        QaQuestion question = new QaQuestion();
        question.setId(answer.getQuestionId());
        QaQuestion qaQuestion = questionService.getById(answer.getQuestionId());
        question.setQaComments(qaQuestion.getQaComments()+1);
        System.out.println("-----打印评论数------");
        System.out.println(qaQuestion.getQaComments()+1);
        questionService.updateById(question);
    }
}
