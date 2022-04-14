package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.mapper.EduArticleCommentMapper;
import com.atguigu.eduservice.mapper.EduArticleMapper;
import com.atguigu.eduservice.model.entity.EduArticle;
import com.atguigu.eduservice.model.entity.EduArticleComment;
import com.atguigu.eduservice.service.EduArticleCommentService;
import com.atguigu.eduservice.service.EduArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EduArticleCommentServiceImpl extends ServiceImpl<EduArticleCommentMapper, EduArticleComment> implements EduArticleCommentService {

}
