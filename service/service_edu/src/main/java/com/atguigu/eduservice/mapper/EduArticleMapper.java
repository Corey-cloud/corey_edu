package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.model.entity.EduArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by corey on 2022/3/31
 **/

public interface EduArticleMapper extends BaseMapper<EduArticle> {

    /**
     * 更新文章的评论数
     * @param contentId
     * @param commentNum
     */
    void updateNumById(@Param("contentId") Integer contentId,@Param("commentNum") Integer commentNum);
}
