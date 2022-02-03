package com.atguigu.eduservice.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by corey on 2021/7/23
 **/

@Data
public class SubjectNestedVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程类别ID")
    private String id;

    @ApiModelProperty(value = "类别名称")
    private String title;


    private List<SubjectVo> children = new ArrayList<>();
}
