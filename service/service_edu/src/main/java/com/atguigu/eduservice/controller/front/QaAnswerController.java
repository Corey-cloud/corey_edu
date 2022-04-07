package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.model.entity.QaAnswer;
import com.atguigu.eduservice.model.entity.QaQuestion;
import com.atguigu.eduservice.service.QaAnswerService;
import com.atguigu.eduservice.service.QaQuestionService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by corey on 2022/3/31
 **/
@RestController
@RequestMapping("/edu/qa-answer")
public class QaAnswerController {

    @Autowired
    private QaAnswerService answerService;

    @Autowired
    private QaQuestionService questionService;

    @ApiOperation(value = "回帖")
    @PostMapping("/reply")
    public R reply(@RequestBody(required = false) QaAnswer answer) {
        try {
            if (!StringUtils.isEmpty((CharSequence) answer)) {
                answerService.reply(answer);
                QaQuestion question = questionService.getById(answer.getQuestionId());
                question.setQaComments(question.getQaComments()+1);
                return R.ok();
            }
            return R.error();
        }catch (Exception e){
            return R.error();
        }
    }

    @ApiOperation(value = "回帖子列表")
    @PostMapping("/getAnswerList/{page}/{limit}")
    public R getQuestionList(@PathVariable("page") Integer page,
                             @PathVariable("limit") Integer limit,
                             @RequestParam Integer questionId) {
        Map<String, Object> map = answerService.getAnswerAnd2Answer(page, limit, questionId);
        return R.ok().data(map);
    }
}
