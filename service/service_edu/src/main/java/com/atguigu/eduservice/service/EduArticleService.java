package com.atguigu.eduservice.service;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.model.entity.EduArticle;
import com.atguigu.eduservice.model.frontvo.ArticleQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * Created by corey on 2022/3/31
 **/

public interface EduArticleService extends IService<EduArticle> {

    Map<String, Object> pageListWeb(Page<EduArticle> pageParam, ArticleQueryVo articleQueryVo);

    /**
     * 获取热门文章排行榜
     * @return
     */
    List<EduArticle> getHotArticle();


    /**
     * 更新评论数
     * @param contentId
     * @param commentNum
     */
    void editCommentNumById(Integer contentId,Integer commentNum);
}
