package com.atguigu.eduservice.model.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by corey on 2021/7/23
 **/

@Data
public class SubjectNestedVo {

    private String id;
    private String title;
    private List<SubjectVo> children = new ArrayList<>();
}
