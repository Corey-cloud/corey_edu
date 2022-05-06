package com.atguigu.eduservice.controller.front;

import com.alibaba.fastjson.JSON;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.vo.UcenterMemberVo;
import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.eduservice.model.entity.EduArticleComment;
import com.atguigu.eduservice.model.entity.EduComment;
import com.atguigu.eduservice.model.entity.EduCourse;
import com.atguigu.eduservice.service.EduCommentService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.utils.HttpURLUtil;
import com.atguigu.eduservice.utils.IP;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by corey on 2021/7/31
 **/

@Api(description = "前台评论信息")
@RestController
@RequestMapping("/edu/comments")
//@CrossOrigin
public class CommentFrontController {

    @Autowired
    private EduCommentService commentService;

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private UcenterClient ucenterClient;

    //根据课程id查询评论列表
    @ApiOperation(value = "评论分页列表")
    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = true)
                    String courseId) {
        Page<EduComment> pageParam = new Page<>(page, limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId).orderByDesc("gmt_create");
        commentService.page(pageParam, wrapper);
        List<EduComment> commentList = pageParam.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return R.ok().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("auth/save")
    public R save(@RequestBody EduComment comment, HttpServletRequest request) throws Exception {
        boolean token = JwtUtils.checkToken(request);
        if (!token) {
            return R.error().code(28000).message("未登录评论");
        }
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        comment.setMemberId(memberId);
        UcenterMemberVo ucenterInfo = ucenterClient.getInfo(memberId);
        if (ucenterInfo == null) {
            return R.error().message("无法获取用户信息");
        }
        comment.setNickname(ucenterInfo.getNickname());
        comment.setAvatar(ucenterInfo.getAvatar());

        // 获取请求主机IP地址
        String ip = IP.getIpAddress(request);
        System.out.println("ip:" + ip);

        // IP归属地查询
        String url = "http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=";
        url += ip;
        String data = HttpURLUtil.doGet(url);

        Map map = JSON.parseObject(data);

        String province = (String) map.get("pro");
        String city = (String) map.get("city");

        comment.setComeFrom(province+city);

        commentService.save(comment);

        // 获取该课程下的评论数
        String courseId = comment.getCourseId();
        EduCourse eduCourse = courseService.getById(courseId);

        // 课程评论数更新
        EduCourse course = new EduCourse();
        course.setId(courseId);
        course.setCommentCount(eduCourse.getCommentCount()+1);
        courseService.updateById(course);
        return R.ok();
    }

    @PutMapping("/zan/{id}")
    public R zan(@PathVariable String id) {
        EduComment comment = commentService.getById(id);
        if (comment == null) {
            return R.error().message("该评论不存在");
        }
        EduComment comment1 = new EduComment();
        comment1.setId(id);
        comment1.setZanCount(comment.getZanCount()+1);
        commentService.updateById(comment1);
        return R.ok();
    }

}