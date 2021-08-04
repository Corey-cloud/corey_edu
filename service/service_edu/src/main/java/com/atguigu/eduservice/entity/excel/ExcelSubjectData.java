package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * Created by corey on 2021/7/23
 **/

@Data
public class ExcelSubjectData {

    @ExcelProperty(index = 0)
    private String oneSubjectName;
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
