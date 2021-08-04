package com.atguigu.commonutils.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by corey on 2021/8/1
 **/

@Data
public class CourseInfoVo {

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "课程讲师名称")
    private String teacherName;

}
