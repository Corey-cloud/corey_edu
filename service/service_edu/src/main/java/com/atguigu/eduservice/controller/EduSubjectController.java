package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.SubjectNestedVo;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Api(description="课程分类管理")
//@CrossOrigin //跨域
@RestController
@RequestMapping("/eduservice/edu-subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    // 添加课程分类
    @ApiOperation(value = "Excel批量导入")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    // 嵌套数据列表
    @ApiOperation(value = "嵌套数据列表")
    @GetMapping
    public R nestedList() {
        List<SubjectNestedVo> subjectNestedVoList = eduSubjectService.nestedList();
        return R.ok().data("items", subjectNestedVoList);
    }

}

