package com.atguigu.eduservice.controller.front;

import com.alibaba.fastjson.JSON;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.model.entity.QaAnswer;
import com.atguigu.eduservice.model.entity.QaQuestion;
import com.atguigu.eduservice.model.entity.QaSecondAnswer;
import com.atguigu.eduservice.service.QaQuestionService;
import com.atguigu.eduservice.service.QaSecondAnswerService;
import com.atguigu.eduservice.utils.HttpURLUtil;
import com.atguigu.eduservice.utils.IP;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by corey on 2022/3/31
 **/
@RestController
@RequestMapping("/edu/qa-second-answer")
public class QaSecondAnswerController {

    @Autowired
    private QaSecondAnswerService answerService;

    @Autowired
    private QaQuestionService questionService;

    @ApiOperation(value = "二级回帖")
    @PostMapping("/2Reply")
    public R reply(@RequestBody QaSecondAnswer answer, HttpServletRequest request) {
        try {
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
            answerService.save(answer);
            QaQuestion qaQuestion = questionService.getById(answer.getQuestionId());
            QaQuestion question = new QaQuestion();
            question.setQaComments(qaQuestion.getQaComments()+1);
            question.setId(answer.getQuestionId());
            questionService.updateById(question);
            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
    }

    @PutMapping("/zan/{id}")
    public R zan(@PathVariable String id) {
        QaSecondAnswer secondAnswer = answerService.getById(id);
        if (secondAnswer == null) {
            System.out.println("111111111");
            return R.error().message("该评论不存在");
        }
        QaSecondAnswer secondAnswer1 = new QaSecondAnswer();
        secondAnswer1.setId(id);
        secondAnswer1.setZanCount(secondAnswer.getZanCount()+1);
        answerService.updateById(secondAnswer1);
        return R.ok();
    }
}
