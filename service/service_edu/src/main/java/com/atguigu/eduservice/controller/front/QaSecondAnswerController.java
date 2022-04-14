package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.model.entity.QaSecondAnswer;
import com.atguigu.eduservice.service.QaSecondAnswerService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by corey on 2022/3/31
 **/
@RestController
@RequestMapping("/edu/qa-second-answer")
public class QaSecondAnswerController {

    @Autowired
    private QaSecondAnswerService answerService;

    @ApiOperation(value = "二级回帖")
    @PostMapping("/2Reply")
    public R reply(@RequestBody QaSecondAnswer answer) {
        try {
            answerService.save(answer);
            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
    }
}
