package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.model.entity.EduArticle;
import com.atguigu.eduservice.model.entity.EduCourse;
import com.atguigu.eduservice.model.frontvo.ArticleQueryVo;
import com.atguigu.eduservice.service.EduArticleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by corey on 2022/3/31
 **/
@RestController
@RequestMapping("/edu/articles")
public class ArticleController {

    @Autowired
    private EduArticleService articleService;

    @ApiOperation(value = "分页文章列表")
    @PostMapping("/{page}/{limit}")
    public R pageContentConditions(
            @ApiParam(name = "page", value = "当前页", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "articleQueryVo", value = "查询对象", required = false)
            @RequestBody(required = false) ArticleQueryVo articleQueryVo) {
        System.out.println("文章查询对象："+articleQueryVo);
        Page<EduArticle> pageParam = new Page<>(page, limit);
        Map<String, Object> map = articleService.pageListWeb(pageParam, articleQueryVo);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据评论数获取文章排行榜")
    @GetMapping("/getHotArticle")
    public R getHotArticle() {
        List<EduArticle> hotArticle = articleService.getHotArticle();
        return R.ok().data("hotArticle", hotArticle);
    }

    @ApiOperation(value = "根据id获取文章")
    @GetMapping("/{id}")
    public R getById(
            @ApiParam(name = "articleId", value = "文章ID", required = true)
            @PathVariable String id) {
        EduArticle article1 = articleService.getById(id);
        EduArticle eduArticle = new EduArticle();
        eduArticle.setId(id).setContentView(article1.getContentView()+1);
        articleService.updateById(eduArticle);
        EduArticle article = articleService.getById(id);
        return R.ok().data("article", article);
    }

    @ApiOperation(value = "点赞")
    @GetMapping("/hitZan/{id}")
    public R hitZan(@PathVariable("id") String id) {
        EduArticle article = articleService.getById(id);
        if (article == null) {
            return R.error().message("该文章不存在，无法点赞");
        }
        EduArticle eduArticle = new EduArticle();
        eduArticle.setId(id).setContentHit(article.getContentHit()+1);
        boolean flag = articleService.updateById(eduArticle);
        if (flag) {
            return R.ok();
        } else {
            return R.error().message("点赞失败");
        }
    }

    @ApiOperation(value = "修改文章")
    @PutMapping
    public R edit(@RequestBody EduArticle article) {
        try {
            articleService.updateById(article);
            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
    }

    @ApiOperation(value = "新增文章")
    @PostMapping
    public R add(@RequestBody EduArticle article) {
        try {
            articleService.save(article);
            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
    }

    @ApiOperation(value = "删除文章信息")
    @DeleteMapping("/{id}")
    public R remove(@PathVariable("id") String id) {
        try {
            articleService.removeById(id);
            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
    }

    @ApiOperation(value = "根据id列表批量删除管理用户")
    @DeleteMapping("/batchRemove")
    public R batchRemove(@RequestBody List<String> idList) {
        try {
            articleService.removeByIds(idList);
            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
    }
}
