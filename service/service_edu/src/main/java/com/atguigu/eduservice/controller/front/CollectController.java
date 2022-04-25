package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.model.entity.EduCollect;
import com.atguigu.eduservice.model.entity.EduCourse;
import com.atguigu.eduservice.service.EduCollectService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api("课程收藏")
@RestController
@RequestMapping("/edu/collect")
public class CollectController {

    @Autowired
    private EduCollectService collectService;

    @Autowired
    private EduCourseService courseService;

    // 获取收藏列表
    @GetMapping("/{memberId}")
    public R list(@PathVariable String memberId) {
        ArrayList<String> courseIds = new ArrayList<>();
        QueryWrapper<EduCollect> qw1 = new QueryWrapper<EduCollect>().eq("member_id", memberId);
        List<EduCollect> list = collectService.list(qw1);
        for (EduCollect ec : list) {
            courseIds.add(ec.getCourseId());
        }
        QueryWrapper<EduCourse> qw2 = new QueryWrapper<>();
        qw2.in("id", courseIds);
        List<EduCourse> collectList = courseService.list(qw2);
        return R.ok().data("collectList", collectList);
    }

    // 收藏
    @PostMapping
    public R collect(@RequestBody EduCollect eduCollect) {
        if (Strings.isNullOrEmpty(eduCollect.getCourseId())) {
            return R.error().message("课程id为空");
        }
        if (Strings.isNullOrEmpty(eduCollect.getMemberId())) {
            return R.error().message("用户id为空");
        }
        boolean save = collectService.save(eduCollect);
        if (save) {
            return R.ok();
        }
        return R.error();
    }

    // 取消收藏
    @DeleteMapping
    public R cancel(@RequestParam String memberId,
                    @RequestParam String courseId) {
        QueryWrapper<EduCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", memberId).eq("course_id", courseId);
        boolean remove = collectService.remove(queryWrapper);
        if (remove) {
            return R.ok();
        }

        return R.error();
    }
}
