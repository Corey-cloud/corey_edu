package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.model.entity.QaQuestion;
import com.atguigu.eduservice.model.frontvo.QaUpdateVo;
import com.atguigu.eduservice.model.frontvo.QuestionQueryVo;
import com.atguigu.eduservice.service.QaQuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by corey on 2022/3/31
 **/
@RestController
@RequestMapping("/edu/qa-question")
public class QaQuestionController {

    @Autowired
    private QaQuestionService questionService;


    @ApiOperation(value = "获取问答回复列表-前台")
    @GetMapping("/getQaAnswerList/{id}")
    public R getQtAnswerList(@PathVariable("id") String id) {
        try {
            QaQuestion question = questionService.getById(id);
            QaQuestion q = new QaQuestion();
            q.setQaView(question.getQaView() + 1);
            q.setId(id);
            questionService.updateById(q);
            QaQuestion qaQuestion = questionService.queryQaAnswer(id);
            return R.ok().data("questionTree", qaQuestion);
        } catch (Exception e) {
            return R.error();
        }
    }

    @ApiOperation(value = "发布问题")
    @PostMapping("/publish")
    public R publish(
            @RequestBody QaQuestion question, @ApiIgnore HttpServletRequest request) {
        boolean token = JwtUtils.checkToken(request);
        if (!token) {
            return R.error().code(28000).message("未登录评论");
        }
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        boolean save = questionService.save(question);
        if (save) {
            return R.ok();
        } else {
            return R.error().message("发布失败");
        }
    }

    @ApiOperation(value = "帖子列表")
    @PostMapping("/getQuestionList/{page}/{limit}")
    public R getQuestionList(@PathVariable("page") Integer page,
                             @PathVariable("limit") Integer limit,
                             @RequestBody(required = false) QuestionQueryVo questionQueryVo) {
        Map<String, Object> map = questionService.getQuestionList(page, limit, questionQueryVo);
        return R.ok().data(map);
    }

    @ApiOperation(value = "帖子列表-后台")
    @PostMapping("/getQListBack/{page}/{limit}")
    public R getQListBack(@PathVariable("page") Integer page,
                          @PathVariable("limit") Integer limit,
                          @RequestBody(required = false) QuestionQueryVo questionQueryVo) {

        Map<String, Object> map = questionService.getQListBack(page, limit, questionQueryVo);
        return R.ok().data(map);
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("/batchRemove")
    public R batchRemove(@RequestBody List<String> idList) {
        try {
            questionService.removeByIds(idList);
            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
    }

    @ApiOperation(value = "根据id删除")
    @DeleteMapping("/{id}")
    public R remove(@PathVariable("id") String id) {
        try {
            questionService.removeById(id);
            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
    }

    @ApiOperation(value = "根据id获取帖子信息")
    @GetMapping("/{id}")
    public R get(@PathVariable("id") String id) {
        try {
            QaQuestion qaQuestion = questionService.getById(id);
            QaQuestion question = new QaQuestion();
            question.setId(id);
            question.setQaView(qaQuestion.getQaView()+1);
            questionService.updateById(question);
            return R.ok().data("question", questionService.getById(id));
        } catch (Exception e) {
            return R.error();
        }
    }

    @ApiOperation(value = "修改问答的发布状态")
    @PutMapping("/updateStatus")
    public R update(@RequestBody QaUpdateVo qaUpdateVo) {
        try {
            questionService.updateEnable(qaUpdateVo);
            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
    }

}
