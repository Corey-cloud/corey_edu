package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.mapper.QaQtMapper;
import com.atguigu.eduservice.model.entity.QaQt;
import com.atguigu.eduservice.model.frontvo.QaTypeVo;
import com.atguigu.eduservice.service.QaQtService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by corey on 2022/4/4
 **/

@Service
public class QaQtServiceImpl extends ServiceImpl<QaQtMapper, QaQt> implements QaQtService {


    @Override
    public List<QaTypeVo> queryTypes() {
        List<QaQt> qaQts = baseMapper.selectList(new QueryWrapper<QaQt>().select("id", "type_name"));
        List<QaTypeVo> resList = new ArrayList<>();
        for (QaQt qaQt : qaQts) {
            QaTypeVo qaQtVo = new QaTypeVo();
            BeanUtils.copyProperties(qaQt, qaQtVo);
            resList.add(qaQtVo);
        }
        return resList;
    }
}
