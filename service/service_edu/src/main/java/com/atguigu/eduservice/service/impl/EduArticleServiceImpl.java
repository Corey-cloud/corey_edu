package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.mapper.EduArticleMapper;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.model.entity.EduArticle;
import com.atguigu.eduservice.model.entity.EduCourse;
import com.atguigu.eduservice.model.frontvo.ArticleQueryVo;
import com.atguigu.eduservice.service.EduArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by corey on 2022/3/31
 **/

@Service
public class EduArticleServiceImpl extends ServiceImpl<EduArticleMapper, EduArticle> implements EduArticleService {

    @Override
    public Map<String, Object> pageListWeb(Page<EduArticle> pageParam, ArticleQueryVo articleQueryVo) {
        QueryWrapper<EduArticle> queryWrapper = new QueryWrapper<>();
        if (articleQueryVo != null) {
            if (!StringUtils.isEmpty(articleQueryVo.getContentTitle())) {
                queryWrapper.like("content_title", articleQueryVo.getContentTitle());
            }
            if (!StringUtils.isEmpty(articleQueryVo.getContentType())) {
                queryWrapper.eq("content_type", articleQueryVo.getContentType());
            }
            if (!StringUtils.isEmpty((CharSequence) articleQueryVo.getStartDate()) && !StringUtils.isEmpty((CharSequence) articleQueryVo.getEndDate())) {
                queryWrapper.between("gmt_create", articleQueryVo.getStartDate(), articleQueryVo.getEndDate());
            }
        }
        queryWrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageParam, queryWrapper);

        List<EduArticle> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public List<EduArticle> getHotArticle() {
        QueryWrapper<EduArticle> qw = new QueryWrapper<>();
        qw.orderByDesc("content_comment", "gmt_create");
        qw.last("limit 8");
        return baseMapper.selectList(qw);
    }

}
