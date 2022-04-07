package com.atguigu.eduservice.service;

import com.atguigu.eduservice.model.entity.QaQt;
import com.atguigu.eduservice.model.frontvo.QaTypeVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Created by corey on 2022/3/31
 **/

public interface QaQtService extends IService<QaQt> {

    /**
     * 获取所有类型
     * @return
     */
    List<QaTypeVo> queryTypes();
}
