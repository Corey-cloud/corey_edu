package com.atguigu.eduservice.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by corey on 2021/7/23
 **/
@Data
public class SubjectVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String title;
}
