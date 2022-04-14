package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.vo.UcenterMemberVo;
import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.eduservice.model.entity.EduArticle;
import com.atguigu.eduservice.model.entity.EduArticleComment;
import com.atguigu.eduservice.service.EduArticleCommentService;
import com.atguigu.eduservice.service.EduArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edu/article-comment")
public class ArticleCommentController {

    @Autowired
    private EduArticleCommentService articleCommentService;

    @Autowired
    private EduArticleService articleService;

    @Autowired
    private UcenterClient ucenterClient;

    //根据文章id分页获取评论列表
    @ApiOperation(value = "文章评论分页列表")
    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = true)
            @RequestParam String articleId) {
        Page<EduArticleComment> pageParam = new Page<>(page, limit);
        QueryWrapper<EduArticleComment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId).orderByDesc("gmt_create");
        articleCommentService.page(pageParam, wrapper);
        List<EduArticleComment> articleCommentList = pageParam.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", articleCommentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return R.ok().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("auth/save")
    public R save(@RequestBody EduArticleComment articleComment, HttpServletRequest request) {
        boolean token = JwtUtils.checkToken(request);
        if (!token) {
            return R.error().code(28000).message("未登录评论");
        }
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        articleComment.setMemberId(memberId);
        UcenterMemberVo ucenterInfo = ucenterClient.getInfo(memberId);
        if (ucenterInfo == null) {
            return R.error().message("无法获取用户信息");
        }
        articleComment.setNickname(ucenterInfo.getNickname());
        articleComment.setAvatar(ucenterInfo.getAvatar());
        articleCommentService.save(articleComment);

        // 获取该文章下的评论数
        String articleId = articleComment.getArticleId();
        EduArticle eduArticle = articleService.getById(articleId);

        // 文章评论数更新
        EduArticle article = new EduArticle();
        article.setId(articleId);
        article.setContentComment(eduArticle.getContentComment()+1);
        articleService.updateById(article);
        return R.ok();
    }


}
