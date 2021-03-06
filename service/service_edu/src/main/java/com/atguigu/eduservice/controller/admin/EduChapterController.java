package com.atguigu.eduservice.controller.admin;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.model.entity.EduChapter;
import com.atguigu.eduservice.model.vo.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Api(description="课程章节管理")
//@CrossOrigin //跨域
@RestController
@RequestMapping("/edu/admin/chapters")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation(value = "获取嵌套章节数据列表")
    @GetMapping("/nestedList/{courseId}")
    public R nestedListByCourseId(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return R.ok().data("chapterNestedList", chapterVoList);
    }

    @ApiOperation(value = "新增章节")
    @PostMapping
    public R save(
            @ApiParam(name = "chapterVo", value = "章节对象", required = true)
            @RequestBody EduChapter chapter){
        boolean save = chapterService.save(chapter);
        return  save ? R.ok() : R.error();
    }

    @ApiOperation(value = "根据ID查询章节")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){
        EduChapter chapter = chapterService.getById(id);
        return R.ok().data("item", chapter);
    }

    @ApiOperation(value = "根据ID修改章节")
    @PutMapping
    public R updateById(
            @ApiParam(name = "chapter", value = "章节对象", required = true)
            @RequestBody EduChapter chapter){
        boolean flag = chapterService.updateById(chapter);
        return flag ? R.ok() : R.error();
    }

    @ApiOperation(value = "根据ID删除章节")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){
        boolean flag = chapterService.removeChapterById(id);
        return flag ? R.ok() : R.error();
    }
}

