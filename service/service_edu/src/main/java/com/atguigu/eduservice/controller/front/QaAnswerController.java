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

    @ApiOperation(value = "回帖")
    @PostMapping("/reply")
    public R reply(@RequestBody QaAnswer answer) {
        try {
            answerService.reply(answer);
            return R.ok();
        }catch (Exception e){
            return R.error();
        }
    }

    @PutMapping("/zan/{id}")
    public R zan(@PathVariable String id) {
        QaAnswer answer = answerService.getById(id);
        if (answer == null) {
            return R.error().message("该评论不存在");
        }
        QaAnswer answer1 = new QaAnswer();
        answer1.setId(id);
        answer1.setZanCount(answer.getZanCount()+1);
        answerService.updateById(answer1);
        return R.ok();
    }
}
